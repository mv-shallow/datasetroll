package com.jupitertools.datasetroll;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Mikhail Boyandin
 *
 * Created on 13.01.2023
 */
class MatchingUtilsTest {

    @Test
    void toMatchElement() {
        Map<String, Object> linkedMap = new LinkedHashMap<>();
        linkedMap.put("fieldB", "BBB");
        linkedMap.put("fieldC{ignoreOrder}", ImmutableList.of("C", "A", "B"));
        linkedMap.put("fieldE", ImmutableMap.of("fieldF", "F",
                                                "fieldG", "G"));

        ImmutableMap<String, Object> stringObjectMap = ImmutableMap.copyOf(linkedMap);

        MatchElement matchElement = MatchingUtils.toMatchElement(stringObjectMap);

        MatchElement nestedExpected = MatchElement.builder()
                                                  .fieldMatchSettingsMap(ImmutableMap.of("fieldF", ImmutableSet.of(),
                                                                                         "fieldG", ImmutableSet.of()))
                                                  .fieldValueMap(ImmutableMap.of("fieldF", "F",
                                                                                 "fieldG", "G"))
                                                  .build();

        MatchElement expected = MatchElement.builder()
                                            .fieldMatchSettingsMap(ImmutableMap.of("fieldB", ImmutableSet.of(),
                                                                                   "fieldC", ImmutableSet.of("ignoreOrder"),
                                                                                   "fieldE", ImmutableSet.of()))
                                            .fieldValueMap(ImmutableMap.of("fieldB", "BBB",
                                                                           "fieldC", ImmutableList.of("C", "A", "B"),
                                                                           "fieldE", nestedExpected))
                                            .build();

        AssertionsForClassTypes.assertThat(matchElement).isEqualToComparingFieldByFieldRecursively(expected);
    }
}