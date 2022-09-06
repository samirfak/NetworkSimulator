package vue.composants;

import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import vue.controleurs.MenuControleur;

/**
 * Classe pour le menu de l'application
 * @author ESCUDERO Thomas
 * @Version 1.0
 */

public class Menu extends JMenuBar {

    /* Les menus */
	/** menu Fichier */
    private JMenu menu_fichier = new JMenu("Fichier");
    /** menu Fichier */
    private JMenu menu_editer = new JMenu("Editer");
    /** bouton Proprietes */
    private JButton menu_proprietes = new JButton("Proprietes");
    /** bouton Aide */
    private JButton menu_aide = new JButton("Aide");
    /** bouton Terminal */
    private JButton menu_terminal = new JButton("Terminal");

    /* Les items */
    /** item ouvrir dans Fichier + raccourci ctrl+O */
    private JMenuItem fichier_ouvrir = new Option("Ouvrir");
    /** item ouvrir dans Fichier + raccourci ctrl+S */
    private JMenuItem fichier_enregistrer = new Option("Enregistrer");
    /** item ouvrir dans Fichier + raccourci ctrl+maj+S */
    private JMenuItem fichier_enregistrer_sous = new Option("Enregistrer sous");
    /** item exporter en pdf */
    private JMenuItem fichier_exporter_pdf = new Option("Exporter en pdf");
    /** item exporter les configs */
    private JMenuItem fichier_exporter_config = new Option("Exporter les configs");

    /** item couper dans Editer + raccourci ctrl+X */
    private JMenuItem editer_couper = new Option("Couper");
    /** item copier dans Editer + raccourci ctrl+C */
    private JMenuItem editer_copier = new Option("Copier");
    /** item coller dans Editer + raccourci ctrl+X */
    private JMenuItem editer_coller = new Option("Coller");

    /** Controleur du Menu */
    private MenuControleur controleur;

    /** Fenêtre parente qui "accueille" le menu */
    private FenetrePrincipale fenetreParente;

    /** Bouton permettant de zoomer la zone de dessin */
    private JButton btnZoom = new JButton("+");

    /** Bouton permettant de dezoomer la zone de dessin */
    private JButton btnDezoom = new JButton("-");

    /**
     * Construit le menu et ses items
     * @param fenetreParente du menu
     */
    public Menu(FenetrePrincipale fenetreParente) {

        /* Creation du controleur */
        this.controleur = new MenuControleur(this);

        this.fenetreParente = fenetreParente;

        /* Ajout de commandes sur les boutons */
        this.menu_terminal.setActionCommand("Terminal");
        this.menu_terminal.addActionListener(controleur);
        this.menu_aide.setActionCommand("Aide");
        this.menu_aide.addActionListener(controleur);
        this.menu_proprietes.setActionCommand("Proprietes");
        this.menu_proprietes.addActionListener(controleur);
        this.btnZoom.setActionCommand("Zoom");
        this.btnZoom.addActionListener(controleur);
        this.btnDezoom.setActionCommand("Dezoom");
        this.btnDezoom.addActionListener(controleur);

        // ajout des Mnemomic
        this.menu_fichier.setMnemonic('f');
        this.menu_editer.setMnemonic('e');

        this.add(menu_fichier);
        this.add(menu_editer);
        this.add(menu_proprietes);
        this.add(menu_aide);
        this.add(menu_terminal);

        // Ajout des boutons de Zoom
        this.add(btnZoom);
        this.add(btnDezoom);

        // gestion des raccourcis clavier


        menu_fichier.add(fichier_ouvrir);
        menu_fichier.add(fichier_enregistrer);
        menu_fichier.add(fichier_enregistrer_sous);
        menu_fichier.add(fichier_exporter_pdf);
        menu_fichier.add(fichier_exporter_config);

        /* Ajout de commandes sur les boutons */
        this.fichier_ouvrir.setActionCommand("Ouvrir");
        this.fichier_ouvrir.addActionListener(controleur);
        this.fichier_enregistrer.setActionCommand("enregistrer");
        this.fichier_enregistrer.addActionListener(controleur);
        this.fichier_enregistrer_sous.setActionCommand("enregistrerSous");
        this.fichier_enregistrer_sous.addActionListener(controleur);
        this.fichier_exporter_pdf.setActionCommand("exporterPdf");
        this.fichier_exporter_pdf.addActionListener(controleur);
        this.fichier_exporter_config.setActionCommand("exporterConfig");
        this.fichier_exporter_config.addActionListener(controleur);

        menu_editer.add(editer_couper);
        menu_editer.add(editer_copier);
        menu_editer.add(editer_coller);
    }

    /**
     * Accesseur sur l'attribut fenetreParente
     * @return l'adresse de fenetreParente
     */
    public FenetrePrincipale getFenetreParente() {
        return this.fenetreParente;
    }

    /**
     * Ajoute une option pour les item de Fichier
     * et Editer
     * @author ESCUDERO Thommas
     *
     */
    static private class Option extends JMenuItem {
        public Option(String intitule) {
            super(intitule);
            switch (intitule) {
			case "Ouvrir":
				setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
				break;
			case "Enregistrer":
				setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
				break;
			case "Enregistrer sous":
				setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK));
				break;
			case "Couper":
				setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
				break;
			case "Copier":
				setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
				break;
			case "Coller":
				setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
				break;
			case "Exporter en pdf":
				setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));
				break;
			case "Exporter les configs":
			        setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK));
			        break;
			default:
				break;
			}
//            setAccelerator(KeyStroke.getKeyStroke("control " + raccourci));
        }
    }
    
    
}
