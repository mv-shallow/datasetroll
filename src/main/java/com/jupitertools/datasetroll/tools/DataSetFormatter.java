package com.jupitertools.datasetroll.tools;

import com.jupitertools.datasetroll.expect.match.MatchField;

import java.util.*;

/**
 * @author Mikhail Boyandin
 *
 * Created on 15.12.2022
 */
public class DataSetFormatter {

    /**
     * recursively constructs copy of input map without settings in fields
     */
    public static Map<String, Object> removeMatchSettingsFromMap(Map<String, Object> stringObjectMap) {
        Map<String, Object> formattedMap = new HashMap<>();

        stringObjectMap.forEach((key, value) -> {
            MatchField matchField = MatchField.fromString(key);

            Object formattedValue = value;

            if (value instanceof Collection) {
                formattedValue = removeMatchSettingsFromCollection((Collection<Object>) value);
            }

            if (value instanceof Map) {
                formattedValue = removeMatchSettingsFromMap((Map<String, Object>) value);
            }

            formattedMap.put(matchField.getName(), formattedValue);
        });

        return formattedMap;
    }

    private static List<Object> removeMatchSettingsFromCollection(Collection<Object> objectCollection) {
        List<Object> formattedCollection = new ArrayList<>();

        for (Object value : objectCollection) {
            Object formattedValue = value;

            if (value instanceof Map) {
                formattedValue = removeMatchSettingsFromMap((Map<String, Object>) value);
            }

            formattedCollection.add(formattedValue);
        }

        return formattedCollection;
    }
}
