"""
Project module.
"""
from __future__ import annotations

import typing
from typing import Self

from sdk.entities.artifact.crud import (
    create_artifact_from_dict,
    delete_artifact,
    get_artifact,
    new_artifact,
)
from sdk.entities.base.entity import Entity
from sdk.entities.dataitem.crud import (
    create_dataitem_from_dict,
    delete_dataitem,
    get_dataitem,
    new_dataitem,
)
from sdk.entities.function.crud import (
    create_function_from_dict,
    delete_function,
    get_function,
    new_function,
)
from sdk.entities.project.metadata import build_metadata
from sdk.entities.project.spec import build_spec
from sdk.entities.utils.utils import get_uiid
from sdk.entities.workflow.crud import (
    create_workflow_from_dict,
    delete_workflow,
    get_workflow,
    new_workflow,
)
from sdk.utils.api import (
    DTO_ARTF,
    DTO_DTIT,
    DTO_FUNC,
    DTO_PROJ,
    DTO_WKFL,
    api_base_create,
)
from sdk.utils.exceptions import EntityError
from sdk.utils.factories import get_client, set_context

if typing.TYPE_CHECKING:
    from sdk.client.client import Client
    from sdk.entities.artifact.entity import Artifact
    from sdk.entities.dataitem.entity import Dataitem
    from sdk.entities.function.entity import Function
    from sdk.entities.project.metadata import ProjectMetadata
    from sdk.entities.project.spec import ProjectSpec
    from sdk.entities.workflow.entity import Workflow

DTO_LIST = [DTO_ARTF, DTO_FUNC, DTO_WKFL, DTO_DTIT]
SPEC_LIST = DTO_LIST + ["source", "context"]


class Project(Entity):
    """
    A class representing a project.
    """

    def __init__(
        self,
        name: str,
        metadata: ProjectMetadata = None,
        spec: ProjectSpec = None,
        local: bool = False,
        uuid: str = None,
        **kwargs,
    ) -> None:
        """
        Initialize the Project instance.

        Parameters
        ----------
        name : str
            Name of the project.
        metadata : ProjectMetadata
            Metadata of the object.
        spec : ProjectSpec
            Specification of the object.
        local: bool
            If True, run locally.
        **kwargs
            Keyword arguments.
        """
        super().__init__()
        self.name = name
        self.kind = "project"
        self.metadata = metadata if metadata is not None else build_metadata(name=name)
        self.spec = spec if spec is not None else build_spec(self.kind, **{})
        self.id = uuid if uuid is not None else get_uiid()

        # Client and local flag
        self._local = local
        self._client = get_client() if not self.local else None

        # Object attributes
        self._artifacts = []
        self._functions = []
        self._workflows = []
        self._dataitems = []

        # Set new attributes
        self._any_setter(**kwargs)

        set_context(self)

    #############################
    #  Save / Export
    #############################

    def save(self, uuid: bool = None) -> dict:
        """
        Save project and context into backend.

        Parameters
        ----------
        uuid : bool
            Ignored, placed for compatibility with other objects.

        Returns
        -------
        dict
            Mapping representation of Project from backend.
        """
        responses = []
        if self.local:
            raise EntityError("Use .export() for local execution.")

        obj = self.to_dict()

        # Try to create project
        # (try to avoid error response if project already exists)
        try:
            api = api_base_create(DTO_PROJ)
            response = self.client.create_object(obj, api)
            responses.append(response)
        except Exception:
            ...

        # Try to save objects related to project
        # (try to avoid error response if object does not exists)
        for i in DTO_LIST:
            for j in self._get_objects(i):
                try:
                    obj = j.save(uuid=j.id)
                    responses.append(obj)
                except Exception:
                    ...

        return responses

    def export(self, filename: str = None) -> None:
        """
        Export object as a YAML file. If the objects are not embedded, the objects are
        exported as a YAML file.

        Parameters
        ----------
        filename : str
            Name of the export YAML file. If not specified, the default value is used.

        Returns
        -------
        None
        """
        obj = self.to_dict()
        filename = filename if filename is not None else "project.yaml"
        self._export_object(filename, obj)

        # Export objects related to project if not embedded
        for i in DTO_LIST:
            for obj in self._get_objects(i):
                if not obj.embedded:
                    obj.export()

    #############################
    #  Generic operations for objects (artifacts, functions, workflows, dataitems)
    #############################

    def _add_object(self, obj: Entity, kind: str) -> None:
        """
        Add object to project as class object and spec.

        Parameters
        ----------
        obj : Entity
            Object to be added to project.
        kind : str
            Kind of object to be added to project.

        Returns
        -------
        None
        """
        # Add to project spec
        obj_dict = obj.to_dict_essential() if not obj.embedded else obj.to_dict()
        attr = getattr(self.spec, kind, []) + [obj_dict]
        setattr(self.spec, kind, attr)

        # Add to project objects
        if kind in DTO_LIST:
            kind = f"_{kind}"
        attr = getattr(self, kind, []) + [obj]
        setattr(self, kind, attr)

    def _delete_object(self, name: str, kind: str, uuid: str = None) -> None:
        """
        Delete object from project.

        Parameters
        ----------
        name : str
            Name of object to be deleted.
        kind : str
            Kind of object to be deleted.
        uuid : str
            UUID.

        Returns
        -------
        None
        """
        if uuid is None:
            attr_name = "name"
            var = name
        else:
            attr_name = "id"
            var = uuid

        # Delete from project spec
        spec_list = getattr(self.spec, kind, [])
        setattr(self.spec, kind, [i for i in spec_list if i.get(attr_name) != var])

        # Delete from project objects
        if kind in DTO_LIST:
            kind = f"_{kind}"
        obj_list = getattr(self, kind, [])
        setattr(self, kind, [i for i in obj_list if getattr(i, attr_name) != var])

    def _get_objects(self, kind: str) -> object:
        """
        Get objects related to project.

        Parameters
        ----------
        kind : str
            Kind of object to be retrieved.

        Returns
        -------
        object
            Object related to project.
        """
        if kind in DTO_LIST:
            kind = f"_{kind}"
        return getattr(self, kind, [])

    #############################
    #  Artifacts
    #############################

    def new_artifact(
        self,
        name: str,
        description: str = "",
        kind: str = "artifact",
        key: str = None,
        src_path: str = None,
        target_path: str = None,
        local: bool = False,
        embedded: bool = False,
        uuid: str = None,
    ) -> Artifact:
        """
        Create an instance of the Artifact class with the provided parameters.

        Parameters
        ----------
        name : str
            Identifier of the artifact.
        description : str
            Description of the artifact.
        kind : str
            The type of the artifact.
        key : str
            Representation of artfact like store://etc..
        src_path : str
            Path to the artifact on local file system or remote storage.
        target_path : str
            Path of destionation for the artifact.
        local : bool
            Flag to determine if object has local execution.
        embedded : bool
            Flag to determine if object must be embedded in project.
        uuid : str
            UUID.

        Returns
        -------
        Artifact
           Object instance.
        """
        obj = new_artifact(
            project=self.name,
            name=name,
            description=description,
            kind=kind,
            key=key,
            src_path=src_path,
            target_path=target_path,
            local=local,
            embedded=embedded,
            uuid=uuid,
        )
        self._add_object(obj, DTO_ARTF)
        return obj

    def get_artifact(self, name: str, uuid: str = None) -> Artifact:
        """
        Get a Artifact from backend.

        Parameters
        ----------
        name : str
            Identifier of the artifact.
        uuid : str
            Identifier of the artifact version.

        Returns
        -------
        Artifact
            Instance of Artifact class.
        """
        obj = get_artifact(
            project=self.name,
            name=name,
            uuid=uuid,
        )
        self._add_object(obj, DTO_ARTF)
        return obj

    def delete_artifact(self, name: str, uuid: str = None) -> None:
        """
        Delete a Artifact from project.

        Parameters
        ----------
        name : str
            Identifier of the artifact.
        uuid : str
            Identifier of the artifact version.

        Returns
        -------
        None
        """
        if not self.local:
            delete_artifact(self.name, name)
        self._delete_object(name, DTO_ARTF, uuid=uuid)

    def set_artifact(self, artifact: Artifact) -> None:
        """
        Set a Artifact.

        Parameters
        ----------
        artifact : Artifact
            Artifact to set.

        Returns
        -------
        None
        """
        self._add_object(artifact, DTO_ARTF)

    #############################
    #  Functions
    #############################

    def new_function(
        self,
        name: str,
        description: str = "",
        kind: str = "job",
        source: str = "",
        image: str = None,
        tag: str = None,
        handler: str = None,
        local: bool = False,
        embedded: bool = False,
        uuid: str = None,
    ) -> Function:
        """
        Create a Function instance with the given parameters.

        Parameters
        ----------
        project : str
            Name of the project.
        name : str
            Identifier of the Function.
        description : str
            Description of the Function.
        kind : str
            The type of the Function.
        source : str
            Path to the Function's source code on the local file system or remote storage.
        image : str
            Name of the Function's Docker image.
        tag : str
            Tag of the Function's Docker image.
        handler : str
            Function handler name.
        local : bool
            Flag to determine if object has local execution.
        embedded : bool
            Flag to determine if object must be embedded in project.
        uuid : str
            UUID.

        Returns
        -------
        Function
           Object instance.
        """
        obj = new_function(
            project=self.name,
            name=name,
            description=description,
            kind=kind,
            source=source,
            image=image,
            tag=tag,
            handler=handler,
            local=local,
            embedded=embedded,
            uuid=uuid,
        )
        self._add_object(obj, DTO_FUNC)
        return obj

    def get_function(self, name: str, uuid: str = None) -> Function:
        """
        Get a Function from backend.

        Parameters
        ----------
        name : str
            Identifier of the function.
        uuid : str
            Identifier of the function version.

        Returns
        -------
        Function
            Instance of Function class.
        """
        obj = get_function(
            project=self.name,
            name=name,
            uuid=uuid,
        )
        self._add_object(obj, DTO_FUNC)
        return obj

    def delete_function(self, name: str, uuid: str = None) -> None:
        """
        Delete a Function from project.

        Parameters
        ----------
        name : str
            Identifier of the function.
        uuid : str
            Identifier of the function version.

        Returns
        -------
        None
        """
        if not self.local:
            delete_function(self.name, name)
        self._delete_object(name, DTO_FUNC, uuid=uuid)

    def set_function(self, function: Function) -> None:
        """
        Set a Function.

        Parameters
        ----------
        function : Function
            Function to set.

        Returns
        -------
        None
        """
        self._add_object(function, DTO_FUNC)

    #############################
    #  Workflows
    #############################

    def new_workflow(
        self,
        name: str,
        description: str = "",
        kind: str = "job",
        test: str = None,
        local: bool = False,
        embedded: bool = False,
        uuid: str = None,
    ) -> Workflow:
        """
        Create a new Workflow instance with the specified parameters.

        Parameters
        ----------
        project : str
            A string representing the project associated with this workflow.
        name : str
            The name of the workflow.
        description : str
            A description of the workflow.
        kind : str
            The kind of the workflow.
        spec_ : dict
            Specification of the object.
        local : bool
            Flag to determine if object has local execution.
        embedded : bool
            Flag to determine if object must be embedded in project.
        uuid : str
            UUID.

        Returns
        -------
        Workflow
            An instance of the created workflow.
        """
        obj = new_workflow(
            project=self.name,
            name=name,
            description=description,
            kind=kind,
            test=test,
            local=local,
            embedded=embedded,
            uuid=uuid,
        )
        self._add_object(obj, DTO_WKFL)
        return obj

    def get_workflow(self, name: str, uuid: str = None) -> Workflow:
        """
        Get a Workflow from backend.

        Parameters
        ----------
        name : str
            Identifier of the workflow.
        uuid : str
            Identifier of the workflow version.

        Returns
        -------
        Workflow
            Instance of Workflow class.
        """
        obj = get_workflow(
            project=self.name,
            name=name,
            uuid=uuid,
        )
        self._add_object(obj, DTO_WKFL)
        return obj

    def delete_workflow(self, name: str, uuid: str = None) -> None:
        """
        Delete a Workflow from project.

        Parameters
        ----------
        name : str
            Identifier of the workflow.
        uuid : str
            Identifier of the workflow version.

        Returns
        -------
        None
        """
        if not self.local:
            delete_workflow(self.name, name)
        self._delete_object(name, DTO_WKFL, uuid=uuid)

    def set_workflow(self, workflow: Workflow) -> None:
        """
        Set a Workflow.

        Parameters
        ----------
        workflow : Workflow
            Workflow to set.

        Returns
        -------
        None
        """
        self._add_object(workflow, DTO_WKFL)

    #############################
    #  Dataitems
    #############################

    def new_dataitem(
        self,
        name: str,
        description: str = "",
        kind: str = "dataitem",
        key: str = None,
        path: str = None,
        local: bool = False,
        embedded: bool = False,
        uuid: str = None,
    ) -> Dataitem:
        """
        Create a Dataitem.

        Parameters
        ----------
        name : str
            Identifier of the dataitem.
        description : str
            Description of the dataitem.
        kind : str
            The type of the dataitem.
        key : str
            Representation of artfact like store://etc..
        path : str
            Path to the dataitem on local file system or remote storage.
        local : bool
            Flag to determine if object has local execution.
        embedded : bool
            Flag to determine if object must be embedded in project.
        uuid : str
            UUID.

        Returns
        -------
        Dataitem
           Object instance.
        """
        obj = new_dataitem(
            project=self.name,
            name=name,
            description=description,
            kind=kind,
            key=key,
            path=path,
            local=local,
            embedded=embedded,
            uuid=uuid,
        )
        self._add_object(obj, DTO_DTIT)
        return obj

    def get_dataitem(self, name: str, uuid: str = None) -> Dataitem:
        """
        Get a Dataitem from backend.

        Parameters
        ----------
        name : str
            Identifier of the dataitem.
        uuid : str
            Identifier of the dataitem version.

        Returns
        -------
        Dataitem
            Instance of Dataitem class.
        """
        obj = get_dataitem(
            project=self.name,
            name=name,
            uuid=uuid,
        )
        self._add_object(obj, DTO_DTIT)
        return obj

    def delete_dataitem(self, name: str, uuid: str = None) -> None:
        """
        Delete a Dataitem from project.

        Parameters
        ----------
        name : str
            Identifier of the dataitem.
        uuid : str
            Identifier of the dataitem version.

        Returns
        -------
        None
        """
        if not self.local:
            delete_dataitem(self.name, name)
        self._delete_object(name, DTO_DTIT, uuid=uuid)

    def set_dataitem(self, dataitem: Dataitem) -> None:
        """
        Set a Dataitem.

        Parameters
        ----------
        dataitem : Dataitem
            Dataitem to set.

        Returns
        -------
        None
        """
        self._add_object(dataitem, DTO_DTIT)

    #############################
    #  Getters and Setters
    #############################

    @property
    def client(self) -> Client:
        """
        Get client.
        """
        if self._client is None and not self.local:
            raise EntityError("Client is not specified.")
        return self._client

    @property
    def local(self) -> bool:
        """
        Get local flag.
        """
        return self._local

    #############################
    #  Generic Methods
    #############################

    @classmethod
    def from_dict(cls, obj: dict) -> Self:
        """
        Create object instance from a dictionary.

        Parameters
        ----------
        obj : dict
            Dictionary to create object from.

        Returns
        -------
        Self
            Self instance.
        """
        parsed_dict = cls._parse_dict(obj)
        obj_ = cls(**parsed_dict)

        # Add objects to project from spec
        for i in obj.get("spec", {}).get(DTO_FUNC, []):
            obj_._add_object(create_function_from_dict(i), DTO_FUNC)
        for i in obj.get("spec", {}).get(DTO_ARTF, []):
            obj_._add_object(create_artifact_from_dict(i), DTO_ARTF)
        for i in obj.get("spec", {}).get(DTO_WKFL, []):
            obj_._add_object(create_workflow_from_dict(i), DTO_WKFL)
        for i in obj.get("spec", {}).get(DTO_DTIT, []):
            obj_._add_object(create_dataitem_from_dict(i), DTO_DTIT)

        return obj_

    @staticmethod
    def _parse_dict(obj: dict) -> dict:
        """
        Parse a dictionary and return a parsed dictionary.

        Parameters
        ----------
        obj : dict
            Dictionary to parse.

        Returns
        -------
        dict
            Parsed dictionary.
        """

        # Mandatory fields
        name = obj.get("name")
        if name is None:
            raise EntityError("Name is not specified.")

        # Optional fields
        uuid = obj.get("id")
        kind = obj.get("kind", "project")

        # Build metadata and spec
        spec_ = obj.get("spec", {})
        spec = {k: v for k, v in spec_.items() if k in SPEC_LIST}
        spec = build_spec(kind=kind, **spec)
        metadata = build_metadata(**obj.get("metadata", {"name": name}))

        return {
            "name": name,
            "kind": kind,
            "uuid": uuid,
            "metadata": metadata,
            "spec": spec,
        }


def project_from_parameters(
    name: str,
    description: str = "",
    kind: str = "project",
    context: str = "",
    source: str = "",
    local: bool = False,
    uuid: str = None,
) -> Project:
    """
    Create project.

    Parameters
    ----------
    name : str
        Identifier of the project.
    description : str
        Description of the project.
    kind : str
        The type of the project.
    context : str
        The context of the project.
    source : str
        The source of the project.
    local : bool
        Flag to determine if object has local execution.
    uuid : str
        UUID.

    Returns
    -------
    Project
        Project object.
    """
    meta = build_metadata(name=name, description=description)
    spec = build_spec(kind, context=context, source=source)
    return Project(
        name=name,
        kind=kind,
        metadata=meta,
        spec=spec,
        local=local,
        uuid=uuid,
    )


def project_from_dict(obj: dict) -> Project:
    """
    Create project from dictionary.

    Parameters
    ----------
    obj : dict
        Dictionary to create project from.

    Returns
    -------
    Project
        Project object.
    """
    return Project.from_dict(obj)
