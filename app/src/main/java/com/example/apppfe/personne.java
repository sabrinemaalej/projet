package com.example.apppfe;

public class personne {
    private String code;
    private String nom;
    private String imageUrl;

    public personne() {
        // Default constructor required for calls to DataSnapshot.getValue(personne.class)
    }

    public personne(String code, String nom, String imageUrl) {
        this.code = code;
        this.nom = nom;
        this.imageUrl = imageUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
