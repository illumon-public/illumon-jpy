import org.jpy.*;

public class Main {
  public static void main(String[] args) {
    PyLib.Diag.setFlags(PyLib.Diag.F_ALL);
    PyLib.startPython();
    try {
      PyDictWrapper dict = PyObject.executeCode("{'a':'a', 'b':'b', 'c':'c'}", PyInputMode.EXPRESSION).asDict();
      dict.entrySet().forEach(e -> System.out.println(e.getKey() + "->" + e.getValue()));
      dict.entrySet().forEach(e -> System.out.println(e.getKey() + "->" + e.getValue()));
      dict.entrySet().forEach(e -> System.out.println(e.getKey() + "->" + e.getValue()));
    } finally {
      PyLib.stopPython();
    }
  }
}
