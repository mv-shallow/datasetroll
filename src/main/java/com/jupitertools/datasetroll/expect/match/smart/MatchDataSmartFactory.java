package com.jupitertools.datasetroll.expect.match.smart;

import com.jupitertools.datasetroll.expect.match.smart.date.MatchDate;
import com.jupitertools.datasetroll.expect.match.smart.groovy.MatchGroovy;
import com.jupitertools.datasetroll.expect.match.smart.javascript.MatchJavaScript;
import com.jupitertools.datasetroll.expect.match.smart.regexp.MatchRegExp;

import java.util.Arrays;
import java.util.List;

/**
 * Created on 22.12.2018.
 *
 * @author Korovin Anatoliy
 */
public class MatchDataSmartFactory {

    private final List<MatchDataSmart> matchDataList = Arrays.asList(new MatchGroovy(),
                                                                     new MatchJavaScript(),
                                                                     new MatchDate(),
                                                                     new MatchRegExp());

    /**
     * check expected value to match on any matchers.
     *
     * @param expected expected value (or some scripts)
     *
     * @return true if this value match to any {@link MatchDataSmart}
     */
    public boolean isNecessary(Object expected) {
        for (MatchDataSmart matchData : matchDataList) {
            if (matchData.isNecessary(expected)) {
                return true;
            }
        }
        return false;
    }

    /**
     * retrieve {@link MatchDataSmart} to the expected value
     *
     * @param expected expected value (or some scripts)
     *
     * @return MatchDataSmart which must be applied to this value
     */
    public MatchDataSmart get(Object expected) {
        for (MatchDataSmart matchData : matchDataList) {
            if (matchData.isNecessary(expected)) {
                return matchData;
            }
        }

        throw new RuntimeException("Not found MatchDataSmart for {" + expected.toString() + "} object.");
    }
}
