package shadow.test.doctool;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shadow.doctool.Documentation;
import shadow.doctool.DocumentationBuilder;
import shadow.doctool.DocumentationException;
import shadow.doctool.DocumentationTool;
import shadow.doctool.tag.ParserManager.ArgDescriptionParser;
import shadow.doctool.tag.ParserManager.DelimitedParser;
import shadow.doctool.tag.ParserManager.TagParser;
import shadow.doctool.tag.TagManager.BlockTagType;
import shadow.doctool.tag.TagManager.InlineTag;
import shadow.doctool.tag.TagManager.InlineTagType;
import shadow.typecheck.type.Type;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DocumentationTests {
  private static final Path outputDirectory = Paths.get("shadow", "test", "doctool", "docs");
  private final ArrayList<String> args = new ArrayList<>();

  @BeforeEach
  public void setup() throws Exception {
    Type.clearTypes();

    try {
      FileUtils.deleteDirectory(outputDirectory.toFile());
    } catch (Exception ignored) {
    }

    String os = System.getProperty("os.name").toLowerCase();

    args.add("-c");
    if (os.contains("windows")) args.add("windows.json");
    else if (os.contains("mac")) args.add("mac.json");
    else args.add("linux.json");

    args.add("-d");
    args.add(outputDirectory.toString());
  }

  @AfterEach
  public void cleanup() {
    // Try to remove the documentation directory
    try {
      FileUtils.deleteDirectory(outputDirectory.toFile());
    } catch (Exception ignored) {
    }
  }

  /**
   * This test should only fail if the documentation tool can't properly ignore misplaced
   * documentation comments
   */
  @Test
  public void misplacedTest() throws Exception {
    args.add("shadow/test/doctool/Misplaced.shadow");
    DocumentationTool.document(args.toArray(new String[] {}));
  }

  @Test
  public void argDescriptionParserTest() throws Exception {
    TagParser parser = new ArgDescriptionParser(3, true);

    List<String> results = parser.parse("\tfirstArg secondOne \tthirdOne and a \tdescription!    ");
    assertEquals(4, results.size());
    assertEquals("firstArg", results.get(0));
    assertEquals("secondOne", results.get(1));
    assertEquals("thirdOne", results.get(2));
    assertEquals("and a description!", results.get(3));

    parser = new ArgDescriptionParser(2, false);

    try {
      parser.parse("this won't work");
      throw new Exception("Excess text was not caught");
    } catch (DocumentationException ignored) {
    }

    results = parser.parse("one-and-a \ttwo");
    assertEquals(2, results.size());
    assertEquals("one-and-a", results.get(0));
    assertEquals("two", results.get(1));
  }

  @Test
  public void delimitedParserTest() throws Exception {
    TagParser parser = new DelimitedParser(',');

    List<String> results = parser.parse(" John Doe, Jane Eyre  , Mary\tShelly,,,\t");
    assertEquals(3, results.size());
    assertEquals("John Doe", results.get(0));
    assertEquals("Jane Eyre", results.get(1));
    assertEquals("Mary Shelly", results.get(2));

    try {
      new DelimitedParser(' ');
      throw new Exception("Whitespace delimiter was not caught");
    } catch (IllegalArgumentException ignored) {
    }
  }

  @Test
  public void typeRetrievalTest() {
    BlockTagType blockType = BlockTagType.getType("author");
    assertEquals(BlockTagType.AUTHOR, blockType);
    blockType = BlockTagType.getType("param");
    assertEquals(BlockTagType.PARAM, blockType);
    blockType = BlockTagType.getType("throws");
    assertEquals(BlockTagType.THROWS, blockType);
    blockType = BlockTagType.getType("FAKE_TAG");
    assertNull(blockType);

    InlineTagType inlineType = InlineTagType.getType("code");
    assertEquals(InlineTagType.CODE, inlineType);
    inlineType = InlineTagType.getType("FAKE_TAG");
    assertNull(inlineType);
  }

  @Test
  public void tagTest() throws Exception {
    DocumentationBuilder builder = new DocumentationBuilder();
    builder.addBlock(
        "This is a documentation comment {@code this is\n"
            + "some literal code} here is some more content and\n"
            + "{@code here is some more}.\n"
            + "@author now for block, tags\n"
            + "@param fake these should be ignored");
    Documentation documentation = new Documentation(builder);
    List<InlineTag> summary = documentation.getSummary();
    List<InlineTag> inline = documentation.getInlineTags();
    List<List<String>> author = documentation.getBlockTags(BlockTagType.AUTHOR);
    List<List<String>> param = documentation.getBlockTags(BlockTagType.PARAM);

    // Summary
    assertEquals(5, summary.size());
    assertEquals(InlineTagType.PLAIN_TEXT, summary.get(0).getType());
    assertEquals("This is a documentation comment ", summary.get(0).getArg(0));
    assertEquals(InlineTagType.CODE, summary.get(1).getType());
    assertEquals("this is some literal code", summary.get(1).getArg(0));
    assertEquals(InlineTagType.PLAIN_TEXT, summary.get(2).getType());
    assertEquals(" here is some more content and ", summary.get(2).getArg(0));
    assertEquals(InlineTagType.CODE, summary.get(3).getType());
    assertEquals("here is some more", summary.get(3).getArg(0));
    assertEquals(".", summary.get(4).getArg(0));

    // Inline
    assertEquals(5, inline.size());
    assertEquals(InlineTagType.PLAIN_TEXT, inline.get(0).getType());
    assertEquals("This is a documentation comment ", inline.get(0).getArg(0));
    assertEquals(InlineTagType.CODE, inline.get(1).getType());
    assertEquals("this is some literal code", inline.get(1).getArg(0));
    assertEquals(InlineTagType.PLAIN_TEXT, inline.get(2).getType());
    assertEquals(" here is some more content and ", inline.get(2).getArg(0));
    assertEquals(InlineTagType.CODE, inline.get(3).getType());
    assertEquals("here is some more", inline.get(3).getArg(0));
    assertEquals(InlineTagType.PLAIN_TEXT, inline.get(4).getType());
    assertEquals(".", inline.get(4).getArg(0));

    // Author tags
    assertEquals(1, author.size());

    // Param tags
    assertEquals(1, param.size());
  }
}
