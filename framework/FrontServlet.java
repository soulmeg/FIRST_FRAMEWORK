package servlet;

import mapping.*;
import dataObject.*;
import annotations.url;
import annotations.*;
import util.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Enumeration;
import java.lang.reflect.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.JFrame;
import javax.swing.ViewportLayout;
import javax.swing.text.html.parser.ContentModel;
import java.sql.Date;
import java.util.Vector;
import java.sql.Timestamp;
import java.lang.reflect.Parameter;
import java.lang.reflect.Method;
import javax.servlet.http.Part;
import javax.servlet.annotation.MultipartConfig;
import com.google.gson.Gson;

@MultipartConfig()
public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> mappingUrls;
    HashMap<String, Object> singletons;
    String sessionName;
    String sessionProfile;

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getSessionProfile() {
        return sessionProfile;
    }

    public void setSessionProfile(String sessionProfile) {
        this.sessionProfile = sessionProfile;
    }

    public HashMap<String, Object> getSingletons() {
        return singletons;
    }

    public void setSingletons(HashMap<String, Object> singletons) {
        this.singletons = singletons;
    }

    public HashMap<String, Mapping> getMappingUrls() {
        return mappingUrls;
    }

    public void setMappingUrls(HashMap<String, Mapping> mappingUrls) {
        this.mappingUrls = mappingUrls;
    }

    public void init() throws ServletException {
        try {
            mappingUrls = new HashMap<String, Mapping>();
            singletons = new HashMap<>();
            String packageName = "dataObject";
            this.setSessionName(getInitParameter("sessionName"));
            this.setSessionProfile(getInitParameter("sessionProfil"));
            URL f = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "/"));
            for (File file : new File(f.getFile()).listFiles()) {
                if (file.getName().contains(".class")) {
                    String className = file.getName().replaceAll(".class$", "");
                    Class<?> cls = Class.forName(packageName + "." + className);
                    for (Method method : cls.getDeclaredMethods()) {
                        if (method.isAnnotationPresent(url.class)) {
                            mappingUrls.put(method.getAnnotation(url.class).value(),
                                    new Mapping(cls.getName(), method.getName()));
                        }
                    }
                    if (cls.isAnnotationPresent(Scope.class)) {
                        singletons.put(cls.getName(), null);
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
        String url2 = url.replace("/", "");
        try {
            if (this.getMappingUrls().containsKey(url2)) {
                String classname = this.getMappingUrls().get(url2).getClassName();
                String methode = this.getMappingUrls().get(url2).getMethod();
                Class<?> cls = Class.forName(classname);
                Method[] methods = cls.getDeclaredMethods();
                Vector<String> nomForm = liste_nomFormulaire(request);
                Object objet = null;
                if (this.getSingletons().containsKey(classname)) {
                    if (this.getSingletons().get(classname) == null) {
                        this.getSingletons().put(classname, cls.getConstructor().newInstance());
                    }
                    objet = this.getSingletons().get(classname);
                } else {
                    objet = cls.getConstructor().newInstance();
                }
                invok_object(objet, request, response);
                Method method = null;
                for (Method m : methods) {
                    if (m.getName().equals(methode)) {
                        method = m;
                        break;
                    }
                }

                if (method != null) {
                    if (method.isAnnotationPresent(Session.class)) {
                        HashMap<String,Object> Hashsessions=new HashMap<>();
                        HttpSession session = request.getSession();
                        List<String> sessions = Collections.list(session.getAttributeNames());
                        for (String string : sessions) {
                            Hashsessions.put(string,session.getAttribute(string));
                        }
                        objet.getClass().getDeclaredMethod("setSession",HashMap.class).invoke(objet,Hashsessions);
                    }
                    Gson gson = new Gson();
                    
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length > 0) {
                        ifHaveParameter(out, objet, methode, method, nomForm, parameterTypes, cls, request, response);
                    } else if (parameterTypes.length == 0) {
                        Method mi = cls.getDeclaredMethod(methode);
                        Object resp = mi.invoke(objet, (Object[]) null);
                        if(method.isAnnotationPresent(RestAPI.class)){
                            out.println(gson.toJson(resp));
                        }
                        if (resp instanceof ModelView) {
                            // ModelView mv=(ModelView) mi.invoke(objet);
                            if(method.isAnnotationPresent(Session.class)) {
                                    HashMap<String,Object> Hashsessions=new HashMap<>();
                                    HttpSession session = request.getSession();
                                    List<String> sessions = Collections.list(session.getAttributeNames());
                                    Hashsessions = (HashMap<String,Object>) objet.getClass().getDeclaredMethod("getSession").invoke(objet);
                                    for (String string : Hashsessions.keySet()) {
                                    session.setAttribute(string,Hashsessions.get(string));
                                }
                            }            
                            ModelView mv = (ModelView) resp;
                            if(mv.isInvalidateSession()){
                                request.getSession().invalidate();
                            }
                            if(mv.getRemoveSession().size() > 0){
                                for (String removeSession : mv.getRemoveSession()) {
                                    request.getSession().removeAttribute(removeSession);
                                }
                            }
                            // javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(),request.getSession().getAttribute(getSessionProfile()));
                            HttpSession session = request.getSession();
                            if (mi.isAnnotationPresent(Authentification.class)) {
                                if ((((Authentification) mi.getAnnotation(Authentification.class)).user().trim().compareTo((String) request.getSession().getAttribute(getSessionProfile())) == 0) || ((Authentification) mi.getAnnotation(Authentification.class)).user().equals("")) {
                                    for (Map.Entry<String,Object> e : mv.getData().entrySet()) {
                                        request.setAttribute(e.getKey(), e.getValue());
                                    }
                                    if(mv.getJson()){
                                        response.setContentType("application/json");
                                        out.println(gson.toJson(mv.getData()));
                                    }
                                    else {
                                        request.getRequestDispatcher(mv.getView()).forward(request, response);
                                    }
                                } else {
                                    throw new Exception("Vous n'avez pas le droit d'acceder a cette page");
                                }
                            } else {
                                checkMethod(mv, request);
                                for (Map.Entry<String, Object> e : mv.getData().entrySet()) {
                                    request.setAttribute(e.getKey(), e.getValue());
                                }
                                if(mv.getJson()){
                                        response.setContentType("application/json");
                                        out.println(gson.toJson(mv.getData()));
                                }
                                else {
                                    request.getRequestDispatcher(mv.getView()).forward(request, response);
                                }
                                // request.getRequestDispatcher(mv.getView()).forward(request, response);

                            }

                        }
                    }
                } else {
                    out.println("Méthode non trouvée");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(out);
            throw new ServletException(e);
        }
    }

    public void ifHaveParameter(PrintWriter out, Object objet, String methode, Method method, Vector<String> nomForm,
            Class<?>[] parameterTypes, Class<?> cls, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Object ob = null; // Initialisez la variable ob
        Method mi = cls.getDeclaredMethod(methode, parameterTypes);
        Gson gson = new Gson();
        Parameter[] parameters = mi.getParameters();
        Vector<Object> vect_object = new Vector<>();
        Vector<String> param_exist = new Vector<>();
        int taille = 0;
        for (Parameter parameter : parameters) {
            String name = parameter.getName();
            if (parameter.getType().isArray()) {
                name = name + "[]";
            }
            for (int i = 0; i < nomForm.size(); i++) {
                Object oo = null;
                if (name.equals(nomForm.get(i))) {
                    if (parameter.getType().isArray()) {
                        oo = request.getParameterValues(name);
                    } else {
                        oo = request.getParameter(name);
                        if (parameter.getType().equals(String.class)) {
                            oo = oo;
                        } else if (parameter.getType().equals(Integer.class)) {
                            oo = Integer.parseInt(String.valueOf(oo));
                        } else if (parameter.getType().equals(Double.class)) {
                            oo = Double.parseDouble(String.valueOf(oo));
                        }
                    }
                    vect_object.add(oo);
                    break;
                }
            }
            param_exist.add(parameter.getName());
        }
        Object[] obj_parametres = vect_object.toArray();
        checkVoid(obj_parametres, request, response);
        if(method.isAnnotationPresent(RestAPI.class)){
            out.println(gson.toJson(objet));
        }
        if (mi.invoke(objet, obj_parametres) instanceof ModelView) {
            
            if (method.isAnnotationPresent(Session.class)) {
                HashMap<String,Object> Hashsessions=new HashMap<>();
                HttpSession session = request.getSession();
                List<String> sessions = Collections.list(session.getAttributeNames());
                Hashsessions = (HashMap<String,Object>) objet.getClass().getDeclaredMethod("getSession").invoke(objet);
                for (String string : Hashsessions.keySet()) {
                    session.setAttribute(string,Hashsessions.get(string));
                }
            }                                                                                                                                                           
            ModelView mv = (ModelView) mi.invoke(objet, obj_parametres);
            if(mv.isInvalidateSession()){
                request.getSession().invalidate();
            }
            if(mv.getRemoveSession().size() > 0){
                for (String removeSession : mv.getRemoveSession()) {
                    request.getSession().removeAttribute(removeSession);
                }
            }
            for (Map.Entry<String, Object> e : mv.getData().entrySet()) {
                request.setAttribute(e.getKey(), e.getValue());
            }
              if(mv.getJson()){
                response.setContentType("application/json");
                out.println(gson.toJson(mv.getData()));
            }
            else {
                request.getRequestDispatcher(mv.getView()).forward(request, response);
            }
            // request.getRequestDispatcher(mv.getView()).forward(request, response);
        }
    }

    public void checkVoid(Object[] obj_parametres, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int count = 0;
        for (int i = 0; i < obj_parametres.length; i++) {
            if (obj_parametres[i] == null)
                count++;
        }
        if (count == obj_parametres.length) {
            request.getRequestDispatcher("Error.jsp").forward(request, response);
        }
    }

    public void invok_object(Object objet, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintWriter out = response.getWriter();
        String[] liste_attribut = takeAllFields(objet);
        Vector<String> parameter = liste_nomFormulaire(request);
        Vector<String> attribut = convertToVector(liste_attribut);
        if(objet.getClass().getAnnotation(Scope.class).value().equals("singleton")) {
            this.resetDefault(objet, request, response);
        }
        try {
            for (int i = 0; i < objet.getClass().getDeclaredFields().length; i++) {
                for (int j = 0; j < parameter.size(); j++) {
                    if (objet.getClass().getDeclaredFields()[i].getName().equals(parameter.get(j))) {
                        String a = request.getParameter(objet.getClass().getDeclaredFields()[i].getName());
                        String set = "set" + capitalize(parameter.get(j));
                        if (objet.getClass().getDeclaredFields()[i].getType().getName().equals("int")) {
                            objet.getClass().getMethod(set, int.class).invoke(objet, a);
                        }
                        if (objet.getClass().getDeclaredFields()[i].getType().getName().equals("double")) {
                            objet.getClass().getMethod(set, double.class).invoke(objet, a);
                        }
                        if (objet.getClass().getDeclaredFields()[i].getType().getName().equals("java.lang.String")) {
                            objet.getClass().getMethod(set, String.class).invoke(objet, a);
                        }
                        if (objet.getClass().getDeclaredFields()[i].getType().getName().equals("java.sql.Date")) {
                            Date x = Date.valueOf(a);
                            objet.getClass().getMethod(set, Date.class).invoke(objet, x);
                        }
                        if (objet.getClass().getDeclaredFields()[i].getType().getName().equals("java.sql.Timestamp")) {
                            objet.getClass().getMethod(set, Timestamp.class).invoke(objet, a);
                        }
                    }
                }
            }

            try {
                ifAttributeFileUpload(objet, request, response);
            } catch (Exception e) {
                e.getMessage();
                e.printStackTrace(out);
            }

        } catch (Exception e) {
            e.printStackTrace(out);
            e.getMessage();
        }

    }

    public String capitalize(String a) throws Exception {
        String premier = a.substring(0, 1).toUpperCase();
        String last = a.substring(1);
        return premier + last;
    }

    public void ifAttributeFileUpload(Object objet, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String contentType = request.getContentType();
        for (int i = 0; i < objet.getClass().getDeclaredFields().length; i++) {

            if (objet.getClass().getDeclaredFields()[i].getType() == util.FileUpload.class) {
                FileUpload fileUpload = new FileUpload();
                String fieldName = objet.getClass().getDeclaredFields()[i].getName();
                // Part filePart = request.getPart(fieldName);
                if (contentType != null && contentType.contains("multipart/form-data")) {
                    Part filePart = null;
                    filePart = request.getPart(fieldName);
                    if (filePart != null) {
                        String fileName = filePart.getSubmittedFileName();
                        // Lire les octets du fichier
                        InputStream fileContent = filePart.getInputStream();
                        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = fileContent.read(buffer)) != -1) {
                            byteStream.write(buffer, 0, bytesRead);
                        }
                        byte[] fileBytes = byteStream.toByteArray();

                        // Setter les valeurs dans l'objet FileUpload
                        fileUpload.setName(fileName);
                        fileUpload.setContent(fileBytes);

                        // Appeler le setter correspondant sur l'objet principal
                        objet.getClass()
                                .getDeclaredMethod(
                                        "set" + capitalize(objet.getClass().getDeclaredFields()[i].getName()),
                                        FileUpload.class)
                                .invoke(objet, fileUpload);
                    }
                }
            }
        }
    }

    // EMP , nom Formulaire
    public Vector<String> attribute_find(Object objet, HttpServletRequest request) throws Exception {
        Vector<String> parameter = liste_nomFormulaire(request);
        String[] liste_attribut = takeAllFields(objet);
        Vector<String> attribut = convertToVector(liste_attribut);
        Vector<String> vect = new Vector<>();
        for (int i = 0; i < attribut.size(); i++) {
            for (int j = 0; j < parameter.size(); j++) {
                if (attribut.get(i).equals(parameter.get(j))) {
                    vect.add(attribut.get(i));
                }
            }
        }
        return vect;
    }

    public Vector<String> liste_nomFormulaire(HttpServletRequest request) throws Exception {
        Enumeration<String> parametersName = request.getParameterNames();
        Vector<String> vect = new Vector<>();
        while (parametersName.hasMoreElements()) {
            String paramsName = parametersName.nextElement();
            vect.add(paramsName);
        }
        return vect;
    }

    public String[] takeAllFields(Object objet) throws Exception {
        String[] tableau = new String[objet.getClass().getDeclaredFields().length];
        for (int i = 0; i < tableau.length; i++) {
            tableau[i] = objet.getClass().getDeclaredFields()[i].getName();
        }
        return tableau;
    }

    public Vector<String> convertToVector(String[] tab) throws Exception {
        Vector<String> vect = new Vector<>();
        for (int i = 0; i < tab.length; i++) {
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

    public void checkMethod(ModelView modelView, HttpServletRequest request) throws Exception {
        if (modelView.getSession() != null) {
            HashMap<String,Object> objet = modelView.getSession();
            for (Map.Entry<String, Object> e : objet.entrySet()) {
                request.getSession().setAttribute(e.getKey(), e.getValue());
            }   
        } 
    }

    public void resetDefault(Object objet, HttpServletRequest request, HttpServletResponse response)throws Exception{
        PrintWriter out = response.getWriter();
        String[] liste_attribut = takeAllFields(objet);
        Vector<String> parameter = liste_nomFormulaire(request);
        Vector<String> attribut = convertToVector(liste_attribut);
        try {
            for (int i = 0; i < objet.getClass().getDeclaredFields().length; i++) {
                for (int j = 0; j < parameter.size(); j++) {
                    if (objet.getClass().getDeclaredFields()[i].getName().equals(parameter.get(j))) {
                        String a = request.getParameter(objet.getClass().getDeclaredFields()[i].getName());
                        String set = "set" + capitalize(parameter.get(j));
                        if (objet.getClass().getDeclaredFields()[i].getType().getName().equals("int")) {
                            objet.getClass().getMethod(set, int.class).invoke(objet, 0);
                        }
                        if (objet.getClass().getDeclaredFields()[i].getType().getName().equals("double")) {
                            objet.getClass().getMethod(set, double.class).invoke(objet, 0);
                        }
                        if (objet.getClass().getDeclaredFields()[i].getType().getName().equals("java.lang.String")) {
                            objet.getClass().getMethod(set, String.class).invoke(objet, (Object)null);
                        }
                        if (objet.getClass().getDeclaredFields()[i].getType().getName().equals("java.sql.Date")) {
                            Date x = Date.valueOf(a);
                            objet.getClass().getMethod(set, Date.class).invoke(objet, (Object)null);
                        }
                        if (objet.getClass().getDeclaredFields()[i].getType().getName().equals("java.sql.Timestamp")) {
                            objet.getClass().getMethod(set, Timestamp.class).invoke(objet, (Object)null);
                        }
                    }
                }
            }

            try {
                ifAttributeFileUpload(objet, request, response);
            } catch (Exception e) {
                e.getMessage();
                e.printStackTrace(out);
            }

        } catch (Exception e) {
            e.printStackTrace(out);
            e.getMessage();
        }
    }
}

