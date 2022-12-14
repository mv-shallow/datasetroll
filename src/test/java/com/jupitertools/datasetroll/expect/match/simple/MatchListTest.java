package com.jupitertools.datasetroll.expect.match.simple;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.jupitertools.datasetroll.expect.match.MatchAny;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Mikhail Boyandin
 *
 * Created on 18.11.2022
 */
class MatchListTest {

    private final MatchAny matchAny = mock(MatchAny.class);
    private final ObjectMapper objectMapper = mock(ObjectMapper.class);

    private final MatchList matchUnordered = new MatchList(matchAny, objectMapper);

    private final Object original = mock(Object.class);
    private final Object expected = mock(Object.class);


    @BeforeEach
    void setup() {
        when(matchAny.match(1, 1)).thenReturn(true);
        when(matchAny.match(2, 2)).thenReturn(true);
        when(matchAny.match(3, 3)).thenReturn(true);
    }

    @Nested
    class MatchWithOrder {
        private final Set<String> settings = ImmutableSet.of();

        @Test
        void match() {
            //Arrange
            mockObjectMapper(original, Lists.newArrayList(1, 2, 3));
            mockObjectMapper(expected, Lists.newArrayList(1, 2, 3));

            //Act
            boolean match = matchUnordered.match(original, expected, settings);

            //Assert
            assertThat(match).isTrue();

            InOrder inOrder = Mockito.inOrder(matchAny);

            inOrder.verify(matchAny).match(1, 1);
            inOrder.verify(matchAny).match(2, 2);
            inOrder.verify(matchAny).match(3, 3);

            verifyNoMoreInteractions(matchAny);
        }

        @Test
        void matchDifferentSize() {
            //Arrange
            mockObjectMapper(original, Lists.newArrayList(1, 2, 3, 4));
            mockObjectMapper(expected, Lists.newArrayList(1, 2, 3));

            //Act
            boolean match = matchUnordered.match(original, expected, settings);

            //Assert
            assertThat(match).isFalse();

            verifyNoInteractions(matchAny);
        }

        @Test
        void matchInvalidOrder() {
            //Arrange
            mockObjectMapper(original, Lists.newArrayList(1, 3, 2));
            mockObjectMapper(expected, Lists.newArrayList(1, 2, 3));

            //Act
            boolean match = matchUnordered.match(original, expected, settings);

            //Assert
            assertThat(match).isFalse();

            InOrder inOrder = Mockito.inOrder(matchAny);

            inOrder.verify(matchAny).match(1, 1);
            inOrder.verify(matchAny).match(3, 2);

            verifyNoMoreInteractions(matchAny);
        }
    }

    @Nested
    class MatchIgnoreOrder {
        private final Set<String> settings = ImmutableSet.of("ignoreOrder");

        @Test
        void match() {
            //Arrange
            mockObjectMapper(original, Lists.newArrayList(1, 2, 3));
            mockObjectMapper(expected, Lists.newArrayList(3, 1, 2));

            //Act
            boolean match = matchUnordered.match(original, expected, settings);

            //Assert
            assertThat(match).isTrue();

            InOrder inOrder = Mockito.inOrder(matchAny);

            inOrder.verify(matchAny).match(1, 3);
            inOrder.verify(matchAny).match(1, 1);
            inOrder.verify(matchAny).match(2, 3);
            inOrder.verify(matchAny).match(2, 2);
            inOrder.verify(matchAny).match(3, 3);

            verifyNoMoreInteractions(matchAny);
        }

        @Test
        void matchDifferentSize() {
            //Arrange
            mockObjectMapper(original, Lists.newArrayList(1, 2, 3, 4));
            mockObjectMapper(expected, Lists.newArrayList(1, 2, 3));

            //Act
            boolean match = matchUnordered.match(original, expected, settings);

            //Assert
            assertThat(match).isFalse();

            verifyNoInteractions(matchAny);
        }

        @Test
        void matchNotExpectedOriginal() {
            //Arrange
            mockObjectMapper(original, Lists.newArrayList(1, 4, 3));
            mockObjectMapper(expected, Lists.newArrayList(3, 1, 2));

            //Act
            boolean match = matchUnordered.match(original, expected, settings);

            //Assert
            assertThat(match).isFalse();

            InOrder inOrder = Mockito.inOrder(matchAny);

            inOrder.verify(matchAny).match(1, 3);
            inOrder.verify(matchAny).match(1, 1);
            inOrder.verify(matchAny).match(4, 3);
            inOrder.verify(matchAny).match(4, 2);

            verifyNoMoreInteractions(matchAny);
        }

        @Test
        void matchNotFoundExpected() {
            //Arrange
            mockObjectMapper(original, Lists.newArrayList(1, 2, 3));
            mockObjectMapper(expected, Lists.newArrayList(3, 1, 4));

            //Act
            boolean match = matchUnordered.match(original, expected, settings);

            //Assert
            assertThat(match).isFalse();

            InOrder inOrder = Mockito.inOrder(matchAny);

            inOrder.verify(matchAny).match(1, 3);
            inOrder.verify(matchAny).match(1, 1);
            inOrder.verify(matchAny).match(2, 3);
            inOrder.verify(matchAny).match(2, 4);

            verifyNoMoreInteractions(matchAny);
        }
    }

    private void mockObjectMapper(Object valueToConvert, List<Integer> convertedValue) {
        when(objectMapper.convertValue(valueToConvert, List.class)).thenReturn(convertedValue);
    }
}