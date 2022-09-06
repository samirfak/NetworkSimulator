package modele;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestMessageIP {

	private IPAddress source;
	private IPAddress destination;
	private IPMessage message;
	
	@Before
	public void setUp() throws Exception {
		this.source = new IPAddress("192.168.0.1","255.255.0.0");
		this.destination = new IPAddress("192.168.0.2","255.255.0.0");
		this.message = new IPMessage(this.source, this.destination, "Salut");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructeurSourceNull() {
		new IPMessage(null, this.destination, "Salut");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructeurDestinationNull() {
		new IPMessage(this.source, null, "Salut");
	}

	@Test
	public void testConstructeurContenuVideEtPayloadLengthEqualsZero(){
		IPMessage message = new IPMessage(this.source, this.destination, null);
		assertEquals(0, message.getPayloadLength());
	}

	@Test
	public void testConstructeurContenuVideEtPayloadLengthNul(){
		new IPMessage(this.source, this.destination, "");
	}
	
	@Test
	public void testGetSource() {
		assertEquals(this.source, this.message.getSource());
	}
	
	@Test
	public void testGetDestination() {
		assertEquals(this.destination, this.message.getDestination());
	}
	
	@Test
	public void testGetContenu() {
		assertEquals("Salut", this.message.getContent());
	}
	@Test
	public void testGetHeaderLength(){
		assertEquals(20, IPMessage.getHeaderLength());
	}

	@Test
	public void testGetPayloadLength(){
		assertEquals(5, this.message.getPayloadLength());
	}

}
