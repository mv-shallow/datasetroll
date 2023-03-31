package com.jupitertools.datasetroll.expect.graph;

import com.jupitertools.datasetroll.MatchElement;

import java.util.HashSet;
import java.util.Set;

/**
 * Add in graph indexes of successful matched patterns
 * and data records.
 */
public class IndexedGraph implements Graph {

    private final Graph graph;
    private boolean indexReady;
    private final Set<Integer> patternIndexes;
    private final Set<Integer> dataIndexes;

    public IndexedGraph(Graph graph) {
        this.graph = graph;

        dataIndexes = new HashSet<>();
        patternIndexes = new HashSet<>();
    }

    @Override
    public boolean[][] calculate() {
        return graph.calculate();
    }

    @Override
    public int dataCount() {
        return graph.dataCount();
    }

    @Override
    public int patternCount() {
        return graph.patternCount();
    }

    @Override
    public MatchElement getDataRecord(int index) {
        return graph.getDataRecord(index);
    }

    @Override
    public MatchElement getPattern(int index) {
        return graph.getPattern(index);
    }

    @Override
    public String getDocumentName() {
        return graph.getDocumentName();
    }

    /**
     * @return set with indexes of patterns used in this graph
     */
    public Set<Integer> evaluatePatternIndexes() {
        if (!indexReady) {
            evaluateIndexes();
        }
        return patternIndexes;
    }

    /**
     * @return set with indexes of data records used in this graph
     */
    public Set<Integer> evaluateDataIndexes() {
        if (!indexReady) {
            evaluateIndexes();
        }
        return dataIndexes;
    }

    /**
     * calculate all used indexes of patterns and
     * data records matched for this patterns
     */
    private void evaluateIndexes() {

        boolean[][] matrix = graph.calculate();

        for (int i = 0; i < dataCount(); i++) {
            for (int j = 0; j < patternCount(); j++) {
                if (matrix[i][j]) {
                    dataIndexes.add(i);
                    patternIndexes.add(j);
                }
            }
        }
        indexReady = true;
    }
}