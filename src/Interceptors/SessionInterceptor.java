package Interceptors;

import Beans.UserBean;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import java.util.Map;

public class SessionInterceptor implements Interceptor {

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        Map<String, Object> sessionAttributes = invocation.getInvocationContext().getSession();

        UserBean userBean = (UserBean) sessionAttributes.get("UserLoginBean");

        if(userBean == null) {
            return "error";
        }
        else{
            return "success";
        }

    }

    @Override
    public void init() { }

    @Override
    public void destroy() { }

}
