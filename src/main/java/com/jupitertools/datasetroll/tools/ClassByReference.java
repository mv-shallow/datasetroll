package com.jupitertools.datasetroll.tools;


/**
 * Created on 27/11/2019
 * <p>
 * TODO: replace on the JavaDoc
 *
 * @author Korovin Anatoliy
 */
public class ClassByReference {

    private final String classReference;

    public ClassByReference(String classReference) {
        this.classReference = classReference;
    }


    public Class<?> get() {
        try {
            return Class.forName(classReference);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unresolved document collection class reference from dataset: " + classReference, e);
        }
    }
}
