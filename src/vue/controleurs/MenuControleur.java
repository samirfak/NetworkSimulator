/*
 * MenuControleur.java        30 mai 2020
 * N7 SN 1APP 2019/2020
 */

package vue.controleurs;

import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import vue.composants.FenetrePrincipale;
import modele.IPEquipment;
import modele.IPNetwork;
import modele.NotFoundException;
import modele.Route;
import modele.RoutingTable;
import vue.composants.AideGUI;
import vue.composants.ErreurModalGUI;
import vue.composants.Menu;
import vue.composants.ProprietesGUI;
import vue.composants.TerminalGUI;
import vue.composants.ZoneSauvegardable;

/**
 * Controleur d'un menu
 * @author V.Bousquie, T.ESCUDERO
 * @version 1.0
 */
public class MenuControleur implements ActionListener {

    /** Menu associé à ce controleur */
    private Menu menu;

    /**
     * Construit l'état initial du controleur
     * @param menu de l'application
     */
    public MenuControleur(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String cmd = evt.getActionCommand();
        if (cmd.equals("Aide")) {
            new AideGUI();
        } else if (cmd.equals("Terminal")) {
            new TerminalGUI(menu.getFenetreParente().getNetwork(), null).setVisible(true);
        } else if (cmd.equals("Proprietes")) {
            new ProprietesGUI(this.menu.getFenetreParente().getNetwork());
        } else if (cmd.equals("Ouvrir")) {
            ouvrirFichier();
        } else if (cmd.equals("enregistrer")) {
            sauvegarder();
        } else if (cmd.equals("enregistrerSous")) {
            sauvegarderSous();
        } else if (cmd.equals("exporterPdf")) {
            exporterPdf();
        } else if (cmd.equals("exporterConfig")) {
            exporterConfig();
        } else if (cmd.equals("Zoom")) {
            // TODO
        } else if (cmd.equals("Dezoom")) {
            // TODO

        }
    }

    /**
     * Ouvrir l'arborescence des fichiers de l'utilisateur pour qu'il
     * choisisse un fichier à ouvrir
     */
    public void ouvrirFichier() {
        JFileChooser choixFichier = new JFileChooser();
        choixFichier.changeToParentDirectory();
        choixFichier.setFileFilter(new
                FileNameExtensionFilter("Network Simulator file", "netsim"));

        // Affichage du FileChooser
        if (choixFichier.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            String fileName = choixFichier.getSelectedFile().toString();
            String fileNameView = fileName + ".view";
            
            FenetrePrincipale fenetre = new FenetrePrincipale(IPNetwork.deserialiser(fileName));
            fenetre.getZoneDessin().dessiner(ZoneSauvegardable.deserialiser(fileNameView));
            fenetre.setTitle("NetWork Simulator | " + fileName);
            fenetre.setNomSauvegarde(fileName);
            fenetre.getSimulateur().setNetwork(fenetre.getNetwork());
            this.menu.getFenetreParente().forcerFermeture();
            IPNetwork.setNumber(fenetre.getNetwork().getIPEquipementNumber());
        }
    }

    /**
     * Ouvrir l'arborescences des fichiers de l'utilisateur pour qu'il
     * choisisse un dossier dans lequel il peut sauvegarder son projet
     */
    public void sauvegarderSous() {
        JFileChooser choixFichier = new JFileChooser();
        choixFichier.changeToParentDirectory();

        // Définir le type de la fenêtre, ici de type sauvegarde
        choixFichier.setDialogType(JFileChooser.SAVE_DIALOG);
        // Nom de fichier par défaut
        choixFichier.setSelectedFile(new File("mon_reseau_lan.netsim"));
        // Filtre sur les fichiers visibles
        choixFichier.setFileFilter(
                new FileNameExtensionFilter("Network Simulator file","netsim"));

        // Lors de l'enregistrement du fichier
        if (choixFichier.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
           // Verification
           String filename = choixFichier.getSelectedFile().toString();
           String fileZoneDessin;
           if (!filename.endsWith(".netsim")) {
                filename += ".netsim";
           }
           fileZoneDessin = filename + ".view";

           // Sérialisation de l'objet network et de la zone dessin
           this.menu.getFenetreParente().getNetwork().serialiser(filename);
           this.menu.getFenetreParente().getZoneDessin().sauvegarder(fileZoneDessin);
           this.menu.getFenetreParente().setTitle("NetWork Simulator | " + filename);
           this.menu.getFenetreParente().setNomSauvegarde(filename);
        }
    }

    /**
     * Sauvegarder le fichier ouvert par l'utilisateur
     */
    public void sauvegarder() {
        String fileName = this.menu.getFenetreParente().getNomSauvegarde();
        
        if (fileName == null) {
            this.sauvegarderSous();
        } else {
        
            String fileNameView = fileName + ".view";
            
            // Sérialisation de l'objet network et de la zone dessin
            this.menu.getFenetreParente().getNetwork().serialiser(fileName);
            this.menu.getFenetreParente().getZoneDessin().sauvegarder(fileNameView);
            this.menu.getFenetreParente().setTitle("NetWork Simulator | " + fileName);
            
        }
        
    }

    /**
     * Redimensionnement d'une image
     * @param bImageimage a redimensionner
     * @param factor coefficient de redimensionnement
     * @return
     */

    public BufferedImage scale(BufferedImage bImage, double factor) {
        int destWidth=(int) (bImage.getWidth() * factor);
        int destHeight=(int) (bImage.getHeight() * factor);
        //créer l'image de destination
        GraphicsConfiguration configuration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage bImageNew = configuration.createCompatibleImage(destWidth, destHeight);
        Graphics2D graphics = bImageNew.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        //dessiner l'image de destination
        graphics.drawImage(bImage, 0, 0, destWidth, destHeight, 0, 0, bImage.getWidth(), bImage.getHeight(), null);
        graphics.dispose();

        return bImageNew;
    }
    
    /**
     * Exporter les configuration de la table de routage
     * dans des fichiers bash pour chaque équipements
     */
    public void exporterConfig() {
        IPNetwork network = this.menu.getFenetreParente().getNetwork();
        List<String> nomsEquipements = network.getIPEquipmentNameList();
        
        // Parcours de la liste des équipements du réseau
        for (String nomEquipement : nomsEquipements) {
            network.getIPEquipment(nomEquipement).exportConfig();
        }
    }

    /**
     * Exporter le schéma d'une modélisation de réseau en pdf
     */
    public void exporterPdf() {

        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("monreseau.pdf"));
            //Ouverture du document en écriture
            doc.open();
            //Ecriture du titre dans le pdf
            doc.add(new Paragraph("Mon reseau: "));
            //Récupération de la zone de dessin
            JPanel myPan = menu.getFenetreParente().getZoneDessin();
            // Prendre une capture d'écran de la zone de dessin
            BufferedImage jImg = new BufferedImage(myPan.getWidth(),
                    myPan.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);

            myPan.paint(jImg.createGraphics());
            //Conversion du BufferedImage en image itext
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            BufferedImage imgNew = scale(jImg, 0.71);
            ImageIO.write(imgNew, "png", baos);
            Image iTextImage = Image.getInstance(baos.toByteArray());
            //Ajout du modèle réseau dans le pdf

            doc.add(iTextImage);
            //Ajout des tables de routage des équipements
            IPNetwork network = menu.getFenetreParente().getNetwork();
            if (network.getIPEquipementNumber() > 0) {
                List<String> nomsEquipements = network.getIPEquipmentNameList();
                IPEquipment equipment;
                String nom;
                RoutingTable tableRoutage;
                List<Route> routeList;
                Route routeDefaut;
                PdfPTable table;
                PdfPCell cell;
                for (int i = 0; i < network.getIPEquipementNumber(); i++) {
                    nom = nomsEquipements.get(i);
                    equipment = network.getIPEquipment(nom);
                    doc.add(new Paragraph(nom + "\n\n"));
                    doc.add(new Paragraph(""));
                    tableRoutage = equipment.getRoutingTable();
                    routeList = tableRoutage.getRouteList();
                    try {
                        routeDefaut = tableRoutage.getDefaultRoute();
                    } catch (NotFoundException e) {
                        // TODO Auto-generated catch block
                    	routeDefaut = null;
                    }
                    table = new PdfPTable(3);
                    cell = new PdfPCell(new Phrase("Adresse Destination"));
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase("Masque Destination"));
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase("Interface"));
                    table.addCell(cell);

                    if (routeDefaut != null) {
                    	cell = new PdfPCell(new Phrase(routeDefaut.getDestAddress()));
                        table.addCell(cell);
                        cell = new PdfPCell(new Phrase(routeDefaut.getDestMask()));
                        table.addCell(cell);
                        cell = new PdfPCell(new Phrase(routeDefaut.getOutInterface().getName()));
                        table.addCell(cell);
                    }
                    for (int j = 0; j < routeList.size(); j++) {
                        cell = new PdfPCell(new Phrase(routeList.get(j).getDestAddress()));
                        table.addCell(cell);
                        cell = new PdfPCell(new Phrase(routeList.get(j).getDestMask()));
                        table.addCell(cell);
                        cell = new PdfPCell(new Phrase(routeList.get(j).getOutInterface().getName()));
                        table.addCell(cell);
                    }
                    doc.add(new Paragraph(""));
                    doc.add(table);
                    doc.add(new Paragraph(""));
                    doc.add(new Paragraph(""));

                }
            }
            //Fermeture du document
            doc.close();
            //Ouverture du pdf sur l'ordinateur
            Desktop.getDesktop().open(new File("monreseau.pdf"));

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
        	new ErreurModalGUI("Erreur export pdf");
            e.printStackTrace();
        } catch (DocumentException e) {
        	new ErreurModalGUI("Erreur export pdf");
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        	new ErreurModalGUI("Erreur export pdf");
            e.printStackTrace();
        }

    }
}
