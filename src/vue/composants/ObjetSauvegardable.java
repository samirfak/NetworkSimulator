/*
 * ObjetSauvegardable.java              13 juin 2020
 * N7 SN 1APP 2018/2019
 */
package vue.composants;

import java.io.Serializable;

import javax.swing.Icon;

/**
 * Création d'un objet sauvegardable
 * @author V.Bousquié
 * @version 1.0
 */
public class ObjetSauvegardable implements Serializable {
    
    /** Clé de hachage */
    private static final long serialVersionUID = 1L;
    
    /** Nom associé à l'objet*/
    private String nom;
    
    /** Abscisse de l'objet */
    private int x;
    
    /** Ordonnée de l'objet */
    private int y;
    
    /** Image associée à l'objet */
    private Icon image;
    
    /**
     * Construit l'état initial de l'objet
     * @param nom de l'objet
     * @param x abscisse de l'objet
     * @param y ordonnée de l'objet
     * @param image associée
     */
    public ObjetSauvegardable(String nom, int x, int y, Icon image) {
        this.nom = nom;
        this.x = x;
        this.y = y;
        this.image = image;
    }

    /**
     * Accesseur sur l'attribut nom
     * @return la valeur de nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Accesseur sur l'abscisse
     * @return la valeur de x
     */
    public int getX() {
        return x;
    }

    /**
     * Accesseur sur l'ordonnée
     * @return la valeur de y
     */
    public int getY() {
        return y;
    }

    /**
     * Accesseur sur l'image associée
     * @return la référence de image
     */
    public Icon getImage() {
        return image;
    }
}
