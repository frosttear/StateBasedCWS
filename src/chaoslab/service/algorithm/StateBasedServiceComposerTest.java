package chaoslab.service.algorithm;

import java.util.ArrayList;

import chaoslab.service.model.AbstractOperation;
import chaoslab.service.model.ConcreteOperation;
import chaoslab.service.model.QoS;
import chaoslab.service.model.QoS.QoSType;
import chaoslab.service.model.Service;


public class StateBasedServiceComposerTest {
	/**
	 * generate random qos values
	 * @param qosType
	 * @return
	 */
	public static QoS[] randomQoS(QoSType[] qosType){
		QoS[] qosVector = new QoS[qosType.length];
		for (int i = 0; i < qosType.length; ++i){
			qosVector[i] = new QoS(qosType[i], Math.random() * 20);
		}
		return qosVector;
	}
	/**
	 * auto test
	 * @param taskNum
	 * @param operationFactor
	 */
	public static void autoTest(int taskNum, int operationFactor){
		final QoSType[] QOS_TYPE = {QoS.QOS_TYPE_PRICE, QoS.QOS_TYPE_EXECUTE_TIME};
		final double [] weights	=  {0.3, 0.7};
		String [] taskNames = new String[taskNum];
		String [] operationNames	 = new String[taskNum];
		for (int i = 0; i < taskNum; ++i){
			taskNames[i] = "task" + (i + 1);
			operationNames[i] = "op" + (i + 1);
		}
		
		int serviceCount = 0;
		ArrayList<ConcreteOperation> operationList = new ArrayList<ConcreteOperation>();
		for (int i = 0; i < operationFactor; ++i){
			for (int j = 1; j <= taskNames.length; ++j){
				for (int k = 0; k < taskNames.length; ++k){
					if (k + j > taskNames.length)
						break;
					serviceCount++;
					Service service = new Service("s" + serviceCount);
					for (int l = k; l < k + j; l++){						
						ConcreteOperation op = 
							new ConcreteOperation(l, taskNames[l], operationNames[l], randomQoS(QOS_TYPE), service);
						
						if (l == k + j - 1)
							op.setIsAcceptable(true);
						operationList.add(op);
					}
				}
			}	 
		}
		
		ArrayList<ArrayList<ConcreteOperation>> candidates = new ArrayList<ArrayList<ConcreteOperation>>();
		for (int i = 0; i < taskNames.length; ++i){
			ArrayList<ConcreteOperation> candidateOperations = new ArrayList<ConcreteOperation>();
			candidates.add(candidateOperations);
		}
		for (ConcreteOperation op : operationList){
			candidates.get(op.getFunctionId()).add(op);
		}		
		
		StateBasedServiceComposer composer = 
			new StateBasedServiceComposer(candidates, weights, false);
		long before = System.currentTimeMillis();
		composer.findComposition();		
		System.out.println("OpNum:" + operationList.size() + ",EXECUTE TIME:" + (System.currentTimeMillis() - before));
	}
	
	public static void main(String []args){	
		for (int taskNum = 6; taskNum < 7; ++taskNum){
			for (int operationFactor = 1; operationFactor < 5; ++operationFactor)
				autoTest(taskNum, operationFactor);
		}
	}
}
