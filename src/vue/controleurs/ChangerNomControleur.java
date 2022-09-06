/*
 * ChangerNomControleur.java        9 juin 2020
 * N7 SN 1APP 2019/2020 
 */
package vue.controleurs;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import modele.AlreadyExistException;
import modele.IPEquipment;
import modele.IPNetwork;
import vue.composants.ChangerNomGUI;

/**
 * Controleur de la fenêtre de changement de nom
 * @author V.Bousquié
 * @version 1.0
 */
public class ChangerNomControleur implements ActionListener {
    
    /** Fenêtre de changement de nom d'un équipement */
    private ChangerNomGUI fenetreChangerNom;
    
    /** Réseau IP (Modèle) */
    private IPNetwork network;
    
    /**
     * Construit l'état initial du constructeur
     * @param fenetreChangerNom fenêtre 
     */
    public ChangerNomControleur(ChangerNomGUI fenetreChangerNom) {
        this.fenetreChangerNom = fenetreChangerNom;
        this.network = this.fenetreChangerNom.getNetwork();
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        String cmd = evt.getActionCommand();
        IPEquipment equipement = this.network.getIPEquipment(fenetreChangerNom.getNomEquipement());
                
        if (cmd.equals("Valider")) {
            if (fenetreChangerNom.getSaisiNom().getText() != "") {
                try {
                    // Mise à jour du modèle
                    this.network.setEquipmentName(equipement, fenetreChangerNom.getSaisiNom().getText());
                    
                    // Mise à jour de la vue
                    this.fenetreChangerNom.getObjetDessin().setNom(fenetreChangerNom.getSaisiNom().getText());
                } catch (AlreadyExistException e) {
                    JOptionPane dialog = new JOptionPane();
                    dialog.showMessageDialog(null, e.getMessage(),
                          "Erreur de configuration", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
            
            this.fenetreChangerNom.fermer();
        }
    }
}
