package com.jupitertools.datasetroll.expect.match;

import com.google.common.collect.ImmutableSet;
import com.jupitertools.datasetroll.tools.MatchSettingsUtils;

import java.util.Set;
import java.util.regex.Matcher;

/**
 * @author Mikhail Boyandin
 *
 * Created on 18.11.2022
 */
public class MatchField {

    private final String name;

    private final Set<String> settings;

    public MatchField(String name, Set<String> settings) {
        this.name = name;
        this.settings = settings;
    }

    public String getName() {
        return name;
    }

    public Set<String> getSettings() {
        return settings;
    }

    public boolean hasSettings() {
        return !settings.isEmpty();
    }

    public static MatchField fromString(String unformattedName) {
        Matcher matcher = MatchSettingsUtils.matchForNameWithSettings(unformattedName);

        String fieldName = matcher.group("fieldName");
        String settings = matcher.group("settings");

        Set<String> settingsSet =
                settings != null && !settings.isEmpty() ? ImmutableSet.copyOf(settings.trim().split("\\W"))
                                                        : ImmutableSet.of();

        return new MatchField(fieldName, settingsSet);
    }
}
