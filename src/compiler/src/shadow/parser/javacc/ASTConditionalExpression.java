/* Generated By:JJTree: Do not edit this line. ASTConditionalExpression.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package shadow.parser.javacc;

public
@SuppressWarnings("all")
class ASTConditionalExpression extends SimpleNode {
  public ASTConditionalExpression(int id) {
    super(id);
  }

  public ASTConditionalExpression(ShadowParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ShadowParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=ee881f011786ca67105d704228149a96 (do not edit this line) */
