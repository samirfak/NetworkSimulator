/*
 * ConfigurationGUI.java          25 mai 2020
 * N7 1APP SN 2019/2020
 */

package vue.composants;
import java.awt.BorderLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modele.IPEquipment;
import modele.IPNetwork;
import vue.controleurs.ConfigurationControleur;

/**
 * Module graphique: Colonne de configuration
 * Permet la configuration d'un equipement à l'aide d'une saisie
 * de diverses informations comme le nom, l'adresse Ip ...
 * @author V.Bousquie
 * @version 1.0
 */
public class ConfigurationGUI extends JPanel {

    /** Label affichant le champ "nom" */
    private JLabel labelNom;

    /** Label affichant le champ "Adresse IPv4" */
    private JLabel labelIP;

    /** Label affichant le champ "Masque" */
    private JLabel labelMasque;

    /** Label affichant le champ "Vitesse d'emission en bit/s" */
    private JLabel labelVEmission;

    /** Label affichant le champ "Interface" */
    private JLabel labelInterface;

    /** Zone de saisie pour le nom d'une interface*/
    private JTextField champNom;

    /** Zone de saisie pour l'adresse IPv4 */
    private JTextField champIP;

    /** Zone de saisie pour le masque de sous-reseau */
    private JTextField champMasque;

    /** Zone de saisie pour la vitesse d'emission en bit/s */
    private JTextField champVEmission;

    /** Bouton pour valider la configuration */
    private JButton btnValider;

    /** Bouton pour afficher la table de routage d'un équipement */
    private JButton btnTableRoutage;

    /** Bouton pour changer le nom d'un equipement l'équipement */
    private JButton btnChangerNom;

    /** Disposition des boutons pour les centrer dans le container */
    private JPanel dispoBoutons;

    /** Label invisible pour espacer les boutons des autres composants */
    private JLabel espacement;

    /** Controleur associé au composant */
    private ConfigurationControleur controleur;

    /**
     * Zone de saisie de l'interface, celle ci sera l'interface configuree
     * avec l'adresse IP saisie precedemment
     */
    private JComboBox<String> champInterface;

    /** IP Network */
    private IPNetwork network;

    /** Nom de l'équipement à configurer */
    private String equipementAConfigurer;

    /** ObjetDessin associé à la configuration */
    private ObjetDessin objetDessin;

    /**
     * Construit l'etat initial de l'interface de configuration
     * Avec des labels, des champs de saisie et une combo box
     * @param network  modele
     */
    public ConfigurationGUI(IPNetwork network) {

        // Appel du constructeur de la super-classe
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.network = network;

        this.equipementAConfigurer = "";
        this.objetDessin = null;

        // Création du controleur
        this.controleur = new ConfigurationControleur(this);

        // Creation des labels
        this.labelNom = new JLabel("Nom Interface:");
        this.labelIP = new JLabel("AdresseIPv4 : ");
        this.labelMasque = new JLabel("Masque : ");
        this.labelVEmission = new JLabel("Bande Passante : ");
        this.labelInterface = new JLabel("Interface : ");

        // Creation des champs de saisies
        this.champNom = new JTextField();
        this.champIP = new JTextField();
        this.champMasque = new JTextField();
        this.champVEmission = new JTextField();
        this.champInterface = new JComboBox<String>();
        this.btnValider = new JButton("VALIDER");
        this.btnValider.setHorizontalAlignment(JLabel.CENTER);
        this.btnTableRoutage = new JButton("TABLE ROUTAGE");
        this.btnTableRoutage.setHorizontalAlignment(JLabel.CENTER);
        this.btnChangerNom = new JButton("CHANGER NOM");
        this.btnChangerNom .setHorizontalAlignment(JLabel.CENTER);
        this.espacement = new JLabel("");

        // Création de la disposition des boutons
        this.dispoBoutons = new JPanel();
        this.dispoBoutons.setLayout(new BorderLayout(15,15));
        this.dispoBoutons.add(btnValider, BorderLayout.NORTH);
        this.dispoBoutons.add(btnChangerNom, BorderLayout.CENTER);
        this.dispoBoutons.add(btnTableRoutage, BorderLayout.SOUTH);

        // Ajout de commandes sur les boutons
        this.btnValider.addActionListener(controleur);
        this.btnValider.setActionCommand("Valider");
        this.btnTableRoutage.addActionListener(controleur);
        this.btnTableRoutage.setActionCommand("TableRoutage");
        this.btnChangerNom.addActionListener(controleur);
        this.btnChangerNom.setActionCommand("ChangerNom");
        this.champInterface.addActionListener(controleur);
        this.champInterface.setActionCommand("ChoixInterface");

        // Ajout des champs et labels

        this.add(this.labelInterface);
        this.add(this.champInterface);

        this.add(this.labelNom);
        this.add(this.champNom);

        this.add(this.labelIP);
        this.add(this.champIP);

        this.add(this.labelMasque);
        this.add(this.champMasque);

        this.add(this.labelVEmission);
        this.add(this.champVEmission);

        this.add(this.espacement);

        this.add(this.dispoBoutons);

        // Creation de marges pour "aerer le formulaire"
        this.setBorder(BorderFactory.createEmptyBorder(40,20,40,20));
        this.labelIP.setBorder(BorderFactory.createEmptyBorder(40,0,0,0));
        this.labelMasque.setBorder(BorderFactory.createEmptyBorder(40,0,0,0));
        this.labelVEmission.setBorder(BorderFactory.createEmptyBorder(40,0,0,0));
        this.labelNom.setBorder(BorderFactory.createEmptyBorder(40,0,0,0));
        this.espacement.setBorder(BorderFactory.createEmptyBorder(40,0,10,0));
    }

    /**
     * Remplie les champs de la combo box des interfaces
     * @param equipement dont on souhaite afficher les interfaces
     */
    public void remplirChamps(IPEquipment equipement) {

        List<String> interfaces = equipement.listInterface();
        this.equipementAConfigurer = equipement.getName();

        this.champInterface.removeAllItems();

        this.champInterface.addItem("<Nouveau element>");

        for (String uneInterface : interfaces) {
            this.champInterface.addItem(uneInterface);
        }
    }

    /**
     * Accesseur sur le champNom
     * @return la référence de champNom
     */
    public JTextField getChampNom() {
        return champNom;
    }

    /**
     * Accesseur sur le champIP
     * @return la référence de champIP
     */
    public JTextField getChampIP() {
        return champIP;
    }

    /**
     * Accesseur sur le champMasque
     * @return la référence champMasque
     */
    public JTextField getChampMasque() {
        return champMasque;
    }

    /**
     * Accesseur sur le champVEmission
     * @return la référence champVEmission
     */
    public JTextField getChampVEmission() {
        return champVEmission;
    }

    /**
     * Accesseur sur le modèle
     * @return la référence de network
     */
    public IPNetwork getNetwork() {
        return network;
    }

    /**
     * Accesseur sur l'équipement à configurer
     * @return equipementAConfigurer
     */
    public String getEquipementAConfigurer() {
        return equipementAConfigurer;
    }

    /**
     * Retourne l'item sélectionné dans la combobox
     * @return le choix de l'utilisateur
     */
    public String getChoixInterface() {
        if (this.champInterface.getSelectedItem() != null) {
            return this.champInterface.getSelectedItem().toString();
        } else {
            return null;
        }
    }

    /**
     * Retourne la référence de la combobox
     * @return la référence de champInterface
     */
    public JComboBox<String> getChampInterface() {
        return champInterface;
    }

    /**
     * Accesseur sur l'attribut objetDessin
     * @return la référence de objetDessin
     */
    public ObjetDessin getObjetDessin() {
        return this.objetDessin;
    }

    /**
     * Modifier la valeur de objetDessin
     * @param newObjet référence du nouvel objetDessin
     */
    public void setObjetDessin(ObjetDessin newObjet) {
        this.objetDessin = newObjet;
    }
}
