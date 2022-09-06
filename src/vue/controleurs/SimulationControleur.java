/*
 * SimulationControleur.java            30 mai 2020
 * N7 SN 1APP 2019/2020
 */

package vue.controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import modele.IPEquipment;
import modele.IPNetwork;
import vue.composants.ProgressBarGUI;
import vue.composants.ResultatSimulation;
import vue.composants.SimulationGUI;

/**
 * Controleur d'un objet SimulationGUI
 * @author V.Bousquie
 * @version 1.0
 */
public class SimulationControleur implements ActionListener {

    /** Composants de simulation associé au controleur */
    private SimulationGUI simulateur;

    /** Réseau IP associé à la vue et au controleur > Modèle */
    private IPNetwork network;

    /** equipements emetteur, destinataire */
    private IPEquipment emetteur, destinataire;

    /** message envoy�  */
    private String message;


    /**
     * Construit l'état initial du controleur
     * @param simulateur associé au controleur
     */
    public SimulationControleur(SimulationGUI simulateur) {
        this.simulateur = simulateur;
        this.network = simulateur.getNetwork();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String cmd = evt.getActionCommand();
        ResultatSimulation result;
        JOptionPane dialog;

        if (cmd.equals("Simuler")) {
            // TODO
        	// r�cup�re les informations des champs
        	try {
	        	// l'�metteur
	            emetteur =  network.getIPEquipment(simulateur.getEmetteur());
	            // le destinataire
	            destinataire = network.getIPEquipment(
	            		simulateur.getDestinataire());
	            // le message
	            message = simulateur.getMessage();
	            // affiche le r�sultat de la simulation
	            ProgressBarGUI pr = new ProgressBarGUI(simulateur);

        	} catch (IllegalArgumentException e) {
        		dialog = new JOptionPane();
                dialog.showMessageDialog(null, e.getMessage(),
                      "Error", JOptionPane.ERROR_MESSAGE);
			}
        }
    }


}
