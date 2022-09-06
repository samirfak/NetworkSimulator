package vue.composants;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

/** Fenêtre affichant l'aide de l'application
 * @author ESCUDERO Thomas, V.Bousquié
 * @version 1.0
 */
public class AideGUI extends JFrame {

    /** Lecteur PDF associé à la fenêtre */
    private LecteurPDF lecteur;

    /** Conteneur scrollable pour le lecteurPDF */
    private JScrollPane scrollPane;
    /**
     * Construit l'état initial de la fenêtre
     */


    public AideGUI() {
        super("Aide");
        this.lecteur = new LecteurPDF("../doc/manuel-utilisateur3.pdf");

        this.scrollPane = new JScrollPane();
        this.scrollPane.setViewportView(this.lecteur);
        this.add(this.scrollPane);

        // Definit une taille minimum de la fenetre
        this.setMinimumSize(new Dimension(800,600));
        // Taille Dynamique
        this.pack();
        // Fenere redimensionnable
        this.setResizable(true);
        // fenetre centrae au centre de l'acran
        this.setLocationRelativeTo(null);
        // Fenetre visible
        this.setVisible(true);
    }
}
