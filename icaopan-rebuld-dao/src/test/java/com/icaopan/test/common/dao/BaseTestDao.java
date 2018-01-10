package com.icaopan.test.common.dao;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.testng.JTester;

@SpringApplicationContext({ "spring-mybatis-test.xml" })
public abstract class BaseTestDao extends JTester {
}
