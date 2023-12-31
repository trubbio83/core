"""
Context module.
"""
from __future__ import annotations

import typing

if typing.TYPE_CHECKING:
    from sdk.entities.project.entity import Project


class Context:
    """
    The context for a project. It contains the project name, the client and the local store.
    It is a simplified version of the project class, used to avoid circular dependencies.
    The context is created by the context builder.
    """

    def __init__(self, project: Project) -> None:
        """
        Initialize the context.

        Parameters
        ----------

        project : Project
            The project to create the context for.

        Returns
        -------
        None
        """
        self.name = project.name
        self.client = project.client
        self.local = project.local

    def create_object(self, obj: dict, api: str) -> dict:
        """
        Create an object.

        Parameters
        ----------
        obj : dict
            The object to create.
        api : str
            The api to create the object with.

        Returns
        -------
        dict
            The created object.
        """
        return self.client.create_object(obj, api)

    def read_object(self, api: str) -> dict:
        """
        Get an object.

        Parameters
        ----------
        api : str
            The api to get the object with.

        Returns
        -------
        dict
            The object.
        """
        return self.client.read_object(api)

    def update_object(self, obj: dict, api: str) -> dict:
        """
        Update an object.

        Parameters
        ----------
        obj : dict
            The object to update.
        api : str
            The api to update the object with.

        Returns
        -------
        dict
            The updated object.
        """
        return self.client.update_object(obj, api)

    def delete_object(self, api: str) -> None:
        """
        Delete an object.

        Parameters
        ----------
        api : str
            The api to delete the object with.

        Returns
        -------
        None
        """
        self.client.delete_object(api)
