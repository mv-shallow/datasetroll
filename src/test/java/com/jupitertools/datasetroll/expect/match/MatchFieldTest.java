package com.jupitertools.datasetroll.expect.match;

import com.google.common.collect.Sets;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @author Mikhail Boyandin
 *
 * Created on 18.11.2022
 */
class MatchFieldTest {

    @ParameterizedTest
    @ValueSource(strings = {"foo", "foo-", "foo->"})
    void createWithoutSettings(String name) {
        //Act
        MatchField field = MatchField.fromString(name);

        //Assert
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(field)
                  .extracting("settings")
                  .containsExactly(Sets.newHashSet());

            softly.assertThat(field.getSettings())
                  .isEmpty();
        });
    }

    @Test
    void createWithSettings() {
        //Act
        MatchField field = MatchField.fromString("fieldName->someSetting,otherSetting");

        //Assert
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(field)
                  .extracting("name", "settings")
                  .containsExactly("fieldName", Sets.newHashSet("someSetting", "otherSetting"));
        });
    }
}