package model;

/**
 * QoS, Quality Of Service.Numeric metrics for evaluating a service.
 * @author liu.zhenxing
 *
 */
public class QoS {
	/**
	 * QoSType, record type name and its positivity.
	 * @author liu.zhenxing
	 *
	 */
	public static class QoSType{
		/** The name of type.*/
		private String mName;
		/** a postivie qos means the larger the qos value is, the better it is.*/
		private boolean mIsPositive;
		/**
		 * 
		 * @param name
		 * @param isPositive
		 */
		public QoSType(String name, boolean isPositive){
			mName 		= name;
			mIsPositive = isPositive;
		}
		/** 
		 * Check whether this type is positive
		 * @return mIsPositive
		 */
		public boolean isPositive(){
			return mIsPositive;
		}
		/**
		 * Get the name of this QoS type
		 * @return mName
		 */
		public String getName(){
			return mName;
		}
	}
	/** Price type, negative*/
	public static final QoSType QOS_TYPE_PRICE = new QoSType("Price", false);
	/** ExecuteTime type, negative*/
	public static final QoSType QOS_TYPE_EXECUTE_TIME = new QoSType("ExecuteTime", false);
	/** Latence type, negative*/
	public static final QoSType QOS_TYPE_LATENCE = new QoSType("Latence", false);
	/** Reputation type, positive*/
	public static final QoSType QOS_TYPE_REPUTATION = new QoSType("Reputation", true);
	/** Avaiability type, positive*/
	public static final QoSType QOS_TYPE_AVAILABILITY = new QoSType("Avaiability", true);	
	/** type of QoS(cost, execute time, etc), can not modified after initialize*/
	private QoSType mType;
	/** The value of QoS, can be modified after initialize*/
	private double mValue;
	/**
	 * constructor
	 * @param type
	 */
	public QoS(QoSType type){
		mType = type;
	}
	/**
	 * Constructor
	 * @param type
	 * @param value
	 */
	public QoS(QoSType type, double value){
		this(type);
		setValue(value);
	}
	/**
	 * Change the value
	 * @param value
	 */
	public void setValue(double value){
		mValue = value;
	}
	/**
	 * Return the QoS's current value.
	 * NOTE:If its a positive QoS, will return -1 * mValue
	 * @return value
	 */
	public double getValue(){
		if (isPositive())
			return - mValue;
		else
			return mValue;
	}
	/**
	 * Return the QoS's type's name
	 * @return typeName
	 */
	public String getTypeName(){
		return mType.getName();
	}
	/**
	 * Return whether it is a positive attribute.
	 * @return isPositive
	 */
	public boolean isPositive(){
		return mType.isPositive();
	}

}
