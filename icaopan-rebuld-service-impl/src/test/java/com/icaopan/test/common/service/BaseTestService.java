package com.icaopan.test.common.service;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.testng.JTester;

@SpringApplicationContext({"spring-mybatis-test.xml","provider-marketdata.xml","provider-securitydata.xml","server-ems-amqp-normal.xml"})
public class BaseTestService extends JTester {

}
