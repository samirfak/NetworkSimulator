/*
 * ZoneDessin.java                        31 mai 2020
 * N7 SN 1APP 2019/2020
 */

package vue.controleurs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import modele.IPNetwork;
import vue.composants.ChoixInterfaceGUI;
import vue.composants.LienGUI;
import vue.composants.ObjetDessin;
import vue.composants.TerminalGUI;
import vue.composants.ZoneDessinGUI;

/**
 * Controleur de la zone de dessin
 * @author V.Bousquie
 * @version 1.0
 */
public class ZoneDessinControleur extends MouseAdapter implements ActionListener {

    /** Zone de dessin */
    private ZoneDessinGUI zone;

    /** Terminal */
    private TerminalGUI terminal;

    /** controleur ObjetDessin */
    private ObjetDessinControleur objetControleur;

    /** Objet en mouvement */
    private boolean mouvement;

    /** Composant en mouvement */
    private JComponent composant;

    /** Composant à supprimer */
    private JComponent composantSupprimable;

    /** Abscisse relative de l'objet en mouvement */
    private int relx;

    /** Ordonnee relative de l'objet en mouvement */
    private int rely;

    /** Objet selectionné */
    private JComponent objetSelected;

    /** Reseau associé */
    private IPNetwork network;

    /** Compte le nombre de clics pour dessiner un lien */
    private int nbClicsLiens;

    /** Conserve la premiere extremite d'un lien */
    private ObjetDessin premiereExtremite;

    /**
     * Construit l'etat initial du controleur
     * @param zone zone de dessin
     */
    public ZoneDessinControleur(ZoneDessinGUI zone) {
        this.zone = zone;
        this.network = zone.getNetwork();
        this.nbClicsLiens = 0;
        this.objetSelected = null;

    }

    @Override
    public void mousePressed(MouseEvent evt) {

        if (zone.getDessinerLien() == true) {
            if (getComposant(evt.getX(),evt.getY()) != null) {
                this.nbClicsLiens++;

                if (nbClicsLiens == 1) {
                    this.premiereExtremite = (ObjetDessin) getComposant(evt.getX(),evt.getY());
                } else if (nbClicsLiens == 2) {
                    
                    // Choix des interfaces à lier
                    try {
                        new ChoixInterfaceGUI(network, premiereExtremite.getNom(), 
                                ((ObjetDessin) getComposant(evt.getX(),evt.getY())).getNom());
                        zone.getLiens().add(new LienGUI(premiereExtremite,
                                (ObjetDessin) getComposant(evt.getX(),evt.getY())));
                    } catch(Exception e) {
                        JOptionPane dialog = new JOptionPane();
                        dialog.showMessageDialog(null, e.getMessage(),
                              "Erreur de configuration", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    // Reinitialisation des variables
                    this.nbClicsLiens = 0;
                    this.premiereExtremite = null;
                    // Remet le curseur par défaut
                    this.zone.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    this.zone.setDessinerLien(false);
                }
                zone.repaint();
            }
        } else {
            if ( SwingUtilities.isRightMouseButton(evt) )  {
                composantSupprimable = getComposant(evt.getX(),evt.getY());



                if (composantSupprimable != null) {
                    composantSupprimable.setBorder(
                            BorderFactory.createLineBorder(Color.RED));
                    ((ObjetDessin) composantSupprimable).getBtnSupprimer().setVisible(true);

                    // Remplissage de configurationGUI
                    zone.getFenetreParente().getConfigurateur()
                    .remplirChamps(network.getIPEquipment(((ObjetDessin) composantSupprimable).getNom()));
                    zone.getFenetreParente().getConfigurateur().setObjetDessin((ObjetDessin) composantSupprimable);
                }
            } else {
                if (mouvement) {
                    mouvement=false; // arrêt du mouvement
                    composant.setBorder(null); // on  supprime la bordure noire
                    composant=null;
                    zone.repaint();
                } else {
                    // on memorise le composant en deplacement
                    composant = getComposant(evt.getX(),evt.getY());
                    if (composant != null) {
                        // place le composant le plus haut possible
                        zone.setComponentZOrder(composant,0);
                        // on memorise la position relative
                        relx = evt.getX() - composant.getX();
                        // on memorise la position relative
                        rely = evt.getY() - composant.getY();
                        mouvement = true; // demarrage du mouvement
                        // on indique le composant selectionne par une bordure noire
                        composant.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    }
                }

                if (composantSupprimable != null && composantSupprimable != composant) {
                    composantSupprimable.setBorder(null);
                    ((ObjetDessin) composantSupprimable).getBtnSupprimer().setVisible(false);
                }

                this.nbClicsLiens = 0;
                this.premiereExtremite = null;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent evt) {
    	// TODO Auto-generated method stub

		if ( SwingUtilities.isRightMouseButton(evt) )  {
			// v�rifie si c'est un composant
			if (getComposant(evt.getX(), evt.getY()) != null) {
				// active terminal item
				zone.setTerminalItem(true);
			} else {
				// desactive terminal item
				zone.setTerminalItem(false);
			}
			// affiche la popup menu
			showPopUp(evt);
	    } else {
	    	// met à jour l'objet selectionné
	    	this.objetSelected = getComposant(evt.getX(), evt.getY());
	    }
    }

    @Override
    public void mouseMoved(MouseEvent evt) {
        if (mouvement) {
            composant.setLocation(evt.getX()-relx, evt.getY()-rely);
        }
    }

    /**
     * On cherche le composant correspondant au clic de la souris
     * @param x abscisse
     * @param y ordonnee
     * @return le composant trouve ou null
     */
    private JComponent getComposant(int x, int y) {
        // on recherche le premier composant qui correspond aux coordonnees de la souris
        for(Component composant : zone.getComponents()) {
            if ( composant instanceof JComponent && composant.getBounds().contains(x, y) ) {
                return (JComponent)composant;
            }
        }
        return null;
    }

    /**
     * Accesseur sur l'attribut mouvement
     * @return la valeur du booleen mouvement
     */
    public boolean getMouvement() {
        return this.mouvement;
    }

    /**
     * affiche le menu popup lors d'un click droit
     * @param me mouse event
     */
    public void showPopUp(MouseEvent me) {
    	zone.getPopUpMenu().show(me.getComponent(), me.getX(), me.getY());
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		String nomEquipement;
		if (cmd == "terminal") {
			// creation du terminal
			if (objetSelected != null) { // l'obet selectionn� est non null � l'initialisation
				nomEquipement =  this.network.getIPEquipment(((ObjetDessin) objetSelected).getNom()).getName();
			} else {
				// prend le premier �quipement de la liste
				nomEquipement =  this.zone.getNetwork().getIPEquipmentNameList().get(0);

			}
			// cr�er la fen�tre terminal de l'�quipement
	        terminal = new TerminalGUI(this.network, nomEquipement);
	        // affiche la fen�tre terminal
	        terminal.setVisible(true);
		}
	}
}
