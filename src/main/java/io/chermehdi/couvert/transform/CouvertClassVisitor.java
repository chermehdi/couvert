package io.chermehdi.couvert.transform;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author chermehdi
 */
public class CouvertClassVisitor extends ClassVisitor implements Opcodes {

  private String className;
  private String sourceFile;

  public CouvertClassVisitor(int api, ClassVisitor classVisitor) {
    super(api, classVisitor);
  }

  @Override
  public void visit(int version, int access, String name, String signature, String superName,
      String[] interfaces) {
    super.visit(version, access, name, signature, superName, interfaces);
    this.className = name;
  }

  @Override
  public void visitSource(String source, String debug) {
    super.visitSource(source, debug);
    this.sourceFile = className.substring(0, className.lastIndexOf("/") + 1) + source;
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
      String[] exceptions) {
    MethodVisitor methodVisitor = super
        .visitMethod(access, name, descriptor, signature, exceptions);
    if (sourceFile == null) {
      return methodVisitor;
    } else {
      return new CouvertMethodVisitor(api, access, name, descriptor, signature, exceptions,
          methodVisitor, sourceFile);
    }
  }


}
