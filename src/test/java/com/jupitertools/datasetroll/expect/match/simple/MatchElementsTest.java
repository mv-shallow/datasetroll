package com.jupitertools.datasetroll.expect.match.simple;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.jupitertools.datasetroll.MatchElement;
import com.jupitertools.datasetroll.expect.match.MatchAny;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Mikhail Boyandin
 *
 * Created on 18.11.2022
 */
public class MatchElementsTest {

    private final MatchAny matchAny = mock(MatchAny.class);

    private final MatchElements matchElements = new MatchElements(matchAny);

    @Test
    void matchWithoutSettings() {
        //Arrange
        MatchElement original = MatchElement.builder()
                                            .fieldMatchSettingsMap(ImmutableMap.of("key", ImmutableSet.of()))
                                            .fieldValueMap(ImmutableMap.of("key", "value"))
                                            .build();

        MatchElement expected = MatchElement.builder()
                                            .fieldMatchSettingsMap(ImmutableMap.of("key", ImmutableSet.of()))
                                            .fieldValueMap(ImmutableMap.of("key", "value"))
                                            .build();

        when(matchAny.match(any(), any(), any())).thenReturn(true);

        //Act
        boolean match = matchElements.match(original, expected);

        //Assert
        assertThat(match).isTrue();

        verify(matchAny).match("value", "value", ImmutableSet.of());
    }

    @Test
    void matchWithSettings() {
        //Arrange
        MatchElement original = MatchElement.builder()
                                            .fieldMatchSettingsMap(ImmutableMap.of("key", ImmutableSet.of()))
                                            .fieldValueMap(ImmutableMap.of("key", "value"))
                                            .build();

        MatchElement expected = MatchElement.builder()
                                            .fieldMatchSettingsMap(ImmutableMap.of("key", ImmutableSet.of("someSetting")))
                                            .fieldValueMap(ImmutableMap.of("key", "value"))
                                            .build();

        when(matchAny.match(any(), any(), any())).thenReturn(true);

        //Act
        boolean match = matchElements.match(original, expected);

        //Assert
        assertThat(match).isTrue();

        verify(matchAny).match("value", "value", ImmutableSet.of("someSetting"));
    }
}
