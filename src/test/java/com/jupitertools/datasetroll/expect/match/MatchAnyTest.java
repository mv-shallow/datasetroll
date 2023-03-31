package com.jupitertools.datasetroll.expect.match;

import com.google.common.collect.ImmutableMap;
import com.jupitertools.datasetroll.MatchElement;
import com.jupitertools.datasetroll.TestDataHelper;
import com.jupitertools.datasetroll.expect.TestData;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 18.12.2018.
 *
 * @author Korovin Anatoliy
 */
class MatchAnyTest {

    @Nested
    class SimpleTests {

        @Test
        void matchWithTheSame() {
            // Arrange
            MatchElement original = TestDataHelper.toMatchElement("id", "1", "data", "data-101");
            MatchElement same = TestDataHelper.toMatchElement("id", "1", "data", "data-101");

            // Act & Asserts
            assertThat(new MatchAny().match(original, same)).isTrue();
        }

        @Test
        void matchWithThePartialSame() {
            // Arrange
            MatchElement original = TestDataHelper.toMatchElement("id", "1", "data", "data-101");
            MatchElement same = TestDataHelper.toMatchElement("data", "data-101");
            // Act & Asserts
            assertThat(new MatchAny().match(original, same)).isTrue();
        }

        @Test
        void matchWithTheDifferent() {
            // Arrange
            MatchElement original = TestDataHelper.toMatchElement("1", "data-101");
            MatchElement same = TestDataHelper.toMatchElement("data", "not same");
            // Act & Asserts
            assertThat(new MatchAny().match(original, same)).isFalse();
        }

        @Test
        void matchWithTheDifferentByOneField() {
            // Arrange
            MatchElement original = TestDataHelper.toMatchElement("1", "data-101");
            MatchElement same = TestDataHelper.toMatchElement("id", "1",
                                                              "data", "not same");
            // Act & Asserts
            assertThat(new MatchAny().match(original, same)).isFalse();
        }

        @Test
        void matchWithAnotherStructuredObject() {
            // Arrange
            MatchElement original = TestDataHelper.toMatchElement("1", "data-101");
            MatchElement same = TestDataHelper.toMatchElement("id", "1",
                                                              "data", "data-101",
                                                              "field", "not_exists_in_origin");
            // Act & Asserts
            assertThat(new MatchAny().match(original, same)).isFalse();
        }

        @Test
        void matchWithDifferentTypesOfField() {
            // Arrange
            MatchElement original = TestDataHelper.toMatchElement("1", "1100");
            MatchElement notSame = TestDataHelper.toMatchElement("data", 1100);
            // Act & Asserts
            assertThat(new MatchAny().match(original, notSame)).isFalse();
        }
    }

    @Nested
    class NestedObjectTests {

        @Test
        void matchWithNested() {
            MatchElement fooBar = TestDataHelper.toMatchElement("id", "1A",
                                                                "data", "FooBar",
                                                                "bar", TestDataHelper.toMatchElement("id", "1B",
                                                                                                     "data", "Bar-404"));

            MatchElement same = TestDataHelper.toMatchElement("id", "1A",
                                                              "data", "FooBar",
                                                              "bar", TestDataHelper.toMatchElement("id", "1B",
                                                                                                   "data", "Bar-404"));
            // Act
            MatchAny matcher = new MatchAny();
            // Asserts
            assertThat(matcher.match(fooBar, same)).isTrue();
        }

        @Test
        void matchWithNestedEntityWithoutOneField() {
            MatchElement fooBar = TestDataHelper.toMatchElement("id", "1A",
                                                                "data", "FooBar",
                                                                "bar", TestDataHelper.toMatchElement("id", "1B",
                                                                                                     "data", "Bar-404"));

            MatchElement same = TestDataHelper.toMatchElement("id", "1A",
                                                              "data", "FooBar",
                                                              "bar", TestDataHelper.toMatchElement("data", "Bar-404"));
            // Act & Asserts
            assertThat(new MatchAny().match(fooBar, same)).isTrue();
        }

        @Test
        void matchNotEqualsWithNestedEntityWithoutOneField() {
            MatchElement fooBar = TestDataHelper.toMatchElement("id", "1A",
                                                                "data", "FooBar",
                                                                "bar", TestDataHelper.toMatchElement("id", "1B",
                                                                                                     "data", "Bar-404"));

            MatchElement same = TestDataHelper.toMatchElement("id", "1A",
                                                              "data", "FooBar",
                                                              "bar", TestDataHelper.toMatchElement("data", "Bar-401"));
            // Act & Asserts
            assertThat(new MatchAny().match(fooBar, same)).isFalse();
        }

        @Test
        void matchEqualsWithDoubleLevelOfNestedEntity() {

            MatchElement secondNested1 = TestDataHelper.toMatchElement("second", "222",
                                                                       "unexpected", "wow");
            MatchElement firstNested1 = TestDataHelper.toMatchElement("first", "111",
                                                                      "secondNested", secondNested1);
            MatchElement bar1 = TestDataHelper.toMatchElement("id", "1B",
                                                              "data", "Bar",
                                                              "firstNested", firstNested1);

            MatchElement secondNested2 = TestDataHelper.toMatchElement("second", "222");
            MatchElement firstNested2 = TestDataHelper.toMatchElement("first", "111",
                                                                      "secondNested", secondNested2);
            MatchElement bar2 = TestDataHelper.toMatchElement("id", "1B",
                                                              "data", "Bar",
                                                              "firstNested", firstNested2);
            // Act & Asserts
            assertThat(new MatchAny().match(bar1, bar2)).isTrue();
        }

        @Test
        void matchNotEqualsWithDoubleLevelOfNestedEntity() {

            MatchElement secondNested1 = TestDataHelper.toMatchElement("second", "222");
            MatchElement firstNested1 = TestDataHelper.toMatchElement("first", "111",
                                                                      "secondNested", secondNested1);
            MatchElement bar1 = TestDataHelper.toMatchElement("id", "1B",
                                                              "data", "Bar",
                                                              "firstNested", firstNested1);

            MatchElement secondNested2 = TestDataHelper.toMatchElement("second", "BBB");
            MatchElement firstNested2 = TestDataHelper.toMatchElement("first", "111",
                                                                      "secondNested", secondNested2);
            MatchElement bar2 = TestDataHelper.toMatchElement("id", "1B",
                                                              "data", "Bar",
                                                              "firstNested", firstNested2);
            // Act & Asserts
            assertThat(new MatchAny().match(bar1, bar2)).isFalse();
        }

        @Test
        void matchNotEqualsWithDoubleLevelOfNestedEntityAndDifferentType() {

            MatchElement secondNested1 = TestDataHelper.toMatchElement("second", 222);
            MatchElement firstNested1 = TestDataHelper.toMatchElement("first", "111",
                                                                      "secondNested", secondNested1);
            MatchElement bar1 = TestDataHelper.toMatchElement("id", "1B",
                                                              "data", "Bar",
                                                              "firstNested", firstNested1);

            MatchElement secondNested2 = TestDataHelper.toMatchElement("second", 223);
            MatchElement firstNested2 = TestDataHelper.toMatchElement("first", "111",
                                                                      "secondNested", secondNested2);
            MatchElement bar2 = TestDataHelper.toMatchElement("id", "1B",
                                                              "data", "Bar",
                                                              "firstNested", firstNested2);
            // Act & Asserts
            assertThat(new MatchAny().match(bar1, bar2)).isFalse();
        }
    }

    @Nested
    class RegexTests {

        @Test
        void simpleRegularExpression() {
            // Arrange
            MatchElement original = TestDataHelper.toMatchElement("id", "1", "data", "data-101");
            MatchElement same = TestDataHelper.toMatchElement("id", "1", "data", "regex: ^data-...$");
            // Act & Asserts
            assertThat(new MatchAny().match(original, same)).isTrue();
        }

        @Test
        void uuidRegularExpression() {
            // Arrange
            String UUID_REGEXP = "regex: [a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89aAbB][a-f0-9]{3}-[a-f0-9]{12}";

            MatchElement original = TestDataHelper.toMatchElement("id", UUID.randomUUID(), "data", "data-101");
            MatchElement same = TestDataHelper.toMatchElement("id", UUID_REGEXP,
                                                              "data", "regex: ^data-...$");
            // Act & Asserts
            assertThat(new MatchAny().match(original, same)).isTrue();
        }

        @Test
        void uuidRegularExpressionNotMatch() {
            // Arrange
            String UUID_REGEXP = "regex: [a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89aAbB][a-f0-9]{3}-[a-f0-9]{12}";


            MatchElement original = TestDataHelper.toMatchElement("id", "123", "data", "data-101");
            MatchElement same = TestDataHelper.toMatchElement("id", UUID_REGEXP,
                                                              "data", "regex: ^data-...$");
            // Act & Asserts
            assertThat(new MatchAny().match(original, same)).isFalse();
        }
    }

    @Nested
    class ComplexTests {

        @Test
        void matchWithNullable() {

            MatchElement first =
                    TestDataHelper.toMatchElement(new TestData().read("/dataset/internal/expect/match_objects.json").get("test").get(0));

            MatchElement second =
                    TestDataHelper.toMatchElement(new TestData().read("/dataset/internal/expect/match_objects.json").get("test").get(1));

            assertThat(new MatchAny().match(first, second)).isTrue();
        }
    }

    @Nested
    class NestedMapTests {

        @Test
        void matchMap() {
            // Arrange
            String dataSetFilePath = "/dataset/internal/expect/match_objects.json";
            MatchElement first = TestDataHelper.toMatchElement(new TestData().read(dataSetFilePath).get("test").get(0));
            MatchElement second = TestDataHelper.toMatchElement(new TestData().read(dataSetFilePath).get("test").get(1));
            // Act & Asserts
            assertThat(new MatchAny().match(first, second)).isTrue();
        }
    }

    @Nested
    class NestedArraysTests {

        @Test
        void matchNestedArray() {
            // Arrange
            String dataSetFilePath = "/dataset/internal/expect/expect_with_nested_array.json";
            MatchElement first = TestDataHelper.toMatchElement(new TestData().read(dataSetFilePath).get("test").get(0));
            MatchElement second = TestDataHelper.toMatchElement(new TestData().read(dataSetFilePath).get("test").get(1));
            // Act
            assertThat(new MatchAny().match(first, second)).isTrue();
        }

        @Test
        void matchNestedArrayNotEquals() {
            // Arrange
            String dataSetFilePath = "/dataset/internal/expect/expect_with_nested_array.json";
            MatchElement first = TestDataHelper.toMatchElement(new TestData().read(dataSetFilePath).get("test").get(0));
            MatchElement second = TestDataHelper.toMatchElement(new TestData().read(dataSetFilePath).get("test").get(2));
            // Act
            assertThat(new MatchAny().match(first, second)).isFalse();
        }

        @Test
        void matchNestedArrayNotSameLength() {
            // Arrange
            String dataSetFilePath = "/dataset/internal/expect/expect_with_nested_array.json";
            MatchElement first = TestDataHelper.toMatchElement(new TestData().read(dataSetFilePath).get("test").get(0));
            MatchElement second = TestDataHelper.toMatchElement(new TestData().read(dataSetFilePath).get("test").get(3));
            // Act
            assertThat(new MatchAny().match(first, second)).isFalse();
        }

        @Test
        void matchArraysOfFloat() {
            // Arrange
            String dataSetFilePath = "/dataset/internal/expect/expect_with_nested_arrays_of_float.json";
            MatchElement first = TestDataHelper.toMatchElement(new TestData().read(dataSetFilePath).get("test").get(0));
            MatchElement second = TestDataHelper.toMatchElement(new TestData().read(dataSetFilePath).get("test").get(1));
            // Act
            assertThat(new MatchAny().match(first, second)).isTrue();
        }

        @Test
        void matchNotSameArraysOfFloat() {
            // Arrange
            String dataSetFilePath = "/dataset/internal/expect/expect_with_nested_arrays_of_float.json";
            MatchElement first = TestDataHelper.toMatchElement(new TestData().read(dataSetFilePath).get("test").get(0));
            MatchElement second = TestDataHelper.toMatchElement(new TestData().read(dataSetFilePath).get("test").get(2));
            // Act
            assertThat(new MatchAny().match(first, second)).isFalse();
        }

        @Test
        void matchNotSameArraysOfFloatWithSingleValue() {
            // Arrange
            String dataSetFilePath = "/dataset/internal/expect/expect_with_nested_arrays_of_float.json";
            MatchElement first = TestDataHelper.toMatchElement(new TestData().read(dataSetFilePath).get("test").get(4));
            MatchElement second = TestDataHelper.toMatchElement(new TestData().read(dataSetFilePath).get("test").get(5));
            // Act
            assertThat(new MatchAny().match(first, second)).isTrue();
        }
    }

    @Nested
    class DifferentTypes {

        @Test
        void intVsLong() {
            MatchElement intVal = TestDataHelper.toMatchElement("value", 123);
            MatchElement longVal = TestDataHelper.toMatchElement("value", 123L);
            // Act & Asserts
            assertThat(new MatchAny().match(intVal, longVal)).isTrue();
        }
    }

    @Nested
    class DateMatchTests {

        @Test
        void now() {
            // Arrange
            MatchElement actual = TestDataHelper.toMatchElement("time", new Date());
            MatchElement expected = TestDataHelper.toMatchElement("time", "date-match:[NOW]");
            // Act & Asserts
            assertThat(new MatchAny().match(actual, expected)).isTrue();
        }

        @Test
        void plusOneDay() {
            // Arrange
            Date tomorrow = new Date(new Date().getTime() + TimeUnit.DAYS.toMillis(1));
            MatchElement actual = TestDataHelper.toMatchElement("time", tomorrow);
            MatchElement expected = TestDataHelper.toMatchElement("time", "date-match:[NOW]+1(DAYS)");
            // Act & Asserts
            assertThat(new MatchAny().match(actual, expected)).isTrue();
        }

        @Test
        void notSame() {
            // Arrange
            Date tomorrow = new Date(new Date().getTime() + TimeUnit.DAYS.toMillis(1));
            MatchElement actual = TestDataHelper.toMatchElement("time", tomorrow);
            MatchElement expected = TestDataHelper.toMatchElement("time", "date-match:[NOW]");
            // Act & Asserts
            assertThat(new MatchAny().match(actual, expected)).isFalse();
        }

        @Test
        void minusOneDay() {
            // Arrange
            Date yesterday = new Date(new Date().getTime() - TimeUnit.DAYS.toMillis(1));
            MatchElement actual = TestDataHelper.toMatchElement("time", yesterday);
            MatchElement expected = TestDataHelper.toMatchElement("time", "date-match:[NOW]-1(DAYS)");
            // Act & Asserts
            assertThat(new MatchAny().match(actual, expected)).isTrue();
        }
    }

    @Nested
    class GroovyMatchTests {

        @Test
        void minusOneDay() {
            // Arrange
            MatchElement actual = TestDataHelper.toMatchElement(ImmutableMap.of("sum", 55));
            MatchElement expected = TestDataHelper.toMatchElement(ImmutableMap.of("sum", "groovy-match: value == (1..10).sum()"));
            // Act & Asserts
            assertThat(new MatchAny().match(actual, expected)).isTrue();
        }
    }

    @Nested
    class JavaScriptMatchTests {

        @Test
        void evenNumber() {
            // Arrange
            MatchElement actual = TestDataHelper.toMatchElement("sum", 32);
            MatchElement expected = TestDataHelper.toMatchElement("sum", "js-match: value % 2 == 0");
            // Act & Asserts
            assertThat(new MatchAny().match(actual, expected)).isTrue();
        }
    }
}