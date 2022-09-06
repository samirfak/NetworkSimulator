package modele;
import java.math.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestLienIP {

	private IPEquipment equipement1;
	private IPEquipment equipement2;
	private IPInterface interface1;
	private IPInterface interface2;
	private IPInterface interface3;
	private IPLink lien12;
	private IPLink lien13;

	
	@Before
	public void setUp() throws Exception {
		this.equipement1 = new IPEquipment("Ordinateur 1", false);
		this.equipement2 = new IPEquipment("Ordinateur 2", false);
		this.interface1 = new IPInterface("eth0",new IPAddress("192.168.0.1", "255.255.0.0"), this.equipement1, 5);
		this.interface2 = new IPInterface("eth0",new IPAddress("192.168.0.2", "255.255.0.0"), this.equipement2, 10);
		this.interface3 = new IPInterface("eth1",new IPAddress("192.168.0.3", "255.255.255.0"), this.equipement2, 10);
		this.lien12 = new IPLink(this.interface1, this.interface2, 100);
		this.lien13 = new IPLink(this.interface1, this.interface3, 30);


	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructeurInterface1Null() {
		new IPLink(null, this.interface1, 10);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructeurInterface2Null() {
		new IPLink(this.interface1, null, 10);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructeurLinkLengthNegatif() {
		new IPLink(this.interface1, this.interface2, -1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructeurLinkLengthNul() {
		new IPLink(this.interface1, this.interface2, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructeurLinkLengthSup100(){
		new IPLink(this.interface1, this.interface2, 300);
	}
	
	@Test
	public void testGetExtremites() {
		assertEquals(this.interface1, this.lien12.getEnds()[0]);
		assertEquals(this.interface2, this.lien12.getEnds()[1]);
	}
	

	@Test
	public void testGetLinkLength() {
		assertEquals(100, this.lien12.getLinkLength(), 0);
		assertEquals(30, this.lien13.getLinkLength(), 0);
	}
	
	@Test
	public void testSetLinkLengthCorrect() {
		this.lien12.setLinkLength(10);
		assertEquals(10, this.lien12.getLinkLength(), 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetLinkLengthNegatif() {
		this.lien12.setLinkLength(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetLinkLengthSup100(){
		this.lien13.setLinkLength(500);
	}

	@Test
	public void testGetLinkPropagationSpeed(){
		assertEquals(Math.pow(2*10, 8), IPLink.getLinkPropagationSpeed(), 0);
	}

	@Test
	public void testGetLinkPropagationDelay(){
		assertEquals(this.lien13.getLinkLength()/IPLink.getLinkPropagationSpeed(), this.lien13.getLinkPropagationDelay(), 0);
	}
	
}
