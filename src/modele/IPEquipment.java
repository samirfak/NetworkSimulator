package modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.LinkedList;

/**
 * IP equipment representation
 * @author Mathieu TEISSEDRE et Ignace CISS
 */
public class IPEquipment implements Serializable {

	/** Clé de hachage */
        private static final long serialVersionUID = 1L;

        /**
	 * Equipment name
	 */
	private String name;
	
	/**
	 * Network interface collection
	 */
	private List<IPInterface> networkInterfaces;
	
	/**
	 * Received IP Message
	 */
	private List<IPMessage> receivedMessage;

	/**
	 * Equipment Type
	 */
	private boolean isRouter;

	/**
	 * Equipment routing table
	 */
	private RoutingTable routingTable;


	/**
	 * Make an IP equipment
	 * @param name Equipment name
	 * @param isRouter Equipment is a router
	 * @throws IllegalArgumentException If name is null
	 */
	public IPEquipment(String name, boolean isRouter) {
		if (name == null) {
			throw new IllegalArgumentException("Name must be not null");
		}
		this.name = name;
		this.networkInterfaces = new LinkedList<IPInterface>();
		this.receivedMessage = new ArrayList<IPMessage>();
		this.routingTable = new RoutingTable();
		this.isRouter = isRouter;
	}
	
	/**
	 * Get equipment name
	 * @return Equipment name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Change equipment name
	 * @param newName for the equipment
	 */
	public void setName(String newName) {
	    this.name = newName;
	}
	
	/**
	 * Add an IP Interface
	 * @param name Interface name
	 * @param address Interface IP Address
	 * @return L'interface IP Generated interface
	 * @throws AlreadyExistException if interface name already exist
	 * @throws IllegalArgumentException if name is null
	 */
	public IPInterface addInterface(String name, IPAddress address, double pBandwidth) throws AlreadyExistException {
		if (name == null) {
			throw new IllegalArgumentException("Name must be not null");
		}
		if (address == null) {
			throw new IllegalArgumentException("Address mus be not null");
		}
		if (this.listInterface().contains(name)) {
			throw new AlreadyExistException("This name is already taken by an other interface");
		}
		if (pBandwidth<=0 || pBandwidth > 100){
			throw new IllegalArgumentException("Interface bandwidth must be positive and less than 100Mbps");
		}
		IPInterface newInterface = new IPInterface(name, address, this, pBandwidth);
		//this.networkInterfaces.add(newInterface);
		return newInterface;
	}
	
	/**
	 * Add an IP Interface
	 * @param newInterface New IP Interface
	 * @return New IP Interface
	 * @throws AlreadyExistException If interface name already exist
	 */
	protected IPInterface addInterface(IPInterface newInterface) throws AlreadyExistException {
		if (this.listInterface().contains(newInterface.getName())) {
			throw new AlreadyExistException("This name is already taken by an other interface");
		}
		this.networkInterfaces.add(newInterface);
		return newInterface;
	}
	
	/**
	 * Remove an interface from the list
	 * @param ipinterface IP Interface
	 */
	public void removeInterface(IPInterface ipinterface) {
		if(this.networkInterfaces.contains(ipinterface)) {
			this.networkInterfaces.remove(ipinterface);
			for(Route route : this.routingTable.getRouteList()) {
				if(route.getOutInterface() == ipinterface) {
					this.routingTable.removeRoute(route.getDestAddress(), route.getDestMask(), route.getOutInterface());
				}
			}
		}
	}
	
	/**
	 * Get equipment type
	 * @return True if equipment is a router
	 */
	public boolean isRouter() {
		return this.isRouter;
	}
	
	/**
	 * Get IP interface by name
	 * @param name IP Interface name
	 * @return IP Interface
	 * @throws NotFoundException If the interface name is not contained in equipment
	 * @throws IllegalArgumentException if the name is null
	 */
	public IPInterface getInterfaceByName(String name) throws NotFoundException {
		if (name == null) {
			throw new IllegalArgumentException("Name must be not null");
		}
		for (IPInterface i : this.networkInterfaces) {
			if(i.getName().equals(name)) {
				return i;
			}
		}
		throw new NotFoundException("Given interface name doesn't exist"); 
	}

	/**
	 * Get the list of interface name
	 * @return List of interface name
	 */
	public List<String> listInterface() {
		List<String> interfaceName = new LinkedList<String>();
		for(IPInterface i : this.networkInterfaces) {
			interfaceName.add(i.getName());
		}
		return interfaceName;
	}
	
	/**
	 * Receive a message
	 * @param message IP Message
	 * @throws PacketDroppedException 
	 * @throws NotFoundException 
	 * @throws DestinationUnreachableException 
	 * @throws IllegalArgumentException if message is null
	 */
	public boolean receive(IPMessage message) throws DestinationUnreachableException, NotFoundException, PacketDroppedException {
		if (message == null) {
			throw new IllegalArgumentException("Message must be not null");
		}
		this.receivedMessage.add(message);
		System.out.println(this.name + " : Message received");
		message.addResultText(this.name + " : Message received \n");
		message.setMessageTTL(message.getMessageTTL()-1);
		if (this.isRouter()){
			message.addHopToList(this.name+" (Router)");
		}
		else{
			message.addHopToList(this.name);
		}
		
		if (message.getMessageTTL() == 0){
			message.showHopsList();
			message.addResultText("From "+ message.getSource().getIPAddress()+" to "+message.getDestination().getIPAddress()+", "+message.getNbHops()+" hops: "+message.getMessageHopsList()+" \n");
			throw new PacketDroppedException(this.name+" : TTL at 0, packet dropped !");
		}
		System.out.println("TTL at "+message.getMessageTTL()+", "+message.getNbHops()+" hop(s)");
		message.addResultText("TTL at "+message.getMessageTTL()+", "+message.getNbHops()+" hop(s) \n");
		boolean found = false;
		//Search an interface correspondig to IPAdress
		for(IPInterface ipinterface : this.networkInterfaces) {
			if(ipinterface.getAddress().equals(message.getDestination())) {
				found = true;
			}
		}
		if (found) {
			System.out.println("Message Content : " + message.getContent());
			message.addResultText("Message Content : " + message.getContent()+" \n");
			message.showHopsList();
			message.addResultText("From "+ message.getSource().getIPAddress()+" to "+message.getDestination().getIPAddress()+", "+message.getNbHops()+" hops: "+message.getMessageHopsList()+" \n");
			return true;
		}
		else {
			if(this.isRouter) {
				return this.send(message);
			}
			else {
				throw new PacketDroppedException("Destination error, packet dropped");
			}
		}
	}
	
	/**
	 * Send a message
	 * @param message IP message
	 * @return true if the message has been received, false else
	 * @throws DestinationUnreachableException if cannot found correct interface
	 * @throws NotFoundException if research in routing table return no result
	 * @throws PacketDroppedException
	 * @throws IllegalArgumentException message is null
	 */
	public boolean send(IPMessage message)
			throws DestinationUnreachableException, NotFoundException, PacketDroppedException {
		if (message == null) {
			throw new IllegalArgumentException("Message must be not null");
		}
		IPInterface interfaceEnvoie = this.routingTable.getInterfaceByIP(message.getDestination());
		if (interfaceEnvoie != null && this.listInterface().contains(interfaceEnvoie.getName())) {
			System.out.println(this.name + " : Message has been sent");
			message.addResultText(this.name + " : Message has been sent \n");
			return interfaceEnvoie.send(message);
		}
		else {
			throw new DestinationUnreachableException("Message can't be sent");
		}
	}

	/**
	 * Get IP equipment routing table
	 * @return Routing table
	 */
	public RoutingTable getRoutingTable(){
		return this.routingTable;
	}
	
	/**
	 * Modify IP equipment routing table
	 * @param table Table de routage
	 * @throws IllegalArgumentException is routing table is null
	 */
	public void setRoutingTable(RoutingTable table) {
		if (table == null) {
			throw new IllegalArgumentException("Routing table must be not null");
		}
		this.routingTable = table;
	}

	/**
	 * Verify if equipment has an IP Address
	 * @param address IP address to verify
	 * @return true if ip address is in the equipment
	 * @throws IllegalArgumentException if address is null
	 */
	public boolean equipementHasIP(IPAddress address){
		if (address == null) {
			throw new IllegalArgumentException("Address must be not null");
		}
		for(IPInterface i: this.networkInterfaces){
			if (i.getAddress().equals(address)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Add route in equipment IP routing table
	 * @param dest Destination network address
	 * @param mask Destination network mask
	 * @param out Out interface
	 * @throws NotFoundException If interface doesn't exist
	 */
	public void addRoute(String dest, String mask, String out) throws NotFoundException {
		if(!(this.listInterface().contains(out))){
			throw new NotFoundException("This interface doesn't exist");
		}
		this.routingTable.addRoute(dest, mask, this.getInterfaceByName(out));
	}

	/**
	 * Define default route in equipment IP routing table
	 * @param out default out interface
	 * @throws NotFoundException if given interface doesn't exist
	 */
	public void setDefaultRoute(String out) throws NotFoundException {
		if(!(this.listInterface().contains(out))){
			throw new NotFoundException("This interface doesn't exist");
		}
		this.routingTable.setDefaultRoute(this.getInterfaceByName(out));
	}
	
	/**
	 * Get a String which represent interfaces
	 * @return Interfaces representation
	 */
	public String showInterfaces() {
		Formatter f = new Formatter();
		f.format("%15s %15s\n", "Name", "Address");
		String interfaces = f.toString();
		for(IPInterface ipinterface : this.networkInterfaces) {
			interfaces += ipinterface.toString();
		}
		f.close();
		return interfaces;
		
	}
	
	/**
	 * Export configuration of the equipment in a Bash File
	 */
	public void exportConfig()  {

	    List<Route> routes = this.routingTable.getRouteList();
	            
	    // Ouverture du fichier
	    try (PrintWriter fichierCible = new PrintWriter(new FileWriter("config_" + this.name + ".sh"))) {

	        // Parcours de la table de routage
	        for (Route route : routes) {
	            fichierCible.println("sudo route add -net "
	                    + route.getDestAddress()
	                    + " netmask " + route.getDestMask() + " gw " 
	                    + route.getOutInterface().getAddress().getIPAddress());
	        }

	        // Fichier fermé automatiquement

	    } catch (IOException erreur) {
	        System.out.println("Probleme d'acces ou de lecture du fichier!");
	    }
	}
}
