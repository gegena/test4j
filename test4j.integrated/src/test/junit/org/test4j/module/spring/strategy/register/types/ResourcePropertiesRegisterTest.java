package org.test4j.module.spring.strategy.register.types;

import org.junit.Test;
import org.test4j.fortest.service.UserService;
import org.test4j.junit.Test4J;
import org.test4j.module.spring.annotations.AutoBeanInject;
import org.test4j.module.spring.annotations.AutoBeanInject.BeanMap;
import org.test4j.module.spring.annotations.SpringBeanByName;
import org.test4j.module.spring.annotations.SpringContext;
import org.test4j.module.spring.testedbeans.resource.UserServiceResourceImpl;

@SpringContext({ "org/test4j/module/spring/testedbeans/xml/data-source.xml",
        "org/test4j/module/spring/testedbeans/xml/annotation-config.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*", impl = "**.*Impl") })
public class ResourcePropertiesRegisterTest extends Test4J {

    @SpringBeanByName(claz = UserServiceResourceImpl.class)
    UserService userService;

    @Test
    // (description = "测试@Resource属性的自动注入")
    public void testRegisterProperties() {
        want.object(userService).notNull();
        Object bean1 = spring.getBean("userDao");
        want.object(bean1).notNull();
        Object bean2 = reflector.getField(userService, "userDao");
        want.object(bean2).notNull().same(bean1);
    }
}
