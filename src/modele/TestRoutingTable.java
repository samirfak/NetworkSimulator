package modele;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestRoutingTable {

	private IPEquipment equipment;
    private IPInterface interface1;
    private IPInterface interface2;
	private RoutingTable table;
	
	@Before
	public void setUp() throws Exception {
		this.table = new RoutingTable();
		this.equipment = new IPEquipment("Ordinateur", false);
    	this.interface1 = new IPInterface("Ethernet", new IPAddress("192.168.0.1", "255.255.0.0"), this.equipment, 100);
    	this.interface2 = new IPInterface("Wifi", new IPAddress("192.170.0.1", "255.255.0.0"), this.equipment, 100);
    	this.table.addRoute("192.168.0.0", "255.255.0.0", this.interface1);
	}
	
	@Test(expected = NotFoundException.class)
	public void testRemoveRouteIntCorrect() throws NotFoundException {
		this.table.removeRoute(0);
		this.table.getInterfaceByIP(new IPAddress("192.168.0.0", "255.255.0.0"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRemoveRouteIntIndexTropPetit() {
		this.table.removeRoute(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRemoveRouteIntIndexTropGrand() {
		this.table.removeRoute(10);
	}
	
	@Test(expected = NotFoundException.class)
	public void testRemoveRouteStringCorrect() throws NotFoundException {
		this.table.removeRoute("192.168.0.0", "255.255.0.0", this.interface1);
		this.table.getInterfaceByIP(new IPAddress("192.168.0.0", "255.255.0.0"));
	}
	
	@Test
	public void testGetInterfaceByIPCorrect() throws NotFoundException {
		assertEquals(this.interface1, this.table.getInterfaceByIP(new IPAddress("192.168.0.0", "255.255.0.0")));
	}

	@Test(expected = NotFoundException.class)
	public void testGetDefaultRouteNexistePas() throws NotFoundException {
		this.table.getDefaultRoute();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetDefaultRouteNull() throws NotFoundException {
		this.table.setDefaultRoute(null);
	}

	@Test
	public void testGetInterfaceByIPCorrectParDefault() throws NotFoundException {
		this.table.setDefaultRoute(this.interface2);
		assertEquals(this.interface2, this.table.getInterfaceByIP(new IPAddress("192.169.0.0", "255.255.0.0")));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetInterfaceByIPAdresseNull() throws NotFoundException {
		this.table.getInterfaceByIP(null);
	}
	
	@Test(expected = NotFoundException.class)
	public void testGetInterfaceByIPAdresseNotFound() throws NotFoundException {
		assertNull(this.table.getInterfaceByIP(new IPAddress("200.168.0.0", "255.255.0.0")));
	}

	
	
}
