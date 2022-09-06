package modele;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * IP Message representation
 * 
 * @author Mathieu TEISSEDRE et Ignace CISS
 *
 */
public class IPMessage  implements Serializable {
    
	/** Clé de Hachage */
        private static final long serialVersionUID = 1L;

        /**
	 * Message source
	 */
	private IPAddress source;
	
	/**
	 * Message destination
	 */
	private IPAddress destination;
	
	/**
	 * Message content
	 */
	private String content;

	/**
	 * Message header length in bytes
	 */
	private static final int headerLength = 20;

	/**
	 * Message Time to Live
	 */
	private int TTL;

	/**
	 * List of hops through which the message travelled
	 */
	private List<String> hopsList;

	/**
	 * Result of sending a message
	 */
	private String resultText;
	
	/**
	 * Build an IP Message
	 * @param source Message source
	 * @param destination Message destination
	 * @param content Message content
	 * @throws IllegalArgumentException If one of the address is null
	 */
	public IPMessage(IPAddress source, IPAddress destination, String content) {
		if (source == null || destination == null) {
			throw new IllegalArgumentException("Adress must be not null");
		}
		this.source = source;
		this.destination = destination;
		this.content = content;
		this.TTL = 255;
		this.hopsList = new LinkedList<String>();
		this.resultText = "Message transmission output \n";
	}
	
	/**
	 * Get message source
	 * @return Message source
	 */
	public IPAddress getSource() {
		return this.source;
	}
	
	/**
	 * Get message destination
	 * @return Message destination
	 */
	public IPAddress getDestination() {
		return this.destination;
	}
	
	/**
	 * Get message content
	 * @return Message content
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * Get header length in bytes
	 * @return Header length
	 */
	public static int getHeaderLength(){
		return IPMessage.headerLength;
	}

	/**
	 * Get payload length in bytes
	 * @return Payload length
	 */
	public int getPayloadLength(){
		if (this.content == null) {
			return 0;
		}
		return this.content.getBytes().length;
	}

	/**
	 * Get a meesage's TTL
	 * @return TTL
	 */
	public int getMessageTTL(){
		return this.TTL;
	}

	/**
	 * Edit a message TLL
	 * @param newTTL new value of the TTL
	 */
	public void setMessageTTL(int newTTL){
		if (newTTL < 0 || newTTL > 255){
			throw new IllegalArgumentException("TLL must be between 0 and 255");
		}
		this.TTL = newTTL;
	}

	/**
	 * Get the number of hops through which the message travelled
	 * @return nulber of hops
	 */
	public int getNbHops(){
		return (255-this.TTL);
	}

	/**
	 * Return a message's hops list
	 * @return hops list
	 */
	public List<String> getMessageHopsList(){
		return this.hopsList;
	}

	/**
	 * Add an equipement to message's hops list
	 * @param eqtName name of the equipement to add
	 */
	public void addHopToList(String eqtName){
		this.hopsList.add(eqtName);
	}
	/**
	 * Show a message hops list
	 */
	public void showHopsList(){
		System.out.println("From "+ this.source.getIPAddress()+" to "+this.destination.getIPAddress()+", "+this.getNbHops()+" hops: "+this.hopsList);
	}

	/**
	 * Get the result of a message transmission
	 * @return
	 */
	public String getResultText(){
		return this.resultText;
	}

	/**
	 * Add text to the result of a message transmission
	 * @param newText
	 */
	public void addResultText(String newText){
		this.resultText += newText;
	}
}
