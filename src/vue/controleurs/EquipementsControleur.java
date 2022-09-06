/*
 * EquipementsControleur.java                       31 mai 2020
 * N7 SN 1APP 2019/2020
 */

package vue.controleurs;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import modele.IPNetwork;
import vue.composants.EquipementsGUI;
import vue.renderer.ImageNText;

/**
 * Controleur du module representant la liste d'equipements
 * @author V.Bousquie, T.ESCUDERO
 * @version 1.0
 */
public class EquipementsControleur implements ActionListener {

    /** Colonne d'equipements */
    private EquipementsGUI colEquipements;

    /** Réseau IP associé à la vue */
    private IPNetwork ipNetwork;

    /**
     * Construit l'etat initial du controleur
     * @param equipements vue des equipements sur laquelle le controleur agit
     */
    public EquipementsControleur(EquipementsGUI equipements) {
        this.colEquipements = equipements;
        this.ipNetwork = this.colEquipements.getIpNetwork();
    }

    /**
     * Création d'un Equipement IP lors du clic sur une combo
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        JComboBox combo = (JComboBox) evt.getSource();
        ImageNText element = (ImageNText) combo.getSelectedItem();
        // relier des elements
        if (element.getName() == "Ethernet 1000 Base T") {
        	//this.colEquipements.getFenetreParente().getZoneDessin().relier();
            this.colEquipements.getFenetreParente().getZoneDessin()
            .setDessinerLien(true);
            this.colEquipements.getFenetreParente().getZoneDessin()
            .setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        } else { // dessiner dans la zone
            String nom = IPNetwork.generateName();
            // Création d'un équipement IP
            if (element.getName().equals("Routeur")) {
                // TODO appeler la méthode de classe générer Nom
                this.ipNetwork.addIPEquipment(nom, true);
                // change la combobox
                colEquipements.getFenetreParente().getSimulateur()
                .changeComboBox();
            } else if (element.getName().equals("Ordinateur")) {
                this.ipNetwork.addIPEquipment(nom, false);
                // change la combobox
                colEquipements.getFenetreParente().getSimulateur()
                .changeComboBox();
            }

	    this.colEquipements.getFenetreParente().getZoneDessin().dessiner(
	            nom, element.getImage());
	    this.colEquipements.getFenetreParente().getZoneDessin()
	    .setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    this.colEquipements.getFenetreParente().getZoneDessin()
	    .setDessinerLien(false);
	    this.colEquipements.getFenetreParente().getSimulateur().repaint();
        }
    }
}