package modele;

import java.io.Serializable;

/**
 * Class which represent an IP address
 * @author Mathieu TEISSEDRE
 *
 */
public class IPAddress implements Serializable {

	/** Clé de hachage */
        private static final long serialVersionUID = 1L;

        /**
	 * IP address representation
	 */
	private int[] address;
	
	/**
	 * Mask representation
	 */
	private int[] mask;
	
	/**
	 * Build an IP address from string
	 * @param address IP address
	 * @param mask Mask
	 * @throws IllegalArgumentException if address or mask are malformed
	 */
	public IPAddress (String address, String mask) throws IllegalArgumentException{
		if (! IPAddress.isValid(address)) {
			throw new IllegalArgumentException("Malformed IP Address");
		}
		if (! IPAddress.isValid(mask)) {
			throw new IllegalArgumentException("Malformed Network Mask");
		}
		this.address = new int[4];
		this.mask = new int[4];
		String[] elementsAddress = address.split("\\.");
		String[] elementsMask = mask.split("\\.");
		for (int i = 0; i < 4; i++) {
			this.address[i] = Integer.parseInt(elementsAddress[i]);
			this.mask[i] = Integer.parseInt(elementsMask[i]);
		}
	}
	
	/**
	 * Build an IP address from integer table
	 * @param address IP address
	 * @param mask Mask
	 * @throws IllegalArgumentException if address or mask are malformed
	 */
	public IPAddress (int[] address, int[] mask) throws IllegalArgumentException{
		if(! IPAddress.isValid(address)) {
			throw new IllegalArgumentException("Malformed IP Address");
		}
		if(! IPAddress.isValid(mask)) {
			throw new IllegalArgumentException("Malformed Network Mask");
		}
		this.address = address;
		this.mask = mask;
	}
	
	/**
	 * Get IP address
	 * @return IP address
	 */
	public String getIPAddress() {
		return this.address[0] + "." + this.address[1] + "." + this.address[2] + "." + this.address[3];
	}
	
	/**
	 * Get network mask
	 * @return network mask
	 */
	public String getNetworkMask() {
		return this.mask[0] + "." + this.mask[1] + "." + this.mask[2] + "." + this.mask[3];
	} 
	
	/**
	 * Say if the IP Address is a network address
	 * @return true if IP Address is a network address, false else
	 */
	public boolean isNetworkAdress() {
		return this.getNetworkAddress().equals(this.getIPAddress());
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof IPAddress)) {
			return false;
		}
		IPAddress address = (IPAddress) o;
		for(int i = 0; i <4; i++) {
			if (address.address[i] != this.address[i] || address.mask[i] != this.mask[i]) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Get network address from address and mask
	 * @return Network address
	 */
	public String getNetworkAddress() {
		String adresseReseau = "";
		for (int i = 0; i < 4; i++) {
			int valueAdresse = this.address[i];
			int valueMasque = this.mask[i];
			adresseReseau += valueAdresse & valueMasque;
			if (i != 3) {
				adresseReseau += ".";
			}
		}
		return adresseReseau;
	}
	
	/**
	 * Say if the String is an IP address
	 * @param address IP address
	 * @return true if the string is an IP address, false else
	 */
	public static boolean isValid (String address) {
		String[] ipAddressElements = address.split("\\.");
		if (ipAddressElements.length != 4) {
			return false;
		}
		for (String element : ipAddressElements) {
			try {
				int value = Integer.parseInt(element);
				if (value > 255 || value < 0) {
					return false;
				}
			}
			catch (Exception e) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Say if the integer table is an IP address
	 * @param address IP address
	 * @return true if the integer table is an IP address, false else
	 */
	public static boolean isValid (int[] address) {
		if (address == null) {
			return false;
		}
		if (address.length != 4) {
			return false;
		}
		for (int element : address) {
			if (element > 255 || element < 0) {
				return false;
			}
		}
		return true;
	}
	
}
