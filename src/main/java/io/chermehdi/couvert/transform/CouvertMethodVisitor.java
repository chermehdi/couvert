package io.chermehdi.couvert.transform;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.tree.MethodNode;

/**
 * @author chermehdi
 */
public class CouvertMethodVisitor extends MethodNode {

  private final int access;
  private final String name;
  private final String descriptor;
  private final String signature;
  private final List<String> exceptions;
  private final MethodVisitor visitor;
  private final String sourceFile;
  private int version;
  private int currentLineNumber = -1;

  public CouvertMethodVisitor(int api, int access, String name, String descriptor, String signature,
      String[] exceptions, MethodVisitor visitor, String sourceFile) {
    super(api);
    this.version = api;
    this.access = access;
    this.name = name;
    this.descriptor = descriptor;
    this.signature = signature;
    this.visitor = visitor;
    this.exceptions = exceptions == null ? Collections.emptyList() : Arrays.asList(exceptions);
    this.sourceFile = sourceFile;
  }

  @Override
  public void visitEnd() {
    super.visitEnd();
    accept(new LineMethodVisitor(version, sourceFile, name, visitor));
  }
}
