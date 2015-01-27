package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test", produces = "application/json")
public class TestController {

	@Autowired
	private TestRepository repo;
	@Autowired
	private JmsTemplate jmsTemplate;

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> create(@PathVariable("id") Long id, @RequestBody TestEntity testEntity) {
		testEntity.setId(id);
		TestEntity created = repo.save(testEntity);
		jmsTemplate.convertAndSend("testqueue", created);
		return new ResponseEntity(created, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> read(@PathVariable("id") Long id) {
		return new ResponseEntity(repo.findOne(id), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> findAll() {
		return new ResponseEntity(repo.findAll(), HttpStatus.OK);
	}

}
