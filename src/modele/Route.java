package modele;

import java.io.Serializable;

/**
 * Route representation
 * @author Ignace CISS et Mathieu TEISSEDRE
 * 
 */
public class Route implements Serializable {
    
    
    /** Clé de Hachage */
    private static final long serialVersionUID = 1L;

    /**
     * Destination network address
     */
    private String destNetwork;

    /**
     * Destination network mask
     */
    private String netmask;

    /**
     * Out interface name
     */
    private IPInterface outInterface;

    /**
     * Build a route
     * @param dest Destination address
     * @param mask Destination mask
     * @param out Out interface
     */
    public Route (String destination, String mask, IPInterface out){
        if (IPAddress.isValid(destination) && IPAddress.isValid(mask)){
            IPAddress ip = new IPAddress(destination, mask);
            if (ip.getNetworkAddress().equals(destination)){
                this.destNetwork=destination;
                this.netmask=mask;
                this.outInterface=out;
            }
            else{
                throw new IllegalArgumentException("Given address isn't a network address");
            }
            
        }
        else{
            throw new IllegalArgumentException("Route isn't valid");
        }
        
    }

    /**
     * Get destination network address
     * @return Destination address
     */
    public String getDestAddress(){
        return this.destNetwork;
    }

    /**
     * Get destination network mask
     * @return Destination mask
     */
    public String getDestMask(){
        return this.netmask;
    }


    /**
     * Get out interface
     * @return Out interface name
     */
    public IPInterface getOutInterface(){
        return this.outInterface;
    }

    /**
     * Modify destination network address
     * @param destination New destination network address
     */
    public void setDestNetwork(String destination){
        if (IPAddress.isValid(destination)){
            IPAddress new_IP = new IPAddress(destination, this.netmask);
            if (new_IP.getNetworkAddress().equals(destination)){
                this.destNetwork = destination;
            }
            else{
                throw new IllegalArgumentException("Given address isn't a network address");
            }
            
        }
        else{
            throw new IllegalArgumentException("Route isn't valid");
        
        }
    }

    /**
     * Modify destination network mask
     * @param mask New destination network mask
     */
    public void setNetmask(String mask){
        if (IPAddress.isValid(mask)){
            IPAddress new_IP = new IPAddress(this.destNetwork, mask);
            if (new_IP.getNetworkAddress().equals(this.destNetwork)){
                this.netmask = mask;
            }
            else{
                throw new IllegalArgumentException("Given mask change address to a classical address");
            }
            
        }
        else{
            throw new IllegalArgumentException("Mask isn't valid");
        
        }
    }

    /**
     * Modify out interface name
     * @param out Out interface name
     * @throws IllegalArgumentException If out is null
     */
    public void setOutInterface(IPInterface out){
        if (out == null){
            throw new IllegalArgumentException("Out interface name must be not null");
        }
        else
            this.outInterface=out;
    }
    
    /**
     * Say if an address correspond to this route
     * @param address IP address
     * @return True if address is in route, false else
     * @throws IllegalArgumentException If address is not an IP address representation
     */
    public boolean isInRoute(String address) {
    	if(IPAddress.isValid(address)) {
    		IPAddress tmpAdresse = new IPAddress(address, this.netmask);
    		IPAddress tmpRoute = new IPAddress(this.destNetwork, this.netmask);
    		return tmpAdresse.getNetworkAddress().equals(tmpRoute.getNetworkAddress());
    	}
    	else {
    		throw new IllegalArgumentException("Given address isn't valid");
    	}
    }

    @Override
    public boolean equals(Object o){
        if (o == null){
            return false;
        }
        if (!(o instanceof Route)){
            return false;
        }
        Route tmp = (Route) o;
        return this.destNetwork.equals(tmp.destNetwork) && 
        this.netmask.equals(tmp.netmask) &&
        this.outInterface.equals(tmp.outInterface);
    }
    
}