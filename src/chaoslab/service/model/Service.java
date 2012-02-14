package chaoslab.service.model;

import java.util.HashSet;

/**
 * A service is composed by serval operations
 * @author liu.zhenxing
 *
 */
public class Service {
	private String mName;
	/** indicate the start operations*/
	private HashSet<ConcreteOperation> mStartOperationSet = new HashSet<ConcreteOperation>();
	/** indicate the end operations*/
	private HashSet<ConcreteOperation> mEndOperationSet = new HashSet<ConcreteOperation>();
	public Service(String name){
		mName = name;		
	}
	/**
	 * add a start operation
	 * @param operation
	 */
	public void addStartOperation(ConcreteOperation operation){
		if (operation.isStartOperation()) //ensure it is a start operation
			mStartOperationSet.add(operation);
	}
	/**
	 * add an end opertion
	 * @param operation
	 */
	public void addEndOperation(ConcreteOperation operation){
		if (operation.isEndOperation()) //ensure it is a end operation
			mEndOperationSet.add(operation);
	}
	/**
	 * return start operation set
	 * @return
	 */
	public final HashSet<ConcreteOperation> getStartOperationSet(){
		return mStartOperationSet;
	}
	/**
	 * return end operation set
	 * @return
	 */
	public final HashSet<ConcreteOperation> getEndOperationSet(){
		return mEndOperationSet;
	}
	/**
	 * return name
	 * @return
	 */
	public String getName(){
		return mName;
	}
}
