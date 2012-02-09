package model;

import java.util.HashSet;

/**
 * AbstractOperation, the atomic node in work flow. 
 * Rather than the real-world-operation, it is just used for designing the workflow.
 * @author liu.zhenxing
 *
 */
public class AbstractOperation {
	/** A mark indicate the operation's functional attribute. */
	private String mFunction;
	/** The operation's name*/
	private String mName;	
	/** The precede nodes list*/
	private HashSet<AbstractOperation> mPrecedeSet;
	/** The succeede nodes list*/
	private HashSet<AbstractOperation> mSucceedeSet;
	
	public AbstractOperation(String function, String name){
		mFunction 		= function;
		mName 	 	 	= name;
		mPrecedeSet 	= new HashSet<AbstractOperation>();
		mSucceedeSet 	= new HashSet<AbstractOperation>();
	}
	/**
	 * Get name
	 * @return
	 */
	public String getName(){
		return mName;
	}
	/**
	 * Get function
	 * @return
	 */
	public String getFunction(){
		return mFunction;
	}
	/**
	 * check whether the given operation's function equals to current one.
	 * @param operation
	 * @return
	 */
	public boolean isSameFunction(AbstractOperation operation){
		return mFunction.equals(operation.getFunction());
	}
	/**
	 * check whether the given operation is one of the succeede node of current one.
	 * @param operation
	 * @return
	 */
	public boolean isSucceede(AbstractOperation operation){
		return mSucceedeSet.contains(operation);
	}
	/**
	 *  check whether the given operation is one of the precede node of current one.
	 * @param operation
	 * @return
	 */
	public boolean isPrecede(AbstractOperation operation){
		return mPrecedeSet.contains(operation);
	}
	/**
	 * Add a precede node
	 * @param operation
	 */
	public void addPrecede(AbstractOperation operation){
		mPrecedeSet.add(operation);
	}
	/**
	 * return the precede set for iterating.
	 * You can not modify it during iterating.
	 * @return
	 */
	public final HashSet<AbstractOperation> getPrecedeSet(){
		return mPrecedeSet;
	}
	/**
	 * return the succeede set for iterating.
	 * You can not modify it during iterating.
	 * @return
	 */
	public final HashSet<AbstractOperation> getSucceedeSet(){
		return mSucceedeSet;
	}
	/**
	 * Add a succeede node
	 * @param operation
	 */
	public void addSucceede(AbstractOperation operation){
		mSucceedeSet.add(operation);
	}
	/**
	 * Check whether this is a start operation(without precede node)
	 * @return
	 */
	public boolean isStartOperation(){
		return mPrecedeSet.size() == 0;
	}
	/**
	 * Check whether this is a end operation(without suceede node)
	 * @return
	 */
	public boolean isEndOperation(){
		return mSucceedeSet.size() == 0;
	}
	/**
	 * Override toString to simplify output.
	 */
	@Override
	public String toString(){
		return mName;
	}
	
}