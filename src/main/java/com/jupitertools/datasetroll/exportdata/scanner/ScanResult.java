package com.jupitertools.datasetroll.exportdata.scanner;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;


public class ScanResult {

    private final Set<Class<?>> scannedDocuments;

    public ScanResult(Set<Class<?>> scannedDocuments) {
        this.scannedDocuments = scannedDocuments;
    }


    public Map<String, Class<?>> mapByClassAttr(Function<Class<?>, String> nameFromClassRetriever) {

        HashMap<String, Class<?>> result = new HashMap<>();
        for (Class<?> doc : scannedDocuments) {
            result.put(nameFromClassRetriever.apply(doc), doc);
            System.out.println("PUT: [" + nameFromClassRetriever.apply(doc) + "] = " + doc.getName());
        }
        return result;
    }


    public <A extends Annotation> Map<String, Class<?>> mapByAnnotationAttr(Class<A> annotationType,
                                                                            Function<A, String> nameRetriever) {

        HashMap<String, Class<?>> result = new HashMap<>();
        for (Class<?> doc : scannedDocuments) {
            A d = doc.getAnnotation(annotationType);
            result.put(nameRetriever.apply(d), doc);
            System.out.println("PUT: [" + nameRetriever.apply(d) + "] = " + doc.getName());
        }
        return result;
    }
}