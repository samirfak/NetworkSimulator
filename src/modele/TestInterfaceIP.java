package modele;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestInterfaceIP {

	private IPEquipment equipement1;
	private IPEquipment equipement2;
	private IPInterface interface1;
	private IPInterface interface2;
	private IPAddress adresse_interface1;
	private IPAddress adresse_interface2;
	
	@Before
	public void setUp() throws Exception {
		this.equipement1 = new IPEquipment("Ordinateur 1", false);
		this.equipement2 = new IPEquipment("Ordinateur 2", false);
		this.adresse_interface1 = new IPAddress("192.168.0.1", "255.255.0.0");
		this.adresse_interface2 = new IPAddress("192.168.0.2", "255.255.0.0");
		this.interface1 = new IPInterface("Ethernet", this.adresse_interface1, this.equipement1, 10);
		this.interface2 = new IPInterface("Wifi", this.adresse_interface2, this.equipement2, 100);
		@SuppressWarnings("unused")
		IPLink lien_interfaces = new IPLink(this.interface1, this.interface2, 100);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructerNomNull() throws AlreadyExistException {
		new IPInterface(null, this.adresse_interface1, this.equipement1, 10);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructeurEquipementNull() throws AlreadyExistException {
		new IPInterface("Ethernet", this.adresse_interface1, null, 100);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructeurAdresseNull() throws AlreadyExistException {
		new IPInterface("Ethernet", null, this.equipement1, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructeurbandwidthNul() throws AlreadyExistException {
		new IPInterface("Ethernet", this.adresse_interface1, this.equipement2, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructeurbandwidthNegatif() throws AlreadyExistException {
		new IPInterface("Ethernet", this.adresse_interface1, this.equipement2, -7);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructeurbandwidthSup100() throws AlreadyExistException {
		new IPInterface("Ethernet", this.adresse_interface1, this.equipement2, 259);
	}
	
	@Test
	public void testGetAdresse() {
		assertEquals(this.adresse_interface1, this.interface1.getAddress());
	}
	
	@Test
	public void testGetNom() {
		assertEquals("Ethernet", this.interface1.getName());
	}

	@Test
	public void testGetBandwidth(){
		assertEquals("Bandwidth", 100, this.interface2.getInterfaceBandwidth(), 0);
	}

	@Test
	public void testSetInterfaceBandwidthCorrect(){
		this.interface1.setInterfaceBandwidth(75);
		assertEquals("bandwidth", 75, this.interface1.getInterfaceBandwidth(), 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetInterfaceBandwidthNegatif(){
		this.interface1.setInterfaceBandwidth(-75);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetInterfaceBandwidthSup100(){
		this.interface1.setInterfaceBandwidth(175);
	}
	
	@Test 
	public void testEnvoyerCorrect() throws DestinationUnreachableException, PacketDroppedException, NotFoundException {
		this.interface1.send(new IPMessage(this.adresse_interface1, this.adresse_interface2,"Salut"));
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testEnvoyerMessageNull()
			throws DestinationUnreachableException, PacketDroppedException, NotFoundException {
		assertTrue(this.interface1.send(null));
	}
	
	@Test(expected=PacketDroppedException.class) 
	public void testEnvoyerMauvaiseSource()
			throws DestinationUnreachableException, PacketDroppedException, NotFoundException {
		this.interface1.send(new IPMessage(this.adresse_interface2, this.adresse_interface1,"Salut"));
	}
	
	@Test(expected = DestinationUnreachableException.class)
	public void testEnvoyerPasDeLien() throws AlreadyExistException,
			PacketDroppedException, NotFoundException, DestinationUnreachableException {
		IPInterface interfaceTemporaire = new IPInterface("eth2", this.adresse_interface1, this.equipement1, 6);
		interfaceTemporaire.send(new IPMessage(this.adresse_interface1, this.adresse_interface2,"Salut"));
	}
	
	@Test
	public void testRecevoirCorrect()
			throws PacketDroppedException, NotFoundException, DestinationUnreachableException {
		assertTrue(this.interface1.receive(new IPMessage(this.adresse_interface1, this.adresse_interface1, "Salut")));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRecevoirMessageNull()
			throws PacketDroppedException, NotFoundException, DestinationUnreachableException {
		this.interface1.receive(null);
	}

	@Test(expected = PacketDroppedException.class)
	public void testRecevoirMauvaiseDestination()
			throws PacketDroppedException, NotFoundException, DestinationUnreachableException {
		this.interface1.receive(new IPMessage(this.adresse_interface1, this.adresse_interface2, "Salut"));
	}
	
}
