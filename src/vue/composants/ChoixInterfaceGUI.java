/*
 * AjoutInterface.java                             9 juin 2020
 * N7 1APP SN 2018/2019
 */
package vue.composants;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modele.IPEquipment;
import modele.IPNetwork;
import modele.NotFoundException;
import vue.controleurs.ChoixInterfaceControleur;

/**
 * Petite fenêtre pour choisi les interfaces des équipements concernés par un
 * lien
 * @author V.Bousquié
 * @version 1.0
 */
public class ChoixInterfaceGUI {

    /** La fenêtre */
    private JFrame fenetre;

    /** Label du champ choixInterface pour l'équipement 1 */
    private JLabel labelChoixInterfaceUn;

    /** Champ de choix de l'interface pour l'équipement 1 */
    private JComboBox<String> choixInterfaceEquipementUn;

    /** Label du champ choixInterface pour l'équipement 2 */
    private JLabel labelChoixInterfaceDeux;

    /** Champ de choix de l'interface pour l'équipement 2 */
    private JComboBox<String> choixInterfaceEquipementDeux;

    /** Label du champ saisieTailleLien */
    private JLabel labelTailleLien;

    /** Saisie de la taille d'un lien */
    private JTextField saisieTailleLien;

    /** Bouton pour Valider le choix*/
    private JButton btnValider;

    /** Réseau IP associé */
    private IPNetwork network;

    /** 1er Equipement concerné par le lien */
    private IPEquipment equipementUn;

    /** 2nd Equipement concerné par le lien */
    private IPEquipment equipementDeux;

    /** Container infos equipementUn */
    private JPanel partieEquipementUn;

    /** Container infos equipementDeux */
    private JPanel partieEquipementDeux;

    /** Container saisie taille du lien */
    private JPanel partieTailleLien;

    /** Container de(s) bouton(s) */
    private JPanel partieBouton;

    /** Espacement entre composant */
    private JLabel espacement;

    /** Controleur associé au composant graphique */
    private ChoixInterfaceControleur controleur;

    /**
     * Construit l'état initial
     * @param network réseau IP associé
     * @param nomEquipementUn nom du 1er équipement
     * @param nomEquipementDeux nom du 2eme équipement
     * @throws NotFoundException levée si un équipement ne possède pas d'interface
     */
    public ChoixInterfaceGUI(IPNetwork network, String nomEquipementUn,
                             String nomEquipementDeux) throws NotFoundException {

        this.fenetre = new JFrame("Choix Interfaces");
        Container contenu = fenetre.getContentPane();
        contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));

        // Création du controleur
        this.controleur = new ChoixInterfaceControleur(this);

        // Création des containers
        this.partieEquipementUn = new JPanel();
        this.partieEquipementUn.setLayout(new BoxLayout(this.partieEquipementUn, BoxLayout.X_AXIS));
        this.partieEquipementDeux = new JPanel();
        this.partieEquipementDeux.setLayout(new BoxLayout(this.partieEquipementDeux, BoxLayout.X_AXIS));
        this.partieTailleLien = new JPanel();
        this.partieTailleLien.setLayout(new BoxLayout(this.partieTailleLien, BoxLayout.X_AXIS));
        this.partieBouton = new JPanel();
        this.partieBouton.setLayout(new BorderLayout());

        // Modèle
        this.network = network;
        this.equipementUn = this.network.getIPEquipment(nomEquipementUn);
        this.equipementDeux = this.network.getIPEquipment(nomEquipementDeux);

        // Création des composants
        this.labelChoixInterfaceUn = new JLabel("Interface " + nomEquipementUn + ": ");
        this.choixInterfaceEquipementUn = new JComboBox<String>();
        this.labelChoixInterfaceDeux = new JLabel("Interface " + nomEquipementDeux + ": ");
        this.choixInterfaceEquipementDeux = new JComboBox<String>();
        this.labelTailleLien = new JLabel("Taille du lien en metres : ");
        this.saisieTailleLien = new JTextField();
        this.espacement = new JLabel("");

        if (equipementUn.listInterface().isEmpty() || equipementDeux.listInterface().isEmpty()) {
            throw new NotFoundException("Veuillez créer une interface pour chaque équipement.");
        }

        for (String uneInterface : equipementUn.listInterface()) {
            choixInterfaceEquipementUn.addItem(uneInterface);
        }

        for (String uneInterface : equipementDeux.listInterface()) {
            choixInterfaceEquipementDeux.addItem(uneInterface);
        }

        // Création du bouton
        this.btnValider = new JButton("VALIDER");
        this.btnValider.addActionListener(controleur);
        this.btnValider.setActionCommand("Valider");

        // Definit une taille minimum de la fenetre
        fenetre.setMinimumSize(new Dimension(400,200));

        this.partieEquipementUn.add(this.labelChoixInterfaceUn);
        this.partieEquipementUn.add(this.choixInterfaceEquipementUn);
        this.partieEquipementDeux.add(this.labelChoixInterfaceDeux);
        this.partieEquipementDeux.add(this.choixInterfaceEquipementDeux);
        this.partieTailleLien.add(this.labelTailleLien);
        this.partieTailleLien.add(this.saisieTailleLien);
        this.partieBouton.add(this.btnValider, BorderLayout.CENTER);

        contenu.add(this.partieEquipementUn);
        contenu.add(this.partieEquipementDeux);
        contenu.add(this.partieTailleLien);
        contenu.add(espacement);
        contenu.add(this.partieBouton);

        // Marges
        ((JComponent) contenu).setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        espacement.setBorder(BorderFactory.createEmptyBorder(15,0,0,0));

        this.fenetre.pack();
        fenetre.setAlwaysOnTop(true);
        fenetre.setVisible(true);
        fenetre.setResizable(false);
        // fenetre centrae au centre de l'acran
        fenetre.setLocationRelativeTo(null);
        // Fenetre visible
        fenetre.setVisible(true);
    }

    /**
     * Accesseur sur l'attribut network
     * @return la référence de network
     */
    public IPNetwork getNetwork() {
        return network;
    }

    /**
     * Accesseur sur l'attribut equipementUn
     * @return la référence de equipementUn
     */
    public IPEquipment getEquipementUn() {
        return equipementUn;
    }

    /**
     * Accesseur sur l'attribut equipementDeux
     * @return la référence de equipementDeux
     */
    public IPEquipment getEquipementDeux() {
        return equipementDeux;
    }

    /**
     * Accesseur sur l'attribut choixInterfaceEquipementUn
     * @return la référence de choixInterfaceEquipementUn
     */
    public JComboBox<String> getChoixInterfaceEquipementUn() {
        return choixInterfaceEquipementUn;
    }

    /**
     * Accesseur sur l'attribut choixInterfaceEquipementDeux
     * @return la référence de choixInterfaceEquipementDeux
     */
    public JComboBox<String> getChoixInterfaceEquipementDeux() {
        return choixInterfaceEquipementDeux;
    }

    /**
     * Accesseur sur l'attribut saisieTailleLien
     * @return la référence de saisieTailleLien
     */
    public JTextField getSaisieTailleLien() {
        return saisieTailleLien;
    }

    /**
     * Fermer la fenêtre courante
     */
    public void fermer() {
        this.fenetre.dispose();
    }
}
