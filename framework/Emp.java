package dataObject;
import annotations.*;
import mapping.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.sql.Date;
import javax.swing.*;
import util.*;

@Scope("Singleton")
public class Emp {
    int Id;
    String Nom;
    String Prenoms;
    FileUpload badge;
    HashMap<String, Object> session;

    public HashMap<String, Object> getSession() {
        return session;
    }

    public void setSession(HashMap<String, Object> session) {
        this.session = session;
    }

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
    public FileUpload getBadge() {
        return badge;
    }

    public void setBadge(FileUpload badge) {
        this.badge = badge;
    }
    public Emp(int id, String nom,String prenom) {
        this.setId(id);
        this.setNom(nom);
        this.setPrenoms(prenom);
    }

    public Emp(int id, String nom) {
        this.setId(id);
        this.setNom(nom);
    }
    
    @RestAPI
    @url("employe-list")
    public Emp[] findAll() {
        // System.out.println("Liste de tous les employes");
        Emp [] emp =new Emp[2];
        emp[0]=new Emp(1,"Diana");        
        emp[1]=new Emp(2,"Megane");
        return emp;
    }

    @url("emp-list")
    public ModelView listEmp()throws Exception{
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
        System.out.println("coucou "+this.getNom()+" "+this.getPrenoms());
    }

    @url("hab-emp")
    public ModelView findByToerana(String [] option)throws Exception{
       ModelView view = new ModelView("detailEmploye.jsp");
        Vector<Emp> emp=new Vector<>();
        Vector<Emp> e=new Vector<>();
        emp.add(new Emp(1,"Rakotomaharo","Diana"));
        emp.add(new Emp(2,"Ramilijaona","Kantoniaina"));
        for(int i=0;i<emp.size();i++ ){
            for(int j=0;j<option.length;j++){
                if(String.valueOf(emp.get(i).getId()).equals(option[j])){
                        e.add(emp.get(i));
                }
            }
        }
        view.addItem("idChoisi",e);
       return view;       
    }

    @url("test-upload")
    public ModelView upload()throws Exception{
        ModelView modelView = new ModelView("test.jsp");
        modelView.addItem("test", this);
        return modelView;
    }

    @Session()
    @url("test-session")
    public ModelView checkClassSession()throws Exception{
        ModelView modelView = new ModelView("test.jsp");
        // javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(),this.getSession().get("profil"));
        this.getSession().put("profil","soulmate");
        modelView.addItem("test", this);
        return modelView;
    }
}
