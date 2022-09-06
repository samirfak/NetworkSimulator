package vue.composants;

import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 * Lecteur PDF affiché ensuite dans une JFrame
 * @author V.Bousquié T.Escudero
 * @version 1.0
 */
public class LecteurPDF extends JPanel {

    /** Images chargées à partir du PDF */
    private ArrayList<BufferedImage> imagesPDF;

    /**
     * Construit l'état initial du JPanel
     * @param nomFichier
     */
    public LecteurPDF(String nomFichier) {
        super();
        this.imagesPDF = new ArrayList<BufferedImage>();
        this.pdfToImage(nomFichier);
        JPanel pan = new JPanel();
        pan.setLayout(new GridLayout(0,1));
        for(int i = 0; i < this.imagesPDF.size(); i++) {

        	ImageIcon img = new ImageIcon(imagesPDF.get(i));
        	JLabel lab = new JLabel();
        	lab.setIcon(img);
        	pan.add(lab);
        }
        this.add(pan);
    }


    /**
     * Converti un fichier pdf en buffered image
     * @param nomFichier pdf analysé
     */
    private void pdfToImage(String nomFichier) {
        BufferedImage bim = null;
        try {
            // Ouverture du pdf
            PDDocument document = PDDocument.load(new File(nomFichier));
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); page++)
            {
                bim = pdfRenderer.renderImageWithDPI(page, 100, ImageType.RGB);
                this.imagesPDF.add(bim);
            }
            document.close();
        } catch (IOException e){
            System.err.println("Problème lors de l'accès au fichier !");
        }
    }
}