package com.jupitertools.datasetroll.expect.match.smart.groovy;

import com.jupitertools.datasetroll.expect.match.smart.MatchDataSmart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created on 22.12.2018.
 *
 * @author Korovin Anatoliy
 */
public class MatchGroovy implements MatchDataSmart {

    private static final String PREFIX = "groovy-match:";
    private final ScriptEngine engine;
    private final Logger log;

    public MatchGroovy() {
        engine = new ScriptEngineManager().getEngineByName("groovy");
        log = LoggerFactory.getLogger(MatchGroovy.class);
    }

    @Override
    public boolean match(Object original, Object expected) {
        try {
            String expectedValue = ((String) expected).replaceFirst(PREFIX, "");
            engine.put("value", original);
            Object result = engine.eval(expectedValue);
            if (!(result instanceof Boolean)) {
                // TODO: improve the system of exceptions
                throw new RuntimeException(PREFIX + " must return a boolean value instead of {" + result + "}");
            }
            return (boolean) result;
        } catch (ScriptException e) {
            log.error("Groovy engine script evaluation error", e);
            // TODO: improve the system of exceptions
            throw new RuntimeException("Groovy engine script evaluation error", e);
        }
    }

    @Override
    public boolean isNecessary(Object expected) {
        if (!(expected instanceof String)) {
            return false;
        }
        return ((String) expected).startsWith(PREFIX);
    }
}
