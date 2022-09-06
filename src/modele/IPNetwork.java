package modele;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * IP Network representation
 * @author Mathieu TEISSEDRE
 *
 */
public class IPNetwork implements Serializable {
	
	/** Clé de hachage */
        private static final long serialVersionUID = 1L;

        /** List of IPEquipment belonging to the IPNetwork */
	private List<IPEquipment> equipmentList;
	
	/** List of IPLink inside the IPNetwork */
	private List<IPLink> linkList;
	
	/** Counter of equipment to generate the name */
	private static int number = 0;
	
	/**
	 * Build a IP Network
	 */
	public IPNetwork() {
		this.equipmentList = new LinkedList<IPEquipment>();
		this.linkList = new LinkedList<IPLink>();
	}
	
	/**
	 * Add an IP equipment 
	 * @param name Equipment name
	 * @param isRouter If equipment is a router
	 * @throws IllegalArgumentException If equipment name is null
	 */
	public void addIPEquipment(String name, boolean isRouter) {
		this.equipmentList.add(new IPEquipment(name, isRouter));
	}
	
	/**
	 * Add an IP equipment
	 * @param equipment New IP Equipment
	 * @throws AlreadyExistException If an equipment already have the same name in the network
	 * @throws IllegalArgumentException If equipment is null
	 */
	public void addIPEquipment(IPEquipment equipment) throws AlreadyExistException, IllegalArgumentException {
		if(equipment == null) {
			throw new IllegalArgumentException("Equipment must be not null");
		}
		boolean trouve = false;
		for (IPEquipment element : this.equipmentList) {
			if(element == equipment) {
				trouve = true;
			}
		}
		if (trouve) {
			throw new AlreadyExistException("Equipment with the same name already exist in the network");
		}
		else {
			this.equipmentList.add(equipment);
		}
	}
	
	/**
	 * Remove an equipment
	 * @param name Equipment name
	 * @throws NotFoundException If equipment name not in the network
	 * @throws IllegalArgumentException If name is null
	 */
	public void removeIPEquipment(String name) throws NotFoundException, IllegalArgumentException {
		IPEquipment equipment = this.getIPEquipment(name);
		if (equipment == null) {
			throw new NotFoundException("Equipment can't be found");
		}
		else {
			this.equipmentList.remove(equipment);
		}
	}
	
	/**
	 * Get an IP Equipment
	 * @param name Equipment name
	 * @return Equipment or null if Equipment can't be found
	 * @throws IllegalArgumentException If name is null
	 */
	public IPEquipment getIPEquipment(String name) throws IllegalArgumentException{
		if (name == null) {
			throw new IllegalArgumentException("Name must be not null");
		}
		IPEquipment equipment = null;
		for(IPEquipment element : this.equipmentList) {
			if (element.getName().equals(name)) {
				equipment = element;
			}
		}
		return equipment;
	}
	
	/**
	 * Get IP equipment name list
	 * @return IP equipment name list
	 */
	public List<String> getIPEquipmentNameList() {
		List<String> list = new LinkedList<String>();
		for(IPEquipment element : this.equipmentList) {
			list.add(element.getName());
		}
		return list;
	}

	/**
	 * Get the number of equipments in a network
	 * @return Number of equipments
	 */
	public int getIPEquipementNumber(){
		return this.equipmentList.size();
	}
	
	/**
	 * Add a link between two interfaces
	 * @param equipement1 Equipment
	 * @param interfaceName1 Equipment 1 interface
	 * @param equipement2 Equipment
	 * @param interfaceName2 Equipment 2 interface
	 * @param bandwidth Link bandwidth
	 * @param linkLength Link length
	 * @throws AlreadyExistException If interfaces are already connected
	 * @throws NotFoundException If any interfacas correspond to the string
	 */
	public void addLink(IPEquipment equipement1, String interfaceName1, IPEquipment equipement2, String interfaceName2,  int linkLength) throws AlreadyExistException, NotFoundException {
		IPInterface interface1 = equipement1.getInterfaceByName(interfaceName1);
		IPInterface interface2 = equipement2.getInterfaceByName(interfaceName2);
		if(interface1.getLink() != null || interface2.getLink() != null) {
			throw new AlreadyExistException("This interface is already connected");
		}
		IPLink link = new IPLink(interface1, interface2, linkLength);
		this.linkList.add(link);
	}
	
	/**
	 * Remove a link between two interfaces
	 * @param equipement1 Equipment
	 * @param interfaceName1 Interface of the Equipment 1
	 * @param equipement2 Equipment
	 * @param interfaceName2 Interface of the Equipment 2
	 * @throws NotFoundException If one of the interface is not found
	 */
	public void removeLink(IPEquipment equipement1, String interfaceName1, IPEquipment equipement2, String interfaceName2) throws NotFoundException {
		IPInterface interface1 = equipement1.getInterfaceByName(interfaceName1);
		IPInterface interface2 = equipement2.getInterfaceByName(interfaceName2);
		for (IPLink link : this.linkList) {
			IPInterface[] interfaces = link.getEnds();
			if (interfaces.length == 2 && ( interfaces[0] == interface1 && interfaces[1] == interface2 || interfaces[0] == interface2 && interfaces[1] == interface1 )) {
				interface1.removeLink();
				interface2.removeLink();
				this.linkList.remove(link);
			}
		}
	}
	
	/**
	 * Remove a link between two interfaces
	 * @param link to remove
	 */
	public void removeLink(IPLink link) {
	    for (int i = 0; i < link.getEnds().length; i++) {
	        link.getEnds()[i].removeLink();
	    }
	    this.linkList.remove(link);
	}
	
	/**
	 * Get the list of the Links
	 * @return Link List
	 */
	public List<IPLink> getLinks() {
		return this.linkList;
	}
	
	/**
	 * Generate an equipment name
	 * @return generated name
	 */
	public static String generateName() {
        IPNetwork.number++;
        return "Equipment" + IPNetwork.number; 
	}
	
	/**
	 * Change la variable de classe
	 * @param newNumber nouvel entier
	 */
	public static void setNumber(int newNumber) {
	    number = newNumber;
	}
	
	/**
	 * Change the name of an equipment
	 * @param equipmentToChange equipment to change
	 * @param newName the new name of the equipment
	 * @throws AlreadyExistException if the new name already exists
	 */
	public void setEquipmentName(IPEquipment equipmentToChange, String newName) throws AlreadyExistException {
	    for (IPEquipment equipment : equipmentList) {
	        // If the new name already exists
	        if (equipmentToChange != equipment && newName.equals(equipment.getName())) {
	            throw new AlreadyExistException("Equipment with the same name already exist in the network");
	        }
	    }
	    // Ok we can change the name
	    equipmentToChange.setName(newName);
	}
	
	
	/**
	 * Sérialiser l'objet courant pour pouvoir sauvegarder la modélisation
	 * d'un utilisateur
	 * @param nomFichier nom du fichier dans lequel le reseau sera
	 *                   sauvegardée
	 */
	public void serialiser(String nomFichier) {
	    // Creation et ecriture dans le fichier
	    try {

	        // Declaration et creation du fichier qui recevra les objets
	        ObjectOutputStream fluxEcriture = new ObjectOutputStream(new FileOutputStream(nomFichier));

	        // Ecriture de l'objet courant dans le fichier
	        fluxEcriture.writeObject(this);

	        // Fermeture du fichier
	        fluxEcriture.close();

	    } catch(IOException e) {
	        System.out.println("Probleme d'acces au fichier" + nomFichier);
	    }
        }
	
	/**
	 * Retourne l'objet lu dans le fichier
	 * @param nomFichier nom du fichier
	 * @return le network lu
	 */
	public static IPNetwork deserialiser(String nomFichier) {

	    IPNetwork networkLu = new IPNetwork();
	    
	    // declaration du fichier et mecture dans le fichier
	    try {

	        // Declaration et ouverture du fichier qui contient les objets
	        ObjectInputStream fluxLecture = new ObjectInputStream(new FileInputStream(nomFichier));

	        networkLu = (IPNetwork) fluxLecture.readObject();
	        
	        // Fermeture du fichier
	        fluxLecture.close();
	        
	        return networkLu;

	    } catch (IOException e) {
	        // Probleme fichier
	        System.out.println("Probleme d'acces au fichier " + nomFichier);
	    } catch (ClassNotFoundException e) {
	        // exception levee si l'objet lu n'est pas de type Eleve
	        System.out.println("Probleme lors de la lecture du fichier " + nomFichier);
	    }
            return null;
	}

	/**
	 * Tester la sérialisation
	 * @param args
	 */
	public static void main(String[] args) {
	    IPNetwork network = new IPNetwork();
	    network.serialiser("/home/n7student/Bureau/mon_reseau.netsim");
	}
}
