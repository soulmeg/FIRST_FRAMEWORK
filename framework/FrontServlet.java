
package servlet;

import mapping.*;
import dataObject.*;
import annotations.url;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Enumeration;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.ViewportLayout;
import javax.swing.text.html.parser.ContentModel;
import java.sql.Date;
import java.util.Vector;
import java.sql.Timestamp;
public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> mappingUrls;

    public HashMap<String, Mapping> getMappingUrls() {
        return mappingUrls;
    }


    public void setMappingUrls(HashMap<String, Mapping> mappingUrls) {
        this.mappingUrls = mappingUrls;
    }

    public void init() throws ServletException {
        try {
            mappingUrls = new HashMap<String, Mapping>();
            String packageName = "dataObject";
            URL f = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "/"));
            for (File file : new File(f.getFile()).listFiles()) {
                if (file.getName().contains(".class")) {
                    String className = file.getName().replaceAll(".class$", "");
                    Class<?> cls = Class.forName(packageName + "." + className);
                    for (Method method : cls.getDeclaredMethods()) {
                        if (method.isAnnotationPresent(url.class)) {
                            mappingUrls.put(method.getAnnotation(url.class).value(), new Mapping(cls.getName(), method.getName()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }



    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String url = request.getServletPath();
        String url2=url.replace("/","");
        try {
        //  for (Map.Entry<String, Mapping> entry : mappingUrls.entrySet()) {
        //     out.println("Nom de l'url: "+entry.getKey()+" //Nom de la classe: "+ entry.getValue().getClassName()+" //Nom des methodes: "+ entry.getValue().getMethod());
        //  }  
            // out.println(url2); //Nom de l'url
            if(this.getMappingUrls().containsKey(url2)){
                String classname = this.getMappingUrls().get(url2).getClassName();
                String methode = this.getMappingUrls().get(url2).getMethod();
                Class<?> cls = Class.forName(classname);
                Method method = cls.getDeclaredMethod(methode);
                Object objet = cls.newInstance();
                invok_object(objet,request,response);
                if(objet.getClass().getSimpleName().equals("Emp")){
                    Emp e = (Emp) objet;
                    e.save();
                    out.println("NOM: "+e.getNom());
                    out.println("PRENOMS: "+ e.getPrenoms());
                    out.println("DATE DE NAISSANCE: "+e.getDateNaissance());
                }
                ModelView mv=(ModelView) method.invoke(objet);
                for (Map.Entry<String, Object> e : mv.getData().entrySet()) {
                    request.setAttribute(e.getKey(),e.getValue());
                }
                request.getRequestDispatcher(mv.getView()).forward(request, response);
            }
            // else throw new Exception("Non valableee");
            
        } catch (Exception e) {
            throw new ServletException(e);
        }
        
    }


    public void invok_object(Object objet,HttpServletRequest request,HttpServletResponse response)throws Exception{
        PrintWriter out = response.getWriter();
        String [] liste_attribut = takeAllFields(objet);
        Vector<String> parameter=liste_nomFormulaire(request);
        Vector<String> attribut=convertToVector(liste_attribut);
        try{
            // String val = request.getParameter(l.get(0));
            for(int i = 0; i < objet.getClass().getDeclaredFields().length; i++){
                for(int j=0;j<parameter.size();j++){
                    if(objet.getClass().getDeclaredFields()[i].getName().equals(parameter.get(j))){
                        String a = request.getParameter(objet.getClass().getDeclaredFields()[i].getName());
                        String set="set"+parameter.get(j);
                            if (objet.getClass().getDeclaredFields()[i].getType().getName().equals("int")){
                                    objet.getClass().getMethod(set,int.class).invoke(objet,a);                
                            }
                            if (objet.getClass().getDeclaredFields()[i].getType().getName().equals("double")){
                                    objet.getClass().getMethod(set,double.class).invoke(objet,a);                
                            }
                            if (objet.getClass().getDeclaredFields()[i].getType().getName().equals("java.lang.String")){
                                    objet.getClass().getMethod(set,String.class).invoke(objet,a);                
                            }
                            if (objet.getClass().getDeclaredFields()[i].getType().getName().equals("java.sql.Date")){
                                Date x =Date.valueOf(a);
                                    objet.getClass().getMethod(set,Date.class).invoke(objet,x);                
                            }
                            if (objet.getClass().getDeclaredFields()[i].getType().getName().equals("java.sql.Timestamp")){
                                    objet.getClass().getMethod(set,Timestamp.class).invoke(objet,a);                
                            }
                        }
                }
            }

        }
        catch (Exception e) {
            e.printStackTrace(out);
            e.getMessage();
        }
    }


     // EMP , nom Formulaire
    public Vector<String> attribute_find(Object objet,HttpServletRequest request)throws Exception{
        Vector<String> parameter=liste_nomFormulaire(request);
        String [] liste_attribut = takeAllFields(objet);
        Vector<String> attribut=convertToVector(liste_attribut);
        Vector<String> vect=new Vector<>();
        for(int i=0;i<attribut.size();i++){
            for(int j=0;j<parameter.size();j++){
                if(attribut.get(i).equals(parameter.get(j))){
                    vect.add(attribut.get(i));     
                }
            }
        }
        return vect;
    }

    public Vector<String> liste_nomFormulaire(HttpServletRequest request)throws Exception{
        Enumeration<String> parametersName = request.getParameterNames();
        Vector<String> vect=new Vector<>();
        while(parametersName.hasMoreElements()){
            String paramsName= parametersName.nextElement();
            // out.println("Nom de l'input "+paramsName);
            vect.add(paramsName);
        }
        return vect;
    }

    public String [] takeAllFields(Object objet)throws Exception{
        String [] tableau = new String[objet.getClass().getDeclaredFields().length];
        for(int i=0;i<tableau.length;i++){
            tableau[i]=objet.getClass().getDeclaredFields()[i].getName();
        }
        return tableau;
    }


    public Vector<String> convertToVector(String [] tab)throws Exception{
        Vector<String> vect=new Vector<>();
        for(int i=0;i<tab.length;i++){
            vect.add(tab[i]);
        }
    return vect;
    }

   
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    public String getServletInfo() {
        return "Short description";
    }

}

