/*
 * ObjetDessinControleur.java                        7 juin 2020
 * N7 SN 1APP 2019/2020
 */
package vue.controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vue.composants.ObjetDessin;

/**
 * Controleur d'un ObjetDessin
 * @author V.Bousquié
 * @version 1.0
 */
public class ObjetDessinControleur implements ActionListener {

    /** ObjetDessin associé au controleur */
    private ObjetDessin objetDessin;

    /**
     * Construit l'état initial du controleur
     * @param objet de dessin
     */
    public ObjetDessinControleur(ObjetDessin objet) {
        this.objetDessin = objet;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        this.objetDessin.getZoneDessin().supprimerComponent(this.objetDessin, this.objetDessin.getNom());
    }
}
