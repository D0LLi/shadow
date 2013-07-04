package shadow.typecheck.type;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TypeParameter extends Type
{
	private Set<Type> bounds = new HashSet<Type>();

	public TypeParameter(String typeName)
	{
		super(typeName, new Modifiers(), null);
		bounds.add(Type.OBJECT);
	}
	
	public void addBound(Type type) {
		bounds.add(type);
	}
	
	public Set<Type> getBounds()
	{
		return bounds;
	}
	
	public void setBounds(Set<Type> bounds)
	{
		this.bounds = bounds;
	}

	public boolean canAccept(ModifiedType modifiedType)
	{
		Type type = modifiedType.getType();
		
		if( equals(type) )
			return true;
		
		for( Type bound : bounds )
			if( !type.isSubtype(bound) )
				return false;		
		
		return true;
	}
	
	public boolean isSubtype(Type type)
	{		
		if( equals(type) || type == Type.OBJECT )
			return true;
		
		for( Type bound : bounds )
			if( bound.isSubtype(type) )
				return true;
		
		return false;
	}
	
	public boolean equals(Object o)
	{
		if( o == Type.NULL )
			return true;
		if( o != null && o instanceof TypeParameter )
		{
			if( o == this )
				return true;
			
			TypeParameter type = (TypeParameter) o;
			
			if( type.getTypeName().equals(getTypeName()) )
			{
				if( type.bounds.size() != bounds.size() )
					return false;
								
				for( Type bound : bounds )
					if( !type.bounds.contains(bound) )
						return false;
					
				return true;
			}	
			else
				return false;
		}
		else
			return false;
	}
	
	public Type replace(SequenceType values, SequenceType replacements )
	{
		for( int i = 0; i < values.size(); i++ )
		{
			if( values.get(i).getType().getTypeName().equals(getTypeName()))
				return replacements.get(i).getType();
		}
		
		return this;
	}
	
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean withBounds) {
		StringBuilder builder = new StringBuilder(getTypeName());
		boolean first = true;
		
		if( withBounds && bounds.size() > 1 ) //always contains Object
		{	
			builder.append(" is ");
			
			for(Type bound : bounds )
			{	
				if( bound != Type.OBJECT )
				{			
					if( !first )
						builder.append(" and ");
					
					builder.append(bound.toString(withBounds));				
					first = false;
				}
			}
		}
		
		return builder.toString();
	}
	
	public List<MethodSignature> getAllMethods(String methodName)
	{
		return getMethods(methodName);
	}
	
	public List<MethodSignature> getMethods(String methodName)
	{
		Set<MethodSignature> signatures = new HashSet<MethodSignature>();
		for(Type bound : bounds )					
			signatures.addAll(bound.getMethods(methodName));
		
		return new ArrayList<MethodSignature>(signatures);
	}

	@Override
	public boolean isRecursivelyParameterized() {
		return isParameterized();
	}

	@Override
	public void printMetaFile(PrintWriter out, String linePrefix) {
		// should never get called
	}

	@Override
	public boolean isDescendentOf(Type type) {		
		// should never get called
		return false;
	}

	@Override
	public boolean hasInterface(InterfaceType type) {
		return false;
	}
}