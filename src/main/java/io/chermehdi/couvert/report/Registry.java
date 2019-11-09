package io.chermehdi.couvert.report;

/**
 * @author chermehdi
 */
public class Registry {

  public static Registry instance = new Registry();

  public void register(int lineNumber, String methodName, String classFile) {
    System.out.println(
        "Executed " + lineNumber + ", methodName: " + methodName + " at file: " + classFile);
  }
}
