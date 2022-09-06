package modele;
import java.io.Serializable;
import java.math.*;

/**
 * Link representation between two interfaces
 * @author Mathieu TEISSEDRE et Ignace CISS
 *
 */
public class IPLink implements Serializable {
    //We consider the Ethernet 1000 Base T, it means max length is 100 meters and propagation speed is 2.10^8 meter/s

    /** Clé de hachage */
    private static final long serialVersionUID = 1L;

    /**
     * Link propagation speed in meter/s
     */
    private static final double linkPropagationSpeed = Math.pow(2*10, 8);

    /**
     * Link length
     */
    private int linkLength;

    /**
     * Link propagation delay in seconds
     */
    private double propagationDelay;
    
    /**
     * Link ends
     */
    private IPInterface[] ends;
    
    /**
     * Build an IP link
     * @param end1 IP interface
     * @param end2 IP interface
     * @param linkLength link length
     * @throws IllegalArgumentException If one of the interfaces is null, or link length is negative
     */
    public IPLink (IPInterface end1, IPInterface end2, int linkLength) {
    	if(end1 == null || end2 == null) {
    		throw new IllegalArgumentException("Interfaces must be not null");
        }
        if(linkLength <= 0 || linkLength > 100){
            throw new IllegalArgumentException("Link lengt must be positive and less than 100 meters");
        }
    	this.ends = new IPInterface[2];
    	this.ends[0] = end1;
    	this.ends[1] = end2;
    	end1.addLink(this);
    	end2.addLink(this);
        this.linkLength = linkLength;
        this.propagationDelay = linkLength / IPLink.linkPropagationSpeed;
    }
    
    /**
     * Get ends of the link
     * @return Link ends
     */
    public IPInterface[] getEnds() {
    	return this.ends;
    }
    
    /**
     * Get Link propagation speed
     * @return Link propagation speed
     */
    public static double getLinkPropagationSpeed() {
    	return IPLink.linkPropagationSpeed;
    }

    /**
     * Get link propagation delay
     * @return link propagation delay
     */
    public double getLinkPropagationDelay(){
        return this.propagationDelay;
    }
    

    /**
     * Get link length
     * @return link length
     */
    public int getLinkLength(){
        return this.linkLength;
    }

    /**
     * Modify link length
     * @param linkLength link length
     * @throws IllegalArgumentException If length is negative
     */
    public void setLinkLength(int linkLength){
        if(linkLength <= 0 || linkLength > 100){
            throw new IllegalArgumentException("Link length must be positive and less than 100 meters");
        }
        this.linkLength = linkLength;
    }
    
    /**
     * Send a message between interfaces
     * @param message Message 
     * @param source Source interface
     * @throws DestinationUnreachableException
     * @throws NotFoundException
     * @throws PacketDroppedException Packet has been dropped by the equipment
     * @throws IllegalArgumentExceptio If one of the parameters are null 
     */
    public boolean send(IPMessage message, IPInterface source)
            throws PacketDroppedException, NotFoundException, DestinationUnreachableException {
    	if(message == null) {
    		throw new IllegalArgumentException("Message must be not null");
    	}
    	if(source == null) {
    		throw new IllegalArgumentException("Interface must be not null");
    	}
    	if(source == this.ends[0]) {
            System.out.println("Propagation delay : "+this.getLinkPropagationDelay() + " s");
            message.addResultText("Propagation delay : "+this.getLinkPropagationDelay() + " s \n");
    		return this.ends[1].receive(message);
    	}
    	else {
            System.out.println("Propagation delay : "+this.getLinkPropagationDelay() + " s");
            message.addResultText("Propagation delay : "+this.getLinkPropagationDelay() + " s \n");
            return this.ends[0].receive(message);
    	}
    }
}