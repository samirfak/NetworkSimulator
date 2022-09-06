/*
 * ZoneDessinGUI.java               30 mai 2020
 * N7 1APP SN 2019/2020
 */

package vue.composants;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import modele.IPNetwork;
import modele.NotFoundException;
import vue.controleurs.ZoneDessinControleur;

/**
 * Zone de dessin ou les equipements du reseau seront deplaces.
 *
 * @author V.Bousquie
 * @version 1.0
 */
public class ZoneDessinGUI extends JPanel {

    /** Controleur de la zone de dessin */
    private ZoneDessinControleur controleur;

    /** La liste des liens entre les objets sur la zone de dessin  */
    private List<LienGUI> liens;

    /** Détermine si un lien doit être dessiné */
    private boolean dessinerLien;

    /** IP Network */
    private IPNetwork network;

    /** Fenetre parente du composant courant */
    private FenetrePrincipale fenetreParente;

    /** Creer une popup menu lors d'un right clic */
    private JPopupMenu popupMenu;

    /** JItem pour la popup menu Terminal */
    private JMenuItem terminalItem;


    /**
     * Construit la Zone de Dessin
     * @param fenetre associé au composant
     */
    public ZoneDessinGUI(FenetrePrincipale fenetre) {
        // Appel au super-constructeur
        super();
        this.setLayout(null); // Suppression du Layout Manager
        this.setBackground(Color.WHITE);

        this.fenetreParente = fenetre;
        // recupere le modele
        this.network = fenetre.getNetwork();

        this.controleur = new ZoneDessinControleur(this);
        this.addMouseListener(controleur);
        this.addMouseMotionListener(controleur);
        
        this.dessinerLien = false;

        this.liens = new ArrayList<LienGUI>();

        // cr�ation du popupMenu
        this.popupMenu = new JPopupMenu();
        // cr�ation des items du JPopUpMenu
        this.terminalItem = new JMenuItem("terminal");
        this.terminalItem.setActionCommand("terminal");
        this.terminalItem.addActionListener(controleur);
        this.terminalItem.setEnabled(false);

        this.popupMenu.add(this.terminalItem);
    }

    /**
     * Dessine une icon dans la zone dessinable
     *
     * @param nom         de l'objet
     * @param cheminImage chemin de l'image
     */
    public void dessiner(String nom, String cheminImage) {
        // Ajout d'un objet de dessin
        ObjetDessin obj = new ObjetDessin(this, nom, cheminImage);
        obj.setBounds(10, 10, 100, 100);
        this.add(obj);
        this.updateUI();
    }

    /**
     * Dessine une ObjetDessin dans ZoneDessinGUI
     *
     * @param nom   de l'objet
     * @param image associee a l objet
     */
    public void dessiner(String nom, Icon image) {
        // Ajout d'un objet de dessin
        ObjetDessin obj = new ObjetDessin(this, nom, image);
        obj.setBounds(10, 10, 100, 100);
        this.add(obj);
        this.updateUI();
    }

    

    /**
     * Accesseur sur l'attribut dessinerLien
     *
     * @return la valeur de dessinerLien
     */
    public boolean getDessinerLien() {
        return this.dessinerLien;
    }

    /**
     * Modifier la valeur de l'attribut dessinerLien
     *
     * @param booleen nouvelle valeur de l'attribut
     */
    public void setDessinerLien(Boolean booleen) {
        this.dessinerLien = booleen;
    }
	

    /**
     * Supprimer un composant du JPanel et du modele
     * et le lien eventuel qui lui est associe
     *
     * @param composant à supprimer
     * @param nomEquipement nom de l'equipement IP
     */
    public void supprimerComponent(Component composant, String nomEquipement) {
        int i = 0, index = 0;
        JOptionPane dialog;
        
        // Si l'équipement est lié on supprime le lien associé
        
        if (objetLie((ObjetDessin) composant)) {
            // liste des liens non vide
            if (liens.size() > 0) {
                // parcourir tous les liens
                for (LienGUI l : liens) {
                    // supprimer le lien correspondant entre les deux extremites
                    if (l.getExtremiteUn().getX() == composant.getX() &&
                            l.getExtremiteUn().getY() == composant.getY()) {
                        index = i; // recupere l'indice dans la liste
                    } else if (l.getExtremiteDeux().getX() == composant.getX() &&
                            l.getExtremiteDeux().getY() == composant.getY()) {
                        index = i; // recupere l'indice dans la liste
                    }
                    i++;
                }
                // supprime le lien dans la vue
                liens.remove(index);
    
                // Supprime le lien dans le modèle
                network.removeLink(network.getLinks().get(index));
            }
        }

        // supprime le composant
        this.remove(composant);
        // supprime l'equipement du modele
        try {
            network.removeIPEquipment(nomEquipement);
            // modifie la jcombobox
            fenetreParente.getSimulateur().changeComboBox();
        } catch(NotFoundException e) {
            dialog = new JOptionPane();
            dialog.showMessageDialog(null, e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        // maj de la zone dessin
        repaint();
        this.updateUI();
    }

    @Override
    public void paintComponent(Graphics g) {

        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Quand un objet est drag pas
        if (this.controleur.getMouvement() == false) {

            // Parcours des liens pour les redessiner
            for (int i = 0; i < liens.size(); i++) {
                g.setColor(Color.BLACK);
                g.drawLine(liens.get(i).getExtremiteUn().getX() + 50,
                        liens.get(i).getExtremiteUn().getY() + 50,
                        liens.get(i).getExtremiteDeux().getX() + 50,
                        liens.get(i).getExtremiteDeux().getY() + 50);
            }
        }
    }

    /**
     * Accesseur sur l'attribut fenetreParente
     * @return la référence de fenetreParente
     */
    public FenetrePrincipale getFenetreParente() {
        return fenetreParente;
    }

    /**
     * Accesseur sur l'attribut network
     * @return la référence de network
     */
    public IPNetwork getNetwork() {
        return this.network;
    }

    /**
     * Accesseur sur l'attribut liens
     * @return la référence de la liste des liens
     */
    public List<LienGUI> getLiens() {
        return liens;
    }

    /**
     * @return popup menu
     */
    public JPopupMenu getPopUpMenu() {
        return this.popupMenu;
    }

    /**
     * change l'etat du terminal item
     * @param state
     */
    public void setTerminalItem(boolean state) {
        this.terminalItem.setEnabled(state);
    }
    
    /**
     * Sauvegarder la zone de dessin courante dans le fichier
     * @param nomFichier
     */
    public void sauvegarder(String nomFichier) {
        ZoneSauvegardable saveZone = new ZoneSauvegardable();
        
        // Parcours des objets dessin
        for (Component objet : this.getComponents()) {
            ((ObjetDessin) objet).sauvegarder();
            saveZone.getObjets().add(((ObjetDessin) objet).getSaveObjet());
        }
        
        // Parcours des liens
        for (LienGUI lien : this.getLiens()) {
            saveZone.getLiens().add(new LienSauvegardable(lien.getExtremiteUn().getSaveObjet(),
                    lien.getExtremiteDeux().getSaveObjet()));
        }
        
        saveZone.serialiser(nomFichier);
    }
    
    /**
     * Dessiner les composants sauvegardés
     * @param uneSaveZone
     */
    public void dessiner(ZoneSauvegardable uneSaveZone) {
        
        // Parcours des objets dessin
        for (ObjetSauvegardable objet : uneSaveZone.getObjets()) {
            // Ajout d'un objet de dessin
            ObjetDessin obj = new ObjetDessin(this, objet.getNom(), objet.getImage());
            obj.setBounds(objet.getX(), objet.getY(), 100, 100);
            this.add(obj);
        }
        
        // Parcours des liens 
        for (LienSauvegardable lien : uneSaveZone.getLiens()) {
            // Ajout lien
            LienGUI unLien = new LienGUI(this.getObjetByName(lien.getExtremiteUn().getNom()),
                    this.getObjetByName(lien.getExtremiteDeux().getNom()));
            
            this.liens.add(unLien);
        }
        
        // Maj de la vue
        this.updateUI();
    }
    
    /**
     * Obtenir la référence d'un objet dessin par son nom
     * @param nomObjet  le nom de l'objet
     * @return  la référence de ObjetDessin correspondant
     */
    public ObjetDessin getObjetByName(String nomObjet) {
        for (Component objet : this.getComponents()) {
            if (((ObjetDessin) objet).getNom().equals(nomObjet)) {
                return (ObjetDessin) objet;
            }
        }
        return null;
    }
    
    /**
     * Détermine si un objet est lié à un autre équipement quelconque
     * @param objetTeste référence de l'objet testé
     * @return true si il est lié false sinon
     */
    public boolean objetLie(ObjetDessin objetTeste) {
        boolean estLie = false;
        
        // Parcours de la liste des liens
        for (LienGUI lien : liens) {
            if (lien.getExtremiteUn() == objetTeste 
                              || lien.getExtremiteDeux() == objetTeste) {
                estLie = true;
                
            }
        }
        return estLie;
    }
}
