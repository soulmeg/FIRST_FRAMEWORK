/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import mapping.*;
import annotations.url;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

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
        String url = request.getHttpServletMapping().getMatchValue();
        try {
            if (mappingUrls.containsKey(url)) {
                Mapping mapping = mappingUrls.get(url);
                Class<?> cls = Class.forName(mapping.getClassName());
                Object value = cls.getMethod(mapping.getMethod()).invoke(null);
                if (value instanceof ModelView) {
                    ModelView view = (ModelView) value;
                    request.getRequestDispatcher(view.getView()).forward(request, response);
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
        
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
    }// </editor-fold>


   

}
