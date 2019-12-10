package com.jupitertools.datasetroll.importdata;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jupitertools.datasetroll.DataSet;
import com.jupitertools.datasetroll.Text;


/**
 * Convert a {@link Text} (in JSON format) to the {@link DataSet}
 *
 * @author Korovin Anatoliy
 */
public class JsonImport implements DataSet {

    private final Text text;
    private final ObjectMapper objectMapper;

    public JsonImport(Text text) {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.text = text;
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
            throw new RuntimeException("JSON parsing error", e);
        }
    }
}