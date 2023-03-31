package com.jupitertools.datasetroll.expect.graph;

import com.jupitertools.datasetroll.MatchElement;
import com.jupitertools.datasetroll.expect.match.MatchAny;

import java.util.List;

/**
 * Created on 09.12.2018.
 * <p>
 * Evaluate the graph of the object matching,
 * try to match all data records to each pattern,
 * and save this in the matrix.
 *
 * @author Korovin Anatoliy
 */
public class MatchGraph implements Graph {

    private final List<MatchElement> dataRecords;
    private final List<MatchElement> patterns;
    private final String documentName;
    private final MatchAny matchAny;

    public MatchGraph(String documentName,
                      List<MatchElement> dataRecords,
                      List<MatchElement> patterns) {
        this.documentName = documentName;
        this.dataRecords = dataRecords;
        this.patterns = patterns;

        matchAny = new MatchAny();
    }

    @Override
    public boolean[][] calculate() {

        int matchedSize = dataCount();
        int patternSize = patternCount();

        boolean[][] matrix = new boolean[matchedSize][patternSize];

        for (int i = 0; i < matchedSize; i++) {
            for (int j = 0; j < patternSize; j++) {
                matrix[i][j] = matchAny.match(dataRecords.get(i), patterns.get(j));
            }
        }

        return matrix;
    }

    @Override
    public int dataCount() {
        return this.dataRecords.size();
    }

    @Override
    public int patternCount() {
        return this.patterns.size();
    }

    @Override
    public MatchElement getDataRecord(int index) {
        return dataRecords.get(index);
    }

    @Override
    public MatchElement getPattern(int index) {
        return patterns.get(index);
    }

    @Override
    public String getDocumentName() {
        return this.documentName;
    }

}
