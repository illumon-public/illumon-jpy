#!/usr/bin/env bash
# Bash3 Boilerplate. Copyright (c) 2014, kvz.io

set -o errexit
set -o pipefail
set -o nounset
# set -o xtrace

__dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
__root="$(cd "$(dirname "${__dir}")" && pwd)"

__libs_dir="${__root}/build/lib.macosx-10.6-x86_64-2.7"
__jpy_lib="${__libs_dir}/jpy.so"
__jdl_lib="${__libs_dir}/jdl.so"
__jpy_jar="${__libs_dir}/jpy-0.10.0-SNAPSHOT.jar"
__main_file="${__dir}/Main.java"

if [[ "$OSTYPE" == "darwin"* ]]; then
  # I *think* referencing the python executable directly only works on OS X?
  python_lib=`which python`
else
  python_lib="/usr/lib64/libpython2.7.so.1.0"
fi

pushd "${__root}"
python setup.py bdist_wheel
popd

javac -cp "${__jpy_jar}" "${__main_file}"
java -cp "${__dir}:${__jpy_jar}" \
  -Djpy.jpyLib="${__jpy_lib}" \
  -Djpy.jdlLib="${__jdl_lib}" \
  -Djpy.pythonLib="${python_lib}" \
  -Djpy.debug=true \
  Main
