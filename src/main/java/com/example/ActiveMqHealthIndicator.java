package com.example;

import javax.jms.Connection;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqHealthIndicator implements HealthIndicator {

	private static final Logger log = LoggerFactory.getLogger(ActiveMqHealthIndicator.class);

	private final ActiveMQConnectionFactory connectionFactory;

	@Autowired
	public ActiveMqHealthIndicator(ActiveMQConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Override
	public Health health() {
		Connection connection = null;
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			return Health.up().withDetail("brokerUrl", connectionFactory.getBrokerURL()).build();
		} catch (JMSException e) {
			return Health.down(e).build();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					log.warn("error closing connection", e);
				}
			}
		}
	}

}
