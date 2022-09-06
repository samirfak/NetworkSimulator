package vue.composants;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

/**
 * Créer une progress bar
 * @author ESCUDERO Thomas
 * @version 1.0
 */
public class ProgressBarGUI extends JFrame {

	/** progressbar */
	private JProgressBar bar;
	/** lancer l'animation */
	private Thread t;

	/** fenêtre résultat de la simulation */
	private ResultatSimulation resultat;

	/** fenêtre parente */
	private SimulationGUI fenetreParente;

	/**
	 * construit la progress bar
	 * @param ouvrirFenetre
	 */
	public ProgressBarGUI(SimulationGUI simulation) {
		super();
		this.fenetreParente = simulation;
		this.setTitle("progression...");
		this.setSize(300, 80);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		t = new Thread(new Traitement());
		bar = new JProgressBar();
		bar.setMinimum(0);
		bar.setMaximum(100);
		bar.setStringPainted(true);
		this.getContentPane().add(bar, BorderLayout.CENTER);
		t.start();

		this.setVisible(true);
	}

	// ferme la fenêtre
	public void fermer() {
		this.dispose();
	}

	/**
	 * Gestion du traitement de la progress bar
	 * @author ESCUDERO Thomas
	 * @version 1.0
	 */
	class Traitement implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			for (int i = 0; i <= 100; i++) {
				bar.setValue(i);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			resultat = new ResultatSimulation(fenetreParente,
					fenetreParente.getEmetteur(),
					fenetreParente.getDestinataire(),
					fenetreParente.getMessage(), 0);
			fermer();

		}

	}
}
