/*
 * AjoutInterface.java                             9 juin 2020
 * N7 1APP SN 2018/2019
 */
package vue.composants;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import modele.IPNetwork;
import vue.controleurs.ChangerNomControleur;

/**
 * Petite fenêtre pour changer le nom d'un équipement
 * @author V.Bousquié
 * @version 1.0
 */
public class ChangerNomGUI {
    
    /** La fenêtre */
    private JFrame fenetre;
    
    /** Label du champ saisiNom */
    private JLabel labelNom;
    
    /** Champ de saisie du nom de l'interface */
    private JTextField saisiNom;
    
    /** Bouton pour valider le nouveau nom de l'équipement */
    private JButton btnValider;
    
    /** Controleur associé à la fenêtre */
    private ChangerNomControleur controleur;
    
    /** IP Network associé à la vue */
    private IPNetwork network;    
    
    /** Nom de l'équipement dont on souhaite changer le nom */
    private String nomEquipement;
    
    /** ObjetDessin à modifier */
    private ObjetDessin objetDessin;
    
    /**
     * Construit l'état initial
     * @param network asssocié à la vue 
     * @param nomEquipement qu'on cherche à modifier
     * @param objetDessin objet à modifier
     */
    public ChangerNomGUI(IPNetwork network, String nomEquipement, ObjetDessin objetDessin) {
        
        this.fenetre = new JFrame("Changer Nom");
        Container contenu = fenetre.getContentPane();
        contenu.setLayout(new BorderLayout(20,20));
        
        this.network = network;
        this.nomEquipement = nomEquipement;
        this.objetDessin = objetDessin;
        
        // Création du controleur
        this.controleur = new ChangerNomControleur(this);
        
        // Création des composants
        this.labelNom = new JLabel("Nom de l'equipement : ");
        this.saisiNom = new JTextField();
        this.saisiNom.setText(nomEquipement);
        this.btnValider = new JButton("VALIDER");
        
        btnValider.addActionListener(controleur);
        btnValider.setActionCommand("Valider");
        
        // Definit une taille minimum de la fenetre
        fenetre.setMinimumSize(new Dimension(400,200));
        
        contenu.add(this.labelNom, BorderLayout.NORTH);
        contenu.add(this.saisiNom, BorderLayout.CENTER);
        contenu.add(this.btnValider, BorderLayout.SOUTH);
        
        ((JComponent) contenu).setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        
        this.fenetre.pack();
        fenetre.setVisible(true);
        fenetre.setResizable(false);
        // fenetre centrae au centre de l'acran
        fenetre.setLocationRelativeTo(null);
        // Fenetre visible
        fenetre.setVisible(true);
    }

    /**
     * Accesseur sur l'attribut network
     * @return network
     */
    public IPNetwork getNetwork() {
        return network;
    }

    /**
     * Accesseur sur l'attribut nomEquipement
     * @return nomEquipement
     */
    public String getNomEquipement() {
        return nomEquipement;
    }
    
    /**
     * Accesseur sur l'attribut saisiNom
     * @return la référence du textfield
     */
    public JTextField getSaisiNom() {
        return saisiNom;    
    }
    
    /**
     * Accesseur sur l'attribut objetDessin
     * @return la référence de objetDessin
     */
    public ObjetDessin getObjetDessin() {
        return objetDessin;   
    }
    
    /**
     * Fermer la fenêtre
     */
    public void fermer() {
        this.fenetre.dispose();
    }
}
