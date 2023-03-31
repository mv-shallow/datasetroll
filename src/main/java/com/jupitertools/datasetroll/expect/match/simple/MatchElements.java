package com.jupitertools.datasetroll.expect.match.simple;

import com.jupitertools.datasetroll.MatchElement;
import com.jupitertools.datasetroll.expect.match.MatchAny;
import com.jupitertools.datasetroll.expect.match.MatchData;

import java.util.Map;
import java.util.Set;

/**
 * Created on 18.12.2018.
 *
 * Match two {@link MatchElement} objects
 *
 * @author Korovin Anatoliy
 */
public class MatchElements implements MatchData {
    private final MatchAny matchAny;

    public MatchElements(MatchAny matchAny) {
        this.matchAny = matchAny;
    }

    @Override
    public boolean match(Object actual, Object expected) {

        Map<String, Object> actualMap = ((MatchElement) actual).getFieldValueMap();
        Map<String, Object> expectedMap = ((MatchElement) expected).getFieldValueMap();

        Map<String, Set<String>> fieldMatchSettingsMap = ((MatchElement) expected).getFieldMatchSettingsMap();

        for (Map.Entry<String, Object> expectedEntry : expectedMap.entrySet()) {
            String key = expectedEntry.getKey();
            Set<String> matchSettings = fieldMatchSettingsMap.get(key);

            Object expectedValue = expectedEntry.getValue();
            Object originalValue = actualMap.get(key);

            if (!matchAny.match(originalValue, expectedValue, matchSettings)) {
                return false;
            }
        }

        return true;
    }
}
