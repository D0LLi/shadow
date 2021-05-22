package shadow.test.typecheck;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shadow.Main;

import java.util.ArrayList;

public class StandardLibraryTests {

  private final ArrayList<String> args = new ArrayList<>();

  @BeforeEach
  public void setup() throws Exception {
    // args.add("-v");
    args.add("--typecheck");

    String os = System.getProperty("os.name").toLowerCase();

    args.add("-c");
    if (os.contains("windows")) args.add("windows.json");
    else if (os.contains("mac")) args.add("mac.json");
    else args.add("linux.json");
  }

  @Test
  public void testArray() throws Exception {
    args.add("shadow/standard/Array.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testBoolean() throws Exception {
    args.add("shadow/standard/Boolean.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testByte() throws Exception {
    args.add("shadow/standard/Byte.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testCanCompare() throws Exception {
    args.add("shadow/standard/CanCompare.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testEqual() throws Exception {
    args.add("shadow/standard/CanEqual.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testCanHash() throws Exception {
    args.add("shadow/standard/CanHash.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testCanIndex() throws Exception {
    args.add("shadow/standard/CanIndex.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testCanIterate() throws Exception {
    args.add("shadow/standard/CanIterate.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testClass() throws Exception {
    args.add("shadow/standard/Class.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testCode() throws Exception {
    args.add("shadow/standard/Code.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testDouble() throws Exception {
    args.add("shadow/standard/Double.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testEnum() throws Exception {
    args.add("shadow/standard/Enum.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testException() throws Exception {
    args.add("shadow/standard/Exception.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testFloat() throws Exception {
    args.add("shadow/standard/Float.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testFloatingPoint() throws Exception {
    args.add("shadow/standard/FloatingPoint.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testIllegalArgumentException() throws Exception {
    args.add("shadow/standard/IllegalArgumentException.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testIndexOutOfBoundsException() throws Exception {
    args.add("shadow/standard/IndexOutOfBoundsException.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testInt() throws Exception {
    args.add("shadow/standard/Int.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testInteger() throws Exception {
    args.add("shadow/standard/Integer.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testIterator() throws Exception {
    args.add("shadow/standard/Iterator.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testLong() throws Exception {
    args.add("shadow/standard/Long.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testMutableString() throws Exception {
    args.add("shadow/standard/MutableString.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testNumber() throws Exception {
    args.add("shadow/standard/Number.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testObject() throws Exception {
    args.add("shadow/standard/Object.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testShort() throws Exception {
    args.add("shadow/standard/Short.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testString() throws Exception {
    args.add("shadow/standard/String.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testSystem() throws Exception {
    args.add("shadow/standard/System.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testUByte() throws Exception {
    args.add("shadow/standard/UByte.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testUInt() throws Exception {
    args.add("shadow/standard/UInt.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testULong() throws Exception {
    args.add("shadow/standard/ULong.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testBigInteger() throws Exception {
    args.add("shadow/standard/BigInteger.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testUnexpectedNullException() throws Exception {
    args.add("shadow/standard/UnexpectedNullException.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testUnsupportedOperationException() throws Exception {
    args.add("shadow/standard/UnsupportedOperationException.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testUShort() throws Exception {
    args.add("shadow/standard/UShort.shadow");
    Main.run(args.toArray(new String[] {}));
  }
}
