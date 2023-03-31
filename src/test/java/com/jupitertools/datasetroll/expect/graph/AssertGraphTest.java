package com.jupitertools.datasetroll.expect.graph;

import com.google.common.collect.ImmutableMap;
import com.jupitertools.datasetroll.MatchElement;
import com.jupitertools.datasetroll.TestDataHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 10.12.2018.
 *
 * @author Korovin Anatoliy
 */
class AssertGraphTest {

    @Test
    void validGraph() {
        // Arrange
        boolean[][] graph = {
                // @formatter:off
                {true, false, false, false, false},
                {false, true, false, false, false},
                {false, false, false, false, true},
                {false, false, false, true, false},
                {false, false, true, false, false}
                // @formatter:on
        };
        // Act & Assert
        Assertions.assertDoesNotThrow(() -> {
            new AssertGraph(new TestGraph(() -> graph)).doAssert();
        });
    }

    @Test
    void graphWithOneUnusedEntity() {
        // Arrange
        boolean[][] graph = {
                // @formatter:off
                {true, false, false, false, false},
                {false, true, false, false, false},
                {false, false, false, false, false},
                {false, false, false, true, false},
                {false, false, true, false, false}
                // @formatter:on
        };
        List<MatchElement> data = TestDataHelper.toMatchElements(
                ImmutableMap.of("id", "1", "value", "BAR-1"),
                ImmutableMap.of("id", "2", "value", "BAR-2"),
                ImmutableMap.of("id", "3", "value", "BAR-3"),
                ImmutableMap.of("id", "4", "value", "BAR-4"),
                ImmutableMap.of("id", "5", "value", "BAR-5"));
        List<MatchElement> patterns = TestDataHelper.toMatchElements(
                ImmutableMap.of("value", "BAR-1"),
                ImmutableMap.of("value", "BAR-2"),
                ImmutableMap.of("value", "BAR-5"),
                ImmutableMap.of("value", "BAR-4"),
                ImmutableMap.of("value", "FOO-xxx"));
        // Act & Assert
        Error error = Assertions.assertThrows(Error.class, () -> {
            new AssertGraph(new TestGraph(() -> graph, data, patterns)).doAssert();
        });
        System.out.println(error);
        new Printer(new TestGraph(() -> graph)).print();
    }

    @Test
    void graphWithOneUnmatchedEntity() {
        // Arrange
        boolean[][] graph = {
                // @formatter:off
                {true, false, false, false, false},
                {false, true, false, false, false},
                {false, false, false, false, true},
                {false, false, false, true, false},
                {false, false, false, false, false}
                // @formatter:on
        };
        List<MatchElement> data = TestDataHelper.toMatchElements(
                ImmutableMap.of("id", "1", "value", "BAR-1"),
                ImmutableMap.of("id", "2", "value", "BAR-2"),
                ImmutableMap.of("id", "3", "value", "BAR-3"),
                ImmutableMap.of("id", "4", "value", "BAR-4"),
                ImmutableMap.of("id", "5", "value", "BAR-5"));
        List<MatchElement> patterns = TestDataHelper.toMatchElements(
                ImmutableMap.of("value", "BAR-1"),
                ImmutableMap.of("value", "BAR-2"),
                ImmutableMap.of("value", "BAR-???"),
                ImmutableMap.of("value", "BAR-4"),
                ImmutableMap.of("value", "BAR-3"));
        // Act & Assert
        Error error = Assertions.assertThrows(Error.class, () -> {
            new AssertGraph(new TestGraph(() -> graph, data, patterns)).doAssert();
        });
        System.out.println(error);
    }


    @Nested
    @DisplayName("Serialization problems for building error texts")
    class TestCornerCaseWithAProblemOfMapping {

        @Test
        void testWithWrongData() {
            // Arrange
            boolean[][] graph = {
                    // @formatter:off
                    {true, false},
                    {false, false},
                    // @formatter:on
            };
            List<MatchElement> data = TestDataHelper.toMatchElements(
                    ImmutableMap.of("id", new ObjectWithPrivateFields()),
                    ImmutableMap.of("id", new ObjectWithPrivateFields()));

            List<MatchElement> patterns = TestDataHelper.toMatchElements(
                    ImmutableMap.of("value", "BAR-1"),
                    ImmutableMap.of("value", "BAR-2"));

            Exception error = Assertions.assertThrows(RuntimeException.class, () -> {
                // Act
                new AssertGraph(new TestGraph(() -> graph, data, patterns)).doAssert();
            });
            // Assert
            assertThat(error.getCause().getMessage()).contains("No serializer found for class");
        }

        /**
         * this class unable to serialize through Jackson (by default),
         * because has private field.
         */
        private class ObjectWithPrivateFields {
            private int a = 123;
        }
    }
}