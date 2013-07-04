package shadow.output.llvm;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import shadow.Main;

public class LLVMMain
{
	public static final boolean DELETE = false, FORCE = false;
	public static void main(String[] args)
	{
		if (DELETE) for (File file : new File("shadow/standard").
				listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".meta");
			}
		})) file.delete();

		List<String> arguments = new ArrayList<String>();

//		arguments.add("--config");
//		arguments.add("src/test_windows_config.xml");

//		arguments.add("--check");
		arguments.add("shadow/test/Test.shadow");

		arguments.add("shadow/standard/Object.shadow");
		arguments.add("shadow/standard/Class.shadow");
		arguments.add("shadow/standard/Array.shadow");
		arguments.add("shadow/standard/String.shadow");
		arguments.add("shadow/standard/MutableString.shadow");
		arguments.add("shadow/standard/Boolean.shadow");
		arguments.add("shadow/standard/Code.shadow");
		arguments.add("shadow/standard/Byte.shadow");
		arguments.add("shadow/standard/UByte.shadow");
		arguments.add("shadow/standard/Short.shadow");
		arguments.add("shadow/standard/UShort.shadow");
		arguments.add("shadow/standard/Int.shadow");
		arguments.add("shadow/standard/UInt.shadow");
		arguments.add("shadow/standard/Long.shadow");
		arguments.add("shadow/standard/ULong.shadow");
		arguments.add("shadow/standard/Float.shadow");
		arguments.add("shadow/standard/Double.shadow");
		arguments.add("shadow/standard/Number.shadow");
		arguments.add("shadow/standard/Integer.shadow");
		arguments.add("shadow/standard/FloatingPoint.shadow");
		arguments.add("shadow/standard/Reference.shadow");
		arguments.add("shadow/standard/System.shadow");
		arguments.add("shadow/standard/Iterator.shadow");

		arguments.add("shadow/standard/CanEqual.shadow");
		arguments.add("shadow/standard/CanCompare.shadow");
		arguments.add("shadow/standard/CanIterate.shadow");
		arguments.add("shadow/standard/CanIndex.shadow");
		arguments.add("shadow/standard/CanHash.shadow");

		arguments.add("shadow/standard/Exception.shadow");
		arguments.add("shadow/standard/IllegalArgumentException.shadow");
		arguments.add("shadow/standard/IndexOutOfBoundsException.shadow");
		arguments.add("shadow/standard/UnexpectedNullException.shadow");

		arguments.add("shadow/io/Console.shadow");
		arguments.add("shadow/io/CanClose.shadow");
		arguments.add("shadow/io/CanRead.shadow");
		arguments.add("shadow/io/CanWrite.shadow");
		arguments.add("shadow/io/Path.shadow");
		arguments.add("shadow/io/File.shadow");
		arguments.add("shadow/io/IOException.shadow");

//		arguments.add("shadow/utility/Random.shadow");
//		arguments.add("shadow/utility/List.shadow");
//		arguments.add("shadow/utility/Set.shadow");
//		arguments.add("shadow/utility/ArrayList.shadow");
//		arguments.add("shadow/utility/LinkedList.shadow");
//		arguments.add("shadow/utility/HashSet.shadow");
//		arguments.add("shadow/utility/IllegalModificationException.shadow");

//		arguments.add("shadow/test/ExceptionA.shadow");
//		arguments.add("shadow/test/ExceptionB.shadow");
//		arguments.add("shadow/test/ExceptionC.shadow");
//		arguments.add("shadow/test/ExceptionTest.shadow");
//		arguments.add("shadow/test/ParentTest.shadow");
//		arguments.add("shadow/test/ChildTest.shadow");
//		arguments.add("shadow/test/ArrayTest.shadow");
//		arguments.add("shadow/test/StringTest.shadow");
//		arguments.add("shadow/test/ConsoleTest.shadow");
//		arguments.add("shadow/test/ArrayListTest.shadow");
//		arguments.add("shadow/test/SortTest.shadow");
//		arguments.add("shadow/test/SortMain.shadow");

//		arguments.add("sokoban/solver/Solver.shadow");
//		arguments.add("sokoban/solver/Board.shadow");


		if (FORCE)
			for (String file : arguments)
			{
				Main.test(new String[] { "--check", file });
				//Main.test(new String[] { file });
			}
		else
		{
			if (DELETE) arguments.add(0, "--check");
			Main.main(arguments.toArray(new String[arguments.size()]));
		}
	}
}
