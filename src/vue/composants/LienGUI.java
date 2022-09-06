/*
 * LienGUI.java    10 juin 2020
 * N7 SN 1APP 2019/2020 
 */
package vue.composants;

/**
 * Représente un lien entre 2 Objets Dessin présents sur la Zone
 * de Dessin
 * @author V.Bousquié
 * @version 1.0
 */
public class LienGUI {
    
    /** Un extrémité du Lien */
    private ObjetDessin extremiteUn;
    
    /** Une autre extrémité du lien */
    private ObjetDessin extremiteDeux;
    
    /**
     * Construit l'état initial du lien
     * @param extremiteUn
     * @param extremiteDeux 
     */
    public LienGUI(ObjetDessin extremiteUn, ObjetDessin extremiteDeux) {
        this.extremiteUn = extremiteUn;
        this.extremiteDeux = extremiteDeux;
    }
    
    /**
     * Accesseur sur l'attribut extremiteUn du lien
     * @return la référence de la première extrémité du lien
     */
    public ObjetDessin getExtremiteUn() {
        return this.extremiteUn;
    }
    
    /**
     * Accesseur sur l'attribut extremiteDeux du lien
     * @return la référence de la deuxième extrémité du lien
     */
    public ObjetDessin getExtremiteDeux() {
        return this.extremiteDeux;
    }
}
