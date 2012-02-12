/* Generated By:JJTree: Do not edit this line. ASTTypeArgument.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package shadow.parser.javacc;

import shadow.typecheck.type.TypeParameterRepresentation;

public
@SuppressWarnings("all")
class ASTTypeArgument extends SimpleNode {
	
	private TypeParameterRepresentation representation = null;
	
	
  public ASTTypeArgument(int id) {
    super(id);
  }

  public ASTTypeArgument(ShadowParser p, int id) {
    super(p, id);
  }
  
  public void setRepresentation(TypeParameterRepresentation representation)
  {
	  this.representation = representation;
  }
  
  public TypeParameterRepresentation getRepresentation()
  {
	  return representation;
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ShadowParserVisitor visitor, Boolean secondVisit) throws ShadowException {
    return visitor.visit(this, secondVisit);
  }
}
/* JavaCC - OriginalChecksum=96655b3d861c8af0412ae9f7929a03a4 (do not edit this line) */
