/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataObject;

import annotations.url;
import mapping.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

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

    @url("emp-list")
    public static ModelView listEmp()throws Exception{
        ModelView view = new ModelView("liste_employe.jsp");
        Vector<Emp> emp=new Vector<>();
        emp.add(new Emp(1,"Diana"));
        emp.add(new Emp(2,"Kanto"));
        emp.add(new Emp(3,"Babe"));
        view.addItem("employe",emp);
        return view;
    }

}
// <h4><% out.print(emp.get(i).getNom());%></h4>
