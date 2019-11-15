import unittest

import jpyutil

jpyutil.init_jvm(jvm_maxmem='512M', jvm_classpath=['target/classes', 'target/test-classes'])
import jpy

class TestEvalExec(unittest.TestCase):
    def setUp(self):
        self.PyObject = jpy.get_type("org.jpy.PyObject")
        self.PyLib = jpy.get_type("org.jpy.PyLib")
        self.assertIsNotNone(self.PyObject)

    def test_foo_42(self):
        foo = 42
        self.assertEqual(self.PyObject.doEval("foo"), 42)

    def test_foo_bar(self):
        foo = "bar"
        self.assertEqual(self.PyObject.doEval("foo"), "bar")

    def test_x_add_y(self):
        x = 123
        y = 456
        self.assertEqual(self.PyObject.doEval("x + y"), 579)

    def test_inc_baz(self):
        baz = 15
        self.PyObject.doExec("baz = baz + 1; self.assertEqual(baz, 16)")
        # note: this *is* correct wrt python semantics w/ exec(code, globals(), locals())
        # https://bugs.python.org/issue4831 (Note: it's *not* a bug, is working as intended)
        self.assertEqual(baz, 15)

    def test_exec_import(self):
        import sys
        self.assertTrue("base64" not in sys.modules)
        self.PyObject.doExec("import base64")
        self.assertTrue("base64" in sys.modules)

    def test_current_globals_is_valid(self):
        self.assertTrue(self.PyLib.getCurrentGlobals() is not None)

    def test_current_locals_is_valid(self):
        self.assertTrue(self.PyLib.getCurrentLocals() is not None)

if __name__ == '__main__':
    print('\nRunning ' + __file__)
    unittest.main()
