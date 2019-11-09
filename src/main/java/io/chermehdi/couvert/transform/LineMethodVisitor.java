package io.chermehdi.couvert.transform;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypePath;

/**
 * @author chermehdi
 */
public class LineMethodVisitor extends MethodVisitor {

  private int currentLineNumber = -1;
  private String sourceFile;
  private String methodName;

  public LineMethodVisitor(int version, String sourceFile, String methodName,
      MethodVisitor visitor) {
    super(version, visitor);
    this.sourceFile = sourceFile;
    this.methodName = methodName;
  }

  @Override
  public void visitLineNumber(int line, Label start) {
    super.visitLineNumber(line, start);
    System.out.println("setting line number => " + line);
    this.currentLineNumber = line;
  }

  private void checkLineNumber() {
    if (currentLineNumber != -1) {
      int ln = currentLineNumber;
      currentLineNumber = -1;
      reportLineNumber(ln);
    }
  }

  private void reportLineNumber(int lineNumber) {
    System.out.println("reporting line number " + lineNumber);
    // get the "instance" field
    mv.visitFieldInsn(Opcodes.GETSTATIC, "io/chermehdi/couvert/report/Registry", "instance",
        "Lio/chermehdi/couvert/report/Registry;");

    mv.visitLdcInsn(lineNumber);
    mv.visitLdcInsn(methodName);
    mv.visitLdcInsn(sourceFile);

    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "io/chermehdi/couvert/report/Registry", "register",
        "(ILjava/lang/String;Ljava/lang/String;)V", false);
  }

  @Override
  public void visitIntInsn(int opcode, int operand) {
    checkLineNumber();
    super.visitIntInsn(opcode, operand);
  }

  @Override
  public void visitVarInsn(int opcode, int var) {
    checkLineNumber();
    super.visitVarInsn(opcode, var);
  }

  @Override
  public void visitTypeInsn(int opcode, String type) {
    if (opcode == Opcodes.NEW) {
      super.visitTypeInsn(opcode, type);
      checkLineNumber();
    } else {
      checkLineNumber();
      super.visitTypeInsn(opcode, type);
    }
  }

  @Override
  public void visitFieldInsn(int opcode, String owner, String name, String desc) {
    checkLineNumber();
    super.visitFieldInsn(opcode, owner, name, desc);
  }

  @Override
  public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
    checkLineNumber();
    super.visitMethodInsn(opcode, owner, name, desc, itf);
  }

  @Override
  public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
    checkLineNumber();
    super.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
  }

  @Override
  public void visitJumpInsn(int opcode, Label label) {
    checkLineNumber();
    super.visitJumpInsn(opcode, label);
  }

  @Override
  public void visitLdcInsn(Object cst) {
    checkLineNumber();
    super.visitLdcInsn(cst);
  }

  @Override
  public void visitIincInsn(int var, int increment) {
    checkLineNumber();
    super.visitIincInsn(var, increment);
  }

  @Override
  public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
    checkLineNumber();
    super.visitTableSwitchInsn(min, max, dflt, labels);
  }

  @Override
  public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
    checkLineNumber();
    super.visitLookupSwitchInsn(dflt, keys, labels);
  }

  @Override
  public void visitMultiANewArrayInsn(String desc, int dims) {
    checkLineNumber();
    super.visitMultiANewArrayInsn(desc, dims);
  }

  @Override
  public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String desc,
      boolean visible) {
    checkLineNumber();
    return super.visitInsnAnnotation(typeRef, typePath, desc, visible);
  }

  @Override
  public void visitMaxs(int maxStack, int maxLocals) {
    // we need to set the maxStack to +3 because we are at most
    // adding 3 bytecode instructions
    mv.visitMaxs(maxStack + 3, maxLocals);
  }
}
