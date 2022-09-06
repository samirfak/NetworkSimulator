package modele;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestReseauxIP {

	private IPNetwork monReseau;
	
	@Before
	public void setUp() {
		this.monReseau = new IPNetwork();
		this.monReseau.addIPEquipment("Premier", false);
	}
	
	@Test
	public void testAjouterEquipementConstructionCorrect() {
		this.monReseau.addIPEquipment("Ordinateur", false);
		assertNotNull(monReseau.getIPEquipment("Ordinateur"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAjouterEquipementConstructionNomNull() throws IllegalArgumentException{
		this.monReseau.addIPEquipment(null, false);
	}
	
	@Test
	public void testAjouterEquipementExistantCorrect() throws IllegalArgumentException, AlreadyExistException {
		IPEquipment equipement = new IPEquipment("Ordinateur",false);
		this.monReseau.addIPEquipment(equipement);
		assertNotNull(monReseau.getIPEquipment("Ordinateur"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAjouterEquipementExistantNull() throws IllegalArgumentException, AlreadyExistException {
		this.monReseau.addIPEquipment(null);
	}
	
	@Test(expected=AlreadyExistException.class)
	public void testAjouterEquipementExistantDeuxFois() throws IllegalArgumentException, AlreadyExistException {
		IPEquipment equipement = new IPEquipment("Ordinateur",false);
		this.monReseau.addIPEquipment(equipement);
		this.monReseau.addIPEquipment(equipement);
	}
	
	@Test
	public void testSupprimerEquipementCorrect() throws IllegalArgumentException, NotFoundException {
		this.monReseau.removeIPEquipment("Premier");
		assertEquals(0, this.monReseau.getIPEquipmentNameList().size());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSupprimerEquipementNomNull() throws IllegalArgumentException, NotFoundException {
		this.monReseau.removeIPEquipment(null);
	}
	
	@Test(expected=NotFoundException.class)
	public void testSupprimerEquipementIntrouvable() throws IllegalArgumentException, NotFoundException {
		this.monReseau.removeIPEquipment("Toto");
	}

	@Test
	public void testGetEquipementIPCorrect() {
		assertNotNull(this.monReseau.getIPEquipment("Premier"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetEquipementIPNomNull() {
		this.monReseau.getIPEquipment(null);
	}
	
	@Test
	public void testGetListeEquipement() {
		assertEquals(1, this.monReseau.getIPEquipmentNameList().size());
	}
}
