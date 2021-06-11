package com.subin.springmyworkspace.contact;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
	Page<Contact> findByName(Pageable page, String name);

	Page<Contact> findByNum(Pageable page, String num);

	Page<Contact> findByMail(Pageable page, String mail);

	// SELECT * FROM contact WHERE name LIKE '%매개변수%';
	Page<Contact> findByNameContaining(Pageable page, String name);

	Page<Contact> findByNumContaining(Pageable page, String num);

	Page<Contact> findByMailContaining(Pageable page, String mail);
}
