package com.jupitertools.datasetroll.expect.graph;

import com.jupitertools.datasetroll.MatchElement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created on 10.12.2018.
 *
 * Stub implementation of the {@link Graph} interface
 *
 * @author Korovin Anatoliy
 */
public class TestGraph implements Graph {

    private final boolean[][] matrix;
    private final List<MatchElement> dataRecords;
    private final List<MatchElement> patterns;

    public TestGraph(Supplier<boolean[][]> matrix) {
        this.matrix = matrix.get();
        this.dataRecords = new ArrayList<>();
        this.patterns = new ArrayList<>();
    }

    public TestGraph(Supplier<boolean[][]> matrix,
                     List<MatchElement> dataRecords,
                     List<MatchElement> patterns) {
        this.matrix = matrix.get();
        this.dataRecords = dataRecords;
        this.patterns = patterns;
    }

    @Override
    public boolean[][] calculate() {
        return matrix;
    }

    @Override
    public int dataCount() {
        return matrix.length;
    }

    @Override
    public int patternCount() {
        return matrix[0].length;
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
        return "TestDocument";
    }
}
