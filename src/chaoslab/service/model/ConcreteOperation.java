package chaoslab.service.model;
/**
 * The atomic service in real world.
 * @author liu.zhenxing
 *
 */
public class ConcreteOperation extends AbstractOperation {
	/** each QoS in mQoSList indicates distinct kinds of QoS*/
	private QoS []mQoSList;
	/** Indicate which service this operation belongs to.*/
	private Service		   mService;
	/**
	 * Constructor
	 * @param function
	 * @param name
	 * @param qosList
	 */
	public ConcreteOperation(int functionId,  String function, String name, QoS[] qosList, Service service){
		super(functionId, function, name);
		mQoSList = qosList;
		mService = service;
	}
	/**
	 * get qos list(only readable)
	 * @return
	 */
	public final QoS[] getQoSList(){
		return mQoSList;
	}
	/**
	 * get the service this operation belongs to.
	 * @return
	 */
	public Service getService(){
		return mService;
	}
	/**
	 * Also used for simplifying output
	 */
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer(mService.getName());
		sb.append(".");
		sb.append(super.toString());
		return sb.toString();
	}

}
