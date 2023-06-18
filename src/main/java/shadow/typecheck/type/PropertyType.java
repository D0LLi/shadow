package shadow.typecheck.type;

import shadow.ShadowException;
import shadow.doctool.Documentation;
import shadow.typecheck.BaseChecker;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.Package;
import shadow.typecheck.TypeCheckException.Error;

import java.util.ArrayList;
import java.util.List;

public class PropertyType extends Type {
  private final MethodSignature getter;
  private MethodSignature setter;
  private final UnboundMethodType method;
  private final ModifiedType prefix;
  private final Type context;

  public List<ShadowException> applyInput(ModifiedType input) {

    List<ShadowException> errors = new ArrayList<>();

    if (method == null) {
      if (isGettable())
        ErrorReporter.addError(
            errors,
            Error.INVALID_PROPERTY,
            "Cannot assign to non-set property " + getter.getSymbol());
      else
        ErrorReporter.addError(
            errors,
            Error.INVALID_PROPERTY,
            "Cannot assign to non-set property"); // should never happen, but...
      return errors;
    }

    Type outer = method.getOuter();
    String name = method.getTypeName();
    SequenceType arguments = new SequenceType();
    arguments.add(input);

    MethodSignature signature = outer.getMatchingMethod(name, arguments);

    if (signature == null)
      ErrorReporter.addError(
          errors,
          Error.INVALID_PROPERTY,
          "Property " + name + " cannot accept input of type " + input.getType());
    else {
      setSetter(signature);

      if (!BaseChecker.methodIsAccessible(signature, context, getPackage().getRoot()))
        ErrorReporter.addError(
            errors,
            Error.ILLEGAL_ACCESS,
            "Property " + name + " is not accessible from this context");

      if (!prefix.getModifiers().isMutable() && signature.getModifiers().isMutable())
        ErrorReporter.addError(
            errors,
            Error.ILLEGAL_ACCESS,
            "Mutable property "
                + name
                + " cannot be called from "
                + (prefix.getModifiers().isImmutable() ? "immutable" : "readonly")
                + " context");
    }

    return errors;
  }

  public PropertyType(
      MethodSignature getter, UnboundMethodType method, ModifiedType prefix, Type context) {
    super(null,
            new Modifiers(),
    null,
    null,
    context.getPackage());
    this.getter = getter;
    this.method = method;
    this.prefix = prefix;
    this.context = context;
  }

  public ModifiedType getPrefix() {
    return prefix;
  }

  public Type getContext() {
    return context;
  }

  public MethodSignature getGetter() {
    return getter;
  }

  public UnboundMethodType getMethod() {
    return method;
  }

  protected void setSetter(MethodSignature setter) {
    this.setter = setter;
  }

  public MethodSignature getSetter() {
    return setter;
  }

  public ModifiedType getGetType() {
    if (getter == null) return null;

    return getter.getReturnTypes().get(0);
  }

  public ModifiedType getSetType() {
    if (setter == null) return null;

    // last input parameter, works for both indexing and properties
    ModifiedType input = setter.getParameterTypes().get(setter.getParameterTypes().size() - 1);
    ModifiedType type = new SimpleModifiedType(input.getType(), input.getModifiers());
    type.getModifiers().addModifier(Modifiers.ASSIGNABLE);
    return type;
  }

  public boolean isGettable() {
    return getter != null;
  }

  public boolean isSettable() {
    return setter != null;
  }

  @Override
  // Probably never gets used
  public boolean isSubtype(Type other) {
    if (other instanceof PropertyType && this.getClass().equals(other.getClass())) {
      PropertyType otherProperty = (PropertyType) other;
      if (otherProperty.getter != null) {
        if (getGetter() == null) return false;
        // Covariant on get
        if (!getGetType().getType().isSubtype(otherProperty.getGetType().getType())) return false;
      }

      if (otherProperty.getSetter() != null) {
        if (getSetter() == null) return false;
        // Contravariant on set
        return otherProperty.getGetType().getType().isSubtype(getGetType().getType());
      }

      return true;
    }

    return false;
  }

  @Override
  public void updateFieldsAndMethods() throws InstantiationException {
    if (getGetter() != null) getGetter().updateFieldsAndMethods();

    if (getSetter() != null) getSetter().updateFieldsAndMethods();
  }

  @Override
  public PropertyType replace(List<ModifiedType> values, List<ModifiedType> replacements)
      throws InstantiationException {

    MethodSignature replacedGetter = null;
    UnboundMethodType replacedMethod = getMethod().replace(values, replacements);
    if (getMethod() != null) replacedGetter = getGetter().replace(values, replacements);

    PropertyType replacement = new PropertyType(replacedGetter, replacedMethod, prefix, context);

    if (getSetter() != null) replacement.setSetter(getSetter().replace(values, replacements));

    return replacement;
  }

  @Override
  public PropertyType partiallyReplace(List<ModifiedType> values, List<ModifiedType> replacements)
      throws InstantiationException {

    MethodSignature replacedGetter = null;
    UnboundMethodType replacedMethod = getMethod().partiallyReplace(values, replacements);
    if (getGetter() != null) replacedGetter = getGetter().partiallyReplace(values, replacements);

    PropertyType replacement = new PropertyType(replacedGetter, replacedMethod, prefix, context);

    if (getSetter() != null)
      replacement.setSetter(getSetter().partiallyReplace(values, replacements));

    return replacement;
  }

  @Override
  public String toString(int options) {
    StringBuilder sb = new StringBuilder("[");

    if (isGettable()) {
      sb.append("get: ");
      sb.append(getGetType().getType().toString(options));
    }

    if (isGettable() && isSettable()) {
      sb.append(", ");
    }

    if (isSettable()) {
      sb.append("set: ");
      sb.append(getSetType().getType().toString(options));
    }
    sb.append("]");
    return sb.toString();
  }
}
