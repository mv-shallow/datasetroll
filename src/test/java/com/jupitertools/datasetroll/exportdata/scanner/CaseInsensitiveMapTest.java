package com.jupitertools.datasetroll.exportdata.scanner;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CaseInsensitiveMapTest {

	@Test
	void put() {
		// Arrange
		CaseInsensitiveMap map = new CaseInsensitiveMap();
		// Act
		map.put("Float", Float.class);
		map.put("Double", Double.class);
		// Asserts
		assertThat(map).hasSize(2)
		               .containsEntry("float", Float.class)
		               .containsEntry("double", Double.class);
	}

	@Test
	void get() {
		// Arrange
		CaseInsensitiveMap map = new CaseInsensitiveMap();
		// Act
		map.put("Float", Float.class);
		map.put("Double", Double.class);
		// Asserts
		assertThat(map.get("float")).isEqualTo(Float.class);
		assertThat(map.get("Float")).isEqualTo(Float.class);
		assertThat(map.get("Double")).isEqualTo(Double.class);
		assertThat(map.get("double")).isEqualTo(Double.class);
	}

}