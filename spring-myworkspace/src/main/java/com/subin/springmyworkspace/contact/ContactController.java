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

	// GET 프로토콜: //서버주소:포트/todos
	@GetMapping(value = "/contacts")
	public List<Contact> getContactList() {
		return repo.findAll();
	}

	// 1건 추가
	@PostMapping(value = "/contacts")
	public Contact addContact(@RequestBody Contact contact, HttpServletResponse res) {
		// 메모가 빈 값이면, 400 에러처리
		// 데이터를 서버에서 처리하는 양식에 맞게 보내지 않았음.

		// 이름이 PK이므로 이름만 입력되면 넘어가는걸로
		if (contact.getName() == null || contact.getName().equals("")) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		contact.setCreatedTime(new Date().getTime());
		return repo.save(contact);
	}

	// 1건 조회
	@RequestMapping(method = RequestMethod.GET, value = "contacts/{id}")
	public Contact getContact(@PathVariable int id, HttpServletResponse res) {

		Optional<Contact> contact = repo.findById(id);

		if (contact.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		return contact.get();
	}

	// 1건 삭제
	// DELETE /contacts/1 -> contact목록에서 id가 1인 레코드 삭제
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

	// 1건 수정
	@PutMapping(value = "/contacts/{id}")
	public Contact modifyContact(@PathVariable int id, @RequestBody Contact contact, HttpServletResponse res) {
		// 1. 기존 데이터 조회
		Optional<Contact> findedContact = repo.findById(id);

		// 2. (요청데이터 검증1) id에 해당하는 리소스가 없으면 404 에러를 띄워줌
		if (findedContact.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		// 3. (요청데이터 검증2) memo 필드가 빈값이면 400에러를 띄워줌
		if (contact.getName() == null && contact.getName().equals("")) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		// 4. 데이터베이스에서 읽어온 기존 데이터에 변경할 필드만 수정함
		Contact toUpdateContact1 = findedContact.get();
		toUpdateContact1.setName(contact.getName());

		// 5. 레코드 update
		return repo.save(toUpdateContact1);
	}

}
