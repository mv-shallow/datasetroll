package com.jupitertools.datasetroll.importdata;

import com.jupitertools.datasetroll.DataSet;

/**
 * Import data to a storage from the {@link DataSet} instance
 *
 * @author Korovin Anatoliy
 */
public interface DataSetImport {

    /**
     * Import {@link DataSet} to the storage
     *
     * @param dataSet source data set
     */
    void importFrom(DataSet dataSet);
}
