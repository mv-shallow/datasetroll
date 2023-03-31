package com.jupitertools.datasetroll.expect.match.simple;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.jupitertools.datasetroll.expect.match.MatchAny;
import com.jupitertools.datasetroll.expect.match.MatchData;

/**
 * Created on 19.12.2018.
 *
 * Factory of matchers by the type of JsonNode
 *
 * @author Korovin Anatoliy
 */
public class MatchDataFactory {

    public MatchData get(JsonNodeType type) {
        switch (type) {
            case OBJECT:
                return new MatchElements(new MatchAny());
            case ARRAY:
                return new MatchList(new MatchAny(), new ObjectMapper());
            case NUMBER:
                return new MatchNumber();
            case STRING:
                return new MatchString();
            default:
                return new MatchObjects();
        }
    }
}
