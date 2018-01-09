package zemzarioumaima.rondo.Entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ouma on 21/11/2016.
 */

public class Rondonnee {
    String id ;
    String Titre;
    String prix ;
    String depart ;
    String destination ;
    String Description ;
    String Date ;
    String image;
    String organisateur;





    public String getOrganisateur() {
        return organisateur;
    }

    public void setOrganisateur(String organisateur) {
        this.organisateur = organisateur;
    }

    public Rondonnee(String titre, String description, String image) {
        Titre = titre;
        Description = description;
        this.image = image;
    }

    public Rondonnee(String titre, String description) {
        Titre = titre;
        Description = description;
    }

    public Rondonnee(String titre, String prix, String date, String depart, String destination, String description, String image, String organisateur) {
        Titre = titre;
        this.prix = prix;
        this.depart = depart;
        this.destination = destination;
        Description = description;
        Date = date;
        this.image = image;
        this.organisateur = organisateur;
    }



    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Rondonnee(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Rondonnee() {

    }


    public Rondonnee(String titre, String description,  String image, String organisateur) {
        Titre = titre;
        Description = description;
        this.image = image;
        this.organisateur= organisateur;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTitre() {
        return Titre;
    }

    public void setTitre(String titre) {
        Titre = titre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Map<String,Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("titre", getTitre());
        result.put("prix", getPrix());
        result.put("date", getDate());
        result.put("depart", getDepart());
        result.put("detination", getDestination());
        result.put("descri", getDescription());
        result.put("image", getImage());
        result.put("organiser",getOrganisateur());


        return result;
    }
}