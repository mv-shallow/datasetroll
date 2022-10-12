package com.jupitertools.datasetroll.importdata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupitertools.datasetroll.DataSet;
import com.jupitertools.datasetroll.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

/**
 * Convert a {@link Text} (in JSON format) to the {@link DataSet}
 *
 * @author Korovin Anatoliy
 */
public class JsonImport implements DataSet {

    private final Text text;
    private final ObjectMapper objectMapper;
    private final Logger log;

    public JsonImport(Text text) {
        this.text = text;

        objectMapper = new ObjectMapper().enable(INDENT_OUTPUT);
        log = LoggerFactory.getLogger(JsonImport.class);
    }

    @Override
    public Map<String, List<Map<String, Object>>> read() {

        String content = text.read();
        try {
            return objectMapper.readValue(content,
                                          new TypeReference<Map<String, List<Map<String, Object>>>>() {
                                          });
        } catch (IOException e) {
            // TODO: improve the system of exceptions
            log.error("Error while parsing the next JSON file: \n{}", content, e);
            throw new RuntimeException("Error when parsing the JSON text.", e);
        }
    }
}