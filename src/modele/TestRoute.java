package modele;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestRoute {
    private IPEquipment equipment;
    private IPInterface interface1;
    private IPInterface interface2;
	private Route route1;
    private Route route2;

    @Before
    public void setUp() throws Exception {
    	this.equipment = new IPEquipment("Ordinateur", false);
    	this.interface1 = new IPInterface("Ethernet", new IPAddress("192.168.0.1", "255.255.0.0"), this.equipment, 100);
    	this.interface2 = new IPInterface("Wifi", new IPAddress("192.168.30.1", "255.255.255.0"), this.equipment, 100);
        this.route1 = new Route("192.168.0.0", "255.255.0.0", this.interface1);
        this.route2 = new Route("192.168.30.0", "255.255.255.0", this.interface2);
    }

    /**
     * Test d'une route non valide
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRouteNonValide(){
        new Route("192.168.30", "255.255.255.0", this.interface1);
    }

    /**
     * Test de getDestNetwork
     */
    @Test
    public void testGetDestNetwork(){
        assertEquals("192.168.30.0", this.route2.getDestAddress());
    }

    /**
     * Test de getNetMask
     */
    @Test
    public void testGetNetMask(){
        assertEquals("255.255.0.0", this.route1.getDestMask());
    }


    /**
     * Test de getOutInterface
     */
    @Test
    public void testGetOutInterface(){
        assertEquals(this.interface1, this.route1.getOutInterface());
    }

    /**
     * Test de setDestNetwork si l'adresse donnée est correcte
     */
    @Test
    public void testSetDestNetworkCorrect() throws IllegalArgumentException{
        this.route1.setDestNetwork("8.8.0.0");
        assertEquals("8.8.0.0", this.route1.getDestAddress());
    }

    /**
     * Test de setDestNetwork si l'adresse donnée est incorrecte
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetDestNetworkIncorrecte() throws IllegalArgumentException{
        this.route1.setDestNetwork("192.168.30.5");
    }

    /**
     * Test de setNetmask si le masque donné est correcte
     */
    @Test
    public void testSetNetmaskCorrect() throws IllegalArgumentException{
        this.route1.setNetmask("255.255.255.0");
        assertEquals("255.255.255.0", this.route1.getDestMask());
    }

    /**
     * Test de setNetmask si le masque donné est incorrecte
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetNetmaskIncorrecte() throws IllegalArgumentException{
        this.route2.setNetmask("255.255.0.0");
    }


    /**
     * Test de setOutInterface avec une interface correcte
     */
    @Test
    public void testSetOutInterfaceCorrecte() throws IllegalArgumentException{
        this.route1.setOutInterface(this.interface2);
        assertEquals(this.interface2, this.route1.getOutInterface());
    }

    /**
     * Test de setOutInterface avec une interface incorrecte
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetOutInterfaceIncorrecte() throws IllegalArgumentException{
        this.route1.setOutInterface(null);
    }

    
    @Test
    public void testIsInRoute(){
        assertTrue(this.route2.isInRoute("192.168.30.45"));
        assertFalse(this.route2.isInRoute("192.168.0.45"));
    }

}