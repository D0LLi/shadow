package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ExceptionType;

public class TACCatchPad extends TACOperand {
  private TACLabel successor;
  private final ExceptionType type;
  private String token;

  public TACCatchPad(TACNode node, ExceptionType catchType) {
    super(node);
    type = catchType;
  }

  public void setSuccessor(TACLabel successor) {
    this.successor = successor;
  }

  public TACLabel getSuccessor() {
    return successor;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  @Override
  public int getNumOperands() {
    return 0;
  }

  @Override
  public TACOperand getOperand(int num) {
    throw new IndexOutOfBoundsException("" + num);
  }

  @Override
  public void accept(TACVisitor visitor) throws ShadowException {
    visitor.visit(this);
  }

  @Override
  public ExceptionType getType() {
    return type;
  }
}
