package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVisitor;

/**
 * TAC representation of a memory assignment to a field or an array cell. Local variable assignment
 * is represented by TACLocalStore. Example: x = y
 *
 * @author Jacob Young
 */
public class TACStore extends TACNode {
  private final TACReference reference;
  private final TACOperand value;
  private boolean incrementReference = true;
  private boolean decrementReference = true;

  public TACStore(TACNode node, TACReference ref, TACOperand op) {
    super(node);
    reference = ref;
    value = check(op, ref);
    op.setMemoryStore(this);
  }

  public TACReference getReference() {
    return reference;
  }

  public TACOperand getValue() {
    return value;
  }

  public boolean isIncrementReference() {
    return incrementReference;
  }

  public void setIncrementReference(boolean value) {
    incrementReference = value;
  }

  public boolean isDecrementReference() {
    return decrementReference;
  }

  public void setDecrementReference(boolean value) {
    decrementReference = value;
  }

  @Override
  public int getNumOperands() {
    return 1;
  }

  @Override
  public TACOperand getOperand(int num) {
    if (num == 0) return value;
    throw new IndexOutOfBoundsException("" + num);
  }

  @Override
  public void accept(TACVisitor visitor) throws ShadowException {
    visitor.visit(this);
  }

  @Override
  public String toString() {
    return reference + " = " + value;
  }
}
