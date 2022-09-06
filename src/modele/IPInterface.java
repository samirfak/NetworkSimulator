package modele;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

/**
 * IP interface Representation
 * @author Mathieu TEISSEDRE et Ignace CISS
 *
 */
public class IPInterface implements Serializable {
    // we consider a fastEthernet interface, it means max interface bandwidth is 100 Mbps

        /** Clé de hachage */
        private static final long serialVersionUID = 1L;
    
	/**
	 * IP Interface name
	 */
	private String name;
	/**
	 * Connected network interface
	 */
	private IPLink link;
	/**
	 * IP Interface address
	 */
	private IPAddress address;
	/**
	 * Received message list
	 */
	private List<IPMessage> receivedMessage;
	/**
	 * Equipment with this IP interface
	 */
	private IPEquipment equipment;
	/**
	 * Interface bandwidth
	 */
	private double interfaceBandwidth;


	/**
	 * Build an IP Interface, interface register himself to equipment
	 * @param nom Interface name
	 * @param address Interface address
	 * @param equipment Interface owner
	 * @throws AlreadyExistException If the name of the interface already exist in the equipment
	 * @throws IllegalArgumentException If one of the parameter is null
	 */
	public IPInterface(String nom, IPAddress address, IPEquipment equipment, double bandwidth) throws AlreadyExistException {
		if (address == null) {
			throw new IllegalArgumentException("Address must be not null");
		}
		if (nom == null) {
			throw new IllegalArgumentException("Name must be not null");
		}
		if (equipment == null) {
			throw new IllegalArgumentException("Equipment must be not null");
		}
		if (bandwidth <=0.0 || bandwidth > 100){
			throw new IllegalArgumentException("Bandwidth must be positive and less than 100 Mbps");
		}
		this.name = nom;
		this.link = null;
		this.address = address;
		this.equipment = equipment;
		this.receivedMessage = new ArrayList<IPMessage>();
		this.equipment.addInterface(this);
		this.interfaceBandwidth= bandwidth;
	}

	/**
	 * Add a link between two interfaces
	 * @param link Link between interfaces
	 * @throws IlleagalArgumentException If the link is null
	 */
	protected void addLink(IPLink link) {
		if (link == null) {
			throw new IllegalArgumentException("Interface must be not null");
		}
		this.link = link;
		int extremites;
		if(link.getEnds()[0] == this) {
			extremites = 1;
		}
		else {
			extremites = 0;
		}
		if(link.getEnds()[extremites].isLinkedToRouter()) {
			this.equipment.getRoutingTable().addRoute(link.getEnds()[extremites].address.getNetworkAddress(), link.getEnds()[extremites].address.getNetworkMask(), this);
		}
		else {
			this.equipment.getRoutingTable().addRoute(link.getEnds()[extremites].address.getIPAddress(), "255.255.255.255", this);
		}


	}

	/**
	 * Remove link
	 */
	protected void removeLink() {
		this.link = null;
	}

	/**
	 * Get connected link
	 * @return Link or null if link is not connected
	 */
	protected IPLink getLink() {
		return this.link;
	}

	/**
	 * Say if the equipment is a router
	 * @return true if the equipment is a router
	 */
	public boolean isLinkedToRouter() {
		return this.equipment.isRouter();
	}


	/**
	 * Get IP address
	 * @return IP address
	 */
	public IPAddress getAddress() {
		return this.address;
	}

	/**
	 * Get interface name
	 * @return interface name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Send a message to the other interface
	 * @param message send message
	 * @return True if the message has been send, false else
	 * @throws IllegalArgumentException if the message is null
	 * @throws DestinationUnreachableException Message can't be send
	 * @throws NotFoundException
	 * @throws PacketDroppedException Packet has been dropped by computer
	 */
	public boolean send(IPMessage message)
			throws DestinationUnreachableException, PacketDroppedException, NotFoundException {
		if (message == null) {
			throw new IllegalArgumentException("Message can't be null");
		}
		if (this.link == null) {
			throw new DestinationUnreachableException("Message can't be sent");
		}
		else {
			double transmissionTime = ((IPMessage.getHeaderLength()*8 + message.getPayloadLength()*8)
					/ (this.getInterfaceBandwidth()*Math.pow(10, 6)));
			System.out.println(this.name + " : Message has been sent, size = " + message.getPayloadLength()+ " Bytes");
			message.addResultText(this.name + " : Message has been sent, size = " + message.getPayloadLength()+ " Bytes \n");
			System.out.println("Transmission Time: "+ transmissionTime + " s");
			message.addResultText("Transmission Time: "+ transmissionTime + " s \n");
			return this.link.send(message, this);

		}
	}

	/**
	 * Receive a message from an other IP interface
	 * @param message received message
	 * @return True if the message has been received, false else
	 * @throws PacketDroppedException packet has been dropped by the equipment
	 * @throws NotFoundException if route can't be found
	 * @throws DestinationUnreachableException
	 * @throws IllegalArgumentException if message is null
	 */
	public boolean receive(IPMessage message)
			throws PacketDroppedException, NotFoundException, DestinationUnreachableException {
		if(message == null) {
			throw new IllegalArgumentException("Message must be not null");
		}
		this.receivedMessage.add(message);
		System.out.println(this.name + " : Message received");
		message.addResultText(this.name + " : Message received \n");
		return this.equipment.receive(message);
	}

	/**
	 * Get interface bandwidth
	 * @return bandwidth of the interface
	 */
	public double getInterfaceBandwidth(){
		return this.interfaceBandwidth;
	}

	/**
	 * Modify bandwidth interface
	 * @param bandwidth new bandwidth
	 * @throws IllegalArgumentException If bandwidth is negative or null
	 */
	public void setInterfaceBandwidth(double bandwidth){
		if(bandwidth <= 0.0 || bandwidth >100){
			throw new IllegalArgumentException("Interface bandwidth must be positive and less than 100 Mbps");
		}
		this.interfaceBandwidth = bandwidth;
	}

    /**
     * Setter of Name
     * @param name the name to set
     * @throws AlreadyExistException
     */
    public void setName(String name) throws AlreadyExistException {
        if (name == null) {
            throw new IllegalArgumentException();
        }

        try {
            this.equipment.getInterfaceByName(name);
            throw new AlreadyExistException("Interface already exists !");
        } catch(NotFoundException e) {
            this.name = name;
        }
    }

    /**
     * Setter of IPAddress
     * @param address the address to set
     */
    public void setAddress(IPAddress address) {
        if (address == null) {
            throw new IllegalArgumentException();
        }
        this.address = address;
    }


    @Override
    public String toString() {
    	Formatter f = new Formatter();
    	f.format("%15s %15s\n", this.name, this.address.getIPAddress());
    	String represent = f.toString();
    	f.close();
    	return represent;
    }
}
