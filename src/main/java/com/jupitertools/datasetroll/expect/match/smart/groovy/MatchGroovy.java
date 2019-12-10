package com.jupitertools.datasetroll.expect.match.smart.groovy;


import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.jupitertools.datasetroll.expect.match.smart.MatchDataSmart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 22.12.2018.
 *
 * @author Korovin Anatoliy
 */
public class MatchGroovy implements MatchDataSmart {

    private static final String PREFIX = "groovy-match:";
    private final ScriptEngine engine;
    private final Logger log = LoggerFactory.getLogger(MatchGroovy.class);

    public MatchGroovy() {
        ScriptEngineManager factory = new ScriptEngineManager();
        engine = factory.getEngineByName("groovy");
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
            log.error("Groovy engine evaluate error: ", e);
            // TODO: improve the system of exceptions
            throw new RuntimeException("Groovy engine evaluate error", e);
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
