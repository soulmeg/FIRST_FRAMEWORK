package mapping;
import java.util.HashMap;
import java.util.Map;

public class ModelView{
    String view;
    HashMap<String, Object> data=new HashMap<String,Object>();

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
    // function addItem(String,Object)

    public void addItem(String key,Object valeur)throws Exception{
        this.getData().put(key,valeur);
    }
}

// <!-- <% Hashmap<String,Object> data = (Hashmap<String,Object>) request.getAttribute("lst"); %> -->
    // <!-- <h1><% out.print(data.get(0)); %></h1> -->
