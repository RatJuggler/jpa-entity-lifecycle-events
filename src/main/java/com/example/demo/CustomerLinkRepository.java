package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerLinkRepository extends JpaRepository<CustomerLink, Long> {

	List<CustomerLink> findByAccount(String account);

  CustomerLink findById(long id);
}
