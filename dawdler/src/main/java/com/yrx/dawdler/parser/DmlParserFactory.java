package com.yrx.dawdler.parser;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DmlParserFactory {
    @Resource(name = "insertParser")
    private IDmlParser insertParser;
    @Resource(name = "deleteParser")
    private IDmlParser deleteParser;
    @Resource(name = "updateParser")
    private IDmlParser updateParser;
    private static final Map<Class<? extends Statement>, IDmlParser> mapping = new HashMap<>(3);

    public IDmlParser getParser(Class<? extends Statement> source) {
        return mapping.get(source);
    }

    @PostConstruct
    private void init() {
        mapping.put(Insert.class, insertParser);
        mapping.put(Delete.class, deleteParser);
        mapping.put(Update.class, updateParser);
    }
}
