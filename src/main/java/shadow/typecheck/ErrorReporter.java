package shadow.typecheck;

import org.apache.logging.log4j.Logger;
import shadow.Loggers;
import shadow.ShadowException;
import shadow.ShadowExceptionErrorKind;
import shadow.parse.Context;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;

import java.util.ArrayList;
import java.util.List;

public class ErrorReporter {
  protected final ArrayList<ShadowException> errorList = new ArrayList<>();
  protected final ArrayList<ShadowException> warningList = new ArrayList<>();

  private final Logger LOGGER;


  public ErrorReporter(Logger typeChecker) {
    LOGGER = typeChecker;
  }

  public void clearErrors() {
    errorList.clear();
    warningList.clear();
  }

  public List<ShadowException> getErrorList() {
    return errorList;
  }

  @SuppressWarnings("unused")
  public List<ShadowException> getWarningList() {
    return warningList;
  }

  /**
   * Adds a temporary list of errors associated with a particular context to the main list of
   * errors.
   *
   * @param ctx context related to errors
   * @param errors list of errors
   */
  public final void addErrors(Context ctx, List<ShadowException> errors) {
    if (errors != null)
      for (ShadowException error : errors) addError(ctx, error.getError(), error.getMessageText());
  }

  /**
   * Adds an error associated with a context to the main list of errors.
   *
   * @param ctx context related to error
   * @param error kind of error
   * @param message message explaining error
   * @param errorTypes types associated with error
   */
  public void addError(
      Context ctx, ShadowExceptionErrorKind error, String message, Type... errorTypes) {
    if (containsUnknown(errorTypes)) return; // Don't add error if it has an unknown type in it.

    if (ctx != null) errorList.add(error.getException(message, ctx));
  }

  /**
   * Adds an exception to the list of errors.
   *
   * @param exception exception for error
   */
  public void addError(ShadowException exception) {
    if (exception != null) errorList.add(exception);
  }

  /**
   * Adds an exception to the list of warnings.
   *
   * @param exception exception for error
   */
  public void addWarning(ShadowException exception) {
    if (Loggers.warningsAreErrors()) addError(exception);
    else if (exception != null) warningList.add(exception);
  }

  /**
   * Adds a warning associated with a context to the main list of warnings.
   *
   * @param ctx context related to warning
   * @param warning kind of warning
   * @param message message explaining warning
   */
  public void addWarning(Context ctx, ShadowExceptionErrorKind warning, String message) {
    if (Loggers.warningsAreErrors())
      addError(ctx, warning, message);
    else if (ctx != null) warningList.add(warning.getException(message, ctx));
  }

  /** Prints the list of errors to the appropriate logger. */
  public final void printErrors() {
    for (ShadowException exception : errorList) LOGGER.error(exception.getMessage());
  }

  /** Prints the list of warnings to the appropriate logger. */
  public final void printWarnings() {
    for (ShadowException exception : warningList) LOGGER.warn(exception.getMessage());
  }

  /**
   * Checks to see if the array contains an unknown type.
   *
   * @param types array of types to be checked
   * @return if the array contains <code>Type.UNKNOWN</code>
   * @see Type#UNKNOWN
   */
  protected static boolean containsUnknown(Type[] types) {
    for (Type type : types) if (containsUnknown(type)) return true;

    return false;
  }

  /**
   * Checks to see if the type is unknown or contains an unknown type. Unknown types are generated
   * by the type-checker to avoid null pointer exceptions, but errors involving unknown types are
   * suppressed to avoid a cascade of errors from the same source.
   *
   * @param type type to be checked
   * @return if the type contains <code>Type.UNKNOWN</code>
   * @see Type#UNKNOWN
   */
  private static boolean containsUnknown(Type type) {
    if (type == null) return false;
    if (type == Type.UNKNOWN) return true;
    if (type instanceof SequenceType sequenceType) {
      for (ModifiedType modifiedType : sequenceType)
        if (containsUnknown(modifiedType.getType())) return true;
    } else if (type instanceof ArrayType) return containsUnknown(((ArrayType) type).getBaseType());

    return false;
  }

  /**
   * Adds an error to the given error list, unless that error refers to unknown types. Unknown type
   * errors are usually symptoms of other errors (like undeclared variables), and are thus
   * unnecessary to report.
   *
   * @param errors list of errors
   * @param error kind of error
   * @param reason message explaining error
   * @param errorTypes types of errors involved, used for suppressing redundant errors
   */
  public static void addError(
      List<ShadowException> errors,
      ShadowExceptionErrorKind error,
      String reason,
      Type... errorTypes) {
    // Don't add an error if it has an Unknown Type in it.
    if (containsUnknown(errorTypes)) return;
    if (errors != null) errors.add(error.getException(reason, null));
  }

  public void addAll(ErrorReporter other) {
    errorList.addAll(other.errorList);
    warningList.addAll(other.warningList);
  }

  public void removeRedundantErrors() {
    removeRedundantErrors(errorList);
    removeRedundantErrors(warningList);
  }

  private static void removeRedundantErrors(List<ShadowException> list) {
    for (int i = 0; i < list.size(); ) {
      boolean redundant = false;
      ShadowException item = list.get(i);
      for (int j = 0; j < list.size() && !redundant; ++j) {
        ShadowException other = list.get(j);
        if (i != j && item.getError() == other.getError() && item.isInside(other)) redundant = true;
      }

      if (redundant) list.remove(i);
      else ++i;
    }
  }

  public void printAndReportErrors() throws ShadowException {
    printErrors();
    printWarnings();

    warningList.clear(); // otherwise the warnings will be printed again

    if (!errorList.isEmpty()) throw errorList.get(0);
  }
}
