/*
 * TerminalGUI.java          26 mai 2020
 * N7 1APP SN 2019/2020
 */

package vue.composants;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modele.IPNetwork;
import vue.controleurs.TerminalControleur;

/**
 * Fenetre Modale representant un terminal de commande
 * L'utilisateur pourra saisir differentes commandes pour manipuler et
 * configurer les equipements de son reseau local.
 * @author V.Bousquie
 * @version 1.0
 */
public class TerminalGUI extends JFrame {

    /** IP Network */
	private IPNetwork reseau;

	/** Equipement actuel */
	private String equipement;
	
    /** Conserver les references des zones de saisies */
    private ArrayList<JTextField> zonesSaisies = new ArrayList<JTextField>();

    /**
     * Construit la fenetre terminal
     */
    public TerminalGUI(IPNetwork network, String eqt) {
    	super();
    	if (network == null) {
    		throw new IllegalArgumentException("Network can't be null");
    	}
    	
    	// Interfaçage avec le modèle
    	this.reseau = network;
    	this.equipement = eqt;
    
    	this.setTitle("NetWork Simulator | " + eqt);
        this.zonesSaisies = new ArrayList<JTextField>();

        // Creation et configuration de la fenetre
        Container contenu = this.getContentPane();
        contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));
        contenu.setBackground(Color.BLACK);

        // Conteneur affichant un JLabel et JTextfield en ligne
        Container ligne = new Container();
        ligne.setLayout(new BoxLayout(ligne, BoxLayout.X_AXIS));

        // Label d'information
        JLabel info = new JLabel("mon_reseau_lan $ > ");
        info.setForeground(Color.white);
        ligne.add(info);

        // Zone de saisie
        JTextField saisie = new JTextField();
        saisie.setBackground(Color.BLACK);
        saisie.setForeground(Color.WHITE);
        saisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        saisie.addKeyListener(new TerminalControleur(this));

        // Ajout dans la liste
        zonesSaisies.add(saisie);

        ligne.add(saisie);
        contenu.add(ligne);

        // Taille Fixe et Affichage
        // Definit une taille minimum de la fenetre
        this.setMinimumSize(new Dimension(800,50));
        this.pack();
        //this.setResizable(false);
        // Fenetre centree au centre de l'ecran
        this.setLocationRelativeTo(null);
    }

    /**
     * Retourner le réseau
     * @return Réseau
     */
    public IPNetwork getReseau() {
		return reseau;
	}
    

    /**
     * Ajout d'une nouvelle ligne de saisie
     */
    public void addSaisie() {
        Dimension dim = this.getMinimumSize();
        dim.setSize(dim.getWidth(), dim.getHeight() + 25);

        this.setMinimumSize(dim);

        Container newLine = new Container();
        newLine.setLayout(new BoxLayout(newLine, BoxLayout.X_AXIS));
        // Label d'information
        JLabel info = new JLabel("mon_reseau_lan $ > ");
        info.setForeground(Color.white);
        newLine.add(info);

        // Champ de saisie
        JTextField saisie = new JTextField();
        saisie.setBackground(Color.BLACK);
        saisie.setForeground(Color.WHITE);
        saisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        saisie.addKeyListener(new TerminalControleur(this));
        newLine.add(saisie);
        this.getContentPane().add(newLine);

        // Blocage du dernier Textfield
        this.zonesSaisies.get(this.zonesSaisies.size()-1).setEnabled(false);

        // Ajout de la zone dans la liste
        this.zonesSaisies.add(saisie);
    }

    /**
     * Afficher le resultat d'une commande
     * @param resultat de la commande
     */
    public void addResultat(String resultat) {
        for (String element : resultat.split("\n")) {
        	JLabel labelResultat = new JLabel(element);
            labelResultat.setForeground(Color.WHITE);
            this.getContentPane().add(labelResultat);
            Dimension dim = this.getMinimumSize();
            dim.setSize(dim.getWidth(), dim.getHeight() + 25);
            this.setMinimumSize(dim);
        }
    }

    /**
     * Creer un JPanel dans un onglet
     * @param layout
     * @return le JPanel
     */
    public JPanel creerPanel(LayoutManager layout) {
    	JPanel result = new JPanel();
    	return result;
    }
    
    public void clearPanel() {
    	this.getContentPane().removeAll();
    	this.setMinimumSize(new Dimension(800,40));
    	this.getContentPane().setSize(800, 40);
    	this.pack();
    }

    public void setEquipement(String equipement) {
    	this.equipement = equipement;
    }
    
    public String getEquipement() {
    	return this.equipement;
    }
    
}
