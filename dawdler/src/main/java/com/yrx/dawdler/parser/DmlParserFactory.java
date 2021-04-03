package com.yrx.dawdler.parser;

import com.yrx.dawdler.annotation.DmlParserFlag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by r.x on 2021/4/3.
 */
@Slf4j
@Component
public class DmlParserFactory implements ApplicationListener<ContextRefreshedEvent> {
    private static final Map<Class<?>, IDmlParser> mapping = new HashMap<>();

    public IDmlParser getParser(Class<?> source) {
        return mapping.get(source);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(DmlParserFlag.class);
        for (Object parser : beans.values()) {
            if (parser instanceof IDmlParser) {
                DmlParserFlag annotation = parser.getClass().getAnnotation(DmlParserFlag.class);
                if (annotation != null) {
                    mapping.put(annotation.target(), (IDmlParser) parser);
                }
            }
        }
    }
}
