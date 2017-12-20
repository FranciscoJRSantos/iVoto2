package Interceptors;

import Actions.UserAction;
import Beans.UserBean;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import java.util.Map;

public class SessionInterceptor implements Interceptor {

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        final long serialVersionUID = 189237412378L;

        Map<String, Object> sessionAttributes = invocation.getInvocationContext().getSession();

        System.out.println(sessionAttributes);
        System.out.println(invocation.getInvocationContext());

        UserBean userBean = (UserBean) sessionAttributes.get("UserBean");

        if(userBean != null) {
            return invocation.invoke();
        }
        else if(invocation.getAction() instanceof UserAction){
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
