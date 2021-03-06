package org.test4j.tools.commons;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.test4j.json.encoder.beans.test.TestedClazz;
import org.test4j.json.encoder.beans.test.TestedIntf;
import org.test4j.testng.Test4J;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings("rawtypes")
@Test(groups = { "test4j" })
public class ClazzHelperTest extends Test4J {

    @Test(dataProvider = "provideClazzName")
    public void getPackFromClassName(String clazz, String pack) {
        want.string(ClazzHelper.getPackFromClassName(clazz)).isEqualTo(pack);
    }

    @DataProvider
    public Object[][] provideClazzName() {
        return new String[][] { { "", "" }, { "EefErr", "" },
                { "org.test4j.utility.ClazzUtilTest", "org.test4j.utility" } };
    }

    @Test(dataProvider = "dataProvider_testGetPathFromPath")
    public void testGetPathFromPath(String clazName, String path) {
        String _path = ClazzHelper.getPathFromPath(clazName);
        want.string(_path).isEqualTo(path);
    }

    @DataProvider
    public Object[][] dataProvider_testGetPathFromPath() {
        return new Object[][] { { "a.b.c.ImplClazz", "a/b/c" }, // <br>
                { "ImplClazz", "" }, /** <br> **/
                { ".ImplClazz", "" }
        /** <br> **/
        };
    }

    @Test
    public void testGetBytes() {
        byte[] bytes = ClazzHelper.getBytes(ClazzHelper.class);
        want.array(bytes).notNull().sizeGt(1);
    }

    @Test(dataProvider = "proxy_types")
    public void testGetUnProxyType(Class type, Class expected) {
        Class actual = ClazzHelper.getUnProxyType(type);
        want.object(actual).isEqualTo(expected);
    }

    @DataProvider
    public Object[][] proxy_types() {
        Object proxy = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[] { TestedIntf.class },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return null;
                    }
                });
        return new Object[][] { { TestedClazz.class, TestedClazz.class },// <br>
                { new TestedClazz() {
                    {
                    }
                }.getClass(), TestedClazz.class }, // <br>
                { new TestedIntf() {
                    {

                    }
                }.getClass(), Object.class },// <br>
                { proxy.getClass(), Object.class } // <br>
        };
    }
}
