package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class JpaEntityLifecycleEventsApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(JpaEntityLifecycleEventsApplicationTests.class);

  @Autowired
  private EntityManager entityManager;
  
  @Autowired
  private CustomerRepository customerRepository;
  
  @Autowired
  private CustomerLinkRepository customerLinkRepository;
  
  @Test
	void testEverything() {
    // save a few customers
    Customer customer1 = new Customer("Test1", "User1", "secret1");
    customerRepository.save(customer1);
    Customer customer2 = new Customer("Test2", "User2", "secret2");
    customerRepository.save(customer2);
    Customer customer3 = new Customer("Test3", "User3", "secret3");
    customerRepository.save(customer3);

    customerRepository.flush();
    entityManager.clear();

    // exerciseCustomers();
    entityManager.clear();

    // save a few customer links
    CustomerLink customerLink1 = new CustomerLink("customer1");
    customerLink1.setCustomer(customerRepository.findById(1L));
    customerLinkRepository.save(customerLink1);
    CustomerLink customerLink2 = new CustomerLink("customer2");
    customerLink2.setCustomer(customerRepository.findById(2L));
    customerLinkRepository.save(customerLink2);
    CustomerLink customerLink3 = new CustomerLink("customer3");
    customerLink3.setCustomer(customerRepository.findById(3L));
    customerLinkRepository.save(customerLink3);

    customerLinkRepository.flush();
    entityManager.clear();

    // exerciseCustomerLinks();
    entityManager.clear();

    log.info("");
    log.info("Find a Customer with findById(1L):");
    log.info("----------------------------------");
    Customer customer = customerRepository.findById(1L);
    log.info(customer.toString());
    log.info("");

    log.info("Find a CutomerLink with findByAccount('customer1'):");
    log.info("---------------------------------------------------");
    customerLinkRepository.findByAccount("customer1").forEach(found -> log.info(found.toString()));
    log.info("");
  }

  private void exerciseCustomers() {
    // fetch all customer
    log.info("Customers found with findAll():");
    log.info("-------------------------------");
    for (Customer customer : customerRepository.findAll()) {
      log.info(customer.toString());
    }
    log.info("");

    // fetch an individual customer by ID
    Customer customer = customerRepository.findById(1L);
    log.info("Customer found with findById(1L):");
    log.info("---------------------------------");
    log.info(customer.toString());
    log.info("");

    // fetch customers by last name
    log.info("Cutomer found with findByLastName('User3'):");
    log.info("-------------------------------------------");
    customerRepository.findByLastName("User3").forEach(found -> log.info(found.toString()));
    log.info("");
  }

  private void exerciseCustomerLinks() {
    // fetch all customer
    log.info("CustomerLinks found with findAll():");
    log.info("-----------------------------------");
    for (CustomerLink customerLink : customerLinkRepository.findAll()) {
      log.info(customerLink.toString());
    }
    log.info("");

    // fetch an individual customer link by ID
    CustomerLink customerLink = customerLinkRepository.findById(4L);
    log.info("CustomerLink found with findById(4L):");
    log.info("-------------------------------------");
    log.info(customerLink.toString());
    log.info("");

    // fetch customer links by account
    log.info("CutomerLink found with findByAccount('customer3'):");
    log.info("--------------------------------------------------");
    customerLinkRepository.findByAccount("customer3").forEach(found -> log.info(found.toString()));
    log.info("");
  }
}
