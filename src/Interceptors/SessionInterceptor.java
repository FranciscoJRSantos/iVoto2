package Interceptors;

import Beans.UserBean;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import java.util.Map;

public class SessionInterceptor implements Interceptor {

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        final long serialVersionUID = 189237412378L;

        Map<String, Object> sessionAttributes = invocation.getInvocationContext().getSession();

        UserBean userBean = (UserBean) sessionAttributes.get("UserLoginBean");

        if(userBean != null) {
            return invocation.invoke();
        }
        else{
            return "error";
        }

    }

    @Override
    public void init() { }

    @Override
    public void destroy() { }

}
