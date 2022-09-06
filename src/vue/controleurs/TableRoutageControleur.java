/*
 * TableRoutageControleur.java          8 juin 2020
 * N7 SN 1APP 2018/2019
 */
package vue.controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import modele.IPEquipment;
import modele.IPNetwork;
import modele.NotFoundException;
import vue.composants.*;

/**
 * Controleur de la fenêtre TableRoutageGUI
 * Réagit aux actions sur les boutons "Ajouter" et "Retour"
 * @author V.Bousquié
 * @version 1.0
 */
public class TableRoutageControleur implements ActionListener {
    
    /** Fenêtre associée au controleur */
    private TableRoutageGUI table;
    
    /** Réseau IP associé */
    private IPNetwork network;
    
    /** Equipement associé */
    private IPEquipment equipement;
    
    /**
     * Construit l'état initial du controleur
     * @param table
     */
    public TableRoutageControleur(TableRoutageGUI table) {
        this.table = table;
        this.network = table.getNetwork();
        this.equipement = table.getEquipement();
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        String cmd = evt.getActionCommand();
        
        if (cmd.equals("Ajouter")) {
            String adresseReseau = table.getSaisieAdresseReseau().getText();
            String masque = table.getSaisieMasque().getText();
            String interfaceSortie = (String) table.getChoixInterfaceSortie().getSelectedItem();
            
            // Ajout d'une route dans la table de routage de equipement
            try {
                equipement.addRoute(adresseReseau, masque, interfaceSortie);
                this.table.getFenetre().dispose();
                this.table = new TableRoutageGUI(network, equipement);
            } catch (Exception e) {
                JOptionPane dialog = new JOptionPane();
                dialog.showMessageDialog(null, e.getMessage(),
                      "Erreur de configuration", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
           
        } else if (cmd.equals("Retour")) {
            this.table.getFenetre().dispose();
        } else if (cmd.equals("SortieDefaultRoute")) {
            try {
                this.equipement.getRoutingTable().setDefaultRoute(
                        equipement.getInterfaceByName((String) table.getChoixInterfaceSortieDefault().getSelectedItem()));
            } catch (NotFoundException e) {
                JOptionPane dialog = new JOptionPane();
                dialog.showMessageDialog(null, e.getMessage(),
                      "Erreur de configuration", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}