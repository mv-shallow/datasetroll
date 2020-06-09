package com.jupitertools.datasetroll.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClassByReferenceTest {

	@Test
	void getByRef() {
		ClassByReference classByReference = new ClassByReference(ClassByReferenceTest.class.getName());
		assertThat(classByReference.get()).isEqualTo(ClassByReferenceTest.class);
	}

	@Test
	void getNotExistedClass() {
		ClassByReference classByReference = new ClassByReference("com.bu.ga.ga.not.exist.class.reference");
		RuntimeException exc = Assertions.assertThrows(RuntimeException.class, classByReference::get);
		assertThat(exc.getMessage()).containsSubsequence("Unresolved document collection class reference from dataset:",
		                                                 "com.bu.ga.ga.not.exist.class.reference");
	}
}