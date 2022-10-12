package com.jupitertools.datasetroll.exportdata;

import com.jupitertools.datasetroll.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Save text in a file.
 *
 * @author Korovin Anatoliy
 */
public class ExportFile {

    private final Text text;
    private final Logger log;

    public ExportFile(Text text) {
        this.text = text;

        log = LoggerFactory.getLogger(ExportFile.class);
    }

    /**
     * save text in a file, if the file does not exist
     * then it will create with all folders in the path.
     *
     * @param fileName path to file
     */
    public void write(String fileName) {
        String textData = this.text.read();
        try {
            Path path = Paths.get(fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, textData.getBytes());
        } catch (Exception e) {
            // TODO: improve the system of exceptions
            log.error("Error while saving text to file", e);
            throw new RuntimeException(e);
        }
    }
}
