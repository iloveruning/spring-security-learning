package com.cll.spel;

import com.cll.security.web.controller.UserController;
import com.cll.security.web.utils.R;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.parameters.AnnotationParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenliangliang
 * @date 2018/6/4
 */
public class SpelTest {


    @Test
    public void test1() throws NoSuchMethodException {

        ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
        String expStr="#{'hello'+'_'+@user.getPassword()}";//引用spring context中的bean
        expStr="#{T(java.lang.Math).abs(-1)}";//调用静态方法
        expStr="#{28>19}";//操作运算符
        expStr="#{T(java.lang.Math).random()>0.5?'大于0.5':'小于0.5'}";
        expStr="#{#r.getMsg()}";
        expStr="#{#r.msg}";//直接访问属性
        expStr="#{{1,2,3}}";//声明list
        expStr="#{#list[0]}";//访问list中的值
        expStr="#{#list[0]='list'}";//修改list中的值
        expStr="#{#map['a']}";//访问map
        expStr="#{#map['a']='all'}";//修改map中的值

        Map<String,Object> map=new HashMap<>();
        map.put("a","allow");
        map.put("b","best");

        List<String> list=Arrays.asList("hello","word");

        ExpressionParser parser=new SpelExpressionParser();


        Expression expression = parser.parseExpression(expStr, new TemplateParserContext());
        StandardEvaluationContext context=new StandardEvaluationContext();
        context.setBeanResolver(new BeanFactoryResolver(ctx));
        context.setVariable("r", R.ok());
        context.setVariable("list",list);
        context.setVariable("map",map);

        //context.setVariable("hello",new User("llchen",120));
       // Class clazz=User.class;
        //System.out.println(clazz);
        //System.out.println(Arrays.toString(clazz.getMethods()));
       //Method method= clazz.getDeclaredMethod("say",String.class);
        //System.out.println(method);
        //context.registerFunction("say",method);
        System.out.println(expression.getValue(context));
        System.out.println(list);
        System.out.println(map.get("a"));



    }


    @Test
    public void test2() throws NoSuchMethodException {

        ParameterNameDiscoverer discoverer=new AnnotationParameterNameDiscoverer("org.springframework.web.bind.annotation.RequestParam");

        discoverer=new DefaultParameterNameDiscoverer();
        Method method = UserController.class.getDeclaredMethod("register", String.class, String.class);
        System.out.println(method);
        Parameter[] parameters = method.getParameters();
        System.out.println(parameters[0].toString());
        String[] parameterNames = discoverer.getParameterNames(method);
        System.out.println(Arrays.toString(parameterNames));
    }

}
