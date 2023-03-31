package com.jupitertools.datasetroll;

import com.jupitertools.datasetroll.expect.match.MatchField;

import java.util.List;
import java.util.Map;

/**
 * @author Mikhail Boyandin
 *
 * Created on 13.01.2023
 */
public interface MatchDataSet {

    Map<String, List<Map<MatchField, Object>>> read();
}
