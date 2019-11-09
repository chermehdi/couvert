package io.chermehdi.couvert.agent;

import io.chermehdi.couvert.transform.CouvertTransformer;
import java.lang.instrument.Instrumentation;

/**
 * @author chermehdi
 */
public class CouvertAgent {

  public static void premain(String options, Instrumentation instrumentation) {
    CouvertTransformer transformer = new CouvertTransformer();
    instrumentation.addTransformer(transformer, false);
  }
}
