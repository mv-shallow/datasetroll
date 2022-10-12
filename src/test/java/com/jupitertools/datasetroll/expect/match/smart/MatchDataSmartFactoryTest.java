package com.jupitertools.datasetroll.expect.match.smart;

import com.jupitertools.datasetroll.expect.match.smart.date.MatchDate;
import com.jupitertools.datasetroll.expect.match.smart.groovy.MatchGroovy;
import com.jupitertools.datasetroll.expect.match.smart.javascript.MatchJavaScript;
import com.jupitertools.datasetroll.expect.match.smart.regexp.MatchRegExp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 19.01.2019.
 *
 * @author Korovin Anatoliy
 */
class MatchDataSmartFactoryTest {

    private static Stream<Arguments> testParams() {
        return Stream.of(
		        Arguments.of("js-match: value == 123", MatchJavaScript.class),
		        Arguments.of("js-match:1<2<3", MatchJavaScript.class),
		        Arguments.of("groovy-match: true", MatchGroovy.class),
		        Arguments.of("groovy-match:true", MatchGroovy.class),
		        Arguments.of("date-match:[NOW]", MatchDate.class),
		        // TODO: fix it in the issue #30
		        //Arguments.of("date-match: [NOW]", MatchDate.class),
		        Arguments.of("regex: ^123..7$", MatchRegExp.class));
    }

    @ParameterizedTest
    @MethodSource("testParams")
    void testMatchTypeByValue(String value, Class<?> expectedType) {
        // Arrange
        MatchDataSmartFactory factory = new MatchDataSmartFactory();
        // Act
        MatchDataSmart smartMatch = factory.get(value);
        // Asserts
        assertThat(smartMatch).isInstanceOf(expectedType);
    }

    @ParameterizedTest
    @MethodSource("testParams")
    void testNecessary(String value) {
        // Arrange
        MatchDataSmartFactory factory = new MatchDataSmartFactory();
        // Act & Asserts
        assertThat(factory.isNecessary(value)).isTrue();
    }

    @Test
    void getNotExisted() {
        // Arrange
        MatchDataSmartFactory factory = new MatchDataSmartFactory();
        // Act & Asserts
        RuntimeException exception =
                Assertions.assertThrows(RuntimeException.class,
                                        () -> factory.get("<-- unscriptable value -->>"));

        assertThat(exception.getMessage())
                .isEqualTo("Not found MatchDataSmart for {<-- unscriptable value -->>} object.");
    }

    @Test
    void notNecessary() {
        // Arrange
        MatchDataSmartFactory factory = new MatchDataSmartFactory();
        // Act & Asserts
        assertThat(factory.isNecessary("<-- unscriptable value -->>")).isFalse();
    }
}