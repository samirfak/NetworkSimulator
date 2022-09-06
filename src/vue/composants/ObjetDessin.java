/*
 * ObjetDessin.java                        31 mai 2020
 * N7 SN 1APP 2019/2020
 */

package vue.composants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vue.controleurs.ObjetDessinControleur;

/**
 * Represente un objet dessine sur la zone de dessin
 * Un nom lui est associe ainsi qu'une image
 * @author V.Bousquie, T.ESCUDERO
 * @version 1.0
 */
public class ObjetDessin extends JPanel {

    /** Nom de l'equipement */
    private JLabel nom;

    /** Image de l'equipement */
    private JLabel image;

    /** Bouton pour supprimer le composant */
    private JButton btnSupprimer;

    /** Controleur de l'ObjetDessin */
    private ObjetDessinControleur controleur;

    /** Zone de dessin contenant l'objet courant */
    private ZoneDessinGUI zoneDessin;
    
    /** Sauvegarder l'icone */
    private Icon iconImage;
    
    /** Objet de sauvegarde associé */
    private ObjetSauvegardable saveObjet;

    /**
     * Construit l'etat initial de l'ObjetDessin
     * @param zone
     * @param nom
     * @param cheminImage
     */
    public ObjetDessin(ZoneDessinGUI zone, String nom, String cheminImage) {
        super();
        this.zoneDessin = zone;
        this.setLayout(new BorderLayout());
        this.nom = new JLabel(nom);
        this.nom.setHorizontalAlignment(JLabel.CENTER);
        this.image = new JLabel();
        this.image.setHorizontalAlignment(JLabel.CENTER);
        this.image.setIcon(new ImageIcon(
        		new ImageIcon(cheminImage).getImage()
        		.getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
        this.btnSupprimer = new JButton("Suppr");

        // Création du controleur
        this.controleur = new ObjetDessinControleur(this);
        this.btnSupprimer.addActionListener(this.controleur);

        // Ajout des composants sur le Panel
        this.add(this.image, BorderLayout.NORTH);
        this.add(this.nom, BorderLayout.CENTER);
        this.add(this.btnSupprimer, BorderLayout.SOUTH);
        this.btnSupprimer.setVisible(false);
        this.setBackground(Color.WHITE);
    }

    /**
     * Construit un ObjetDessin avec un nom et une icone
     * @param zone
     * @param nom de l'objet
     * @param image icone de l'objet
     */
    public ObjetDessin(ZoneDessinGUI zone, String nom, Icon image) {
        super();
        this.iconImage = image;
        this.zoneDessin = zone;
        this.setLayout(new BorderLayout());
        this.nom = new JLabel(nom);
        this.nom.setHorizontalAlignment(JLabel.CENTER);
        this.image = new JLabel();
        this.image.setHorizontalAlignment(JLabel.CENTER);
        this.image.setIcon(image);
        this.btnSupprimer = new JButton("Suppr");

        // Création du controleur
        this.controleur = new ObjetDessinControleur(this);
        this.btnSupprimer.addActionListener(this.controleur);

        this.add(this.image, BorderLayout.NORTH);
        this.add(this.nom, BorderLayout.CENTER);
        this.add(btnSupprimer, BorderLayout.SOUTH);
        this.btnSupprimer.setVisible(false);
        this.setBackground(Color.WHITE);
    }

    /**
     * Accesseur sur l'attribut btnSupprimer
     * @return la référence du bouton supprimer
     */
    public JButton getBtnSupprimer() {
        return this.btnSupprimer;
    }

    /**
     * Accesseur sur l'attribut zoneDessin
     * @return la référence de la zone de dessin associée
     */
    public ZoneDessinGUI getZoneDessin() {
        return this.zoneDessin;
    }

    /**
     * Retourne le nom de l'objet en fonction du label nom
     * @return la chaine de caractère obtenue
     */
    public String getNom() {
        return this.nom.getText();
    }

    /**
     * Modifier le nom de l'objet
     * @param newNom à afficher
     */
    public void setNom(String newNom) {
        this.nom.setText(newNom);
    }
    
    /**
     * Accesseur sur l'attribut iconImage
     * @return la référence de iconImage
     */
    public Icon getIconImage() {
        return this.iconImage;
    }
    
    /**
     * Sauvegarde les infos dans l'objet saveObjet
     */
    public void sauvegarder() {
        saveObjet = new ObjetSauvegardable(this.getNom(),this.getX(), this.getY(), this.getIconImage());
    }
    
    /**
     * Accesseur sur l'attribut saveObjet
     * @return la référence de saveObjet
     */
    public ObjetSauvegardable getSaveObjet() {
        return saveObjet;
    }
}
