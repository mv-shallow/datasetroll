package com.jupitertools.datasetroll.exportdata;


import com.jupitertools.datasetroll.DataSet;

/**
 * Export data from MongoDb --> {@link DataSet}
 *
 * @author Korovin Anatoliy
 */
public interface DataSetExport {

    /**
     * export data set from MongoDb to the {@link DataSet} instance
     *
     * @return {@link DataSet}
     */
    DataSet export();
}
