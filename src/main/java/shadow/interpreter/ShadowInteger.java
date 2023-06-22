package shadow.interpreter;

import shadow.interpreter.InterpreterException.Error;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

import java.math.BigInteger;
import java.util.Map;

public class ShadowInteger extends ShadowNumeric {

  private final int size; // in bytes
  private final boolean signed;
  private final int preferredBase; // used for writing the value back out as a literal
  private BigInteger value;

  private final BigInteger max;
  private final BigInteger min;

  public ShadowInteger(BigInteger value, int size, boolean signed, int preferredBase) {
    super(Modifiers.IMMUTABLE);
    this.value = value;
    this.size = size;
    this.signed = signed;
    this.preferredBase = preferredBase;

    if (signed) {
      max = BigInteger.valueOf(2).pow(size * 8 - 1);
      min = max.negate();
    } else {
      max = BigInteger.valueOf(2).pow(size * 8);
      min = BigInteger.ZERO;
    }

    fixValue();
  }

  public ShadowInteger(BigInteger value, int size, boolean signed) {
    this(value, size, signed, 10);
  }

  public ShadowInteger(int value) {
    this(BigInteger.valueOf(value), 4, true);
  }

  public ShadowInteger(long value) {
    this(BigInteger.valueOf(value), 8, true);
  }

  public static ShadowInteger parseNumber(String string) {
    return parseNumber(string, false);
  }

  public static ShadowInteger parseNumber(String string, boolean negated) {
    int base = 10;
    int bytes = 4;
    boolean signed = true;
    int end = 0;
    string = string.toLowerCase();

    if (string.endsWith("uy")) {
      bytes = 1;
      signed = false;
      end = 2;
    } else if (string.endsWith("y")) {
      bytes = 1;
      end = 1;
    } else if (string.endsWith("us")) {
      bytes = 2;
      signed = false;
      end = 2;
    } else if (string.endsWith("s")) {
      bytes = 2;
      end = 1;
    } else if (string.endsWith("ui")) {
      signed = false;
      end = 2;
    } else if (string.endsWith("i")) {
      end = 1;
    } else if (string.endsWith("u")) {
      signed = false;
      end = 1;
    } else if (string.endsWith("ul")) {
      bytes = 8;
      signed = false;
      end = 2;
    } else if (string.endsWith("l")) {
      bytes = 8;
      end = 1;
    }

    string = string.substring(0, string.length() - end);

    if (string.startsWith("0b")) {
      base = 2;
      string = string.substring(2);
    } else if (string.startsWith("0c")) {
      base = 8;
      string = string.substring(2);
    } else if (string.startsWith("0x")) {
      base = 16;
      string = string.substring(2);
    }

    BigInteger integer = new BigInteger(string, base);
    BigInteger test = negated ? integer.negate() : integer;
    int bits = signed ? bytes * 8 - 1 : bytes * 8;

    if (test.bitLength() > bits) throw new NumberFormatException("Value out of range");

    return new ShadowInteger(integer, bytes, signed, base);
  }

  private void fixValue() {
    if (signed) {
      while (value.compareTo(max) >= 0 || value.compareTo(min) < 0) {
        if (value.compareTo(max) >= 0) {
          value = value.subtract(max);
          value = value.add(min);
        }

        if (value.compareTo(min) < 0) {
          value = value.subtract(min);
          value = value.add(max);
        }
      }
    } else value = value.mod(max);
  }

  @Override
  public Type getType() {
    if (signed) {
      switch (size) {
        case 1:
          return Type.BYTE;
        case 2:
          return Type.SHORT;
        case 4:
          return Type.INT;
        case 8:
          return Type.LONG;
      }
    } else {
      switch (size) {
        case 1:
          return Type.UBYTE;
        case 2:
          return Type.USHORT;
        case 4:
          return Type.UINT;
        case 8:
          return Type.ULONG;
      }
    }

    return null;
  }

  public BigInteger getValue() {
    return value;
  }

  @Override
  public ShadowValue[] callMethod(String method, ShadowValue... arguments)
      throws InterpreterException {
    if (arguments.length == 0) {
      switch (method) {
        case "flipEndian":
          return new ShadowInteger[]{flipEndian()};
        case "leadingZeroes":
          return new ShadowInteger[]{leadingZeroes()};
        case "ones":
          return new ShadowInteger[]{ones()};
        case "trailingZeroes":
          return new ShadowInteger[]{trailingZeroes()};
        case "toSigned":
          return new ShadowInteger[]{toSigned()};
        case "toUnsigned":
          return new ShadowInteger[]{toUnsigned()};
      }
    }

    return super.callMethod(method, arguments);
  }

  @Override
  public ShadowInteger negate() throws InterpreterException {
    if (signed) return new ShadowInteger(value.negate(), size, true);

    throw new InterpreterException(
        Error.UNSUPPORTED_OPERATION, "Unsigned values cannot be negated");
  }

  @Override
  public ShadowInteger bitwiseComplement() {
    return new ShadowInteger(value.negate().subtract(BigInteger.ONE), size, signed);
  }

  @Override
  public ShadowValue cast(Type type) throws InterpreterException {
    if (type.equals(getType())) return this;
    if (type.equals(Type.BYTE)) return new ShadowInteger(value, 1, true);
    if (type.equals(Type.SHORT)) return new ShadowInteger(value, 2, true);
    if (type.equals(Type.INT)) return new ShadowInteger(value, 4, true);
    if (type.equals(Type.LONG)) return new ShadowInteger(value, 8, true);
    if (type.equals(Type.UBYTE)) return new ShadowInteger(value, 1, false);
    if (type.equals(Type.USHORT)) return new ShadowInteger(value, 2, false);
    if (type.equals(Type.UINT)) return new ShadowInteger(value, 4, false);
    if (type.equals(Type.ULONG)) return new ShadowInteger(value, 8, false);
    if (type.equals(Type.DOUBLE)) return new ShadowDouble(value.doubleValue());
    if (type.equals(Type.FLOAT)) return new ShadowFloat(value.floatValue());
    if (type.equals(Type.CODE)) return new ShadowCode(value.intValue());
    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Cannot cast " + getType() + " to " + type);
  }

  @Override
  public ShadowInteger add(ShadowValue other) throws InterpreterException {
    if (getType().equals(other.getType())) {
      ShadowInteger input = (ShadowInteger) other;
      return new ShadowInteger(value.add(input.value), size, signed);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowInteger subtract(ShadowValue other) throws InterpreterException {
    if (getType().equals(other.getType())) {
      ShadowInteger input = (ShadowInteger) other;
      return new ShadowInteger(value.subtract(input.value), size, signed);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowInteger multiply(ShadowValue other) throws InterpreterException {
    if (getType().equals(other.getType())) {
      ShadowInteger input = (ShadowInteger) other;
      return new ShadowInteger(value.multiply(input.value), size, signed);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowInteger divide(ShadowValue other) throws InterpreterException {
    if (getType().equals(other.getType())) {
      ShadowInteger input = (ShadowInteger) other;
      return new ShadowInteger(value.divide(input.value), size, signed);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowInteger modulus(ShadowValue other) throws InterpreterException {
    if (getType().equals(other.getType())) {
      ShadowInteger input = (ShadowInteger) other;
      return new ShadowInteger(value.mod(input.value), size, signed);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowInteger bitShiftLeft(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowInteger) {
      ShadowInteger input = (ShadowInteger) other;
      return new ShadowInteger(
          value.shiftLeft(input.value.mod(new BigInteger("64")).intValue()), size, signed);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowInteger bitShiftRight(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowInteger) {
      ShadowInteger input = (ShadowInteger) other;
      return new ShadowInteger(
          value.shiftRight(input.value.mod(new BigInteger("64")).intValue()), size, signed);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowInteger bitRotateLeft(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowInteger) {
      ShadowInteger input = (ShadowInteger) other;
      int shift = input.value.mod(new BigInteger("64")).intValue();
      BigInteger result = value.shiftLeft(shift);

      if (signed) result = result.mod(max.multiply(new BigInteger("2")));

      result = result.or(value.shiftRight(8 * size - shift));

      return new ShadowInteger(result, size, signed);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowInteger bitRotateRight(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowInteger) {
      ShadowInteger input = (ShadowInteger) other;
      int shift = input.value.mod(new BigInteger("64")).intValue();
      BigInteger result = value.shiftLeft(8 * size - shift);

      if (signed) result = result.mod(max.multiply(new BigInteger("2")));

      result = result.or(value.shiftRight(shift));

      return new ShadowInteger(result, size, signed);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowBoolean equal(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowInteger) {
      ShadowInteger input = (ShadowInteger) other;
      return new ShadowBoolean(value.equals(input.value));
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowBoolean lessThan(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowInteger) {
      ShadowInteger input = (ShadowInteger) other;
      return new ShadowBoolean(value.compareTo(input.value) < 0);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowBoolean lessThanOrEqual(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowInteger) {
      ShadowInteger input = (ShadowInteger) other;
      return new ShadowBoolean(value.compareTo(input.value) <= 0);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowBoolean greaterThan(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowInteger) {
      ShadowInteger input = (ShadowInteger) other;
      return new ShadowBoolean(value.compareTo(input.value) > 0);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowBoolean greaterThanOrEqual(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowInteger) {
      ShadowInteger input = (ShadowInteger) other;
      return new ShadowBoolean(value.compareTo(input.value) >= 0);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowInteger bitwiseAnd(ShadowValue other) throws InterpreterException {
    if (getType().equals(other.getType())) {
      ShadowInteger input = (ShadowInteger) other;
      return new ShadowInteger(value.and(input.value), size, signed);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowInteger bitwiseOr(ShadowValue other) throws InterpreterException {
    if (getType().equals(other.getType())) {
      ShadowInteger input = (ShadowInteger) other;
      return new ShadowInteger(value.or(input.value), size, signed);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowInteger bitwiseXor(ShadowValue other) throws InterpreterException {
    if (getType().equals(other.getType())) {
      ShadowInteger input = (ShadowInteger) other;
      return new ShadowInteger(value.xor(input.value), size, signed);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowInteger copy(Map<ShadowValue, ShadowValue> newValues) throws InterpreterException {
    return new ShadowInteger(value, size, signed);
  }

  @Override
  public ShadowInteger hash() throws InterpreterException {
    if (size < 8) return (ShadowInteger) cast(Type.UINT);

    ShadowInteger first = (ShadowInteger) cast(Type.UINT);
    ShadowInteger second =
        (ShadowInteger)
            bitShiftRight(new ShadowInteger(BigInteger.valueOf(32), 4, false)).cast(Type.UINT);

    return first.bitwiseXor(second);
  }

  @Override
  public String toLiteral() {
    return getLiteralPrefix(preferredBase)
        + value.toString(preferredBase)
        + getLiteralSuffix(signed, size);
  }

  private static String getLiteralPrefix(int base) {
    switch (base) {
      case 2:
        return "0b";
      case 8:
        return "0c";
      case 10:
        return "";
      case 16:
        return "0x";
    }

    throw new IllegalArgumentException("Unsupported base " + base);
  }

  private static String getLiteralSuffix(boolean signed, int size) {
    if (signed) {
      switch (size) {
        case 1:
          return "y";
        case 2:
          return "s";
        case 4:
          return "";
        case 8:
          return "L";
      }
    } else {
      switch (size) {
        case 1:
          return "uy";
        case 2:
          return "us";
        case 4:
          return "u";
        case 8:
          return "uL";
      }
    }

    throw new IllegalArgumentException("Unknown integer type with size " + size);
  }

  @Override
  public String toString() {
    return value.toString();
  }

  public String toString(int base) {
    return value.toString(base);
  }

  @Override
  public ShadowInteger abs() throws InterpreterException {
    return new ShadowInteger(value.abs(), size, false);
  }

  @Override
  public ShadowDouble cos() throws InterpreterException {
    return new ShadowDouble(Math.cos(value.doubleValue()));
  }

  @Override
  public ShadowDouble sin() throws InterpreterException {
    return new ShadowDouble(Math.sin(value.doubleValue()));
  }

  @Override
  public ShadowDouble power(ShadowNumber number) throws InterpreterException {
    double exponent = ((ShadowDouble) number.cast(Type.DOUBLE)).getValue();
    return new ShadowDouble(Math.pow(value.doubleValue(), exponent));
  }

  @Override
  public ShadowDouble squareRoot() throws InterpreterException {
    return new ShadowDouble(Math.sqrt(value.doubleValue()));
  }

  @Override
  public ShadowDouble logBase10() throws InterpreterException {
    return new ShadowDouble(Math.log10(value.doubleValue()));
  }

  @Override
  public ShadowDouble logBase2() throws InterpreterException {
    return new ShadowDouble(Math.log(value.doubleValue()) / Math.log(2.0));
  }

  @Override
  public ShadowDouble logBaseE() throws InterpreterException {
    return new ShadowDouble(Math.log(value.doubleValue()));
  }

  public ShadowInteger ones() throws InterpreterException {
    int count;
    if (value.compareTo(BigInteger.ZERO) < 0)
      count = value.bitLength() + 1 + (value.bitLength() - value.bitCount());
    else count = value.bitCount();

    return new ShadowInteger(BigInteger.valueOf(count), size, signed);
  }

  public ShadowInteger trailingZeroes() throws InterpreterException {
    int count = 0;
    if (value.compareTo(BigInteger.ZERO) == 0) count = 8 * size;
    else {
      for (int i = 0; i < value.bitLength() && !value.testBit(i); i++) count++;
    }

    return new ShadowInteger(BigInteger.valueOf(count), size, signed);
  }

  public ShadowInteger leadingZeroes() throws InterpreterException {
    int count;
    if (value.compareTo(BigInteger.ZERO) < 0) count = 0;
    else count = 8 * size - value.bitLength();

    return new ShadowInteger(BigInteger.valueOf(count), size, signed);
  }

  public ShadowInteger flipEndian() {
    BigInteger result = BigInteger.ZERO;
    BigInteger mask = BigInteger.valueOf(0xFF);

    for (int i = 0; i < size; i++)
      result = result.or(value.shiftRight(8 * (size - i - 1)).and(mask).shiftLeft(i));

    return new ShadowInteger(result, size, signed);
  }

  public ShadowInteger toSigned() {
    return new ShadowInteger(value, size, true, preferredBase);
  }

  public ShadowInteger toUnsigned() {
    return new ShadowInteger(value, size, false, preferredBase);
  }
}
