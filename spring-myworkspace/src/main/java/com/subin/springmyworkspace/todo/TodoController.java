package com.subin.springmyworkspace.todo;

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
public class TodoController {

	private TodoRepository repo;

	@Autowired
	public TodoController(TodoRepository repo) {
		this.repo = repo;
	}

	// GET 프로토콜://서버주소:포트/todos
	@GetMapping(value = "/todos")
	public List<Todo> getTodoList() {

		return repo.findAll(Sort.by("id").descending());

	}

	@GetMapping(value = "/todos/paging")
	public Page<Todo> getTodoListPaging(@RequestParam int page, @RequestParam int size) {

		return repo.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
	}

	@GetMapping(value = "/todos/search")
	public Page<Todo> getTodoListSearch(@RequestParam int page, @RequestParam int size, @RequestParam String keyword) {
//		return repo.findByMemo(PageRequest.of(page, size, Sort.by("id").descending()), keyword);
		return repo.findByMemoContaining(PageRequest.of(page, size, Sort.by("id").descending()), keyword);
	}

	// 1건 추가
	// POST /todos {memo:"Spring 공부하기"}
	@PostMapping(value = "/todos")
	public Todo addTodo(@RequestBody Todo todo, HttpServletResponse res) {

		// 메모가 빈 값이면, 400 에러처리
		// 데이터를 서버에서 처리하는 양식에 맞게 보내지 않았음.
		if (todo.getMemo() == null || todo.getMemo().equals("")) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		// 데이터 검증(validation)과 소독(sanitization)
		// 클라이언트에서 넘오는 데이터에 대해서 점검
		todo.setCreatedTime(new Date().getTime());

		// repository.save(entity)
		// @Id: 필드 값이 없으면 INSERT, 있으면 UPDATE
		return repo.save(todo);
	}

	// 1건 조회
	// GET /todos/1 -> todo목록에서 id가 1인 레코드 조회
//	@GetMapping(value = "/todos/{id}")
	@RequestMapping(method = RequestMethod.GET, value = "/todos/{id}")
	public Todo getTodo(@PathVariable int id, HttpServletResponse res) {

		// SELECT ... FROM todo WHERE id = ?
		// null-safe한 방법으로 객체를 처리하게 함
		Optional<Todo> todo = repo.findById(id);

		// 리소스가 없으면 404 에러를 띄워줌
		if (todo.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		return todo.get();
//		return todo.orElse(null);
	}

	// 1건 삭제
	// DELETE /todos/1 -> todo목록에서 id가 1인 레코드 삭제
	@DeleteMapping(value = "/todos/{id}")
	public boolean removeTodo(@PathVariable int id, HttpServletResponse res) {

		Optional<Todo> todo = repo.findById(id);

		// 리소스가 없으면 404 에러를 띄워줌
		if (todo.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}

		// soft-delete: 특정 컬럼을 업데이트하여 조회할 때 안 보이게함.
		// hard-delete: 실제로 DELETE 문으로 레코드를 삭제
		// JPA repository는 기본적으로 hard-delete를 사용한다.
		repo.deleteById(id);
		return true;
	}

	// 1건 수정
	// PUT /todo/1 {"memo":"JPA 공부하기"}
	@PutMapping(value = "/todos/{id}")
	public Todo modifyTodo(@PathVariable int id, @RequestBody Todo todo, HttpServletResponse res) {

		// 데이터를 수정할 때는 특정 필드만 업데이트 해야함
		// ex) 이전에 입력한 시스템 필드는 변경하면 안 됨.

		// repository.save(entity)
		// id값 제외하고 전체 필드를 업데이트함

		/* -- 기존데이터 조회 후 변경된 데이터만 설정한 다음에 save --- */

		// 1. 기존 데이터 조회
		Optional<Todo> findedTodo = repo.findById(id);

		// 2. (요청데이터 검증1) id에 해당하는 리소스가 없으면 404 에러를 띄워줌
		if (findedTodo.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		// 3. (요청데이터 검증2) memo 필드가 빈값이면 400에러를 띄워줌
		if (todo.getMemo() == null && todo.getMemo().equals("")) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		// 4. 데이터베이스에서 읽어온 기존 데이터에 변경할 필드만 수정함
		Todo toUpdateTodo = findedTodo.get();
		toUpdateTodo.setMemo(todo.getMemo());

		// 5. 레코드 update
		return repo.save(toUpdateTodo);
	}
}