package com.jupitertools.datasetroll.expect.graph;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Evaluate the IndexedGraph and
 * assert that all patterns applied to any data record.
 */
public class AssertGraph {

    private final IndexedGraph indexGraph;
    private final ObjectMapper objectMapper;
    private final Logger log;

    private boolean failed;
    private final List<String> errors;


    public AssertGraph(Graph graph) {
        this.indexGraph = new IndexedGraph(graph);

        objectMapper = new ObjectMapper();
        log = LoggerFactory.getLogger(AssertGraph.class);

        failed = false;
        errors = new ArrayList<>();
    }

    public void doAssert() {
        validateDataRecords(indexGraph.evaluateDataIndexes());
        validatePatterns(indexGraph.evaluatePatternIndexes());
        if (failed) {
            throw new AssertionError("\nExpectedDataSet of " +
                                     indexGraph.getDocumentName() + " \n\n" +
                                     String.join("\n", errors) + "\n");
        }
    }

    private void validateDataRecords(Set<Integer> indexes) {
        if (indexes.size() != indexGraph.dataCount()) {

            String notFoundDataRecords = IntStream.range(0, indexGraph.dataCount())
                                                  .boxed()
                                                  .filter(i -> !indexes.contains(i))
                                                  .map(indexGraph::getDataRecord)
                                                  .map(this::mapToString)
                                                  .collect(Collectors.joining("\n"));

            error("Not expected: \n" + notFoundDataRecords + "\n");
        }
    }


    private void validatePatterns(Set<Integer> indexes) {
        if (indexes.size() != indexGraph.patternCount()) {

            String notFoundPatterns = IntStream.range(0, indexGraph.patternCount())
                                               .boxed()
                                               .filter(i -> !indexes.contains(i))
                                               .map(indexGraph::getPattern)
                                               .map(this::mapToString)
                                               .collect(Collectors.joining("\n"));

            error("Expected but not found: \n" + notFoundPatterns + "\n");
        }
    }

    private String mapToString(Map<String, Object> stringObjectMap) {
        try {
            return objectMapper.writeValueAsString(stringObjectMap);
        } catch (JsonProcessingException e) {
            log.error("Error while convert object to string: {}", stringObjectMap, e);
            // TODO: improve the system of exceptions
            throw new RuntimeException("Error while convert object to string", e);
        }
    }

    private void error(String message) {
        failed = true;
        errors.add(message);
    }
}