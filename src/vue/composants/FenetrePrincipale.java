/*
 * FenetrePrincipale.java         25 mai 2020
 * N7 1APP SN 2019/2020    NetWorkSimulator
 */
package vue.composants;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import modele.IPNetwork;
import vue.controleurs.MenuControleur;

/**
 * Fenetre principale de l'application
 * @author T.Escudero - S.Fakorede - V.Bousquie
 * @version 1.0
 */
public class FenetrePrincipale {

    /* Les Attributs sont la listes de composants de la fenetre principal */

    /** Fenetre de l'application */
    private JFrame fenetre;

    /** Module de configuration, JPanel spacialisa */
    private ConfigurationGUI configurateur;

    /** Module de simulation, JPanel spacialisa */
    private SimulationGUI simulateur;

    /** Module des aquipements, JPanel spacialisa */
    private EquipementsGUI equipements;

    /** Module Menu de l'application */
    private Menu menu;

    /** Zone de dessin pour daplacer les aquipements */
    private ZoneDessinGUI zoneDessin;

    /** Tabbed Pane pour ajouter les composants Simulation et Configuration */
    private JTabbedPane colonneDroite;

    /** controleur du menu */
    private MenuControleur controleurMenu;
    
    /** Nom de la sauvegarde associée */
    private String nomSauvegarde;

    /** Network IP */
    private IPNetwork network;

    /**
     * Construit la fenetre principale avec tous ses composants
     * @param network  modèle associé à la vue
     */
    public FenetrePrincipale(IPNetwork network) {

    	//Connexion avec le modele
    	this.network = network;
    	
    	this.nomSauvegarde = null;

        // Craation et configuration de la fenetre
        this.fenetre = new JFrame("NetWork Simulator | Nouveau projet");
        Container contenu = this.fenetre.getContentPane();
        contenu.setLayout(new BorderLayout());

        // Creation des sous-modules
        configurateur = new ConfigurationGUI(this.network);
        simulateur = new SimulationGUI(this.network);
        zoneDessin = new ZoneDessinGUI(this);
        colonneDroite = new JTabbedPane();
        equipements = new EquipementsGUI(this);
        menu = new Menu(this);

        // Creation du controleur du menu
        controleurMenu = new MenuControleur(menu);

        colonneDroite.add("Configurer",configurateur);
        colonneDroite.add("Simuler",simulateur);

        // Ajout des sous-modules
        contenu.add(colonneDroite, BorderLayout.EAST); // Config + Simulation
        contenu.add(equipements, BorderLayout.WEST); // aquipements
        contenu.add(zoneDessin, BorderLayout.CENTER); // Zone de dessin

        // ajout du menu
        fenetre.setJMenuBar(this.menu); // Menu

        // Definit une taille minimum de la fenetre
        fenetre.setMinimumSize(new Dimension(800,600));
        // Taille Dynamique
        fenetre.pack();
        // Fenere redimensionnable
        fenetre.setResizable(true);
        // L'utilisateur clique sur la croix
        fenetre.addWindowListener(new WindowAdapter() {
        	@Override
			public void windowClosing(WindowEvent e) {
        		// Confirmation fermer
        		fermer();
        	}
		});
        fenetre.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // fenetre centrae au centre de l'acran
        fenetre.setLocationRelativeTo(null);
        // Fenetre visible
        fenetre.setVisible(true);
    }

    /**
     * Accesseur sur l'attribut du reseau IP
     * @return reseau IP
     */
    public IPNetwork getNetwork() {
	return network;
    }
    
    /**
     * Modifier le réseau IP associé
     * @param newNetwork  nouveau réseau IP
     */
    public void setNetwork(IPNetwork newNetwork) {
        this.network = newNetwork;
    }

    /**
     * Accesseur sur l'attribut zone de dessin
     * @return la reference de zoneDessin
     */
    public ZoneDessinGUI getZoneDessin() {
        return zoneDessin;
    }

    /**
     * Accesseur sur l'attribut configurationGUI
     * @return la référence du configurateur
     */
    public ConfigurationGUI getConfigurateur() {
        return configurateur;
    }

    /** Demande confirmation lors de la fermeture de
     * l'appication
     */
    public void fermer() {
    	JOptionPane ask = new JOptionPane();

    	String[] choix = { "Sauvegarder", "Quitter", "Annuler" };
    	int option = ask.showOptionDialog(null,
    			"Voulez-vous quitter NetworkSimulator ?",
    			"Quitter",
    			JOptionPane.YES_NO_CANCEL_OPTION,
    			JOptionPane.WARNING_MESSAGE,
    			null,
    			choix,
    			choix[2]);


    	// si choix sauvegarder
    	if (option == 0) {
    		// aucune sauvegarde existante
    		if (this.getNomSauvegarde() != null) {
    			controleurMenu.sauvegarder();
    		} else {
    			controleurMenu.sauvegarderSous();
    		}
    	}
    	
    	// Fermeture de l'application
    	System.exit(0);

    }
    
    /**
     * Fermer sans demander la permission à l'utilisateur
     */
    public void forcerFermeture() {
        this.fenetre.dispose();
    }

    /**
     * Accesseur sur simulateur
     * @return le simulateur
     */
    public SimulationGUI getSimulateur() {
    	return this.simulateur;
    }
    
    /**
     * Mettre à jour le nom du fichier
     * @param newTitle nouveau titre
     */
    public void setTitle(String newTitle) {
        this.fenetre.setTitle(newTitle);
    }
    
    /**
     * Accesseur sur l'attribut nomSauvegarde
     * @return la chaine de caractères nomSauvegarde
     */
    public String getNomSauvegarde() {
        return nomSauvegarde;
    }
    
    /**
     * Modifier le nom du fichier de sauvegarde associé à
     * l'application.
     * @param newNom nouveau nom
     */
    public void setNomSauvegarde(String newNom) {
        this.nomSauvegarde = newNom;
    }

    /**
     * Lancement de l'application
     * @param args non utilisés
     */
    public static void main(String[] args) {
    	IPNetwork network = new IPNetwork();
        FenetrePrincipale appli = new FenetrePrincipale(network);
    }
}
