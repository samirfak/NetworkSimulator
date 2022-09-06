/*
 * TableRoutageGUI.java      29 mai 2020
 * N7 1APP SN 2019/2020
 */

package vue.composants;

import vue.controleurs.*;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

import modele.IPEquipment;
import modele.IPNetwork;
import modele.Route;

/**
 * Fenetre affichant la table de routage d'un equipement
 * Zones de saisies pour ajouter une nouvelle ligne
 * dans la table de routage.
 * @author V.Bousquie
 * @version 1.0
 */
public class TableRoutageGUI {
    
    /** La fenetre principale */
    private JFrame fenetre;
    
    /** Zone de saisie de l'adresse du reseau */
    private JTextField saisieAdresseReseau;
    
    /** Zone de saisie du masque de sous-reseau */
    private JTextField saisieMasque;
    
    /** Zone de saisie de l'interface de sortie */
    private JComboBox<String> choixInterfaceSortie;
    
    /** Zone de choix de l'interface de sortie pour la route par défaut */
    private JComboBox<String> choixInterfaceSortieDefault;
    
    /** Label champ saisieAdresseReseau */
    private JLabel labelAdresseReseau;
    
    /** Label champ saisieMasque */
    private JLabel labelMasque;
    
    /** Label champ saisieInterfaceSortie */
    private JLabel labelInterfaceSortie;
    
    /** Bouton d'ajout d'une entrée dans la table */
    private JButton btnAjouter;
    
    /** Bouton pour revenir à la fenêtre principale */
    private JButton btnRetour;
    
    /** Panel avec gridLayout > afficher la table de routage d'un équipement */
    private JPanel table;
    
    /** Afficher un formulaire pour ajouter une entrée à la table de routage */
    private JPanel formulaire;
    
    /** Manager la disposition des boutons */
    private JPanel boutons;
    
    /** Controleur associé à la fenêtre */
    private TableRoutageControleur controleur;
    
    /** Equipement associé à la table de routage */
    private IPEquipment equipement;
    
    /** Réseau associé à la vue */
    private IPNetwork network;
    
    /**
     * Construit l'etat initial de la fenetre
     * @param network réseau associé
     * @param equipement associé
     */
    public TableRoutageGUI(IPNetwork network, IPEquipment equipement) {
        this.fenetre = new JFrame("Table de routage");
        Container contenu = fenetre.getContentPane();
        contenu.setLayout(new BorderLayout());
        
        // Création du modèle
        this.network = network;
        this.equipement = equipement;
        
        // Création du controleur
        this.controleur = new TableRoutageControleur(this);
        
        // Creation des zones de saisie
        saisieAdresseReseau = new JTextField();
        saisieMasque = new JTextField();
        choixInterfaceSortie = new JComboBox<String>();
        choixInterfaceSortieDefault = new JComboBox<String>();
        
        // Création des labels
        labelAdresseReseau = new JLabel("Adresse Reseau : ");
        labelMasque = new JLabel("Masque Reseau : ");
        labelInterfaceSortie = new JLabel("Adresse Interface : ");
        
        // Création des boutons
        this.boutons = new JPanel();
        btnAjouter = new JButton("AJOUTER");
        btnRetour = new JButton("RETOUR");
        this.boutons.add(btnAjouter);
        this.boutons.add(btnRetour);
        
        // Ajout controleur et commande sur les boutons
        this.btnAjouter.addActionListener(controleur);
        this.btnRetour.addActionListener(controleur);
        this.btnAjouter.setActionCommand("Ajouter");
        this.btnRetour.setActionCommand("Retour");
        this.choixInterfaceSortieDefault.addActionListener(controleur);
        this.choixInterfaceSortieDefault.setActionCommand("SortieDefaultRoute");
        
        // Remplir automatiquement la grille en fonction du modèle
        this.table = new JPanel();
        LinkedList<Route> routes = (LinkedList<Route>) equipement.getRoutingTable().getRouteList();
        
        this.table.setLayout(new GridLayout(routes.size()+2,3));
        
        // Label des colonnes
        this.table.add(new JLabel("Destination Reseau"));
        this.table.add(new JLabel("Masque Reseau"));
        this.table.add(new JLabel("Interface sortie"));
        
        // Affiche route par défaut
        this.table.add(new JLabel("0.0.0.0"));
        this.table.add(new JLabel("0.0.0.0"));
        this.table.add(choixInterfaceSortieDefault);
        
        // Parcours de la liste
        for (Route route : routes) {
            this.table.add(new JLabel(route.getDestAddress()));
            this.table.add(new JLabel(route.getDestMask()));
            this.table.add(new JLabel(route.getOutInterface().getName()));
        }
        
        //  Création du formulaire
        this.formulaire = new JPanel();
        this.formulaire.setLayout(new BoxLayout(this.formulaire, 
                                                BoxLayout.Y_AXIS));
        // Ajout des champs dans le container
        this.formulaire.add(labelAdresseReseau);
        this.formulaire.add(saisieAdresseReseau);
        this.formulaire.add(labelMasque);
        this.formulaire.add(saisieMasque);
        this.formulaire.add(labelInterfaceSortie);
        this.formulaire.add(choixInterfaceSortie);
        
        // Ajout des composants sur la fenêtre
        contenu.add(this.table, BorderLayout.NORTH);
        contenu.add(this.formulaire, BorderLayout.CENTER);
        contenu.add(this.boutons, BorderLayout.SOUTH);
        
        // Remplir la combobox en fonction des interfaces de l'équipement
        List<String> interfaces = equipement.listInterface();
        
        for (String uneInterface : interfaces) {
            this.choixInterfaceSortie.addItem(uneInterface);
            this.choixInterfaceSortieDefault.addItem(uneInterface);
        }
        
        // Ajout de marges
        // Creation de marges pour "aerer le formulaire"
        ((JComponent) contenu).setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        labelAdresseReseau.setBorder(BorderFactory.createEmptyBorder(50,0,10,0));
        labelMasque.setBorder(BorderFactory.createEmptyBorder(40,0,10,0));
        labelInterfaceSortie.setBorder(BorderFactory.createEmptyBorder(40,0,10,0));
        
        // Definit une taille minimum de la fenetre
        fenetre.setMinimumSize(new Dimension(800,500));
        
        this.fenetre.pack();
        fenetre.setVisible(true);
        // fenetre centrae au centre de l'acran
        fenetre.setLocationRelativeTo(null);
        // Fenetre visible
        fenetre.setVisible(true);
    }
    
    /**
     * Accesseur sur l'attribut fenetre
     * @return la référence de la fenêtre
     */
    public JFrame getFenetre() {
        return this.fenetre;
    }
    
    /**
     * Accesseur sur l'attribut saisieAdresseReseau
     * @return la référence du JTextField
     */
    public JTextField getSaisieAdresseReseau() {
        return saisieAdresseReseau;
    }
    
    /**
     * Accesseur sur l'attribut saisieMasque
     * @return la référence du JTextField
     */
    public JTextField getSaisieMasque() {
        return saisieMasque;
    }
    
    /**
     * Accesseur sur l'attribut choixInterfaceSortie
     * @return la référence de la combo box
     */
    public JComboBox<String> getChoixInterfaceSortie() {
        return choixInterfaceSortie;
    }
    
    /**
     * Accesseur sur l'attribut network
     * @return la référence du réseau IP associé
     */
    public IPNetwork getNetwork() {
        return network;
    }
    
    /**
     * Accesseur sur l'attribut equipement
     * @return la référence de l'équipement IP
     */
    public IPEquipment getEquipement() {
        return equipement;
    }

    /**
     * Accesseur sur l'attribut choixInterfaceSortieDefault
     * @return la référence de choixInterfaceSortieDefault
     */
    public JComboBox<String> getChoixInterfaceSortieDefault() {
        return choixInterfaceSortieDefault;
    }
}
