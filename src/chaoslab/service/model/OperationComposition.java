package chaoslab.service.model;

import java.util.ArrayList;

/**
 * OperationCompostition is a queue of Operation 
 * @author liu.zhenxing
 *
 */
public class OperationComposition {
	/** the operation list*/
	private ArrayList<ConcreteOperation> mOperationList;
	/** record the composition's total qos*/
	private QoS[] mQoSList;
	/** */
	private boolean mIsValid = true;
	public OperationComposition(){
		mOperationList = new ArrayList<ConcreteOperation>();
	}
	
	public OperationComposition(OperationComposition oc){
		mOperationList = new ArrayList<ConcreteOperation>(oc.mOperationList.size());
		for (ConcreteOperation op : oc.mOperationList){
			mOperationList.add(op);
		}
	}
	
	public void addOperation(ConcreteOperation op){
		mOperationList.add(op);	
		addQoS(op.getQoSList());
	}
	
	/**
	 * add composition's qos value when insert an op.
	 * @param op
	 */
	private void addQoS(final QoS[] qosList){
		if (mQoSList == null){
			mQoSList = new QoS[qosList.length];
			for (int i = 0; i < qosList.length; ++i){
				mQoSList[i] = qosList[i];
			}
		}else{
			if (mQoSList.length != qosList.length){
				throw new RuntimeException("Runtime Error:QoS's dimension not equals!");
			}else{
				for (int i = 0; i < mQoSList.length; ++i){
					mQoSList[i].add(qosList[i]);
				}
			}
			
		}
	}
	
	public void removeOperation(int index){
		final QoS[] qosList = mOperationList.get(index).getQoSList();
		for (int i = 0; i < mQoSList.length; ++i){
			mQoSList[i].remove(qosList[i]);
		}
		
		mOperationList.remove(index);
	}
	
	public final QoS[] getQoS(){
		return mQoSList;
	}
	
	/**
	 * validate whether this composition is feasible due to the service.
	 * @return
	 */
	public boolean validate(){
		for (int i = 0 ; i < mOperationList.size() - 1; ++i){			
			if (!mOperationList.get(i).isAcceptable() 
				&& mOperationList.get(i + 1).getService() != mOperationList.get(i).getService()){
				mIsValid = false;
				return mIsValid;
			}
		}
		//check the last operation
		return mOperationList.get(mOperationList.size() - 1).isAcceptable();
		
	}
	
	/**
	 * check wheter op can add in
	 * @return
	 */
	public boolean validate(ConcreteOperation op){
		//the last operation in current composition
		if (mOperationList.size() == 0)
			return true;
		ConcreteOperation lastOp = mOperationList.get(mOperationList.size() - 1); 
		if (lastOp.getService() != op.getService()
				&& !lastOp.isAcceptable())
			return false;
		return true;
	}
	public boolean isValid(){
		return mIsValid;
	}
	
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for (ConcreteOperation op : mOperationList){
			sb.append(op.toString());
			sb.append("->");
		}
		sb.append("END");
		return sb.toString();
	}
}
