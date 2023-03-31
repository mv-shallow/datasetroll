package com.jupitertools.datasetroll.expect.match;

import com.google.common.collect.ImmutableSet;
import com.jupitertools.datasetroll.tools.MatchSettingsUtils;

import java.util.Objects;
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

    public static MatchField fromString(String field) {
        Matcher matcher = MatchSettingsUtils.matcherForNameWithSettings(field);

        String fieldName = matcher.group("fieldName");
        String settings = matcher.group("settings");

        Set<String> settingsSet =
                settings != null && !settings.isEmpty() ? ImmutableSet.copyOf(settings.trim().split("\\W"))
                                                        : ImmutableSet.of();

        return new MatchField(fieldName, settingsSet);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchField that = (MatchField) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
