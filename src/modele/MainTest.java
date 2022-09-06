package modele;
public class MainTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws NotFoundException, AlreadyExistException,
			DestinationUnreachableException, PacketDroppedException {
		//Creation des adresses IP
		IPAddress address1_interface1 = new IPAddress("192.168.0.1", "255.255.0.0");
		IPAddress address2_interface1 = new IPAddress("192.169.0.10", "255.255.0.0");
		IPAddress address1_interface2 = new IPAddress("192.168.0.5", "255.255.0.0");
		IPAddress address2_interface2 = new IPAddress("192.169.0.50", "255.255.0.0");
		IPAddress address1_interface3 = new IPAddress("192.169.0.30", "255.255.0.0");

		//Creation des equipements
		IPEquipment equipment1 = new IPEquipment("Ordinateur 1", false);
		IPEquipment equipment2 = new IPEquipment("Ordinateur 2", true);
		IPEquipment equipment3 = new IPEquipment("Ordinateur 3", false);

		//Creation des interfaces pour chaque equipement
		IPInterface interface1_equipment1 = new IPInterface("eth0", address1_interface1, equipment1, 20);
		IPInterface interface2_equipment1 = new IPInterface("eth1", address2_interface1, equipment1, 20);
		IPInterface interface1_equipment2 = new IPInterface("eth0", address1_interface2, equipment2, 25);
		IPInterface interface2_equipment2 = new IPInterface("eth1", address2_interface2, equipment2, 5);
		IPInterface interface1_equipment3 = new IPInterface("eth0", address1_interface3, equipment3, 5);

		//Ajout des liens entre les interfaces
		IPLink lien12 = new IPLink(interface1_equipment1, interface1_equipment2, 5);
		IPLink lien23 = new IPLink(interface2_equipment2, interface1_equipment3, 50);

		//Configuration des tables de routage
		//equipement1.getRoutingTable().addRoute("192.168.0.0", "255.255.0.0", "eth0");
		//equipement1.getRoutingTable().addRoute("192.169.0.0", "255.255.0.0", "eth1");
		//equipement2.getRoutingTable().addRoute("192.168.0.0", "255.255.0.0", "eth0");
		//equipement2.getRoutingTable().addRoute("192.169.0.0", "255.255.0.0", "eth1");

		equipment1.getRoutingTable().setDefaultRoute(interface1_equipment1);
		equipment2.getRoutingTable().setDefaultRoute(interface2_equipment2);


		equipment1.getRoutingTable().showRoute();
		equipment2.getRoutingTable().showRoute();
		equipment3.getRoutingTable().showRoute();

		//Envoie un message de l'equipement 1 vers l'equipement 2
		IPMessage testMessage = new IPMessage(address1_interface1,address1_interface3,"Salut");
		//testMessage.setMessageTTL(2);
		equipment1.send(testMessage);

		System.out.println(testMessage.getResultText());

	}
}
