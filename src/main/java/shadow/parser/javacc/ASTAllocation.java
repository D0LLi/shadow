/* Generated By:JJTree: Do not edit this line. ASTAllocation.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package shadow.parser.javacc;

public
class ASTAllocation extends SimpleNode {
  public ASTAllocation(int id) {
    super(id);
  }

  public ASTAllocation(ShadowParser p, int id) {
    super(p, id);
  }

  

  /** Accept the visitor. **/
  public Object jjtAccept(ShadowParserVisitor visitor, Boolean data) throws ShadowException {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=6408ac0c2c5592b005dac9686197be00 (do not edit this line) */