package com.jupitertools.datasetroll.expect.match;

import java.util.Set;

/**
 * Created on 18.12.2018.
 *
 * Checks the equality of two objects.
 *
 * @author Korovin Anatoliy
 */
public interface MatchData {

    /**
     * @param original value of the original object (this object we try to check on equals to another)
     * @param expected expected value of the original object
     * @param settings set of options that configures applied matcher
     *
     * @return true if the original value match to the expected value,
     * and false if not.
     */
    default boolean match(Object original, Object expected, Set<String> settings) {
        return match(original, expected);
    }

    /**
     * @param original value of the original object (this object we try to check on equals to another)
     * @param expected expected value of the original object
     *
     * @return true if the original value match to the expected value,
     * and false if not.
     */
    boolean match(Object original, Object expected);

}
