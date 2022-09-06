package modele;

import java.util.LinkedList;
import java.util.List;
import java.io.Serializable;
import java.util.Formatter;

/**
 * Routing table representation
 * @author Ignace CISS et Mathieu TEISSEDRE
 */
public class RoutingTable implements Serializable {
    
    /** Clé de Hachage */
    private static final long serialVersionUID = 1L;

    /**
     * Default route
     */
    private Route defaultRoute;
    
    /**
     * Route list
     */
    private List<Route> routeList;

    /**
     * Build a routing table
     */
    public RoutingTable(){
        this.defaultRoute = null;
        this.routeList = new LinkedList<Route>();
    }

    /**
     * Add a new route 
     * @param dest Destination network address
     * @param mask Destination network mask
     * @param out Out interface
     */
    public void addRoute (String dest, String mask, IPInterface out){
        routeList.add(new Route(dest, mask, out));
    }

    /**
     * Remove a route
     * @param index Line number in the routing table
     * @throws IllegalArgumentException If incorrect index
     */
    public void removeRoute(int index){
        if (index >= this.routeList.size() || index < 0){
            throw new IllegalArgumentException("Incorrect index");
        }
        this.routeList.remove(index);
    }
    
    /**
     * Remove a route
     * @param dest Destination network address
     * @param mask Destination network mask
     * @param out Destination out interface
     */
    public void removeRoute(String dest, String mask, IPInterface out){
        Route route = new Route(dest, mask, out);
        for (int i = 0; i<this.routeList.size(); i++){
            if (this.routeList.get(i).equals(route)){
                this.routeList.remove(this.routeList.get(i));
            }
        }
    }

    /**
     * Get default route
     * @return Default route
     * @throws NotFoundException If default route doesn't exist
     */
    public Route getDefaultRoute() throws NotFoundException {
        if(this.defaultRoute == null){
        	throw new NotFoundException("Default route can't be found");
        }
        return this.defaultRoute;
	}

	/**
	 * Modify default route out interface
	 * @param pOutInterface Out interface name 
	 * @throws NotFoundException If given interface name is null
	 */
	public void setDefaultRoute(IPInterface pOutInterface) throws NotFoundException{
		if(pOutInterface == null){
            throw new IllegalArgumentException("Le nom d'interface ne peut etre vide");
		}
		this.defaultRoute = new Route("0.0.0.0", "0.0.0.0", pOutInterface);

	}

    /**
     * Show routing table
     * @throws NotFoundException If default route can't be found
     */
    public void showRoute() throws NotFoundException {
        System.out.println(this);
    }
    
    /**
     * Override of toString method
     */
    @Override
    public String toString() {
    	String routingTable = "Showing The Routing Table...\n";
        //default route
        if (this.defaultRoute == null){
            System.out.println("No default route !");
        }
        else {
        	Formatter d = new Formatter();
            d.format("%15s %15s %15s\n", "Dest", "Netmask", "Out_interface");
            routingTable += "Default Route\n";
            try {
				d.format("%15s %15s %15s\n", this.getDefaultRoute().getDestAddress(), this.getDefaultRoute().getDestMask(), this.getDefaultRoute().getOutInterface().getName());
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
            routingTable += d;
        }
        //other routes
        Formatter f = new Formatter();
        f.format("%15s %15s %15s %15s\n", "Line", "Dest", "Netmask", "Out_interface");
        routingTable += "Other Routes\n";
        for (int i =0; i<this.routeList.size(); i++){
            f.format("%15s %15s %15s %15s\n", i, this.routeList.get(i).getDestAddress(), this.routeList.get(i).getDestMask(), this.routeList.get(i).getOutInterface().getName());
        }
        routingTable += f;
        return routingTable;
    }
    
    /**
     * Get out interface by IP 
     * @param address IP address
     * @return Out interface name
     * @throws NotFoundException If interface can't be found with this IP address
     * @throws IllegalArgumentException If address can't be found
     */
    public IPInterface getInterfaceByIP(IPAddress address) throws NotFoundException {
    	if (address == null) {
    		throw new IllegalArgumentException("Address can't be null");
    	}
    	for(Route rte : this.routeList) {
    		if (rte.isInRoute(address.getIPAddress())) {
    			return rte.getOutInterface();
    		}
    	}
    	if(!(this.defaultRoute == null)){
            return defaultRoute.getOutInterface();
        }
        throw new NotFoundException("Interface can't be found with this IP address");
    }
    
    /**
     * Get the route list of the table
     * @return the reference of the list
     */
    public List<Route> getRouteList() {
        return routeList;
    }
}