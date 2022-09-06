/*
 * ZoneSauvegardable.java                    13 juin 2020
 * N7 SN 1APP 2018/2019 
 */
package vue.composants;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import modele.IPNetwork;

/**
 * Sauvegarder l'objet ZoneDessin
 * @author V.Bousquié
 * @version 1.0
 */
public class ZoneSauvegardable implements Serializable {

    /** Clé de hachage */
    private static final long serialVersionUID = 1L;
    
    /** Liste des liens */
    private List<LienSauvegardable> liens;
    
    /** Liste des objets */
    private List<ObjetSauvegardable> objets;
    
    /**
     * Construit l'état initial de l'objet
     */
    public ZoneSauvegardable() {
        this.liens = new ArrayList<LienSauvegardable>();
        this.objets = new ArrayList<ObjetSauvegardable>();
    }
    
    /**
     * Sérialiser l'objet courant pour pouvoir sauvegarder la modélisation
     * d'un utilisateur
     * @param nomFichier nom du fichier dans lequel la Zone de dessin sera
     *                   sauvegardée
     */
    public void serialiser(String nomFichier) {
        // Creation et ecriture dans le fichier
        try {

            // Declaration et creation du fichier qui recevra les objets
            ObjectOutputStream fluxEcriture = new ObjectOutputStream(new FileOutputStream(nomFichier));

            // Ecriture de l'objet courant dans le fichier
            fluxEcriture.writeObject(this);

            // Fermeture du fichier
            fluxEcriture.close();

        } catch(IOException e) {
            System.out.println("Probleme d'acces au fichier" + nomFichier);
        }
    }
    
    /**
     * Retourne l'objet lu dans le fichier
     * @param nomFichier nom du fichier dans lequel se trouve  l'eleve 
     * @return l'objet lu
     */
    public static ZoneSauvegardable deserialiser(String nomFichier) {

        ZoneSauvegardable zoneLue = new ZoneSauvegardable();
        
        // declaration du fichier et mecture dans le fichier
        try {

            // Declaration et ouverture du fichier qui contient les objets
            ObjectInputStream fluxLecture = new ObjectInputStream(new FileInputStream(nomFichier));

            zoneLue = (ZoneSauvegardable) fluxLecture.readObject();
            
            // Fermeture du fichier
            fluxLecture.close();
            
            return zoneLue;

        } catch (IOException e) {
            // Probleme fichier
            System.out.println("Probleme d'acces au fichier " + nomFichier);
        } catch (ClassNotFoundException e) {
            // exception levee si l'objet lu n'est pas de type Eleve
            System.out.println("Probleme lors de la lecture du fichier " + nomFichier);
        }
        return null;
    }

    /**
     * Accesseur sur la liste de liens
     * @return la référence de liens
     */
    public List<LienSauvegardable> getLiens() {
        return liens;
    }

    /**
     * Accesseur sur la liste d'objets
     * @return la référence de objets
     */
    public List<ObjetSauvegardable> getObjets() {
        return objets;
    }
}
