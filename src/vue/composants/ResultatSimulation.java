package vue.composants;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.plaf.DimensionUIResource;

import modele.DestinationUnreachableException;
import modele.IPEquipment;
import modele.IPInterface;
import modele.IPMessage;
import modele.IPNetwork;
import modele.NotFoundException;
import modele.PacketDroppedException;

/**
 * Fenêtre qui affiche le résultat de la simulation
 * @author ESCUDERO Thomas
 * @version 1.0
 */

public class ResultatSimulation extends JFrame {

	/** contenu de la JFrame */
	private Container contenu;

	/** JPanel rapport de la simulation */
	private JPanel rapport;

	/** L'équipement émetteur */
	private String emetteur;
	/** L'équipement destinataire */
	private String destinataire;
	/** Le message envoyé */
	private String message;
	/** Le temps de la transmission */
	private int temps;

	/** etat de la simulation text + image */
	private Box etatSimulation;

	/** Test chemin des images */
	private BufferedImage successImage, failImage;

	/** Icons de success et fail */
	private ImageIcon successIcon, failIcon;

	/** JLabel de l'image */
	private JLabel iconLabel;

	/** network de la simulation */
	private IPNetwork network;

	/** fenetre parente */
	private SimulationGUI fenetreParente;

	/**
	 * Constructeur de ResultatSimulation
	 */
	public ResultatSimulation(SimulationGUI fenetre, String emetteur,
			String destinataire, String message, int tps) {
		super();
		this.emetteur = emetteur;
		this.destinataire = destinataire;
		this.message = message;
		this.temps = tps;

		this.setTitle("Resultat de la simulation");
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);

		contenu = this.getContentPane();
		contenu.setLayout(new BorderLayout());
		JLabel titreResultat = new JLabel("Resultat de la simulation entre les "
				+ "deux equipements", SwingConstants.CENTER);


		titreResultat.setFont(new Font("Serif", Font.PLAIN, 18));
		rapport = new JPanel();
		rapport.setLayout(new BorderLayout());

		// création du tableau récapitulatif
		Object[][] donnees = {
				{emetteur, destinataire, message}
		};

		String[] entetes = {"Emetteur", "Destinataire", "Message"};

		JTable tableauRecapitulatif = new JTable(donnees, entetes);

		rapport.add(tableauRecapitulatif.getTableHeader(), BorderLayout.NORTH);
		rapport.add(tableauRecapitulatif, BorderLayout.CENTER);
		rapport.setPreferredSize(new DimensionUIResource(this.getWidth(),
				this.getHeight()));

		// test la bonne ouverture des image
		try {
			successImage = ImageIO.read(
					new File("../src/vue/icons/success.png"));
			failImage = ImageIO.read(
					new File("../src/vue/icons/fail.png"));
		} catch(Exception e) {
			// affiche le modal erreur
			new ErreurModalGUI(e.getMessage());
		}
		etatSimulation = Box.createHorizontalBox();
		etatSimulation.add(new JLabel("Resultat de la simulation: "));

		// création de l'icon success
		successIcon = new ImageIcon(
				new ImageIcon("../src/vue/icons/success.png").getImage()
				.getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		// création de l'icon fail
		failIcon = new ImageIcon(
				new ImageIcon("../src/vue/icons/fail.png").getImage()
				.getScaledInstance(30, 30, Image.SCALE_DEFAULT));

		iconLabel = new JLabel();

		JLabel labelRetour = new JLabel();

		JPanel contenuInfo = new JPanel();
		contenuInfo.setLayout(new GridLayout(3,1));


		JTextArea text = new JTextArea(30, 30);
		// la zone de text n'est pas éditable
		text.setEditable(false);
		JScrollPane scrollText = new JScrollPane(text);





		this.fenetreParente = fenetre;
		this.network = fenetreParente.getNetwork();

		// récupère les équipements
		IPEquipment eqtEmt = network.getIPEquipment(emetteur);
		IPEquipment eqtDest = network.getIPEquipment(destinataire);

		// récupère les interfacess
		IPInterface intEmt;
		IPInterface intDest;

		try {
			intEmt = eqtEmt.getInterfaceByName(
					eqtEmt.listInterface().get(0));

			intDest = eqtDest.getInterfaceByName(
					eqtDest.listInterface().get(0));

			IPMessage testMessage = new IPMessage(intEmt.getAddress(),
					intDest.getAddress(),
					message);

			eqtEmt.send(testMessage);
			text.setText(testMessage.getResultText());
			text.setCaretPosition(text.getDocument().getLength());

			iconLabel.setIcon(successIcon);
			labelRetour.setText("Simulation reussie");
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			text.setText(e.getMessage());
			iconLabel.setIcon(failIcon);
			labelRetour.setText("Simulation echec");
		} catch (PacketDroppedException e2) {
			e2.printStackTrace();
			text.setText(e2.getMessage());
			iconLabel.setIcon(failIcon);
			labelRetour.setText("Simulation echec");
		} catch (DestinationUnreachableException e3) {
			e3.printStackTrace();
			text.setText(e3.getMessage());
			iconLabel.setIcon(failIcon);
			labelRetour.setText("Simulation echec");
		}

		contenuInfo.add(rapport);
		contenuInfo.add(scrollText);
		etatSimulation.add(iconLabel);
		etatSimulation.add(labelRetour);
		contenuInfo.add(etatSimulation);
		contenu.add(contenuInfo, BorderLayout.CENTER);

		this.setResizable(false);
		this.setVisible(true);
	}
}
