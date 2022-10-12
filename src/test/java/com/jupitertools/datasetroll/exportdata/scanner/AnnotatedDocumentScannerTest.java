package com.jupitertools.datasetroll.exportdata.scanner;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Map;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.assertj.core.api.Assertions.assertThat;

class AnnotatedDocumentScannerTest {

	@Test
	void scanAndMapByClassType() {
		ScanResult scanResult = new AnnotatedDocumentScanner("").scan(MyTestAnnotation.class);
		Map<String, Class<?>> map = scanResult.mapByClassAttr(Class::getName);
		assertThat(map.keySet()).hasSize(2);
		assertThat(map).containsEntry(FirstTestClass.class.getName(), FirstTestClass.class);
		assertThat(map).containsEntry(SecondTestClass.class.getName(), SecondTestClass.class);
	}

	@Test
	void scanAndMapByAnnotation() {
		ScanResult scanResult = new AnnotatedDocumentScanner("").scan(MyTestAnnotation.class);
		Map<String, Class<?>> map = scanResult.mapByAnnotationAttr(MyTestAnnotation.class, MyTestAnnotation::value);
		assertThat(map.keySet()).hasSize(2);
		assertThat(map).containsEntry("one", FirstTestClass.class);
		assertThat(map).containsEntry("two", SecondTestClass.class);
	}

	@MyTestAnnotation("one")
	class FirstTestClass {

	}

	@MyTestAnnotation("two")
	class SecondTestClass {

	}


	@Target(TYPE)
	@Retention(RUNTIME)
	@interface MyTestAnnotation {
		String value() default "";
	}
}