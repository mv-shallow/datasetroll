package com.jupitertools.datasetroll.tools;

import com.jupitertools.datasetroll.MatchElement;

import java.util.List;
import java.util.Map;

/**
 * @author Mikhail Boyandin
 *
 * Created on 13.01.2023
 */
public class CollectionConverter {

    public static List<MatchElement> convertKeysToMatchFields(List<Map<String, Object>> input) {
        return null;
/*        return input.stream()
                    .map(map -> MatchingUtils.toMatchElement(map, false))
                    .collect(Collectors.toList());*/
    }
}
