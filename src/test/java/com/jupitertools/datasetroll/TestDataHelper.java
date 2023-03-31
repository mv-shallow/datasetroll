package com.jupitertools.datasetroll;

import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Mikhail Boyandin
 *
 * Created on 13.01.2023
 */
public class TestDataHelper {

    @SafeVarargs
    public static List<MatchElement> toMatchElements(
            Map<String, Object>... maps) {
        return Arrays.stream(maps)
                     .map(MatchingUtils::toMatchElement)
                     .collect(Collectors.toList());
    }

    public static MatchElement toMatchElement(Map<String, Object> map) {
        return MatchingUtils.toMatchElement(map);
    }

    public static MatchElement toMatchElement(String k1, Object v1) {
        return MatchingUtils.toMatchElement(ImmutableMap.of(k1, v1));
    }

    public static MatchElement toMatchElement(String k1, Object v1, String k2, Object v2) {
        return MatchingUtils.toMatchElement(ImmutableMap.of(k1, v1, k2, v2));
    }

    public static MatchElement toMatchElement(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
        return MatchingUtils.toMatchElement(ImmutableMap.of(k1, v1, k2, v2, k3, v3));
    }
}
