package com.jupitertools.datasetroll.expect.match.simple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupitertools.datasetroll.expect.match.MatchAny;
import com.jupitertools.datasetroll.expect.match.MatchData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public MatchList(MatchAny matchAny, ObjectMapper objectMapper) {
        this.matchAny = matchAny;
        this.objectMapper = objectMapper;
        log = LoggerFactory.getLogger(MatchList.class);
    }

    @Override
    public boolean match(Object original, Object expected) {
        return match(original, expected, new HashSet<>());
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean match(Object original, Object expected, Set<String> settings) {
        boolean ignoreOrder = settings.contains("ignoreOrder");

        List<Object> originalList = (List<Object>) original;
        List<Object> expectedList = (List<Object>) expected;

        if (originalList.size() != expectedList.size()) {
            log.error("different array sizes: \n  actual: {}\n  expected: {}",
                      writeObject(original),
                      writeObject(expected));
            return false;
        }

        return ignoreOrder ? matchIgnoreOrder(originalList, expectedList)
                           : matchWithOrder(originalList, expectedList);
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

    /**
     * <p>
     * Take an element from original collection and iterate over expected elements while matching original with expected.
     * If match is found remove matched elements by their indices from both collections and start new iteration on outer loop
     * (Increment expression isn't needed because of removal).
     * </p>
     * <p>
     * If no match is found for any of the original elements - collections are not equal
     * </p>
     *
     * @param originalList list of original elements (result of test execution)
     * @param expectedList list of expected elements
     *
     * @return true if collections are equal ignoring order
     */
    private boolean matchIgnoreOrder(List<Object> originalList, List<Object> expectedList) {
        List<Object> original = new ArrayList<>(originalList);
        List<Object> expected = new ArrayList<>(expectedList);

        for (int i = 0; i < original.size(); ) {

            Object originalValue = original.get(i);

            boolean matches = false;

            for (int j = 0; j < expected.size(); j++) {
                Object expectedValue = expected.get(j);

                matches = matchAny.match(originalValue, expectedValue);

                if (matches) {
                    original.remove(i);
                    expected.remove(j);
                    break;
                }
            }

            if (matches) {
                continue;
            }

            return false;
        }

        return true;
    }

    private boolean matchWithOrder(List<Object> originalList, List<Object> expectedList) {
        for (int i = 0; i < originalList.size(); i++) {
            if (!matchAny.match(originalList.get(i),
                                expectedList.get(i))) {
                return false;
            }
        }

        return true;
    }
}
