package com.example;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


import com.example.dao.ClientRepository;
import com.example.entities.Client;

@SpringBootApplication

public class DemoApplication implements CommandLineRunner {
	private Logger log = Logger.getLogger(DemoApplication.class);
	@Autowired
	ClientRepository clientRepository;
	public static void main(String[] args) {
		  SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
		Client client=clientRepository.save(new Client("fr"));
		log.info("Saved client nom  " + client.getNom());
		
	}
}
