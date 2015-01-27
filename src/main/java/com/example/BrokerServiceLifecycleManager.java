package com.example;

import javax.annotation.PreDestroy;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrokerServiceLifecycleManager {

	private static final Logger log = LoggerFactory.getLogger(BrokerServiceLifecycleManager.class);

	private final BrokerService brokerService;

	@Autowired
	public BrokerServiceLifecycleManager(ActiveMQConnectionFactory connectionFactory) throws Exception {
		brokerService = new BrokerService();
		brokerService.addConnector(connectionFactory.getBrokerURL());
		log.info("starting AMQ broker service on: {}", brokerService.getTransportConnectorURIsAsMap());
		brokerService.start();
	}

	@PreDestroy
	public void stop() {
		log.info("stopping AMQ broker service");
		try {
			brokerService.stop();
		} catch (Exception e) {
			log.warn("error stopping AMQ broker service", e);
		}
	}
}
