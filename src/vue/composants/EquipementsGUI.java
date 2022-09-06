package vue.composants;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.IPNetwork;
import vue.controleurs.EquipementsControleur;
import vue.renderer.ImageNText;
import vue.renderer.ImageTextRenderer;

/** Classe GUI listant tous les equipements
 * @author ESCUDERO Thomas, V.Bousquie
 * @version 1.0
 */

public class EquipementsGUI extends JPanel{

	/** Test chemin des images	 */
	private BufferedImage image_pc, image_borne_wifi, image_routeur,
			image_serveur, image_switch, image_nuage, image_lien;

	/** Titre des equipements d'extremite */
	private JLabel titreEX;
	/** ComboBox des equipements d'extremite */
	private JComboBox liste_extremite;

	/** Titre des Equipements d'interconnexion */
	private JLabel titreIC;
	/** ComboBox des equipements d'interconnexion */
	private JComboBox liste_interconnexion;

	/** Titre reseaux externes */
	private JLabel titreRE;
	/** ComboBox des reseaux externes */
	private JComboBox liste_reseaux;

	/** Titre cables */
	private JLabel titreCables;
	/** Combobox des cables	*/
	private JComboBox liste_cables;

	/** Controleurs des comboxbox */
	private EquipementsControleur controleur;

	/** Fenêtre parente qui "accueille" le menu */
	private FenetrePrincipale fenetreParente;

	/** Réseau IP associé à la vue */
	private IPNetwork ipNetwork;

	/**
	 * TODO commenter l'etat initial construit
	 * @param fenetreParente de la colonne Equipements
	 */
	public EquipementsGUI(FenetrePrincipale fenetreParente) {
		super();
		this.setLayout(new GridLayout(8,1));
		this.fenetreParente = fenetreParente;

		this.ipNetwork = fenetreParente.getNetwork();

		// Creation du controleur
		this.controleur = new EquipementsControleur(this);

	    // combo box des equipements d'extremite
	    liste_extremite = new JComboBox();
	    liste_extremite.setPreferredSize(new Dimension(200, 50));
	    liste_extremite.setRenderer(new ImageTextRenderer());
	    liste_extremite.setModel(eq_extr());
	    liste_extremite.addActionListener(this.controleur);

	    // combo box des equipements d'interconnexions
	    liste_interconnexion = new JComboBox();
	    liste_interconnexion.setPreferredSize(new Dimension(200, 50));
	    liste_interconnexion.setRenderer(new ImageTextRenderer());
	    liste_interconnexion.setModel(eq_interco());
	    liste_interconnexion.addActionListener(this.controleur);

	    // combo box des reseaux externes
	    liste_reseaux = new JComboBox();
	    liste_reseaux.setPreferredSize(new Dimension(200, 50));
	    liste_reseaux.setRenderer(new ImageTextRenderer());
	    liste_reseaux.setModel(net_externe());
	    liste_reseaux.addActionListener(this.controleur);

	    // combo box cablage
	    liste_cables = new JComboBox();
	    liste_cables.setPreferredSize(new Dimension(200, 50));
	    liste_cables.setRenderer(new ImageTextRenderer());
	    liste_cables.setModel(eq_cablage());
	    liste_cables.addActionListener(this.controleur);

		titreEX = new JLabel("Equipements d'extremites");
		titreIC = new JLabel("Equipements d'interconnexions");
		titreRE = new JLabel("Reseaux externes");
		titreCables = new JLabel("Cablage");
		add(titreEX);
		add(liste_extremite);
		add(titreIC);
		add(liste_interconnexion);
		add(titreRE);
		add(liste_reseaux);
		add(titreCables);
		add(liste_cables);

	    // Ajout de marges
	    this.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
	}

	/** Retourne le model de la combo box des equipements
	 * 	d'extremites
	 * @return dm
	 */
	private DefaultComboBoxModel eq_extr() {
		DefaultComboBoxModel dm = new DefaultComboBoxModel();
		// verifie si la valide du chemin des differentes images
		try {
			image_pc = ImageIO.read(new File("../src/vue/icons/pc.png"));
			image_serveur = ImageIO.read(new File("../src/vue/icons/serveur.png"));
		} catch (Exception e) {
			System.out.println("Error " + e.getMessage());
		}

		ImageIcon img_pc = new ImageIcon(
				new ImageIcon("../src/vue/icons/pc.png").getImage()
				.getScaledInstance(60, 60, Image.SCALE_DEFAULT));


		ImageIcon img_serveur = new ImageIcon(
				new ImageIcon("../src/vue/icons/serveur.png").getImage()
				.getScaledInstance(60, 60, Image.SCALE_DEFAULT));

		dm.addElement(new ImageNText(img_pc, "Ordinateur"));
		dm.addElement(new ImageNText(img_serveur, "Serveur"));

		return dm;
	}

	/** Retourne le model de la combo box des equipements d'interconnexions
	 * @return dm
	 */
	private DefaultComboBoxModel eq_interco() {
		DefaultComboBoxModel dm = new DefaultComboBoxModel();
		// verifie le chemin des images
		try {
			image_borne_wifi = ImageIO.read(
					new File("../src/vue/icons/borne_wifi.png"));
			image_routeur = ImageIO.read(new File("../src/vue/icons/routeur.png"));
			image_switch = ImageIO.read(new File("../src/vue/icons/switch.png"));
		} catch (Exception e) {
			new ErreurModalGUI(e.getMessage());
		}

		ImageIcon img_routeur = new ImageIcon(
				new ImageIcon("../src/vue/icons/routeur.png").getImage()
				.getScaledInstance(60, 60, Image.SCALE_DEFAULT));

		ImageIcon img_switch = new ImageIcon(
				new ImageIcon("../src/vue/icons/switch.png").getImage()
				.getScaledInstance(60, 60, Image.SCALE_DEFAULT));

		ImageIcon img_borne = new ImageIcon(
				new ImageIcon("../src/vue/icons/borne_wifi.png").getImage()
				.getScaledInstance(60, 60, Image.SCALE_DEFAULT));

		dm.addElement(new ImageNText(img_routeur, "Routeur"));
		dm.addElement(new ImageNText(img_switch, "Switch"));
		dm.addElement(new ImageNText(img_borne, "Borne wifi"));

		return dm;
	}

	/**
	 * Retourne le model de la combo box des reseaux externes
	 * @return dm
     */
    private DefaultComboBoxModel net_externe() {
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        // verifie le chemin des images
        try {
        	image_nuage = ImageIO.read(new File("../src/vue/icons/nuage.png"));
        } catch (Exception e) {
        	System.out.println("Error: " + e.getMessage());
        }


        ImageIcon img_nuage = new ImageIcon(
                        new ImageIcon("../src/vue/icons/nuage.png").getImage()
                        .getScaledInstance(60, 60, Image.SCALE_DEFAULT));

        dm.addElement(new ImageNText(img_nuage, "Externe"));

        return dm;
    }

    /** Retourne le model de la combo box des equipements de cablage
     * @return dm
     */
    private DefaultComboBoxModel eq_cablage() {
            DefaultComboBoxModel dm = new DefaultComboBoxModel();
            // verifie le chemin des images
            try {
                    image_lien = ImageIO.read(
                    		new File("../src/vue/icons/lien.png"));
            } catch (Exception e) {
                    System.out.println("Error " + e.getMessage());
            }

            ImageIcon image_lien = new ImageIcon(
                            new ImageIcon("../src/vue/icons/lien.png").getImage()
                            .getScaledInstance(60, 60, Image.SCALE_DEFAULT));

            dm.addElement(new ImageNText(image_lien, "Ethernet 1000 Base T"));

            return dm;
    }

    /**
     * Accesseur sur l'attribut fenetreParente
     * @return la fenetre parente de la colonne des equipements
     */
    public FenetrePrincipale getFenetreParente() {
        return this.fenetreParente;
    }

    /**
     * Accesseur sur l'attribut ipNetwork
     * @return la référence de ipNetwork
     */
    public IPNetwork getIpNetwork() {
        return this.ipNetwork;
    }
}
