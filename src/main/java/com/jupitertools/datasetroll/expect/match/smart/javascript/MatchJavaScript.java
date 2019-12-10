package com.jupitertools.datasetroll.expect.match.smart.javascript;


import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.jupitertools.datasetroll.expect.match.smart.MatchDataSmart;


/**
 * Created on 22.12.2018.
 *
 * @author Korovin Anatoliy
 */
public class MatchJavaScript implements MatchDataSmart {

    private static final String PREFIX = "js-match:";
    private final ScriptEngine engine;


    public MatchJavaScript() {
        ScriptEngineManager factory = new ScriptEngineManager();
        engine = factory.getEngineByName("js");
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

    private Object evaluate(String script, Object value){
        try {
            String expectedValue = script.replaceFirst(PREFIX, "");
            engine.put("value", value);
            return engine.eval(expectedValue);
        } catch (Throwable e) {
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
