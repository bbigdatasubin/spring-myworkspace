package com.subin.springmyworkspace.contact;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

	private ContactRepository repo;

	@Autowired
	public ContactController(ContactRepository repo) {
		this.repo = repo;
	}

	// GET ��������: //�����ּ�:��Ʈ/todos
	@GetMapping(value = "/contacts")
	public List<Contact> getContactList() {
		return repo.findAll();
	}

	// 1�� �߰�
	@PostMapping(value = "/contacts")
	public Contact addContact(@RequestBody Contact contact, HttpServletResponse res) {
		// �޸� �� ���̸�, 400 ����ó��
		// �����͸� �������� ó���ϴ� ��Ŀ� �°� ������ �ʾ���.

		// �̸��� PK�̹Ƿ� �̸��� �ԷµǸ� �Ѿ�°ɷ�
		if (contact.getName() == null || contact.getName().equals("")) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		contact.setCreatedTime(new Date().getTime());
		return repo.save(contact);
	}

	// 1�� ��ȸ
	@RequestMapping(method = RequestMethod.GET, value = "contacts/{id}")
	public Contact getContact(@PathVariable int id, HttpServletResponse res) {

		Optional<Contact> contact = repo.findById(id);

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
		if (contact.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}
		repo.deleteById(id);
		return true;
	}

	// 1�� ����
	@PutMapping(value = "/contacts/{id}")
	public Contact modifyContact(@PathVariable int id, @RequestBody Contact contact, HttpServletResponse res) {
		// 1. ���� ������ ��ȸ
		Optional<Contact> findedContact = repo.findById(id);

		// 2. (��û������ ����1) id�� �ش��ϴ� ���ҽ��� ������ 404 ������ �����
		if (findedContact.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		// 3. (��û������ ����2) memo �ʵ尡 ���̸� 400������ �����
		if (contact.getName() == null && contact.getName().equals("")) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		// 4. �����ͺ��̽����� �о�� ���� �����Ϳ� ������ �ʵ常 ������
		Contact toUpdateContact1 = findedContact.get();
		toUpdateContact1.setName(contact.getName());

		// 5. ���ڵ� update
		return repo.save(toUpdateContact1);
	}

}
