package com.jupitertools.datasetroll.exportdata;

import com.jupitertools.datasetroll.DataSet;

/**
 * Export data from a storage to {@link DataSet}
 *
 * @author Korovin Anatoliy
 */
public interface DataSetExport {

    /**
     * export data set from some storage to the {@link DataSet} instance
     *
     * @return {@link DataSet}
     */
    DataSet export();
}
