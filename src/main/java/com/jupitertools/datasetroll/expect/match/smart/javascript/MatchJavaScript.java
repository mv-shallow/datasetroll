package com.jupitertools.datasetroll.expect.match.smart.javascript;

import com.jupitertools.datasetroll.expect.match.smart.MatchDataSmart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * Created on 22.12.2018.
 *
 * @author Korovin Anatoliy
 */
public class MatchJavaScript implements MatchDataSmart {

    private static final String PREFIX = "js-match:";
    private final ScriptEngine engine;
    private final Logger log;

    public MatchJavaScript() {
        engine = new ScriptEngineManager().getEngineByName("js");
        log = LoggerFactory.getLogger(MatchJavaScript.class);
    }

    @Override
    public boolean match(Object original, Object expected) {

        Object result = evaluate((String) expected, original);

        if (!(result instanceof Boolean)) {
            // TODO: improve the system of exceptions
            throw new RuntimeException(PREFIX + " must return a boolean value instead of {" + result + "}");
        }

        return (boolean) result;
    }

    private Object evaluate(String script, Object value) {
        try {
            String expectedValue = script.replaceFirst(PREFIX, "");
            engine.put("value", value);
            return engine.eval(expectedValue);
        } catch (Throwable e) {
            log.error("JS engine script evaluation error", e);
            // TODO: improve the system of exceptions
            throw new RuntimeException("JS engine evaluate error", e);
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
