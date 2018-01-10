package com.icaopan.msg.channel.mandao.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

	private static Properties msmRegisterProperties;
	private static Properties msmConfigProperties;
	private static Properties rabbitMQProperties;
	static {
		msmRegisterProperties = readPropertyFiles("stock_trade_server_conf.properties");
		msmConfigProperties = readPropertyFiles("stock_trade_server_conf.properties");
		rabbitMQProperties = readPropertyFiles("rabbitMQ.properties");
	}

	public static Properties readPropertyFiles(String propertyFilePath) {
		Properties prop = new Properties();
		InputStream in = PropertyReader.class.getClassLoader()
				.getResourceAsStream(propertyFilePath);
		try {
			prop.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	public static Properties getMsmRegisterProperties() {
		return msmRegisterProperties;
	}

	public static void setMsmRegisterProperties(Properties msmRegisterProperties) {
		PropertyReader.msmRegisterProperties = msmRegisterProperties;
	}

	public static Properties getMsmConfigProperties() {
		return msmConfigProperties;
	}

	public static void setMsmConfigProperties(Properties msmConfigProperties) {
		PropertyReader.msmConfigProperties = msmConfigProperties;
	}

	public static Properties getRabbitMQProperties() {
		return rabbitMQProperties;
	}

	public static void setRabbitMQProperties(Properties rabbitMQProperties) {
		PropertyReader.rabbitMQProperties = rabbitMQProperties;
	}
	
	
}
