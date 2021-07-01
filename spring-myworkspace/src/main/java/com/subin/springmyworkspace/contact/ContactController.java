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

	// GET 프로토콜: //서버주소:포트/contact
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

	// 1건 추가
	@PostMapping(value = "/contacts")
	public Contact addContact(@RequestBody Contact contact, HttpServletResponse res) {
		// 메모가 빈 값이면, 400 에러처리
		// 데이터를 서버에서 처리하는 양식에 맞게 보내지 않았음.
		// 이름, 번호, 메일 中 1개라도 빈 값이 있다면 안됨.
		if (contact.getName() == null || contact.getNum() == null || contact.getMail() == null
				|| contact.getName().equals("")) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		contact.setCreatedTime(new Date().getTime());
		return repo.save(contact);
	}

	// 1건 조회
	@RequestMapping(method = RequestMethod.GET, value = "contacts/{id}")
	// @PathVariable: URL 경로에 변수를 넣어주는거
	public Contact getContact(@PathVariable int id, HttpServletResponse res) {

		// null-safe(객체참조가 null 또는 void 값을 갖지 않음을 객체 지향 프로그래밍 언어 내에서 보장) 방법으로 객체 처리
		Optional<Contact> contact = repo.findById(id);

		// 리소스가 없으면 404 에러를 띄워줌
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

		// 리소스가 없으면 404 에러를 띄워줌
		if (contact.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}
		repo.deleteById(id);
		return true;
	}

	// 1건 수정
	@PutMapping(value = "/contacts/{id}")
	public Contact modifyContact1(@PathVariable int id, @RequestBody Contact contact, HttpServletResponse res) {
		// 1. 기존 데이터 조회
		Optional<Contact> findedContact = repo.findById(id);

		// 2. (요청데이터 검증1) id에 해당하는 리소스가 없으면 404 에러를 띄워줌
		if (findedContact.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		// 3. (요청데이터 검증2) name필드가 빈값이면 400에러를 띄워줌
		if (contact.getName() == null || contact.getName().equals("")) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		// 4. 데이터베이스에서 읽어온 기존 데이터에 변경할 필드만 수정함

		Contact toUpdateContact = findedContact.get();
		toUpdateContact.setName(contact.getName());
		toUpdateContact.setNum(contact.getNum());
		toUpdateContact.setMail(contact.getMail());

		// 5. 레코드 update

		return repo.save(toUpdateContact);

	}

}
