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
    createCustomer("Test1", "User1", "secret1");
    createCustomer("Test2", "User2", "secret2");
    createCustomer("Test3", "User3", "secret3");

    log.info("Flush Customers...");
    customerRepository.flush();
    log.info("Clear Customers from cache...");
    entityManager.clear();

    // exerciseCustomers();
    entityManager.clear();

    // save a few customer links
    createCustomerLink("customer1", 1L);
    createCustomerLink("customer2", 2L);
    createCustomerLink("customer3", 3L);

    log.info("Flush CustomerLinks...");
    customerLinkRepository.flush();
    log.info("Clear CustomerLinks from cache...");
    entityManager.clear();

    // exerciseCustomerLinks();
    entityManager.clear();

    log.info("");
    log.info("Scenario 1 - Find the same Customer/CustomerLink by Primary Key (entity can be identified in the cache)");
    log.info("-------------------------------------------------------------------------------------------------------");
    log.info("");
    log.info("Find Customer with findById(1L):");
    log.info("--------------------------------");
    Customer customerS1 = customerRepository.findById(1L);
    log.info(customerS1.toString());
    log.info("");

    log.info("Find CustomerLink for the same Customer with findById(4L):");
    log.info("----------------------------------------------------------");
    CustomerLink customerLinkS1 = customerLinkRepository.findById(4L);
    log.info(customerLinkS1.toString());
    log.info("");

    entityManager.clear();

    log.info("");
    log.info("Scenario 2 - Find the same Customer/CustomerLink by Alternate Key (cache is flushed and linked entity is the same)");
    log.info("------------------------------------------------------------------------------------------------------------------");
    log.info("");
    log.info("Find Customer with findById(1L):");
    log.info("--------------------------------");
    Customer customerS2 = customerRepository.findById(1L);
    log.info(customerS2.toString());
    log.info("");

    // detaching the intial Customer entity would give the correct result.
    // entityManager.detach(customerS2);

    log.info("Find CustomerLink for the same Customer with findByAccount('customer1'):");
    log.info("------------------------------------------------------------------------");
    CustomerLink customerLinkS2 = customerLinkRepository.findByAccount("customer1");
    log.info(customerLinkS2.toString());
    log.info("");

    entityManager.clear();

    log.info("");
    log.info("Scenario 3 - Find different Customer/CustomerLink by Alternate Key (cache is flushed but linked entity is different)");
    log.info("--------------------------------------------------------------------------------------------------------------------");
    log.info("");
    log.info("Find Customer with findById(1L):");
    log.info("-------------- -----------------");
    Customer customerS3 = customerRepository.findById(1L);
    log.info(customerS3.toString());
    log.info("");

    log.info("Find CustomerLink for a diferent Customer with findByAccount('customer3'):");
    log.info("--------------------------------------------------------------------------");
    CustomerLink customerLinkS3 = customerLinkRepository.findByAccount("customer3");
    log.info(customerLinkS3.toString());
    log.info("");
  }

  private void createCustomer(String firstName, String lastName, String secret) {
    Customer customer = new Customer(firstName, lastName, secret);
    customerRepository.save(customer);
  }

  private void createCustomerLink(String account, Long customerId) {
    CustomerLink customerLink = new CustomerLink(account);
    customerLink.setCustomer(customerRepository.findById(customerId).orElseThrow());
    customerLinkRepository.save(customerLink);
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
    log.info("Customer found with findById(1L):");
    log.info("---------------------------------");
    Customer customer = customerRepository.findById(1L);
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
    log.info("CustomerLink found with findById(4L):");
    log.info("-------------------------------------");
    CustomerLink customerLinkById = customerLinkRepository.findById(4L);
    log.info(customerLinkById.toString());
    log.info("");

    // fetch customer links by account
    log.info("CutomerLink found with findByAccount('customer3'):");
    log.info("--------------------------------------------------");
    CustomerLink customerLinkByAccount = customerLinkRepository.findByAccount("customer3");
    log.info(customerLinkByAccount.toString());
    log.info("");
  }
}
