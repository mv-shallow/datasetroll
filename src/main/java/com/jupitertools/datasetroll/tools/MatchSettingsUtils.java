package com.jupitertools.datasetroll.tools;

import com.jupitertools.datasetroll.MatchElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
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

    // alt (?<fieldName>.+?)(\{(?<settings>.*?)\}|$)
    private static final Pattern pattern = Pattern.compile("^(?<fieldName>.+?)(?:\\{(?<settings>.*)\\})?$");


    public static Matcher matcherForNameWithSettings(String value) {
        Matcher matcher = pattern.matcher(value);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Document field must not be blank");
        }

        return matcher;
    }


    /**
     * recursively extracts settings from fields
     */
    public static List<String> getSettings(MatchElement stringObjectMap) {
        return getSettingsFromMap(null, stringObjectMap);
    }

    private static List<String> getSettingsFromMap(String baseKey, MatchElement stringObjectMap) {
        String prefix = baseKey == null ? "" : baseKey + ".";

        List<String> settings = new ArrayList<>();

        stringObjectMap.getFieldValueMap().forEach((key, value) -> {
            String settingsPath = prefix + key;


            Set<String> fieldSettings = stringObjectMap.getFieldMatchSettingsMap()
                                                       .get(key);

            if (!fieldSettings.isEmpty()) {
                settings.add(settingsPath + ": " + fieldSettings);
            }

            List<String> valueSettings = new ArrayList<>();

            if (value instanceof Collection) {
                valueSettings = getSettingsFromCollection(settingsPath, (Collection<Object>) value);
            }

            if (value instanceof MatchElement) {
                valueSettings = getSettingsFromMap(settingsPath, (MatchElement) value);
            }

            settings.addAll(valueSettings);
        });

        return settings;
    }

    private static List<String> getSettingsFromCollection(String baseKey, Collection<Object> objectCollection) {
        List<String> fieldSettings = new ArrayList<>();

        int i = 0;
        for (Object value : objectCollection) {
            List<String> valueSettings = new ArrayList<>();

            if (value instanceof MatchElement) {
                valueSettings = getSettingsFromMap(baseKey + "[" + i + "]", (MatchElement) value);
            }

            fieldSettings.addAll(valueSettings);

            i++;
        }

        return fieldSettings;
    }
}
