/*
 * ConfigurationControleur.java                 30 mai 2020
 * N7 SN 1APP 2019/2020
 */

package vue.controleurs;

import vue.composants.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import modele.AlreadyExistException;
import modele.IPAddress;
import modele.IPInterface;
import modele.IPNetwork;
import modele.NotFoundException;

/**
 * Controleur du composant permettant de configurer un equipement
 * @author V.Bousquie
 * @version 1.0
 */
public class ConfigurationControleur implements ActionListener {
    
    /** Composant graphique associé au controleur */
    private ConfigurationGUI configurateur;
    
    /** Réseau associé */
    private IPNetwork network;

    /**
     * Construit l'état initial du controleur
     * @param configurateur composant associé
     */
    public ConfigurationControleur(ConfigurationGUI configurateur) {
        this.configurateur = configurateur;
        this.network = configurateur.getNetwork();
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        String cmd = evt.getActionCommand();
        
        String nom = configurateur.getChampNom().getText();
        String adr = configurateur.getChampIP().getText();
        String masque = configurateur.getChampMasque().getText();
        String bandePassante = configurateur.getChampVEmission().getText();
        
        if (cmd.equals("Valider")) {
            
            if (configurateur.getChoixInterface().equals("<Nouveau element>")) {
                try {
                    this.network.getIPEquipment(
                    configurateur.getEquipementAConfigurer()).addInterface(nom, 
                    new IPAddress(adr, masque), Double.parseDouble(bandePassante));
                } catch (Exception e) {
                    JOptionPane dialog = new JOptionPane();
                    dialog.showMessageDialog(null, e.getMessage(),
                          "Erreur de configuration", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }

                // Vider les champs
                configurateur.getChampNom().setText("");
                configurateur.getChampIP().setText("");
                configurateur.getChampMasque().setText("");
                configurateur.getChampVEmission().setText("");
            } else {
                String i = configurateur.getChoixInterface();
                try {
                    IPInterface interf = this.network.getIPEquipment(
                            configurateur.getEquipementAConfigurer()).getInterfaceByName(i);
                    if (!nom.equals(interf.getName())) {
                        interf.setName(nom);
                    }
                    interf.setAddress(new IPAddress(adr, masque));
                    interf.setInterfaceBandwidth(Double.parseDouble(bandePassante));
                } catch(Exception e) {
                    JOptionPane dialog = new JOptionPane();
                    dialog.showMessageDialog(null, e.getMessage(),
                          "Erreur de configuration", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
            
            this.configurateur.remplirChamps(this.network.getIPEquipment(
                    configurateur.getEquipementAConfigurer()));
            this.configurateur.getChampInterface().setSelectedItem("<Nouveau element>");
            
        } else if (cmd.equals("TableRoutage")) {
            new TableRoutageGUI(this.network,
                    this.network.getIPEquipment(
                            configurateur.getEquipementAConfigurer()));
        } else if (cmd.equals("ChangerNom")) {
            new ChangerNomGUI(network, configurateur.getEquipementAConfigurer(),
                    configurateur.getObjetDessin());
        } else if (cmd.equals("ChoixInterface")) {
            String interfaceChoisie = configurateur.getChoixInterface();
            
            if (interfaceChoisie != null && interfaceChoisie.equals("<Nouveau elemesnt>")) {
                // Vider les champs
                configurateur.getChampNom().setText("");
                configurateur.getChampIP().setText("");
                configurateur.getChampMasque().setText("");
                configurateur.getChampVEmission().setText("");
            } else if (interfaceChoisie != null && !interfaceChoisie.equals("<Nouveau element>")) {
                try {
                    System.out.println(interfaceChoisie);
                    IPInterface i = this.network.getIPEquipment(
                            configurateur.getEquipementAConfigurer()).getInterfaceByName(interfaceChoisie);
                    configurateur.getChampNom().setText(i.getName());
                    configurateur.getChampIP().setText(i.getAddress().getIPAddress());
                    configurateur.getChampMasque().setText(i.getAddress().getNetworkMask());
                    configurateur.getChampVEmission().setText(String.valueOf(i.getInterfaceBandwidth()));
                } catch (IllegalArgumentException | NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
