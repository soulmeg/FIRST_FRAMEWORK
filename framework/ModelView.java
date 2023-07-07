package mapping;
import java.util.HashMap;
import java.util.Map;


public class ModelView{
    String view;
    HashMap<String, Object> data=new HashMap<String,Object>();
    HashMap<String, Object> session;

    public void setSession(HashMap<String, Object> session) {
        this.session = session;
    }

    public HashMap<String, Object> getSession() {
        return session;
    }

    public String getView(){
        return this.view;
    }
    public void setView(String v){
        this.view=v;
    }
    public ModelView(){
    }
    public ModelView(String view) {
        setView(view);
    }
    public HashMap<String, Object>  getData(){
        return this.data;
    }
    public void setData(HashMap<String, Object> d){
        this.data=d;
    }
// ke,valeur argument ---->mampitonbo anle zavatra anaty hasmap
    public void addItem(String key,Object valeur)throws Exception{
        this.getData().put(key,valeur);
    }
    
     public void addSessionItem(String key, Object value){
        if (session == null){
            setSession(new HashMap<String, Object>());
        }
        session.put(key, value);
    }
}
