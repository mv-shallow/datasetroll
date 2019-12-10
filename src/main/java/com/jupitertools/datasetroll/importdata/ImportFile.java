package com.jupitertools.datasetroll.importdata;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.jupitertools.datasetroll.Text;
import org.apache.commons.io.IOUtils;


/**
 * Load text from a file.
 *
 * @author Korovin Anatoliy
 */
public class ImportFile implements Text {

    private final String fileName;

    public ImportFile(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String read() {
        try (InputStream inputStream = getResourceStream()) {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (Exception e) {
            // TODO: improve the system of exceptions
            throw new RuntimeException("Error while reading the data from file: " + fileName, e);
        }
    }

    private InputStream getResourceStream() throws IOException {
        String dataFileName = (!fileName.startsWith("/"))
                              ? "/" + fileName
                              : fileName;

        InputStream inputStream = getClass().getResourceAsStream(dataFileName);
        if (inputStream == null) {
            inputStream = getClass().getResourceAsStream("/dataset" + dataFileName);
        }
        if (inputStream == null) {
            inputStream = Files.newInputStream(Paths.get(fileName));
        }
        return inputStream;
    }
}
