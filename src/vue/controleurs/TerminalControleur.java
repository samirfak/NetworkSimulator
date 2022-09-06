/*
 * TerminalControleur.java  27 mai 2020
 * N7 1APP SN 2019/2020
 */

package vue.controleurs;

import vue.composants.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

/**
 * Controleur de la fenêtre Terminal. Interpète egalement la saisie
 * de l'utilisateur pour modifier la vue
 * @author V.Bousquie
 * @version 1.0
 */
public class TerminalControleur extends KeyAdapter {
    
    /** Terminal sur lequel le controleur agit */
    private TerminalGUI terminal;
    
    /**
     * Construit l'etat initial du controleur
     * @param terminal interface du terminal
     */
    public TerminalControleur(TerminalGUI terminal) {
        this.terminal = terminal;
    }

    @Override
    public void keyTyped(KeyEvent evt) {
        JTextField textfield = (JTextField) evt.getSource();
        if (evt.getKeyChar() == '\n') {
            this.interpreter(textfield.getText());
            
            /* Ajout d'une nouvelle ligne de saisie */
            this.terminal.addSaisie();
        }
    }
    
    
    /**
     * Interpréter la commande en fonction des arguments
     * @param cmd commande saisie par l'utilisateur
     */
    private void interpreter(String cmd) {
        String[] sousChaines = cmd.split(" ");
        try {
        	Commandes.valueOf(sousChaines[0]).execute(terminal, sousChaines);
        }
        catch(IllegalArgumentException e) {
        	this.terminal.addResultat("Commande inconnue.");
        }
    }
}
