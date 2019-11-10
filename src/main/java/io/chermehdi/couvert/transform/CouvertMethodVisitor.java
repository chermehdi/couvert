package io.chermehdi.couvert.transform;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.MethodNode;

/**
 * @author chermehdi
 */
public class CouvertMethodVisitor extends MethodNode {

  private final String name;
  private final MethodVisitor visitor;
  private final String sourceFile;
  private int version;

  public CouvertMethodVisitor(int api, int access, String name, String descriptor, String signature,
      String[] exceptions, MethodVisitor visitor, String sourceFile) {
    super(access, name, descriptor, signature, exceptions);
    this.version = api;
    this.name = name;
    this.visitor = visitor;
    this.sourceFile = sourceFile;
  }

  @Override
  public void visitEnd() {
    super.visitEnd();
    accept(new LineMethodVisitor(version, sourceFile, name, visitor));
  }
}
