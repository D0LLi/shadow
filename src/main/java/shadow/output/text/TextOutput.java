package shadow.output.text;

import shadow.ShadowException;
import shadow.output.AbstractOutput;
import shadow.output.Cleanup;
import shadow.tac.TACAbstractVisitor;
import shadow.tac.TACMethod;
import shadow.tac.TACMethod.TACFinallyFunction;
import shadow.tac.TACModule;
import shadow.tac.TACVariable;
import shadow.tac.nodes.*;
import shadow.typecheck.type.*;

import java.io.Writer;
import java.util.List;
import java.util.Map;

public class TextOutput extends AbstractOutput {
  private final Inline inline = new Inline();
  private TACLabel block = null;

  public TextOutput(Writer out) throws ShadowException {
    super(out);
  }

  @Override
  public void walk(TACNode nodes) throws ShadowException {
    try {
      super.walk(nodes);
    } finally {
      Cleanup.getInstance().walk(nodes);
    }
  }

  private void startBlock(TACLabel label) throws ShadowException {
    if (block != null) writer.writeLeft("// Missing terminator!!!");
    block = label;
  }

  private void continueBlock(boolean terminate) throws ShadowException {
    if (block == null) writer.writeLeft("// Missing label!!!");
    if (terminate) block = null;
  }

  @Override
  public void startFile(TACModule module) throws ShadowException {
    Type type = module.getType();
    StringBuilder sb =
        new StringBuilder(type.getModifiers().toString())
            .append("class ")
            .append(type.toString(Type.PACKAGES | Type.TYPE_PARAMETERS | Type.PARAMETER_BOUNDS));
    if (type instanceof ClassType) {
      ClassType classType = module.getClassType();
      if (classType.getExtendType() != null)
        sb.append(" extends ")
            .append(classType.getExtendType().toString(Type.PACKAGES | Type.TYPE_PARAMETERS));
      List<InterfaceType> interfaceTypes = classType.getInterfaces();
      if (!interfaceTypes.isEmpty()) {
        sb.append(" implements ");
        for (InterfaceType interfaceType : interfaceTypes)
          sb.append(interfaceType.toString(Type.PACKAGES | Type.TYPE_PARAMETERS)).append(", ");
        sb.delete(sb.length() - 2, sb.length());
      }
    }
    writer.write(sb.append(" {").toString());
    writer.indent();
  }

  @Override
  public void endFile(TACModule module) throws ShadowException {
    writer.outdent();
    writer.write('}');
  }

  @Override
  public void startMethod(TACMethod method, TACModule module) throws ShadowException {
    MethodSignature signature = method.getSignature();
    StringBuilder sb =
        new StringBuilder(signature.getModifiers().toString())
            .append(signature.getSymbol())
            .append('(');
    for (TACVariable param : method.getParameters())
      sb.append(param.getType().toString(Type.PACKAGES | Type.TYPE_PARAMETERS))
          .append(' ')
          .append(param.getName())
          .append(", ");
    if (!method.getParameters().isEmpty()) sb.delete(sb.length() - 2, sb.length());
    sb.append(") => (");
    for (ModifiedType retType : signature.getFullReturnTypes())
      sb.append(retType.getType().toString(Type.PACKAGES | Type.TYPE_PARAMETERS));
    sb.append(')');
    if (signature.isImport()) writer.write(sb.append(';').toString());
    else {
      writer.write(sb.append(" {").toString());
      writer.indent();
      for (TACVariable param : method.getLocals())
        if (!method.getParameters().contains(param))
          writer.write(
              param.getType().toString(Type.PACKAGES | Type.TYPE_PARAMETERS)
                  + ' '
                  + param.getName()
                  + ';');
    }
  }

  @Override
  public void endMethod(TACMethod method, TACModule module) throws ShadowException {
    MethodSignature signature = method.getSignature();

    // startBlock(defaultBlock);
    if (!signature.isImport()) {
      writer.outdent();
      writer.write('}');
    }

    for (TACFinallyFunction function : method.getFinallyFunctions()) {
      block = null;
      writer.write();
      String header =
          "void _finally" + function.getNumber() + signature.getMangledName() + "() => () {";
      writer.write(header);
      writer.indent();

      walk(function.getNode());

      writer.outdent();
      writer.write('}');
    }
  }

  @Override
  public void visit(TACLabel node) throws ShadowException {
    startBlock(node);
    writer.writeLeft(symbol(node) + ':');
  }

  protected String symbol(TACLabel label) {
    return label.toString();
  }

  @Override
  public void visit(TACPhi node) throws ShadowException {
    Map<TACLabel, TACOperand> values = node.getPreviousStores();
    if (values.size() > 1) {
      StringBuilder sb = new StringBuilder(node.getVariable().toString()).append(" = phi ");
      for (Map.Entry<TACLabel, TACOperand> entry : values.entrySet()) {
        inline.visit(sb.append("[ "), entry.getValue());
        sb.append(", ").append(entry.getKey()).append(" ],");
      }
      writer.write(sb.deleteCharAt(sb.length() - 1).toString());
    } else if (values.size() == 1)
      writer.write(
          inline
              .visit(
                  new StringBuilder(node.getVariable().toString()).append(" = "),
                  values.values().iterator().next())
              .append(';')
              .toString());
    else writer.write("//No nodes stored in phi for " + node.getVariable().toString());
  }

  @Override
  public void visit(TACLocalStore node) throws ShadowException {
    continueBlock(false);
    writer.write(
        inline
            .visit(new StringBuilder(node.getVariable().toString()).append(" = "), node.getValue())
            .append(';')
            .toString());
  }

  @Override
  public void visit(TACStore node) throws ShadowException {
    continueBlock(false);
    TACReference reference = node.getReference();
    StringBuilder sb = new StringBuilder();

    visitReference(sb, reference);
    writer.write(inline.visit(sb.append(" = "), node.getValue()).append(';').toString());
  }

  @Override
  public void visit(TACChangeReferenceCount node) throws ShadowException {
    continueBlock(false);

    StringBuilder sb = new StringBuilder(node.isIncrement() ? "_IncrementRef(" : "_DecrementRef(");

    if (node.isField()) {
      TACFieldRef field = node.getField();
      visitReference(sb, field);
    } else sb.append(node.getVariable().toString());

    writer.write(sb.append(");").toString());
  }

  private void visitReference(StringBuilder sb, TACReference reference) throws ShadowException {
    if (reference instanceof TACArrayRef) {
      TACArrayRef arrayRef = (TACArrayRef) reference;
      inline.visit(inline.visit(sb, arrayRef.getArray()).append('['), arrayRef.getIndex());
      sb.append(']');
    } else if (reference instanceof TACFieldRef) {
      TACFieldRef fieldRef = (TACFieldRef) reference;
      inline.visit(sb, fieldRef.getPrefix()).append(':').append(fieldRef.getName());
    } else if (reference instanceof TACSingletonRef) {
      TACSingletonRef singleton = (TACSingletonRef) reference;
      sb.append(singleton.getType().toString());
    }
  }

  @Override
  public void visit(TACBranch node) throws ShadowException {
    continueBlock(true);
    StringBuilder sb = new StringBuilder("goto ");
    if (node.isConditional())
      inline
          .visit(sb, node.getCondition())
          .append(" ? ")
          .append(symbol(node.getTrueLabel()))
          .append(", ")
          .append(symbol(node.getFalseLabel()));
    else if (node.isIndirect()) {
      TACPhi phi = node.getPhi();
      sb.append("phi ");
      for (TACOperand operand : phi.getPreviousStores().values()) {
        TACLabelAddress address = (TACLabelAddress) operand;
        sb.append("label ").append(symbol(address.getLabel())).append(", ");
      }
      sb.deleteCharAt(sb.length() - 1);
    } else if (node.isDirect()) sb.append(symbol(node.getLabel()));
    writer.write(sb.append(';').toString());
  }

  @Override
  public void visit(TACCallFinallyFunction node) throws ShadowException {
    continueBlock(false);
    TACFinallyFunction function = node.getFinallyFunction();
    writer.write(
        "_finally" + function.getNumber() + function.getSignature().getMangledName() + "()");
  }

  @Override
  public void visit(TACCall node) throws ShadowException {
    continueBlock(node.getBlock().getUnwind() != null); // if there are catches or cleanups
    if (!node.hasLocalStore() && !node.hasMemoryStore())
      writer.write(inline.visit(new StringBuilder(), node).append(';').toString());
  }

  @Override
  public void visit(TACReturn node) throws ShadowException {
    continueBlock(true);
    StringBuilder sb = new StringBuilder("return");
    if (node.hasReturnValue()) inline.visit(sb.append(' '), node.getReturnValue());
    writer.write(sb.append(';').toString());
  }

  @Override
  public void visit(TACThrow node) throws ShadowException {
    continueBlock(true);
    writer.write(
        inline.visit(new StringBuilder("throw "), node.getException()).append(';').toString());
  }

  @Override
  public void visit(TACCatchPad node) throws ShadowException {
    continueBlock(false);
    writer.write("catch( " + node.getType().toString(Type.PACKAGES | Type.TYPE_PARAMETERS) + " )");
  }

  @Override
  public void visit(TACCatchRet node) throws ShadowException {
    continueBlock(true);
    writer.write("catchret to " + symbol(node.getLabel()));
  }

  @Override
  public void visit(TACLandingPad node) throws ShadowException {
    continueBlock(false);
    writer.write("landing pad");
  }

  @Override
  public void visit(TACCatch node) throws ShadowException {
    continueBlock(false);
    writer.write("catch(" + node.getType() + ")");
  }

  @Override
  public void visit(TACResume node) throws ShadowException {
    continueBlock(true);
    writer.write("// resume;");
  }

  @Override
  public void visit(TACCleanupRet node) throws ShadowException {
    continueBlock(true);
    writer.write("// resume;");
  }

  @Override
  public void visit(TACCleanupPad node) throws ShadowException {
    continueBlock(false);
    writer.write("finally");
  }

  private class Inline extends TACAbstractVisitor {
    private boolean parentheses;
    private StringBuilder sb;

    public StringBuilder visit(StringBuilder builder, TACNode node) throws ShadowException {
      sb = builder;
      node.accept(this);
      return builder;
    }

    @Override
    public void visit(TACUnary node) throws ShadowException {
      sb.append(node.getOperation());
      parentheses = true;
      visit(sb, node.getOperand());
      parentheses = false;
    }

    @Override
    public void visit(TACBinary node) throws ShadowException {
      boolean paren = parentheses;
      if (paren) sb.append('(');
      parentheses = true;
      visit(sb, node.getFirst()).append(' ').append(node.getOperation()).append(' ');
      parentheses = true;
      visit(sb, node.getSecond());
      if (paren) sb.append(')');
      parentheses = false;
    }

    @Override
    public void visit(TACCall node) throws ShadowException {
      TACMethodRef method = node.getMethodRef();
      if (method instanceof TACMethodName) {
        TACMethodName methodName = (TACMethodName) method;
        sb.append(methodName.getOuterType().toString(Type.PACKAGES | Type.TYPE_PARAMETERS))
            .append('.')
            .append(methodName.getName())
            .append('(');
      } else if (method instanceof TACMethodPointer) {
        TACMethodPointer methodPointer = (TACMethodPointer) method;
        sb.append(methodPointer).append('(');
      }

      for (TACOperand param : node.getParameters()) visit(sb, param).append(", ");
      if (node.getNumParameters() != 0) sb.delete(sb.length() - 2, sb.length());
      sb.append(')');

      TACLabel unwindLabel = node.getBlock().getUnwind();
      if (unwindLabel != null)
        sb.append(" to label ")
            .append(symbol(node.getNoExceptionLabel()))
            .append(" unwind label ")
            .append(symbol(unwindLabel));
    }

    @Override
    public void visit(TACCast node) throws ShadowException {
      sb.append("cast<")
          .append(node.getType().toString(Type.PACKAGES | Type.TYPE_PARAMETERS))
          .append(">(");

      TACOperand op = node.getOperand(0);
      while (op instanceof TACCast) op = op.getOperand(0);

      visit(sb, op).append(')');
    }

    @Override
    public void visit(TACLocalLoad node) throws ShadowException {
      sb.append(node.getVariable());
    }

    @Override
    public void visit(TACLoad node) throws ShadowException {
      TACReference reference = node.getReference();
      visitReference(sb, reference);
    }

    @Override
    public void visit(TACSequence node) throws ShadowException {
      sb.append('(');
      for (TACOperand operand : node) visit(sb, operand).append(", ");
      sb.delete(sb.length() - 2, sb.length()).append(')');
    }

    @Override
    public void visit(TACNewObject node) throws ShadowException {
      sb.append(node.getType().toString(Type.PACKAGES | Type.TYPE_PARAMETERS)).append(":create");
    }

    @Override
    public void visit(TACNewArray node) throws ShadowException {
      sb.append(node.getType().getBaseType().toString(Type.PACKAGES | Type.TYPE_PARAMETERS))
          .append(":create[")
          .append(node.getSize());
      sb.append(']');
    }

    @Override
    public void visit(TACClass node) throws ShadowException {
      sb.append(node.getClassType()).append(":class");
    }

    @Override
    public void visit(TACClass.TACClassData node) throws ShadowException {
      sb.append(node.getClassType()).append(":class");
    }

    @Override
    public void visit(TACCleanupPad node) throws ShadowException {
      sb.append("cleanuppad");
    }

    @Override
    public void visit(TACCleanupRet node) throws ShadowException {
      sb.append("cleanupret");
    }

    @Override
    public void visit(TACTypeId node) throws ShadowException {
      visit(sb.append("typeid("), node.getOperand()).append(')');
    }

    @Override
    public void visit(TACSequenceElement node) throws ShadowException {
      TACOperand op = node.getOperand(0);

      if (op instanceof TACSequence) visit(sb, op.getOperand(node.getIndex()));
      else {
        visit(sb.append("extract("), op);
        sb.append(", ").append(node.getIndex()).append(")");
      }
    }

    @Override
    public void visit(TACClass.TACMethodTable node) throws ShadowException {
      Type type = node.getClassType().getTypeWithoutTypeArguments();
      if (type instanceof ArrayType) type = ((ArrayType) type).recursivelyGetBaseType();

      if (type instanceof InterfaceType) sb.append("null");
      else sb.append("methodtable(").append(type).append(")");
    }

    @Override
    public void visit(TACLiteral node) throws ShadowException {
      sb.append(node);
    }

    @Override
    public void visit(TACPointerToLong node) throws ShadowException {
      visit(sb.append("long("), node.getOperand(0));
      sb.append(")");
    }

    @Override
    public void visit(TACLongToPointer node) throws ShadowException {
      visit(sb.append("pointer("), node.getOperand(0));
      sb.append(")");
    }

    @Override
    public void visit(TACParameter node) throws ShadowException {
      sb.append(node);
    }
  }
}
