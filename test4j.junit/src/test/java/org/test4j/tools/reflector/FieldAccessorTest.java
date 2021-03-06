package org.test4j.tools.reflector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.test4j.fortest.reflector.TestObject;
import org.test4j.junit.Test4J;
import org.test4j.tools.exception.NoSuchFieldRuntimeException;

@SuppressWarnings("rawtypes")
public class FieldAccessorTest extends Test4J {

    private FieldAccessor aPrivate;

    @Before
    public void setUp() throws Exception {
        aPrivate = new FieldAccessor(TestObject.class, "aPrivate");
    }

    @After
    public void tearDown() throws Exception {
        aPrivate = null;
    }

    @Test(expected = NoSuchFieldRuntimeException.class)
    public void testFieldAccessor1() {
        new FieldAccessor(Object.class, "missing");
    }

    @Test(expected = NullPointerException.class)
    public void testFieldAccessor2() {
        new FieldAccessor("missing", null);
    }

    @Test
    public void testFieldAccessor3() {
        FieldAccessor accessor = new FieldAccessor(TestObject.class, "aSuperStaticPrivate");
        accessor.setStatic(1);
    }

    /**
     * Test method for {@link com.j2speed.accessor.FieldAccessor#get()}.
     */
    @Test
    public void testGet() {
        TestObject toTest = new TestObject();
        int actual = ((Integer) aPrivate.get(toTest)).intValue();
        want.number(actual).isEqualTo(26071973);
    }

    /**
     * Test method for
     * {@link com.j2speed.accessor.FieldAccessor#set(java.lang.Object)}.
     */
    @Test
    public void testSet() {
        TestObject toTest = new TestObject();
        int newValue = 26072007;
        aPrivate.set(toTest, Integer.valueOf(newValue));
        int actual = ((Integer) aPrivate.get(toTest)).intValue();
        want.number(actual).isEqualTo(newValue);
        try {
            aPrivate.set(toTest, null);
            want.fail();
        } catch (Throwable e) {
            String info = e.getMessage();
            want.string(info).contains("to set field").contains("into target").contains("error");
        }
    }
}
