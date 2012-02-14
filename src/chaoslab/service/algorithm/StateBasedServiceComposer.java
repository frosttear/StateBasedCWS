package chaoslab.service.algorithm;

import java.util.ArrayList;

import chaoslab.service.model.ConcreteOperation;
import chaoslab.service.model.OperationComposition;
import chaoslab.service.model.QoS;
import chaoslab.service.model.QoS.QoSType;

public class StateBasedServiceComposer {
	private boolean mIsNovel = false;	
	private OperationComposition mComposition;
	/** indicates max value in each*/
	private QoS[]		mMaxQoSVector;
	private QoS[]		mMinQoSVector;
	private double[]	mQoSWeights;
	private double	mMaxScore;
	private int  mTaskNum;
	private ArrayList<ArrayList<ConcreteOperation>> mCandidateOperations;

	public  StateBasedServiceComposer( 
			ArrayList<ArrayList<ConcreteOperation>> candidateOperations,
			double[] weights, boolean isNovel){
		mTaskNum 				= candidateOperations.size();
		mCandidateOperations 	= candidateOperations;
		mQoSWeights			 	= weights;
		mIsNovel = isNovel;
		initialize();
	}
	
	/**
	 * compute max & min QoS vector
	 */
	private void initialize(){
		
		mMaxQoSVector = new QoS[mQoSWeights.length];
		mMinQoSVector = new QoS[mQoSWeights.length];
		
		for (int i = 0; i < mQoSWeights.length; ++i){
			//for each dimension, find largest qos sum
			QoSType	type = null;
			for (int j = 0; j < mCandidateOperations.size(); ++j){
				double max = Double.MIN_VALUE;
				double min = Double.MAX_VALUE;
			
				ArrayList<ConcreteOperation> candidateOperations = 
					mCandidateOperations.get(j);
				for (int k = 0; k < candidateOperations.size(); ++k){
					QoS[] qosList = candidateOperations.get(k).getQoSList();
					if (type == null){
						type = qosList[i].getType();
						
					}
					double value = qosList[i].getValue();
					if (max < value){
						max = value;
					}
					if (min > value){
						min = value;
					}
				}
				if (mMaxQoSVector[i] == null){
					mMaxQoSVector[i] = new QoS(type, 0);
					mMinQoSVector[i] = new QoS(type, 0);
				}
				mMaxQoSVector[i].add(max);
				mMinQoSVector[i].add(min);
			}
		}
		
	}
	
	public void findComposition(){
	
		int candidateIndex[] = new int[mTaskNum];
		int compositionNum = 0;
		//dps search
		int step = 0;
		//indicates the loop's end.
		boolean flag = true;
		OperationComposition currentComposition = new OperationComposition();
		while (flag){			
			ArrayList<ConcreteOperation> candidateOperations = 
				mCandidateOperations.get(step);
			while (flag && candidateIndex[step] >= candidateOperations.size()){
				candidateIndex[step] = 0;				
				step--;
				if (step < 0){
					flag = false;
				}else{
					currentComposition.removeOperation(step);
					candidateIndex[step]++;
					candidateOperations = 
						mCandidateOperations.get(step);
				}
			}
			if (!flag)
				break;
			ConcreteOperation op = candidateOperations.get(candidateIndex[step]);
			if (!mIsNovel || currentComposition.validate(op)){
				currentComposition.addOperation(op);
			}else{
				candidateIndex[step]++;				
				continue;
			}
			if (step == mTaskNum - 1){
				//get a full composition, validate it and then backward
				//System.out.print(currentComposition);
				compositionNum++;
				if (currentComposition.validate()){
					double score = getScore(currentComposition);
					if (mComposition == null || mMaxScore < score){
						mComposition = new OperationComposition(currentComposition);
						mMaxScore 	 = score;
					}
					//System.out.println("\tvalid!");
				}else{
					//System.out.println("\tinvalid!");
				}
				//backward start
				currentComposition.removeOperation(step);
				candidateIndex[step]++;
				
			}else{
				step++;	
			}
		}
		if (mComposition != null)
			System.out.println(mTaskNum + ":" + compositionNum + "," + mComposition);
		else
			System.out.println("No valid composition!");
	}
	
	/**
	 * score the composition due to qos and weights
	 * @return
	 */
	public double getScore(OperationComposition oc){
		double score = 0;
		QoS[] qosList = oc.getQoS();
		for (int i = 0; i < qosList.length; ++i){
			score += (mMaxQoSVector[i].getValue() - qosList[i].getValue()) 
			/ (mMaxQoSVector[i].getValue() - mMinQoSVector[i].getValue())
			* mQoSWeights[i];
		}
		return score;
	}
}
