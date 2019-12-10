package com.jupitertools.datasetroll.exportdata;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.jupitertools.datasetroll.Text;

/**
 * Save text in a file.
 *
 * @author Korovin Anatoliy
 */
public class ExportFile {

    private final Text text;

    public ExportFile(Text text) {
        this.text = text;
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
            throw new RuntimeException("Error while save text in the file: " + fileName, e);
        }
    }
}
