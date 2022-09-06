package modele;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestEquipementIP {

	private IPEquipment equipement1;
	private IPAddress adresseEquipement1;
	private IPEquipment equipement2;
	private IPAddress adresseEquipement2;
	private IPInterface interface2;
	private IPAddress adresseEquipement22;
	private IPInterface interface22;
	private IPInterface interface1;
	private IPLink link;
	
	@Before
	public void setUp() throws Exception {
		this.equipement1 = new IPEquipment("Ordinateur 1", false);
		this.equipement2 = new IPEquipment("Ordinateur 2", false);
		this.adresseEquipement1 = new IPAddress("192.168.0.1", "255.255.0.0");
		this.adresseEquipement2 = new IPAddress("192.168.0.2", "255.255.0.0");
		this.adresseEquipement22 = new IPAddress("192.168.12.3", "255.255.0.0");
		this.interface1 = new IPInterface("Ethernet", this.adresseEquipement1, this.equipement1, 5);
		this.interface2 = new IPInterface("Ethernet", this.adresseEquipement2, this.equipement2, 4);
		this.interface22 = new IPInterface("Wifi", this.adresseEquipement22, this.equipement2, 2);
		this.link = new IPLink(this.interface1, this.interface2, 100);
	}
	

	@Test(expected=IllegalArgumentException.class)
	public void testConstructeurNomNull() {
		new IPEquipment(null ,false);
	}
	
	@Test
	public void testGetNom() {
		assertEquals("Ordinateur 1", this.equipement1.getName());
	}
	
	@Test
	public void testAjouterInterfaceCorrect() throws AlreadyExistException {
		this.equipement1.addInterface("Ethernet3", this.adresseEquipement1, 5);
	}

	@Test
	public void testAjouterInterfaceCorrect2() throws AlreadyExistException {
		this.equipement1.addInterface(interface22);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAjouterInterfaceNomNull() throws AlreadyExistException {
		this.equipement1.addInterface(null, this.adresseEquipement1, 5);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAjouterInterfaceAdresseNull() throws AlreadyExistException {
		this.equipement1.addInterface("Ethernet", null, 6);
	}
	
	@Test(expected=AlreadyExistException.class)
	public void testAjouterInterfaceAdresseDejaPrise() throws AlreadyExistException {
		this.equipement1.addInterface("Ethernet", this.adresseEquipement1, 3);
		this.equipement1.addInterface("Ethernet", this.adresseEquipement1, 3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAjouterInterfacebandwidthNul() throws AlreadyExistException{
		this.equipement2.addInterface("Wifi2", this.adresseEquipement22, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAjouterInterfacebandwidthNegatif() throws AlreadyExistException{
		this.equipement2.addInterface("Wifi2", this.adresseEquipement22, -3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAjouterInterfacebandwidthSup100() throws AlreadyExistException{
		this.equipement2.addInterface("Wifi2", this.adresseEquipement22, 340);
	}
	
	@Test
	public void testGetInterfaceParNomCorrect() throws AlreadyExistException, NotFoundException {
		this.equipement1.addInterface("Ethernet1", this.adresseEquipement1, 5);
		assertEquals("Ethernet1", this.equipement1.getInterfaceByName("Ethernet1").getName());
		assertEquals("192.168.0.1", this.equipement1.getInterfaceByName("Ethernet1").getAddress().getIPAddress());
		assertEquals("255.255.0.0", this.equipement1.getInterfaceByName("Ethernet1").getAddress().getNetworkMask());
		assertEquals("Bandwidth", 5, this.equipement1.getInterfaceByName("Ethernet1").getInterfaceBandwidth(), 0);
	}
	
	@Test(expected = NotFoundException.class)
	public void testGetInterfaceParNomNexistePas() throws NotFoundException {
		this.equipement1.getInterfaceByName("toto");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGetInterfaceParNomNomNull() throws NotFoundException {
		this.equipement1.getInterfaceByName(null);
	}
	
	@Test
	public void testRecevoirCorrect() throws DestinationUnreachableException, NotFoundException, PacketDroppedException {
		assertTrue(this.equipement1.receive(new IPMessage(this.adresseEquipement2, this.adresseEquipement1, "Salut")));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRecevoirMessageNull() throws DestinationUnreachableException, NotFoundException, PacketDroppedException {
		this.equipement1.receive(null);
	}
	
	@Test
	public void testEnvoyerCorrect() throws AlreadyExistException, NotFoundException, DestinationUnreachableException,
			PacketDroppedException {
		IPInterface interfaceCreer = this.equipement1.addInterface("Ethernet2", this.adresseEquipement1, 2);
		this.equipement1.getRoutingTable().addRoute("192.168.0.0", "255.255.0.0", interfaceCreer);
		@SuppressWarnings("unused")
		IPLink lien = new IPLink(interfaceCreer, this.interface2, 100);
		assertTrue(this.equipement1.send(new IPMessage(this.adresseEquipement1, this.adresseEquipement2, "Salut")));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testEnvoyerMessageNull()
			throws DestinationUnreachableException, NotFoundException, PacketDroppedException {
		this.equipement1.send(null);
	}

	@Test
	public void testEquipementHasIP(){
		assertTrue(this.equipement2.equipementHasIP(adresseEquipement22));
	}

	@Test
	public void testAddRouteCorrect() throws NotFoundException {
		this.equipement2.addRoute("200.1.0.0", "255.255.0.0", "Ethernet");
	}

	@Test(expected = NotFoundException.class)
	public void testAddRouteIncorrect() throws NotFoundException {
		this.equipement2.addRoute("200.1.0.0", "255.255.0.0", "Ethernet2");
	}

	@Test
	public void testSetDefaultRouteCorrect() throws NotFoundException {
		this.equipement2.setDefaultRoute("Ethernet");
	}

	@Test(expected = NotFoundException.class)
	public void testSetDefaultRouteIncorrect() throws NotFoundException {
		this.equipement2.setDefaultRoute("Ethernet2");
	}
	
	
	
}
