package com.jupitertools.datasetroll.expect.match.simple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupitertools.datasetroll.expect.match.MatchAny;
import com.jupitertools.datasetroll.expect.match.MatchData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created on 18.12.2018.
 * <p>
 * Match one list to another list.
 *
 * @author Korovin Anatoliy
 */
public class MatchList implements MatchData {

    private final ObjectMapper objectMapper;
    private final MatchAny matchAny;
    private final Logger log;

    public MatchList() {
        objectMapper = new ObjectMapper();
        matchAny = new MatchAny();
        log = LoggerFactory.getLogger(MatchList.class);
    }

    @Override
    public boolean match(Object original, Object expected) {

        List<Object> originalList = convertToList(original);
        List<Object> expectedList = convertToList(expected);

        if (originalList.size() != expectedList.size()) {
            log.error("different array sizes: \n  actual: {}\n  expected: {}",
                      writeObject(original),
                      writeObject(expected));
            return false;
        }

        for (int i = 0; i < originalList.size(); i++) {
            if (!matchAny.match(originalList.get(i),
                                expectedList.get(i))) {
                return false;
            }
        }

        return true;
    }

    private List<Object> convertToList(Object object) {
        return objectMapper.convertValue(object, List.class);
    }

    private String writeObject(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Jackson parsing error", e);
            // TODO: improve the system of exceptions
            throw new RuntimeException(e);
        }
    }
}
