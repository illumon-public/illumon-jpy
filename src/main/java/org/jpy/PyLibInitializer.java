package org.jpy;

import java.util.Objects;

/**
 * This helper class is to safely and programmatically configure jpy
 */
public class PyLibInitializer {

  /**
   * Should be set to true during {@link PyLib}'s static initialization
   */
  static boolean pyLibInitialized = false;

  /**
   * Should be set to true during {@link DL}'s static initialization
   */
  static boolean dlInitialized = false;

  /**
   * This method should only be called once - it is dependent on {@link PyLib} and {@link DL} being
   * uninitialized. Any consumers who want to programmatically configure jpy should call this method
   * first.
   *
   * @param pyLib the python library
   * @param jpyLib the jpy library
   * @param jdlLib the jdl library
   */
  public static void initPyLib(String pyLib, String jpyLib, String jdlLib) {
    synchronized (PyLibInitializer.class) {
      System.setProperty(PyLibConfig.PYTHON_LIB_KEY, pyLib);
      System.setProperty(PyLibConfig.JPY_LIB_KEY, jpyLib);
      System.setProperty(PyLibConfig.JDL_LIB_KEY, jdlLib);

      ensurePyLibInit();

      // safety check to make sure that the PyLib initialization process didn't change any values
      ensurePropertySame(PyLibConfig.PYTHON_LIB_KEY, pyLib);
      ensurePropertySame(PyLibConfig.JPY_LIB_KEY, jpyLib);
      ensurePropertySame(PyLibConfig.JDL_LIB_KEY, jdlLib);
    }
  }

  private static void ensurePyLibInit() {
    if (pyLibInitialized) {
      throw new IllegalStateException("PyLib is already initialized");
    }
    if (dlInitialized) {
      throw new IllegalStateException("DL is already initialized");
    }

    // cleaner than Class.forName(...)
    PyLib.dummyMethodForInitialization();

    if (!pyLibInitialized) {
      throw new IllegalStateException(
          "PyLib should have been initialized. This should not happen.");
    }
    // DL is not always initialized (platform dependent), so don't need to check it
  }

  private static void ensurePropertySame(String propertyName, String propertyValue) {
    String currentValue = System.getProperty(propertyName);
    if (!Objects.equals(propertyValue, currentValue)) {
      throw new IllegalStateException(String.format(
          "PyLib initialization has changed the value of system property '%s': was '%s', is now '%s'",
          propertyName, propertyValue, currentValue));
    }
  }
}