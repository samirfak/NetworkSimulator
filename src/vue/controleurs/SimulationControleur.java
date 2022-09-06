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

    /** Composants de simulation associÃ© au controleur */
    private SimulationGUI simulateur;

    /** RÃ©seau IP associÃ© Ã  la vue et au controleur > ModÃ¨le */
    private IPNetwork network;

    /** equipements emetteur, destinataire */
    private IPEquipment emetteur, destinataire;

    /** message envoyé  */
    private String message;


    /**
     * Construit l'Ã©tat initial du controleur
     * @param simulateur associÃ© au controleur
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
        	// récupère les informations des champs
        	try {
	        	// l'émetteur
	            emetteur =  network.getIPEquipment(simulateur.getEmetteur());
	            // le destinataire
	            destinataire = network.getIPEquipment(
	            		simulateur.getDestinataire());
	            // le message
	            message = simulateur.getMessage();
	            // affiche le résultat de la simulation
	            ProgressBarGUI pr = new ProgressBarGUI(simulateur);

        	} catch (IllegalArgumentException e) {
        		dialog = new JOptionPane();
                dialog.showMessageDialog(null, e.getMessage(),
                      "Error", JOptionPane.ERROR_MESSAGE);
			}
        }
    }


}
