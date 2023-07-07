package dataObject;

import annotations.*;
import mapping.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.sql.Date;
import util.*;
import annotations.*;

@Scope(value = "singleton")
public class Handrana {
    String ok;
    String admin;

    public String getOk() {
        return ok;
    }
    public void setOk(String ok) {
        this.ok = ok;
    }
    public String getAdmin() {
        return admin;
    }
    public void setAdmin(String admin) {
        this.admin = admin;
    }

    @Authentification(user = "")
    @url("test2-insert")
    public ModelView insert()throws Exception{
        ModelView modelView = new ModelView("list.jsp");
        // modelView.addItem("test", this);
        String[] list = new String[2];
        list[0] = "contenue 1";
        list[1] = "contenue 2";
        modelView.addItem("ls", list);
        modelView.setJson(true);
        return modelView;
    }

    @url("test2-login")
    public ModelView Login() throws Exception{
        ModelView modelView = new ModelView("list.jsp");
        String[] list = new String[2];
        list[0] = "List";
        list[1] = "listt";
        modelView.addItem("ls", list);
        modelView.addSessionItem("isConnected", this);
        modelView.addSessionItem("profil", this.getAdmin());
        return modelView;
    }
}