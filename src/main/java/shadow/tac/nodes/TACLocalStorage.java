package shadow.tac.nodes;

import shadow.tac.TACVariable;
import shadow.typecheck.type.Type;

public abstract class TACLocalStorage extends TACUpdate {
	
	private final TACVariable variable;
	private final int number;

	protected TACLocalStorage(TACNode node, TACVariable variable) {
		super(node);		
		this.variable = variable;
		number = getMethod().incrementVariableCounter();
	}
	
	@Override
	public Object getData() {	
		if( getUpdatedValue() == null )	
			return '%' + variable.getName() + '.' + number;		
		else
			return getUpdatedValue().getData();		
	}

	@Override
	public Type getType() {		
		return variable.getType();
	}	
	
	public TACVariable getVariable() {
		return variable;
	}
	
	public int getNumber() {
		return number;
	}
	
	@Override
	public boolean canPropagate() {
		return true;
	}
}
