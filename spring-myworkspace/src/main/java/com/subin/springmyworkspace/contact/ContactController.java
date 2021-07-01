package com.subin.springmyworkspace.contact;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

	private ContactRepository repo;

	@Autowired
	public ContactController(ContactRepository repo) {
		this.repo = repo;
	}

	// GET ��������: //�����ּ�:��Ʈ/contact
	@GetMapping(value = "/contacts")
	public List<Contact> getContactList() {
		return repo.findAll(Sort.by("id").descending());
	}

	@GetMapping(value = "/contacts/paging")
	public Page<Contact> getContactListPaging(@RequestParam int page, @RequestParam int size) {

		return repo.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
	}

	@GetMapping(value = "/contacts/search")
	public Page<Contact> getContactListSearch(@RequestParam int page, @RequestParam int size,
			@RequestParam String keyword) {
//		return repo.findByMemo(PageRequest.of(page, size, Sort.by("id").descending()), keyword);
		return repo.findByNameContaining(PageRequest.of(page, size, Sort.by("id").descending()), keyword);

	}

	// 1�� �߰�
	@PostMapping(value = "/contacts")
	public Contact addContact(@RequestBody Contact contact, HttpServletResponse res) {
		// �޸� �� ���̸�, 400 ����ó��
		// �����͸� �������� ó���ϴ� ��Ŀ� �°� ������ �ʾ���.
		// �̸�, ��ȣ, ���� �� 1���� �� ���� �ִٸ� �ȵ�.
		if (contact.getName() == null || contact.getNum() == null || contact.getMail() == null
				|| contact.getName().equals("")) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		contact.setCreatedTime(new Date().getTime());
		return repo.save(contact);
	}

	// 1�� ��ȸ
	@RequestMapping(method = RequestMethod.GET, value = "contacts/{id}")
	// @PathVariable: URL ��ο� ������ �־��ִ°�
	public Contact getContact(@PathVariable int id, HttpServletResponse res) {

		// null-safe(��ü������ null �Ǵ� void ���� ���� ������ ��ü ���� ���α׷��� ��� ������ ����) ������� ��ü ó��
		Optional<Contact> contact = repo.findById(id);

		// ���ҽ��� ������ 404 ������ �����
		if (contact.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		return contact.get();
	}

	// 1�� ����
	// DELETE /contacts/1 -> contact��Ͽ��� id�� 1�� ���ڵ� ����
	@DeleteMapping(value = "contacts/{id}")
	public boolean removeContact(@PathVariable int id, HttpServletResponse res) {
		Optional<Contact> contact = repo.findById(id);

		// ���ҽ��� ������ 404 ������ �����
		if (contact.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}
		repo.deleteById(id);
		return true;
	}

	// 1�� ����
	@PutMapping(value = "/contacts/{id}")
	public Contact modifyContact1(@PathVariable int id, @RequestBody Contact contact, HttpServletResponse res) {
		// 1. ���� ������ ��ȸ
		Optional<Contact> findedContact = repo.findById(id);

		// 2. (��û������ ����1) id�� �ش��ϴ� ���ҽ��� ������ 404 ������ �����
		if (findedContact.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		// 3. (��û������ ����2) name�ʵ尡 ���̸� 400������ �����
		if (contact.getName() == null || contact.getName().equals("")) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		// 4. �����ͺ��̽����� �о�� ���� �����Ϳ� ������ �ʵ常 ������

		Contact toUpdateContact = findedContact.get();
		toUpdateContact.setName(contact.getName());
		toUpdateContact.setNum(contact.getNum());
		toUpdateContact.setMail(contact.getMail());

		// 5. ���ڵ� update

		return repo.save(toUpdateContact);

	}

}
