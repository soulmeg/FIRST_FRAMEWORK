package dataObject;

import annotations.url;
import mapping.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.sql.Date;
public class Emp {
    int Id;
    String Nom;
    String Prenoms;
    Date DateNaissance;

    public Emp() {
    }

    public int getId() {
        return Id;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getPrenoms(){
        return Prenoms;
    }
     public void setPrenoms(String nom) {
        this.Prenoms = nom;
    }
    public void setDateNaissance(Date d){
        this.DateNaissance=d;
    }
    public Date getDateNaissance(){
        return DateNaissance;
    }
    public Emp(int id, String nom,String prenom, Date d) {
        this.setId(id);
        this.setNom(nom);
        this.setPrenoms(prenom);
        this.setDateNaissance(d);
    }

    public Emp(int id, String nom) {
        this.setId(id);
        this.setNom(nom);
    }
    
    @url("emp-j")
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
    
    @url("emp-save")
    public void save(){
        System.out.println("coucou "+this.getNom()+" "+this.getPrenoms()+" nee le "+this.getDateNaissance());
    }
}
// <h4><% out.print(emp.get(i).getNom());%></h4>
