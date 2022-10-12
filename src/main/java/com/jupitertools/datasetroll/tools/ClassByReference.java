package com.jupitertools.datasetroll.tools;

/**
 * Created on 27/11/2019
 * <p>
 * This tool resolves class type from the absolute reference in String.
 *
 * @author Korovin Anatoliy
 */
public class ClassByReference {

	private final String classReference;

	/**
	 * create {@link ClassByReference} instance with name of desired class.
	 *
	 * @param classReference The fully qualified name of the desired class.
	 */
	public ClassByReference(String classReference) {
		this.classReference = classReference;
	}

	/**
	 * Resolve class type and return the {@code Class} object associated with the class.
	 * Use the current class loader and throw {@link RuntimeException} if the class cannot be located.
	 *
	 * @return the {@code Class} object associated with the class
	 */
	public Class<?> get() {
		try {
			return Class.forName(classReference);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Unresolved document collection class reference from dataset: " + classReference, e);
		}
	}
}
