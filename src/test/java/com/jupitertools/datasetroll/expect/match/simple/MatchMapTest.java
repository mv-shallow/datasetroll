package com.jupitertools.datasetroll.expect.match.simple;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.jupitertools.datasetroll.expect.match.MatchAny;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Mikhail Boyandin
 *
 * Created on 18.11.2022
 */
public class MatchMapTest {

    private final MatchAny matchAny = mock(MatchAny.class);

    private final MatchMap matchMap = new MatchMap(matchAny);

    @Test
    void matchWithoutSettings() {
        //Arrange
        Map<String, String> original = ImmutableMap.of("key", "value");
        Map<String, String> expected = ImmutableMap.of("key", "value");

        when(matchAny.match(any(), any(), any())).thenReturn(true);

        //Act
        boolean match = matchMap.match(original, expected);

        //Assert
        assertThat(match).isTrue();

        verify(matchAny).match("value", "value", ImmutableSet.of());
    }

    @Test
    void matchWithSettings() {
        //Arrange
        Map<String, String> original = ImmutableMap.of("key", "value");
        Map<String, String> expected = ImmutableMap.of("key->someSetting", "value");

        when(matchAny.match(any(), any(), any())).thenReturn(true);

        //Act
        boolean match = matchMap.match(original, expected);

        //Assert
        assertThat(match).isTrue();

        verify(matchAny).match("value", "value", ImmutableSet.of("someSetting"));
    }
}
