package com.jupitertools.datasetroll.expect;

import com.google.common.collect.ImmutableMap;
import com.jupitertools.datasetroll.DataSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;


class MatchDataSetsTest {

	@Test
	void matchTheSameDataSets() {

		ImmutableMap<String, Object> firstObject = ImmutableMap.of("fieldA", "AAA",
		                                                           "fieldB", "BBB");

		ImmutableMap<String, Object> secondObject = ImmutableMap.of("fieldA", "AAA",
		                                                            "fieldB", "BBB");

		DataSet actualDataSet = () -> ImmutableMap.of("document-name", Arrays.asList(firstObject, secondObject));
		DataSet expectedDataSet = () -> ImmutableMap.of("document-name", Arrays.asList(firstObject, secondObject));

		new MatchDataSets(actualDataSet, expectedDataSet).check();
	}

	@Test
	void matchDifferentDataSets() {

		ImmutableMap<String, Object> firstObject = ImmutableMap.of("fieldA", "AAA",
		                                                           "fieldB", "BBB");

		ImmutableMap<String, Object> secondObject = ImmutableMap.of("fieldA", "AAA",
		                                                            "fieldB", "BBB");

		ImmutableMap<String, Object> changedSecondObject = ImmutableMap.of("fieldA", "AAA",
		                                                                   "fieldB", "---");

		DataSet actualDataSet = () -> ImmutableMap.of("document-name", Arrays.asList(firstObject, secondObject));
		DataSet expectedDataSet = () -> ImmutableMap.of("document-name", Arrays.asList(firstObject, changedSecondObject));


		Error error = Assertions.assertThrows(Error.class, () -> {
			new MatchDataSets(actualDataSet, expectedDataSet).check();
		});

		assertThat(error.getMessage()).containsSubsequence("Not expected:", "{\"fieldA\":\"AAA\",\"fieldB\":\"BBB\"}",
		                                                   "Expected but not found:", "{\"fieldA\":\"AAA\",\"fieldB\":\"---\"}");
	}

	@Test
	void matchDataSetsWithDifferentRecordCounts() {

		ImmutableMap<String, Object> firstObject = ImmutableMap.of("fieldA", "AAA",
		                                                           "fieldB", "BBB");

		ImmutableMap<String, Object> secondObject = ImmutableMap.of("fieldA", "AAA",
		                                                            "fieldB", "BBB");

		DataSet actualDataSet = () -> ImmutableMap.of("document-name", Arrays.asList(firstObject, secondObject));
		DataSet expectedDataSet = () -> ImmutableMap.of("document-name", Arrays.asList(firstObject));

		Error error = Assertions.assertThrows(Error.class, () -> {
			new MatchDataSets(actualDataSet, expectedDataSet).check();
		});

		assertThat(error).hasMessageContaining("expected 1 but found 2 - document-name entities");
	}

	@Test
	void matchDifferentDocumentName() {

		ImmutableMap<String, Object> firstObject = ImmutableMap.of("fieldA", "AAA",
		                                                           "fieldB", "BBB");

		ImmutableMap<String, Object> secondObject = ImmutableMap.of("fieldA", "AAA",
		                                                            "fieldB", "BBB");

		DataSet actualDataSet = () -> ImmutableMap.of("document-name", Arrays.asList(firstObject, secondObject));
		DataSet expectedDataSet = () -> ImmutableMap.of("another-name", Arrays.asList(firstObject, secondObject));


		Error error = Assertions.assertThrows(Error.class, () -> {
			new MatchDataSets(actualDataSet, expectedDataSet).check();
		});

		assertThat(error).hasMessageContaining("Not equal document collections");
		assertThat(error.getMessage()).containsSubsequence("expected:", "another-name",
		                                                   "actual:", "document-name");
	}

	@Test
	void matchDifferentDocumentNameWithMultipleDocTypes() {

		ImmutableMap<String, Object> record = ImmutableMap.of("fieldA", "AAA",
		                                                      "fieldB", "BBB");

		DataSet actualDataSet = () -> ImmutableMap.of("first-document-name", Collections.singletonList(record),
		                                              "second-document-name", Collections.singletonList(record));

		DataSet expectedDataSet = () -> ImmutableMap.of("first-document-name", Collections.singletonList(record),
		                                                "second-document-name", Collections.singletonList(record),
		                                                "SPECIFIC-document-name", Collections.singletonList(record));

		Error error = Assertions.assertThrows(Error.class, () -> {
			new MatchDataSets(actualDataSet, expectedDataSet).check();
		});

		assertThat(error).hasMessageContaining("Not equal document collections");
	}
}