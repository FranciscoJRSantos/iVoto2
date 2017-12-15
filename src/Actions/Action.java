package Actions;

import Beans.Bean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class Action extends ActionSupport implements SessionAware{
    Map<String,Object> session;
    Bean bean;

    @Override
    public void setSession(Map<String,Object> map){
        this.session = map;
    }

    public String execute() throws Exception{
        return "success";
    }

    public String show(Bean bean) throws Exception {
        this.bean = bean;
        return "success";
    }
}
