package model;

import java.util.ArrayList;
/**
 * The atomic service in real world.
 * @author liu.zhenxing
 *
 */
public class ConcreteOperation extends AbstractOperation {
	private ArrayList<QoS> mQoSList;
	/** Indicate which service this operation belongs to.*/
	private Service		   mService;
	/**
	 * Constructor
	 * @param function
	 * @param name
	 * @param qosList
	 */
	public ConcreteOperation(String function, String name, ArrayList<QoS> qosList, Service service){
		super(function, name);
		mQoSList = qosList;
		mService = service;
	}
	/**
	 * get qos list(only readable)
	 * @return
	 */
	public final ArrayList<QoS> getQoSList(){
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
