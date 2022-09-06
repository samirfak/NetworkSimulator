/*
 * LienSauvegardable.java                     13 juin 2020
 * N7 SN 1APP 2019/2020
 */
package vue.composants;

import java.io.Serializable;

/**
 * Sauvegarder un lien
 * @author V.Bousquié
 * @version 1.0
 */
public class LienSauvegardable implements Serializable {
    
    /** Extrémité une du lien */
    private ObjetSauvegardable extremiteUn;
    
    /** Extrémité deux du lien */
    private ObjetSauvegardable extremiteDeux;
    
    /**
     * Construire l'état initial du LienSauvegardable
     * @param extremiteUn du lien
     * @param extremiteDeux du lien
     */
    public LienSauvegardable(ObjetSauvegardable extremiteUn, 
                                    ObjetSauvegardable extremiteDeux) {
        
        this.extremiteUn = extremiteUn;
        this.extremiteDeux = extremiteDeux;
    }

    /**
     * Accesseur sur l'attribut extremiteUn
     * @return la référence de extremiteUn
     */
    public ObjetSauvegardable getExtremiteUn() {
        return extremiteUn;
    }

    /**
     * Accesseur sur l'attribut extremiteDeux
     * @return la référence de extremiteDeux
     */
    public ObjetSauvegardable getExtremiteDeux() {
        return extremiteDeux;
    } 
}
