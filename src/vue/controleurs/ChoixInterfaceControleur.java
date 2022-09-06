/*
 * ChoixInterfaceControleur.java           12 juin 2020
 * N7 SN 1APP 2019/2020 
 */
package vue.controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import modele.AlreadyExistException;
import modele.IPEquipment;
import modele.IPNetwork;
import modele.NotFoundException;
import vue.composants.ChoixInterfaceGUI;

/**
 * Controleur du composant graphique ChoixInterfaceGUI
 * @author V.Bousquié
 * @version 1.0
 */
public class ChoixInterfaceControleur implements ActionListener {
       
    /** Composant graphique associé au controleur */
    private ChoixInterfaceGUI choixInterface;
    
    /**
     * Construit l'état initial du controleur
     * @param choixInterface 
     */
    public ChoixInterfaceControleur(ChoixInterfaceGUI choixInterface) {
        this.choixInterface = choixInterface;
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        String cmd = evt.getActionCommand();
        
        if (cmd.equals("Valider"))  {
            // Création du lien dans le modèle
            String nomInterfaceEquipementUn = 
                    (String) choixInterface.getChoixInterfaceEquipementUn().getSelectedItem();
            String nomInterfaceEquipementDeux = 
                    (String) choixInterface.getChoixInterfaceEquipementDeux().getSelectedItem();
            int tailleLien = Integer.parseInt(choixInterface.getSaisieTailleLien().getText());
            
            try {
                choixInterface.getNetwork().addLink(choixInterface.getEquipementUn(), nomInterfaceEquipementUn, 
                        choixInterface.getEquipementDeux(), nomInterfaceEquipementDeux, tailleLien);
            } catch (Exception e) {
                JOptionPane dialog = new JOptionPane();
                dialog.showMessageDialog(null, e.getMessage(),
                      "Erreur de configuration", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } finally {
                choixInterface.fermer();
            }
        }
    }
}
