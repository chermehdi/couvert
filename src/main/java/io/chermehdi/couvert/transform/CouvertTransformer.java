package io.chermehdi.couvert.transform;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

/**
 * @author chermehdi
 */
public class CouvertTransformer implements ClassFileTransformer {

  private static final String PACKAGE_NAME = "io/chermehdi/couvert";

  @Override
  public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
      ProtectionDomain protectionDomain, byte[] classfileBuffer) {
    if (shouldTransform(className, loader, classfileBuffer)) {
      // transform
      ClassReader classReader = new ClassReader(classfileBuffer);
      ClassWriter classWriter = new ClassWriter(classReader, 0);
      CouvertClassVisitor classVisitor = new CouvertClassVisitor(Opcodes.ASM7, classWriter);
      classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);
      return classWriter.toByteArray();
    }
    // indication to the JVM to not do the class rewrite.
    return null;
  }

  private boolean shouldTransform(String className, ClassLoader loader, byte[] classfileBuffer) {
    if (isClassBootstrapLoader(loader)) {
      return false;
    }

    if (isAgentClass(className)) {
      return false;
    }

    return true;
  }

  private boolean isAgentClass(String className) {
    return className.startsWith(PACKAGE_NAME);
  }

  private boolean isClassBootstrapLoader(ClassLoader loader) {
    return loader == null;
  }
}
