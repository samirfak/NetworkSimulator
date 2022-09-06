/*
 * SimulationGUI.java          29 mai 2020
 * N7 1APP SN 2019/2020 
 */

package vue.composants;

import vue.controleurs.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;

import modele.IPNetwork;

/**
 * Module graphique pour l'affichage des proprietes du reseau
 * Permet la simulation d'un reseau à partir de ses configurations
 * @author Samir FAKOREDE
 * @version 1.0
 */
public class ProprietesGUI {
    
    /** Label affichant le champ "Nombre d'equipements" */
    private JLabel labelNbEquip;
    
    /** Label affichant le champ "Nombre de liens" */
    private JLabel labelNbLiens;
    
    /** Label affichant le nombre de liens dans le réseau */
    private JLabel nbLiens;
    
    /** Label affichant le nombre d'équipements dans le réseau */
    private JLabel nbEquip;
    
    /** Fenetre de l'interface */
    private JFrame fenetre;
    
    /** Réseau IP associé à la fenêtre */
    private IPNetwork network;
    
    /**
     * Construit l'etat initial de l'interface graphique d'affichage des proprietes
     * @param network associé
     */
    public ProprietesGUI(IPNetwork network) {
        
        // Modèle
        this.network = network;
        
        // Creation et configuration de la fenêtre
        this.fenetre = new JFrame("Proprietes mon_reseau_lan.netsim");
        Container contenu = this.fenetre.getContentPane();
        contenu.setLayout(new GridLayout(2, 1));
        
        // Creation des labels
        labelNbEquip = new JLabel("Nombre d'equipements: ");
        labelNbLiens = new JLabel("Nombre de liens: ");
        
        // Initialisation en fonction du modèle
        nbEquip = new JLabel("" + network.getIPEquipementNumber());
        nbLiens = new JLabel("" + network.getLinks().size());
                
        JPanel nbEq = new JPanel(new GridLayout(1,2));
        // Ajout des composants sur le pannel
        nbEq.add(labelNbEquip);
        nbEq.add(nbEquip);
        contenu.add(nbEq);
        
        
        JPanel containerNbLiens = new JPanel(new GridLayout(1,2));
        // Ajout des composants sur le pannel
        containerNbLiens.add(labelNbLiens);
        containerNbLiens.add(nbLiens);
        contenu.add(containerNbLiens);
        
        // Definit une taille minimum de la fenêtre
        fenetre.setMinimumSize(new Dimension(500,200));
        // Fenetre non redimensionnable
        fenetre.setResizable(false);
        // fenêtre centree au centre de l'ecran
        fenetre.setLocationRelativeTo(null);
        // Fenêtre visible
        fenetre.setVisible(true);
    }
}