package com.jupitertools.datasetroll;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class TextTest {

    @Test
    void simple() {

        Text test = new Text() {
            public String read() {
                return "123";
            }
        };

        assertThat(test.read()).isEqualTo("123");
    }

}
