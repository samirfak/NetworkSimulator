/*
 * SimulationGUI.java          29 mai 2020
 * N7 1APP SN 2019/2020
 */

package vue.composants;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import modele.IPNetwork;
import vue.controleurs.SimulationControleur;

/**
 * Module graphique: Colonne de simulation
 * Permet la simulation d'un reseau à partir de ses configurations
 * @author Samir FAKOREDE, V.Bousquié
 * @version 1.0
 */
public class SimulationGUI extends JPanel {

    /** Label affichant le champ "Emetteur" */
    private JLabel labelEmt;

    /** Label affichant le champ "Destinataire" */
    private JLabel labelDest;

    /** mod�le de la combo box emetteur */
    private DefaultComboBoxModel<String> modelEmt =
    		new DefaultComboBoxModel<String>();

    /** mod�le de la combo box destinataire */
    private DefaultComboBoxModel<String> modelDest =
    		new DefaultComboBoxModel<String>();

    /**
     * Liste deroulante de selection de l'emetteur parmi les machines
     * prealablement configurees associ� au model emetteur
     */
    private JComboBox<String> champEmt;


    /**
     * Liste deroulante de selection du destinataire parmi les machines
     *  prealablement configurees associ� au model destinataire
     */
    private JComboBox<String> champDest;

    /** Bouton pour lancer une simulation */
    private JButton btnSimuler;

    /** Label invisible pour espacer les composants */
    private JLabel espacement;

    /** Disposition des boutons pour les centrer dans le container */
    private JPanel dispoBouton;

    /** IP Network */
    private IPNetwork network;

    /** Label associé à la saisie d'un message */
    private JLabel labelSaisiMessage;

    /** Zone de saisi d'un message */
    private JTextField saisiMessage;

    /** Controleur associé à la vue */
    private SimulationControleur controleur;

    /** DocumentListener sur les champs */
    private DocumentListener listener;

    /**
     * Construit l'etat initial de l'interface de simulation
     * @param network modèle associé à la vue
     */
    public SimulationGUI(IPNetwork network) {

        // Appel du constructeur de la super-classe
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.network = network;

        this.listener = new MyDocListener();

        // Création du controleur
        this.controleur = new SimulationControleur(this);

        // Creation des labels
        this.labelEmt = new JLabel("Emetteur :");
        this.labelDest = new JLabel("Destinataire : ");
        this.labelSaisiMessage = new JLabel("Contenu du message : ");
        this.espacement = new JLabel("");

        // Creation des champs de saisies
        this.champEmt = new JComboBox<String>(modelEmt);

        this.champDest = new JComboBox<String>(modelDest);

        this.saisiMessage = new JTextField();
        // ajout d'un document listener sur le champ saisi message
        this.saisiMessage.getDocument().addDocumentListener(listener);

        // Creation du bouton
        this.btnSimuler = new JButton("SIMULER");
        this.btnSimuler.setEnabled(false);
        this.btnSimuler.setHorizontalAlignment(JLabel.CENTER);
        this.btnSimuler.addActionListener(this.controleur);
        this.btnSimuler.setActionCommand("Simuler");

        this.dispoBouton = new JPanel();
        this.dispoBouton.setLayout(new BorderLayout());
        this.dispoBouton.add(btnSimuler,BorderLayout.CENTER);


        // Ajout des composants sur le pannel
        this.add(this.labelEmt);
        this.add(this.champEmt);

        this.add(this.labelDest);
        this.add(this.champDest);

        this.add(this.labelSaisiMessage);
        this.add(this.saisiMessage);

        this.add(this.espacement);

        this.add(this.dispoBouton);

        // Creation de marges pour "aerer le formulaire"
        this.setBorder(BorderFactory.createEmptyBorder(50,20,50,20));
        this.labelEmt.setBorder(BorderFactory.createEmptyBorder(50,0,10,0));
        this.labelDest.setBorder(BorderFactory.createEmptyBorder(50,0,10,0));
        this.labelSaisiMessage.setBorder(BorderFactory
        		.createEmptyBorder(50,0,10,0));
        this.espacement.setBorder(BorderFactory.createEmptyBorder(90,0,10,0));
        
        this.changeComboBox();


     }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    /**
     * Accesseur sur l'attribut network
     * @return la référence du modèle
     */
    public IPNetwork getNetwork() {
        return network;
    }

    /**
     * Retourne l'emetteur
     * @return champ emt
     */
    public String getEmetteur() {
    	return (String)this.champEmt.getSelectedItem();
    }

    /**
     * Retourne le destinataire
     * @return champ dest
     */
    public String getDestinataire() {
    	return (String)this.champDest.getSelectedItem();
    }

    /**
     * Retourne le message
     * @return message
     */
    public String getMessage() {
    	return this.saisiMessage.getText();
    }

    /**
     * TODO commenter le rôle de cette méthode
     * @param network
     */
    public void setNetwork(IPNetwork network) {
       this.network = network; 
    }
    
    /**
     * Active le bouton simuler si tous les champs
     * sont remplis
     * @author Thomas
     *
     */
    private class MyDocListener implements DocumentListener {

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			changedUpdate(e);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			changedUpdate(e);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			if (champEmt.getSelectedItem() != null &&
					champDest.getSelectedItem() != null &&
					saisiMessage.getText().length() > 0) {
				// si tous les champs sont remplis
				btnSimuler.setEnabled(true); // btn enabled
			} else {
				btnSimuler.setEnabled(false); // btn disabled
			}
		}

    }

    public void changeComboBox() {
    	List<String> equipements = network.getIPEquipmentNameList();
    	modelEmt.removeAllElements();
    	modelDest.removeAllElements();
    	for (String nomEquipement : equipements) {
    		modelEmt.addElement(nomEquipement);
    		modelDest.addElement(nomEquipement);
    		modelEmt.setSelectedItem(nomEquipement);
    		modelDest.setSelectedItem(nomEquipement);
    	}
    }
}