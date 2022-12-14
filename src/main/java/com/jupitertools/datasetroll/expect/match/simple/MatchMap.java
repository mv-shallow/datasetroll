package com.jupitertools.datasetroll.expect.match.simple;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupitertools.datasetroll.expect.match.MatchAny;
import com.jupitertools.datasetroll.expect.match.MatchData;
import com.jupitertools.datasetroll.expect.match.MatchField;

import java.util.Map;

/**
 * Created on 18.12.2018.
 *
 * Match two {@link Map} objects
 *
 * @author Korovin Anatoliy
 */
public class MatchMap implements MatchData {

    private final ObjectMapper objectMapper;
    private final MatchAny matchAny;

    public MatchMap(MatchAny matchAny) {
        this.matchAny = matchAny;

        this.objectMapper = new ObjectMapper();
    }

    @Override
    public boolean match(Object original, Object expected) {

        Map<String, Object> originalMap = convertToMap(original);
        Map<String, Object> expectedMap = convertToMap(expected);

        for (Map.Entry<String, Object> expectedEntry : expectedMap.entrySet()) {
            MatchField field = MatchField.fromString(expectedEntry.getKey());

            Object expectedValue = expectedEntry.getValue();
            Object originalValue = originalMap.get(field.getName());

            if (!matchAny.match(originalValue, expectedValue, field.getSettings())) {
                return false;
            }
        }

        return true;
    }

    private Map<String, Object> convertToMap(Object object) {
        return objectMapper.convertValue(object, Map.class);
    }
}
