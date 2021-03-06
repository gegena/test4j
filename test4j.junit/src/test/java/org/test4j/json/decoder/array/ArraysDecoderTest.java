package org.test4j.json.decoder.array;

import java.util.Map;

import org.junit.Test;
import org.test4j.json.JSON;
import org.test4j.json.encoder.beans.test.User;
import org.test4j.junit.Test4J;

@SuppressWarnings("rawtypes")
public class ArraysDecoderTest extends Test4J {

    @Test
    public void testParseFromJSONArray_SpecArrayClaz() {
        String json = "[{name:'name1'},{name:'name2'}]";

        User[] users = JSON.toObject(json, User[].class);
        want.array(users).sizeEq(2).propertyEq("name", new String[] { "name1", "name2" });
    }

    @Test
    public void testParseFromJSONArray_UnSpecArrayClaz() {
        String json = "[{name:'name1'},{name:'name2'}]";

        Object[] users = JSON.toObject(json);
        want.array(users).sizeEq(2).propertyEq("name", new String[] { "name1", "name2" });
    }

    @Test
    public void testIntsArray() {
        String json = "[{'#class':'Integer','#value':1},{'#class':'Integer','#value':2},{'#class':'Integer','#value':3},{'#class':'Integer','#value':4}]";
        Integer[] ints = JSON.toObject(json, int[].class);
        want.array(ints).reflectionEq(new int[] { 1, 2, 3, 4 });
    }

    @Test
    public void testIntsArray2() {
        String json = "{'#class':'int[]','#value':[{'#class':'Integer','#value':1},{'#class':'Integer','#value':2},{'#class':'Integer','#value':3},{'#class':'Integer','#value':4}]}";
        Integer[] ints = JSON.toObject(json);
        want.array(ints).reflectionEq(new int[] { 1, 2, 3, 4 });
    }

    @Test
    public void testStringArray() {
        String json = "{'key1':['string1','string2'],'key2':'value2'}";
        Map map = JSON.toObject(json);
        want.map(map).sizeEq(2).propertyEq("key1", new String[] { "string1", "string2" });
    }

    @Test
    public void testStringArray_UserArray() {
        String json = "{'key1':[{name:'name1'},{name:'name2'}],'key2':'value2'}";
        Map map = JSON.toObject(json);
        want.map(map).sizeEq(2);
        want.list((Object[]) map.get("key1")).propertyEq("name", new String[] { "name1", "name2" });
    }
}
