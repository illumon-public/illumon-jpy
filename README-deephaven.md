Deephaven jpy
=============

This package is fork of jpy https://github.com/bcdev/jpy.git

- This fork of "jpy" adds custom extensions for the "deephaven" Python package.

Installation
------------

- Note that "deephaven-jpy" is not compatible with "jpy" as they both install shared libraries with the same name.
- If you already have "jpy" installed, it should be removed before installing "deephaven-jpy"

```

    # Remove jpy if it is currently installed
    pip uninstall jpy

    # Install deephaven-jpy
    pip install deephaven-jpy

```

Usage
-----

    >>> import deephaven
    >>> ...
