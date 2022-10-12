package com.jupitertools.datasetroll.exportdata.scanner;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Created on 19/11/2019
 * <p>
 * TODO: replace on the JavaDoc
 *
 * @author Korovin Anatoliy
 */
public class AnnotatedDocumentScanner {

    private final String basePackage;

    public AnnotatedDocumentScanner(String basePackage) {
        this.basePackage = basePackage;
    }

    public <A extends Annotation> ScanResult scan(Class<A> annotationType) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> documents = reflections.getTypesAnnotatedWith(annotationType);
        return new ScanResult(documents);
    }
}
