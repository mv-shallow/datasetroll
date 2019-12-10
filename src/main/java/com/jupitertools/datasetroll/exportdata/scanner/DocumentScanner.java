package com.jupitertools.datasetroll.exportdata.scanner;

import java.util.Map;

/**
 * Created on 04.01.2019.
 * <p>
 * Find all MongoDb Document collections with their classes.
 *
 * @author Korovin Anatoliy
 */
public interface DocumentScanner {


    Map<String, Class<?>> scan();
}
