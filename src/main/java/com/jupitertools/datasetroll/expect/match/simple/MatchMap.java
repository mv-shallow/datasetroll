package com.jupitertools.datasetroll.expect.match.simple;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupitertools.datasetroll.expect.match.MatchAny;
import com.jupitertools.datasetroll.expect.match.MatchData;

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

    public MatchMap() {
        objectMapper = new ObjectMapper();
        matchAny = new MatchAny();
    }

    @Override
    public boolean match(Object original, Object expected) {

        Map<String, Object> originalMap = convertToMap(original);
        Map<String, Object> expectedMap = convertToMap(expected);

        for (Map.Entry<String, Object> expectedEntry : expectedMap.entrySet()) {

            Object expectedValue = expectedEntry.getValue();
            Object originValue = originalMap.get(expectedEntry.getKey());

            if (!matchAny.match(originValue, expectedValue)) {
                return false;
            }
        }

        return true;
    }

    private Map<String, Object> convertToMap(Object object) {
        return objectMapper.convertValue(object, Map.class);
    }
}
