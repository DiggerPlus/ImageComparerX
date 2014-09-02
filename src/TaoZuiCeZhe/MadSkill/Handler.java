package TaoZuiCeZhe.MadSkill;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Handler implements InvocationHandler{
	//要代理的原始对象
    private Object obj;
    
    public Handler(Object obj){
    	super();
    	this.obj = obj;
    }
    
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
	  Object result = null;
//调用原始对象的方法
	  result = method.invoke(obj, args);
	  return result;
	}

}
