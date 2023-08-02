package shadow.doctool;

import java.util.ArrayDeque;

/**
 * Represents the contents of a Shadow documentation comment. Should be associated with a
 * class/interface/exception/singleton declaration or a field/method declaration. Note that
 * whitespace in documentation comments is not guaranteed to be preserved
 */
public class DocumentationBuilder {
  private final ArrayDeque<String> lines = new ArrayDeque<>();

  /** Parses a single line comment, removing leading/trailing whitespace */
  public void appendLine(String line) {
    line = line.trim();
    if (!line.isEmpty()) lines.add(clean(line));
  }

  /**
   * Parses and splits a block comment. Leading asterisks and leading/trailing whitespace are also
   * removed
   */
  public void addBlock(String block) {
    String[] split = block.split("[\r\n]+");
    boolean first = true;

    for (String line : split) {
      line = line.trim();

      // Remove the leading asterisk, if it exists. The first line of a
      // multi-line comment (following /**) should be excluded from this
      if (first) first = false;
      else if (line.indexOf('*') == 0) line = line.substring(1).trim();

      // Only keep non-empty lines
      if (!line.equals("")) lines.addLast(clean(line));
    }
  }

  /** Converts all whitespace chunks into single spaces */
  static String clean(String value) {
    return value.replaceAll("\\s+", " ");
  }

  /** Determines if documentation text has actually been added */
  public boolean hasContent() {
    return (lines.size() > 0);
  }

  /**
   * Parses and processes the directives present in the documentation text, returning a
   * Documentation object containing the results
   */
  public Documentation process() throws DocumentationException {
    Documentation documentation = new Documentation(this);
    lines.clear();
    return documentation;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    int i = 0;
    for (String line : lines) {
      builder.append(line);

      if (i < lines.size() - 1) builder.append("\n");

      i++;
    }

    return builder.toString();
  }
}
