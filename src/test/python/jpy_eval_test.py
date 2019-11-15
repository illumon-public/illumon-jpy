import unittest

import jpyutil

jpyutil.init_jvm(jvm_maxmem='512M', jvm_classpath=['target/test-classes','target/classes'])
import jpy
import numpy as np


class TestEval(unittest.TestCase):
    def setUp(self):
        self.Fixture = jpy.get_type('org.jpy.fixtures.EvalTestFixture')
        self.assertIsNotNone(self.Fixture)

    def test_foo_42(self):
        foo = 42
        self.assertEqual(self.Fixture.expression("foo"), 42)

    def test_foo_bar(self):
        foo = "bar"
        self.assertEqual(self.Fixture.expression("foo"), "bar")

    def test_x_add_y(self):
        x = 123
        y = 456
        self.assertEqual(self.Fixture.expression("x + y"), 579)


    def test_import(self):
        self.Fixture.statement("import numpy as numfoo")
        result = self.Fixture.expression("numfoo.array([1,2,3])")
        print(result)
        self.assertEqual(len(result), 3)


    def test_new_var(self):
        self.Fixture.statement("bar = 3")
        self.assertEqual(self.Fixture.expression("bar*2"), 6)


if __name__ == '__main__':
    print('\nRunning ' + __file__)
    unittest.main()

#jpy.get_type('org.jpy.PyObject').eval("foo + q",jpy.get_type('org.jpy.PyInputMode').EXPRESSION)