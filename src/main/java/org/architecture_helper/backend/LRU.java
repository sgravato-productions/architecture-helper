package org.architecture_helper.backend;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LRU {
    private List<Integer> values;
    private int lastIndex;

    public LRU(int elements) {
        //values = 3,2,1,0, so the cache will use 0 as the first
        values = IntStream.range(0, elements).boxed().collect(Collectors.toList());
        Collections.reverse(values);
        lastIndex = elements - 1;
    }

    public int getLeastRecentlyUsed() {
        return values.get(lastIndex);
    }

    public void pushToTop(int index) {
        values.remove(index);
        values.add(0, index);
    }
}