package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@Autowired
	private TestRepository repo;
	@Autowired
	private JmsTemplate jmsTemplate;

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody TestEntity testEntity) {
		TestEntity created = repo.save(testEntity);
		jmsTemplate.convertAndSend("testqueue", created);
		return new ResponseEntity(created, HttpStatus.OK);
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseEntity<?> read(Long id) {
		return new ResponseEntity(repo.findOne(id), HttpStatus.OK);
	}

}
