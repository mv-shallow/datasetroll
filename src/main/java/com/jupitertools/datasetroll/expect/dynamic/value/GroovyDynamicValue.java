package com.jupitertools.datasetroll.expect.dynamic.value;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 16.12.2018.
 *
 * @author Korovin Anatoliy
 */
public class GroovyDynamicValue implements DynamicValue {

    private final ScriptEngine engine;
    private final Logger log = LoggerFactory.getLogger(GroovyDynamicValue.class);

    public GroovyDynamicValue() {
        ScriptEngineManager factory = new ScriptEngineManager();
        engine = factory.getEngineByName("groovy");
    }

    @Override
    public boolean isNecessary(Object value) {
        return value instanceof String &&
               ((String) value).startsWith("groovy:");
    }

    @Override
    public Object evaluate(Object value) {
        try {
            return engine.eval((String) value);
        } catch (ScriptException e) {
            log.error("Groovy engine evaluate error: ", e);
            throw new RuntimeException("Groovy engine evaluate error", e);
        }
    }
}
