/* Generated By:JJTree: Do not edit this line. ASTAllocationExpression.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package shadow.parser.javacc;

public
@SuppressWarnings("all")
class ASTAllocationExpression extends PrefixNode {
	
  public ASTAllocationExpression(int id) {
    super(id);
  }

  public ASTAllocationExpression(ShadowParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ShadowParserVisitor visitor, Boolean secondVisit) throws ShadowException {
    return visitor.visit(this, secondVisit);
  }  
}
/* JavaCC - OriginalChecksum=f504147c25c087f38a8fb23dfd95e3d5 (do not edit this line) */
