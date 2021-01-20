package com.yrx.dawdler.parser;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.drop.Drop;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DdlParserFactory {
    @Resource(name = "createTableParser")
    private IDdlParser createTableParser;
    @Resource(name = "dropTableParser")
    private IDdlParser dropTableParser;
    @Resource(name = "alterTableParser")
    private IDdlParser alterTableParser;
    @Resource(name = "createIndexParser")
    private IDdlParser createIndexParser;
    private static final Map<Class<? extends Statement>, IDdlParser> mapping = new HashMap<>(4);

    public IDdlParser getParser(Class<? extends Statement> source) {
        return mapping.get(source);
    }

    @PostConstruct
    private void init() {
        mapping.put(CreateTable.class, createTableParser);
        mapping.put(Drop.class, dropTableParser);
        mapping.put(Alter.class, alterTableParser);
        mapping.put(CreateIndex.class, createIndexParser);
    }
}
