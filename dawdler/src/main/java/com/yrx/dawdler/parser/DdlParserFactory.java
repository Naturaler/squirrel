package com.yrx.dawdler.parser;

import com.yrx.dawdler.annotation.DdlParserFlag;
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
public class DdlParserFactory implements ApplicationListener<ContextRefreshedEvent> {
    private static final Map<Class<?>, IDdlParser> mapping = new HashMap<>();

    public IDdlParser getParser(Class<?> source) {
        return mapping.get(source);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(DdlParserFlag.class);
        for (Object parser : beans.values()) {
            if (parser instanceof IDdlParser) {
                DdlParserFlag annotation = parser.getClass().getAnnotation(DdlParserFlag.class);
                mapping.put(annotation.target(), (IDdlParser) parser);
            }
        }
    }
}
