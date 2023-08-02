package shadow.interpreter;

import shadow.interpreter.InterpreterException.Error;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.UnboundMethodType;

import java.util.Map;

public class ShadowUnboundMethod extends ShadowValue {

  private final ShadowValue object;
  private final UnboundMethodType type;

  public ShadowUnboundMethod(ShadowValue object, UnboundMethodType type) {
    this.object = object;
    this.type = type;
  }

  @Override
  public UnboundMethodType getType() {
    return type;
  }

  public ShadowValue getObject() {
    return object;
  }

  @Override
  public ShadowValue cast(Type type) throws InterpreterException {
    if (type instanceof UnboundMethodType) return this;
    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Cannot cast " + getType() + " to " + type);
  }

  @Override
  public ShadowValue copy(Map<ShadowValue, ShadowValue> newValues) throws InterpreterException {
    return new ShadowUnboundMethod(object, type);
  }

  @Override
  public String toLiteral() {
    return null;
  }
}
