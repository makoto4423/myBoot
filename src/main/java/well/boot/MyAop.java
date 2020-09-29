package well.boot;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;
import well.annotation.MyAnnotation;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class MyAop {


    @Before(value = "execution(* well.boot.AbcController.kkk(..)) && args(request,name,..)", argNames = "request,name")
    public void before(HttpServletRequest request,String name){
        int i = 0;
    }

    @After(value = "@annotation(well.annotation.MyAnnotation)")
    public void after(JoinPoint joinPoint){
        MyAnnotation annotation = ((MethodSignature) ((MethodInvocationProceedingJoinPoint) joinPoint).getSignature()).getMethod().getAnnotation(MyAnnotation.class);
    }
}
