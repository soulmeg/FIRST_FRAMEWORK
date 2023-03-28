/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu2008.framework.dataObject;

import etu2008.framework.annotations.url;

/**
 *
 * @author megane
 */
public class Emp {
      int id;
    String nom;

    public Emp() {
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Emp(int id, String nom) {
        this.setId(id);
        this.setNom(nom);
    }
    
    @url("emp-save")
    public void findAll() {
        System.out.println("Liste de tous les employes");
    }
}
