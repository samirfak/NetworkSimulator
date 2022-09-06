package vue.composants;

import javax.swing.JOptionPane;

/**
 * Affiche un modal d'erreur
 * @author ESCUDERO Thomas
 *
 */
public class ErreurModalGUI extends JOptionPane {
	/**
	 * construit le modal d'erreur
	 * @param message d'erreur
	 */
	public ErreurModalGUI(String message) {
		this.showMessageDialog(null, message, "Error",
				JOptionPane.ERROR_MESSAGE);
	}
}
