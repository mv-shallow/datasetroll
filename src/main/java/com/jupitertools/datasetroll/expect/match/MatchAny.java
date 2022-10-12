package com.jupitertools.datasetroll.expect.match;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupitertools.datasetroll.expect.match.simple.MatchDataFactory;
import com.jupitertools.datasetroll.expect.match.smart.MatchDataSmartFactory;

/**
 * Created on 18.12.2018.
 * <p>
 * Top-level matcher, to match objects of any types
 * (List, Map, Number e.t.c.)
 *
 * @author Korovin Anatoliy
 */
public class MatchAny implements MatchData {

    private final ObjectMapper objectMapper;
    private final MatchDataFactory matchDataFactory;
    private final MatchDataSmartFactory matchDataSmartFactory;

    public MatchAny() {
        objectMapper = new ObjectMapper();
        matchDataFactory = new MatchDataFactory();
        matchDataSmartFactory = new MatchDataSmartFactory();
    }

    @Override
    public boolean match(Object original, Object expected) {

        if (matchDataSmartFactory.isNecessary(expected)) {
            return matchDataSmartFactory.get(expected)
                                        .match(original, expected);
        }

        return simpleMatch(original, expected);
    }

    private boolean simpleMatch(Object original, Object expected) {
        if (original == null) {
            return expected == null;
        }

        if (expected == null) {
            return true;
        }


        JsonNode originalNode = objectMapper.valueToTree(original);
        JsonNode expectedNode = objectMapper.valueToTree(expected);

        if (originalNode.getNodeType() != expectedNode.getNodeType()) {
            return false;
        }

        return matchDataFactory.get(originalNode.getNodeType())
                               .match(original, expected);
    }
}
