/* Generated By:JJTree: Do not edit this line. ASTExplicitConstructorInvocation.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package shadow.parser.javacc;

public
@SuppressWarnings("all")
class ASTExplicitConstructorInvocation extends SimpleNode {
  public ASTExplicitConstructorInvocation(int id) {
    super(id);
  }

  public ASTExplicitConstructorInvocation(ShadowParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ShadowParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=13642242f38c7b5f543dc50a9bba2725 (do not edit this line) */
