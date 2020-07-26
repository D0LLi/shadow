package shadow.tac;

import shadow.ShadowException;
import shadow.tac.nodes.TACAllocateVariable;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCallFinallyFunction;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACCatch;
import shadow.tac.nodes.TACCatchPad;
import shadow.tac.nodes.TACCatchRet;
import shadow.tac.nodes.TACChangeReferenceCount;
import shadow.tac.nodes.TACClass;
import shadow.tac.nodes.TACClass.TACClassData;
import shadow.tac.nodes.TACClass.TACMethodTable;
import shadow.tac.nodes.TACCleanupPad;
import shadow.tac.nodes.TACCleanupRet;
import shadow.tac.nodes.TACCopyMemory;
import shadow.tac.nodes.TACLabel;
import shadow.tac.nodes.TACLabelAddress;
import shadow.tac.nodes.TACLandingPad;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACLoad;
import shadow.tac.nodes.TACLocalEscape;
import shadow.tac.nodes.TACLocalLoad;
import shadow.tac.nodes.TACLocalRecover;
import shadow.tac.nodes.TACLocalStore;
import shadow.tac.nodes.TACLongToPointer;
import shadow.tac.nodes.TACMethodName;
import shadow.tac.nodes.TACMethodPointer;
import shadow.tac.nodes.TACNewArray;
import shadow.tac.nodes.TACNewObject;
import shadow.tac.nodes.TACParameter;
import shadow.tac.nodes.TACPhi;
import shadow.tac.nodes.TACPointerToLong;
import shadow.tac.nodes.TACResume;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACSequenceElement;
import shadow.tac.nodes.TACStore;
import shadow.tac.nodes.TACThrow;
import shadow.tac.nodes.TACTypeId;
import shadow.tac.nodes.TACUnary;

public interface TACVisitor {	
	public abstract void visit(TACAllocateVariable node) throws ShadowException;	
	public abstract void visit(TACBinary node) throws ShadowException;	
	public abstract void visit(TACBlock node) throws ShadowException;
	public abstract void visit(TACBranch node) throws ShadowException;
	public abstract void visit(TACCall node) throws ShadowException;
	public abstract void visit(TACCallFinallyFunction node) throws ShadowException;
	public abstract void visit(TACCast node) throws ShadowException;
	public abstract void visit(TACCatch node) throws ShadowException;
	public abstract void visit(TACCatchPad node) throws ShadowException;
	public abstract void visit(TACCatchRet node) throws ShadowException;
	public abstract void visit(TACClass node) throws ShadowException;
	public abstract void visit(TACClassData node) throws ShadowException;
	public abstract void visit(TACCleanupPad node) throws ShadowException;
	public abstract void visit(TACCopyMemory node) throws ShadowException;
	public abstract void visit(TACChangeReferenceCount node) throws ShadowException;
	public abstract void visit(TACLabel node) throws ShadowException;	
	public abstract void visit(TACLabelAddress node) throws ShadowException;
	public abstract void visit(TACLandingPad node) throws ShadowException;
	public abstract void visit(TACLiteral node) throws ShadowException;
	public abstract void visit(TACLoad node) throws ShadowException;
	public abstract void visit(TACLocalEscape node) throws ShadowException;
	public abstract void visit(TACLocalLoad node) throws ShadowException;
	public abstract void visit(TACLocalRecover node) throws ShadowException;
	public abstract void visit(TACLocalStore node) throws ShadowException;
	public abstract void visit(TACLongToPointer node) throws ShadowException;
	public abstract void visit(TACMethodName node) throws ShadowException;
	public abstract void visit(TACMethodPointer node) throws ShadowException;
	public abstract void visit(TACMethodTable tacMethodTable) throws ShadowException;
	public abstract void visit(TACNewArray node) throws ShadowException;
	public abstract void visit(TACNewObject node) throws ShadowException;
	public abstract void visit(TACPhi node) throws ShadowException;	
	public abstract void visit(TACPointerToLong node) throws ShadowException;
	public abstract void visit(TACResume node) throws ShadowException;
	public abstract void visit(TACSequenceElement node) throws ShadowException;
	public abstract void visit(TACCleanupRet node) throws ShadowException;
	public abstract void visit(TACReturn node) throws ShadowException;	
	public abstract void visit(TACSequence node) throws ShadowException;
	public abstract void visit(TACStore node) throws ShadowException;
	public abstract void visit(TACThrow node) throws ShadowException;
	public abstract void visit(TACTypeId node) throws ShadowException;
	public abstract void visit(TACUnary node) throws ShadowException;	
	public abstract void visit(TACParameter node) throws ShadowException;
}
