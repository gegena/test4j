package org.test4j.module.jmockit.mockbug;

import mockit.Mock;

import org.junit.Test;
import org.test4j.junit.Test4J;
import org.test4j.module.spring.annotations.AutoBeanInject;
import org.test4j.module.spring.annotations.SpringBeanByName;
import org.test4j.module.spring.annotations.SpringContext;
import org.test4j.module.spring.annotations.SpringInitMethod;

@SpringContext
@AutoBeanInject
public class MethodServiceTest_SpringInject extends Test4J {
    @SpringBeanByName
    TestedMethodService service;

    @SpringInitMethod
    protected void mockTestedMethodService() {
        new MockUp<TestedMethodService>() {
            TestedMethodService it;

            @Mock
            public void $init() {
                reflector.setField(it, "name", "init mock");
            }
        };
    }

    @Test
    public void testBeforeMethodMock() {
        String result = service.sayHello();
        System.out.println(result);
        want.string(result).isEqualTo("hello, init mock");

        String name = reflector.getField(service, "name");
        want.string(name).isEqualTo("init mock");
    }
}
