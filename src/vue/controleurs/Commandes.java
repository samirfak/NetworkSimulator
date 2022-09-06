package vue.controleurs;

import modele.DestinationUnreachableException;
import modele.IPAddress;
import modele.IPEquipment;
import modele.IPMessage;
import modele.NotFoundException;
import modele.PacketDroppedException;
import vue.composants.TerminalGUI;

public enum Commandes {
	clear {
		@Override
		public void execute(TerminalGUI terminal, String[] commande) {
			terminal.clearPanel();
		}
	},
	ssh {
		@Override
		public void execute(TerminalGUI terminal, String[] commande) {
			if(commande.length == 2) {
				IPEquipment equipement = terminal.getReseau().getIPEquipment(commande[1]);
				if (equipement == null) {
					terminal.addResultat("Erreur : L'équipement n'existe pas");
				}else {
					terminal.setTitle("NetWork Simulator | " + equipement.getName());
					terminal.setEquipement(equipement.getName());
				}
			}
			else {
				terminal.addResultat("Erreur : Commande Incorrect\n Format de la commande : ssh <nom_machine>");
			}
		}
	},
	ip {
		@Override
		public void execute(TerminalGUI terminal, String[] commande) {
			if (commande.length == 6 && commande[1].equals("add") && commande[2].equals("route")) {
				if (! IPAddress.isValid(commande[3])) {
					terminal.addResultat("Erreur : L'adresse est invalide");
				}
				else if (! IPAddress.isValid(commande[4])) {
					terminal.addResultat("Erreur : Le masque est invalide");
				}
				try {
					terminal.getReseau().getIPEquipment(terminal.getEquipement()).getInterfaceByName(commande[5]);
					terminal.getReseau().getIPEquipment(terminal.getEquipement()).addRoute(commande[3], commande[4], commande[5]);
				}
				catch (NotFoundException e) {
					terminal.addResultat("Erreur : L'interface est introuvable");
				}
				
			}
			else if (commande.length == 4 && commande[1].equals("rm") && commande[2].equals("route")) {
				if (! IPAddress.isValid(commande[3])) {
					terminal.addResultat("Erreur : L'adresse est invalide");
				}
				else if (! IPAddress.isValid(commande[4])) {
					terminal.addResultat("Erreur : Le masque est invalide");
				}
				try {
					terminal.getReseau().getIPEquipment(terminal.getEquipement()).getRoutingTable().removeRoute(commande[3], commande[4], terminal.getReseau().getIPEquipment(terminal.getEquipement()).getInterfaceByName(commande[5]));
				}
				catch (NotFoundException e) {
					terminal.addResultat("Erreur : L'interface est introuvable");
				}
			}
			else if (commande.length == 6 && commande[1].equals("add") && commande[2].equals("interface")) {
				if (! IPAddress.isValid(commande[3])) {
					terminal.addResultat("Erreur : L'adresse est invalide");
				}
				else if (! IPAddress.isValid(commande[4])) {
					terminal.addResultat("Erreur : Le masque est invalide");
				}
				try {
					terminal.getReseau().getIPEquipment(terminal.getEquipement()).addInterface(commande[5], new IPAddress(commande[3], commande[4]) ,100);
				}
				catch (Exception e) {
					terminal.addResultat("Erreur : Interface ne peut pas être créer");
				}
				
			}
			else if (commande.length == 4 && commande[1].equals("rm") && commande[2].equals("interface")) {
				try {
					terminal.getReseau().getIPEquipment(terminal.getEquipement()).removeInterface(terminal.getReseau().getIPEquipment(terminal.getEquipement()).getInterfaceByName(commande[3]));
				}
				catch (NotFoundException e) {
					terminal.addResultat("Erreur : L'interface est introuvable");
				}
			}
			else {
				terminal.addResultat("Erreur : Commande incorrect\n");
			}
		}
	},
	sh {
		@Override
		public void execute(TerminalGUI terminal, String[] commande) {
			if(commande.length == 2 && commande[1].equals("conf")) {
				terminal.addResultat(terminal.getReseau().getIPEquipment(terminal.getEquipement()).showInterfaces());
			}
			else if(commande.length == 3 && commande[1].equals("ip") && commande[2].equals("route")) {
				terminal.addResultat(terminal.getReseau().getIPEquipment(terminal.getEquipement()).getRoutingTable().toString());
			}
			else {
				terminal.addResultat("Erreur : Commande incorrect\nCommande disponible :\nsh conf\nsh ip route\n");
			}
		}
	},
	ping {
		@Override
		public void execute(TerminalGUI terminal, String[] commande) {
			if (commande.length == 3) {
				if (! IPAddress.isValid(commande[1]) || ! IPAddress.isValid(commande[2])) {
					terminal.addResultat("L'adresse source est incorrect");
				}
				IPEquipment source = terminal.getReseau().getIPEquipment(terminal.getEquipement());
				IPMessage testMessage;
				try {
					testMessage = new IPMessage(source.getInterfaceByName(source.listInterface().get(0)).getAddress(),new IPAddress(commande[1], commande[2]),"Traceroute message");
					terminal.getReseau().getIPEquipment(terminal.getEquipement()).send(testMessage);
					terminal.addResultat("Le paquet a été reçu");
				} catch (IllegalArgumentException | DestinationUnreachableException | NotFoundException
						| PacketDroppedException e) {
					terminal.addResultat("ERREUR : " + e.getMessage());
				}
			}
		}
	},
	traceroute {
		@Override
		public void execute(TerminalGUI terminal, String[] commande) {
			if (commande.length == 3) {
				if (! IPAddress.isValid(commande[1]) || ! IPAddress.isValid(commande[2])) {
					terminal.addResultat("L'adresse source est incorrect");
				}
				IPEquipment source = terminal.getReseau().getIPEquipment(terminal.getEquipement());
				
				IPMessage testMessage;
				try {
					testMessage = new IPMessage(source.getInterfaceByName(source.listInterface().get(0)).getAddress(),new IPAddress(commande[1], commande[2]),"Traceroute message");
					terminal.getReseau().getIPEquipment(terminal.getEquipement()).send(testMessage);
					terminal.addResultat(testMessage.getResultText());
				} catch (IllegalArgumentException | DestinationUnreachableException | NotFoundException
						| PacketDroppedException e) {
					terminal.addResultat("ERREUR : " + e.getMessage());
				}
			}
		}
	};
	
	public abstract void execute(TerminalGUI terminal, String[] commande);
}
