"""
General utilities module.
"""
import base64
from uuid import uuid4

from sdk.utils.exceptions import EntityError
from sdk.utils.factories import get_context
from sdk.utils.io_utils import read_text


def get_uiid() -> str:
    """
    Create a uuid.

    Returns
    -------
    str
        The uuid.
    """
    return uuid4().hex


def encode_source(path: str) -> str:
    """
    Read a file and encode in base64 the content.

    Parameters
    ----------
    path : str
        The file path to read.

    Returns
    -------
    str
        The file content encoded in base64.
    """
    data = read_text(path)
    return base64.b64encode(data.encode()).decode()


def check_local_flag(project: str, local: bool) -> None:
    """
    Checks if the local flag of the context matches the local flag of the object.

    Parameters
    ----------
    project : str
        Name of the project.
    local : bool
        Flag to determine if object has local execution.

    Raises
    ------
    EntityError
        If the local flag of the context does not match the local flag of the object.
    """
    if get_context(project).local != local:
        raise EntityError("Context local flag does not match local flag of object")


def save_or_export(obj: object, local: bool) -> None:
    """
    Save or export the object based on local flag.

    Parameters
    ----------
    obj : object
        The object to save or export.
    local : bool
        Flag to determine if object has local execution.

    Returns
    -------
    None
    """
    if local:
        obj.export()
    else:
        obj.save()
