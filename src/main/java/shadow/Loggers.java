package shadow;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;

public class Loggers {
  public static final Logger SHADOW = LogManager.getLogger("Shadow");
  public static final Logger PARSER = LogManager.getLogger("Shadow Parser");
  public static final Logger TYPE_CHECKER = LogManager.getLogger("Shadow Type Checker");
  public static final Logger AST_INTERPRETER = LogManager.getLogger("Shadow Constant Evaluator");
  public static final Logger TAC = LogManager.getLogger("Shadow TAC");
  public static final Logger DOC_TOOL = LogManager.getLogger("Shadow Documentation Tool");

  private static boolean warningsAreErrors = false;

  public static void setAllToLevel(Level level) {
    LoggerContext context = (LoggerContext) LogManager.getContext(false);
    org.apache.logging.log4j.core.config.Configuration config = context.getConfiguration();
    LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
    loggerConfig.setLevel(level);
    context.updateLoggers();
  }

  public static boolean warningsAreErrors() {
    return warningsAreErrors;
  }

  public static void setWarningsAsErrors(boolean value) {
    warningsAreErrors = value;
  }
}
