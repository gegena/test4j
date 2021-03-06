package org.test4j.json.helper;

import org.junit.Test;
import org.test4j.junit.Test4J;

public class JSONArrayTest extends Test4J {

    @Test
    public void testDescription() {
        JSONArray array = new JSONArray();
        array.add(new JSONSingle("value"));

        String result = array.toString();
        want.string(result).isEqualTo("[value]");
    }
}
