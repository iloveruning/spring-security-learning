package com.cll.security.web.common.express;

import com.cll.security.web.utils.SpringContextHolder;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author chenliangliang
 * @date 2018/6/4
 */
@Component
public class SysLogExpressContext {

    private ParserContext parserContext = new TemplateParserContext();

    private ExpressionParser parser = new SpelExpressionParser();

    private CllEvaluationContext context = new CllEvaluationContext();

    @PostConstruct
    public void init() {
        context.setBeanResolver(new BeanFactoryResolver(SpringContextHolder.getContext()));
    }


    public void addVariable(String name, Object variable) {
        context.setVariable(name, variable);
    }

    public void removeVariable(String name) {
        context.removeVariable(name);
    }

    public Object parseSpel(String spel) {
        Expression expression = parser.parseExpression(spel, parserContext);
        return expression.getValue(context);
    }


    public <T> T parseSpel(String spel,Class<T> resultType) {
        Expression expression = parser.parseExpression(spel, parserContext);
        return expression.getValue(context,resultType);
    }
}
