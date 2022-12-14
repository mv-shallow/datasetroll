package com.jupitertools.datasetroll.tools;

import com.jupitertools.datasetroll.expect.match.MatchField;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mikhail Boyandin
 *
 * Created on 15.12.2022
 */
public class MatchSettingsUtils {
    /**
     * <p>
     * Pattern for field with optional settings
     * </p>
     * <p>
     * Examples:
     *     <ul>
     *         <li>"foo" maps to fieldName="foo" and settings=null</li>
     *         <li>"foo->" maps to fieldName=foo and settings=null</li>
     *         <li>"foo->ignoreCase" maps to fieldName="foo" and settings="ignoreCase"</li>
     *         <li>"foo->ignoreCase,ignoreOrder" maps to fieldName="foo" and settings="ignoreCase,ignoreOrder"</li>
     *     </ul>
     * </p>
     */
    private static final Pattern pattern = Pattern.compile("^(?<fieldName>.+?(?:(?=->)|$))(?:->(?<settings>.*))?$");


    public static Matcher matchForNameWithSettings(String value) {
        Matcher matcher = pattern.matcher(value);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Document field must not be blank");
        }

        return matcher;
    }


    /**
     * recursively extracts settings from fields
     */
    public static List<String> getSettings(Map<String, Object> stringObjectMap) {
        return getSettingsFromMap(null, stringObjectMap);
    }

    private static List<String> getSettingsFromMap(String baseKey, Map<String, Object> stringObjectMap) {
        List<String> fieldSettings = new ArrayList<>();

        String prefix = baseKey == null ? "" : baseKey + ".";

        stringObjectMap.forEach((key, value) -> {
            MatchField matchField = MatchField.fromString(key);

            String settingsPath = prefix + matchField.getName();

            if (matchField.hasSettings()) {
                fieldSettings.add(settingsPath + ": " + matchField.getSettings());
            }

            List<String> valueSettings = new ArrayList<>();

            if (value instanceof Collection) {
                valueSettings = getSettingsFromCollection(settingsPath, (Collection<Object>) value);
            }

            if (value instanceof Map) {
                valueSettings = getSettingsFromMap(settingsPath, (Map<String, Object>) value);
            }

            fieldSettings.addAll(valueSettings);
        });

        return fieldSettings;
    }

    private static List<String> getSettingsFromCollection(String baseKey, Collection<Object> objectCollection) {
        List<String> fieldSettings = new ArrayList<>();

        int i = 0;
        for (Object value : objectCollection) {
            List<String> valueSettings = new ArrayList<>();

            if (value instanceof Map) {
                valueSettings = getSettingsFromMap(baseKey + "[" + i + "]", (Map<String, Object>) value);
            }

            fieldSettings.addAll(valueSettings);

            i++;
        }

        return fieldSettings;
    }
}
