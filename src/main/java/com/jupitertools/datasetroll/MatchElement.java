package com.jupitertools.datasetroll;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Mikhail Boyandin
 *
 * Created on 13.01.2023
 */
public class MatchElement {

    private Map<String, Set<String>> fieldMatchSettingsMap;

    private Map<String, Object> fieldValueMap;

    public MatchElement(Map<String, Set<String>> fieldMatchSettingsMap,
                        Map<String, Object> fieldValueMap) {
        this.fieldMatchSettingsMap = fieldMatchSettingsMap;
        this.fieldValueMap = fieldValueMap;
    }

    public Map<String, Object> getBaseMap() {
        Map<String, Object> result = new HashMap<>();

        fieldValueMap.forEach((key, value) -> {
            Object val = value;

            if (value instanceof MatchElement) {
                val = ((MatchElement) value).getBaseMap();
            }

            result.put(key, value);
        });

        return result;
    }

    public Map<String, Set<String>> getFieldMatchSettingsMap() {
        return fieldMatchSettingsMap;
    }

    public Map<String, Object> getFieldValueMap() {
        return fieldValueMap;
    }

    public static MatchElementBuilder builder() {
        return new MatchElementBuilder();
    }

    public static class MatchElementBuilder {
        private Map<String, Set<String>> fieldMatchSettingsMap;
        private Map<String, Object> fieldValueMap;

        MatchElementBuilder() {
        }

        public MatchElementBuilder fieldMatchSettingsMap(final Map<String, Set<String>> fieldMatchSettingsMap) {
            this.fieldMatchSettingsMap = fieldMatchSettingsMap;
            return this;
        }

        public MatchElementBuilder fieldValueMap(final Map<String, Object> fieldValueMap) {
            this.fieldValueMap = fieldValueMap;
            return this;
        }

        public MatchElement build() {
            return new MatchElement(this.fieldMatchSettingsMap, this.fieldValueMap);
        }

        public String toString() {
            return "MatchElement.MatchElementBuilder(fieldMatchSettingsMap=" + this.fieldMatchSettingsMap + ", fieldValueMap=" + this.fieldValueMap + ")";
        }
    }
}
