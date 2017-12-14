package Actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import java.util.Map;

public class Action extends ActionSupport implements SessionAware{
    Map<String,Object> session;

    @Override
    public void setSession(Map<String,Object> map){
        this.session = map;
    }
}
