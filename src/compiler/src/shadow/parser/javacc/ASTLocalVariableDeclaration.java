/* Generated By:JJTree: Do not edit this line. ASTLocalVariableDeclaration.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package shadow.parser.javacc;

public
@SuppressWarnings("all")
class ASTLocalVariableDeclaration extends SimpleNode {
  public ASTLocalVariableDeclaration(int id) {
    super(id);
  }

  public ASTLocalVariableDeclaration(ShadowParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ShadowParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=3d136cc82466ead418cf1f681ca9746f (do not edit this line) */
