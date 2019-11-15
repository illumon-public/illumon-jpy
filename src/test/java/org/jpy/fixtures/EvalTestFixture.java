package org.jpy.fixtures;

import org.jpy.PyInputMode;
import org.jpy.PyLib;
import org.jpy.PyObject;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class EvalTestFixture {

    public static PyObject expression(String expression) {
        return PyObject.executeCode(expression,PyInputMode.EXPRESSION, PyLib.getCurrentGlobals(),PyLib.getCurrentLocals());
    }

    public static void statement(String expression) {
        PyObject.executeCode(expression,PyInputMode.STATEMENT, PyLib.getCurrentGlobals(),PyLib.getCurrentLocals());
    }

    public static void main(String[] args) throws IOException {
        String importPath = new File("src/test/python/fixtures").getCanonicalPath();

        PyLib.startPython(importPath);
        assertEquals(true, PyLib.isPythonRunning());
        System.out.println(PyLib.getCurrentGlobals());
        statement("x = 10");
        System.out.println(expression("2*x"));

    }
}
