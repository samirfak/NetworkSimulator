package modele;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestIPAddress {

    private IPAddress adresse1;
    private IPAddress adresse2;

    @Before
    public void setUp(){
        this.adresse1 = new IPAddress("192.169.0.5", "255.255.0.0");
        int[] address = {192,168,0,10};
        int[] mask = {255,255,0,0};
		this.adresse2 = new IPAddress(address, mask);
    }

    /**
     * Invalid Address Test from String
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAddressString(){
        new IPAddress("123.4.3", "255.255.0.0");
    }

    /**
     * Invalid Mask test from String
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMaskString(){
        new IPAddress("123.4.3.5", "255.255.0");
    }
    
    /**
     * Invalid Address from integer (null)
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAddressIntegerNull() {
    	int[] address = null;
    	int[] mask = {255,255,0,0};
    	new IPAddress(address, mask);
    }
    
    /**
     * Invalid Address from integer (256)
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAddressInteger256() {
    	int[] address = {256,168,0,0};
    	int[] mask = {255,255,0,0};
    	new IPAddress(address, mask);
    }
    
    /**
     * Invalid Address from integer (3 elements)
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAddressIntegerNotEnoughElements() {
    	int[] address = {192,168,0};
    	int[] mask = {255,255,0,0};
    	new IPAddress(address, mask);
    }
    
    /**
     * Invalid Mask from integer (null)
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMaskIntegerNull() {
    	int[] address = {192,168,0,10};
    	int[] mask = null;
    	new IPAddress(address, mask);
    }
    
    /**
     * Invalid Mask from integer (256)
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMaskInteger256() {
    	int[] address = {192,168,0,10};
    	int[] mask = {255,256,0,0};
    	new IPAddress(address, mask);
    }
    
    /**
     * Invalid Mask from integer (3 elements)
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMaskIntegerNotEnoughElements() {
    	int[] address = {192,168,0,10};
    	int[] mask = {255,255,0};
    	new IPAddress(address, mask);
    }
    
    /**
     * GetIPAddress Test
     */
    @Test
    public void testGetIPAddress(){
        assertEquals("192.169.0.5", this.adresse1.getIPAddress());
    }

    /**
     * GetMask Test
     */
    @Test
    public void testGetMasque(){
        assertEquals("255.255.0.0", this.adresse2.getNetworkMask());
    }

    /**
     * GetNetworkAddress test
     */
    @Test
    public void testGetNetworkAddress(){
        assertEquals("192.169.0.0", this.adresse1.getNetworkAddress());
    }

    /**
     * IsValid Test
     */
    @Test
    public void testIsValid(){
        assertFalse(IPAddress.isValid("123.4.6"));
        assertFalse(IPAddress.isValid("12.R.g.6"));
        assertTrue(IPAddress.isValid("123.4.6.4"));
    }


    
}