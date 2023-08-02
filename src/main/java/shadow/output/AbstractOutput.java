package shadow.output;

import shadow.ShadowException;
import shadow.tac.TACAbstractVisitor;
import shadow.tac.TACMethod;
import shadow.tac.TACModule;

import java.io.OutputStream;
import java.io.Writer;
import java.nio.file.Path;

public abstract class AbstractOutput extends TACAbstractVisitor implements Output {
  protected TabbedLineWriter writer;
  protected Path file;

  public AbstractOutput(TabbedLineWriter out) {
    writer = out;
  }

  public AbstractOutput(OutputStream stream) {
    this(new TabbedLineWriter(stream));
  }

  public AbstractOutput(Writer out) {
    this(new TabbedLineWriter(out));
  }

  public AbstractOutput(Path out) throws ShadowException {
    this(new TabbedLineWriter(out));
    file = out;
  }

  public Path getFile() {
    return file;
  }

  public void build(TACModule module) throws ShadowException {
    startFile(module);
    for (TACMethod method : module.getMethods()) build(method, module);
    endFile(module);
  }

  public void build(TACMethod method, TACModule module) throws ShadowException {
    startMethod(method, module);
    walk(method.getNode());
    endMethod(method, module);
  }

  @Override
  public void startFile(TACModule module) throws ShadowException {}

  @Override
  public void endFile(TACModule module) throws ShadowException {}

  @Override
  public void startMethod(TACMethod method, TACModule module) throws ShadowException {}

  @Override
  public void endMethod(TACMethod method, TACModule module) throws ShadowException {}
}
