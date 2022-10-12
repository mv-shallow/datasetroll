package com.jupitertools.datasetroll.expect.dynamic.value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created on 16.12.2018.
 *
 * @author Korovin Anatoliy
 */
public class JavaScriptDynamicValue implements DynamicValue {

    private final ScriptEngine engine;
    private final Logger log;

    public JavaScriptDynamicValue() {
        engine = new ScriptEngineManager().getEngineByName("js");
        log = LoggerFactory.getLogger(JavaScriptDynamicValue.class);
    }

    @Override
    public boolean isNecessary(Object value) {
        return value instanceof String &&
               ((String) value).startsWith("js:");
    }

    @Override
    public Object evaluate(Object value) {
        try {
            return engine.eval((String) value);
        } catch (ScriptException e) {
            log.error("JavaScript engine evaluate error: ", e);
            throw new RuntimeException("JavaScript engine evaluate error", e);
        }
    }
}
