package com.jupitertools.datasetroll;

import com.jupitertools.datasetroll.expect.match.MatchField;

import java.util.*;

/**
 * @author Mikhail Boyandin
 *
 * Created on 13.01.2023
 */
public class MatchingUtils {

    public static Map<String, Object> extractData(Map<String, Object> original) {
        Map<String, Object> result = new HashMap<>();

        original.forEach((k, v) -> {
            Object value = v;

            if (v instanceof MatchElement) {
                v = extractData(((MatchElement) v).getFieldValueMap());
            }

            if (v instanceof Collection) {
                v = ext((Collection<Object>) v);
            }

            result.put(k, value);
        });

        return result;
    }

    private static Collection<Object> ext(Collection<Object> collection) {
        List<Object> convertedCollection = new ArrayList<>();

        for (Object e : collection) {
            Object element = e;

            if (e instanceof Map) {
                element = extractData((Map<String, Object>) e);
            }

            if (e instanceof Collection) {
                element = ext((Collection<Object>) e);
            }

            convertedCollection.add(element);
        }

        return convertedCollection;
    }

    @SuppressWarnings("unchecked")
    public static MatchElement toMatchElement(Map<String, Object> map) {
        Map<String, Set<String>> fieldMatchSettingsMap = new HashMap<>();

        Map<String, Object> fieldValueMap = new HashMap<>();

        map.forEach((k, v) -> {
            MatchField matchField = MatchField.fromString(k);

            String key = matchField.getName();
            Object value = v;

            fieldMatchSettingsMap.put(matchField.getName(), matchField.getSettings());

            if (v instanceof Map) {
                value = toMatchElement((Map<String, Object>) value);
            }

            if (value instanceof Collection) {
                value = convertCollection((Collection<Object>) value);
            }

            fieldValueMap.put(key, value);
        });

        return new MatchElement(fieldMatchSettingsMap, fieldValueMap);
    }

    @SuppressWarnings("unchecked")
    private static Collection<Object> convertCollection(Collection<Object> collection) {
        List<Object> convertedCollection = new ArrayList<>();

        for (Object e : collection) {
            Object element = e;

            if (e instanceof Map) {
                element = toMatchElement((Map<String, Object>) e);
            }

            if (e instanceof Collection) {
                element = convertCollection((Collection<Object>) e);
            }

            convertedCollection.add(element);
        }

        return convertedCollection;
    }
}
