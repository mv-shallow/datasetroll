package com.jupitertools.datasetroll.exportdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupitertools.datasetroll.DataSet;
import com.jupitertools.datasetroll.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

/**
 * Convert object to {@link Text} in JSON format.
 *
 * @author Korovin Anatoliy
 */
public class JsonExport implements Text {

    private final DataSet dataSet;
    private final ObjectMapper objectMapper;
    private final Logger log;

    public JsonExport(DataSet dataSet) {
        this.dataSet = dataSet;

        objectMapper = new ObjectMapper().enable(INDENT_OUTPUT);
        log = LoggerFactory.getLogger(JsonExport.class);
    }

    @Override
    public String read() {
        try {
            return objectMapper.writeValueAsString(dataSet.read());
        } catch (Exception e) {
            log.error("Error while converting a dataset to the JSON string: ", e);
            // TODO: improve the system of exceptions
            throw new RuntimeException(e);
        }
    }
}
