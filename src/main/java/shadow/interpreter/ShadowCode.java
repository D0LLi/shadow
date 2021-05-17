package shadow.interpreter;

import shadow.interpreter.InterpreterException.Error;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

import java.math.BigInteger;

public class ShadowCode extends ShadowNumber {
  private final int value;

  public ShadowCode(int value) {
    super(Modifiers.IMMUTABLE);
    this.value = value;
  }

  @Override
  public Type getType() {
    return Type.CODE;
  }

  public int getValue() {
    return value;
  }

  @Override
  public ShadowValue cast(Type type) throws InterpreterException {
    if (type.equals(Type.CODE)) return this;
    if (type.equals(Type.BYTE)) return new ShadowInteger(BigInteger.valueOf(getValue()), 1, true);
    if (type.equals(Type.SHORT)) return new ShadowInteger(BigInteger.valueOf(getValue()), 2, true);
    if (type.equals(Type.INT)) return new ShadowInteger(BigInteger.valueOf(getValue()), 4, true);
    if (type.equals(Type.LONG)) return new ShadowInteger(BigInteger.valueOf(getValue()), 8, true);
    if (type.equals(Type.UBYTE)) return new ShadowInteger(BigInteger.valueOf(getValue()), 1, false);
    if (type.equals(Type.USHORT))
      return new ShadowInteger(BigInteger.valueOf(getValue()), 2, false);
    if (type.equals(Type.UINT)) return new ShadowInteger(BigInteger.valueOf(getValue()), 4, false);
    if (type.equals(Type.ULONG)) return new ShadowInteger(BigInteger.valueOf(getValue()), 8, false);
    if (type.equals(Type.FLOAT)) return new ShadowFloat(getValue());
    if (type.equals(Type.DOUBLE)) return new ShadowDouble(getValue());
    throw new UnsupportedOperationException("Cannot cast " + getType() + " to " + type);
  }

  @Override
  public ShadowValue copy() throws InterpreterException {
    return new ShadowCode(getValue());
  }

  @Override
  public String toString() {
    return toLiteral();
  }

  @Override
  public String toLiteral() {
    return new StringBuilder("'").appendCodePoint(getValue()).append("'").toString();
  }

  public static ShadowCode parseCode(String literal) {
    int value;
    if (literal.charAt(1) == '\\') {
      switch (literal.charAt(2)) {
        case '\'':
          value = '\'';
          break;
        case '\"':
          value = '\"';
          break;
        case '\\':
          value = '\\';
          break;
        case 'b':
          value = '\b';
          break;
        case 'f':
          value = '\f';
          break;
        case 'n':
          value = '\n';
          break;
        case 'r':
          value = '\r';
          break;
        case 't':
          value = '\t';
          break;
        case 'u':
          value = Integer.parseInt(literal.substring(3, literal.length() - 1), 16);
          break;
        default:
          value = Integer.parseInt(literal.substring(3, literal.length() - 1), 8);
          break;
      }
    } else value = literal.codePointAt(1);

    return new ShadowCode(value);
  }

  @Override
  public ShadowBoolean equal(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowCode) {
      ShadowCode input = (ShadowCode) other;
      return new ShadowBoolean(value == input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  public ShadowBoolean lessThan(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowCode) {
      ShadowCode input = (ShadowCode) other;
      return new ShadowBoolean(value < input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  public ShadowBoolean lessThanOrEqual(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowCode) {
      ShadowCode input = (ShadowCode) other;
      return new ShadowBoolean(value <= input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  public ShadowBoolean greaterThan(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowCode) {
      ShadowCode input = (ShadowCode) other;
      return new ShadowBoolean(value > input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  public ShadowBoolean greaterThanOrEqual(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowCode) {
      ShadowCode input = (ShadowCode) other;
      return new ShadowBoolean(value >= input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  public ShadowCode bitwiseAnd(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowCode) {
      ShadowCode input = (ShadowCode) other;
      return new ShadowCode(value & input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  public ShadowCode bitwiseOr(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowCode) {
      ShadowCode input = (ShadowCode) other;
      return new ShadowCode(value | input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  public ShadowCode bitwiseXor(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowCode) {
      ShadowCode input = (ShadowCode) other;
      return new ShadowCode(value ^ input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  public ShadowCode bitwiseComplement() {
    return new ShadowCode(~value);
  }
}
