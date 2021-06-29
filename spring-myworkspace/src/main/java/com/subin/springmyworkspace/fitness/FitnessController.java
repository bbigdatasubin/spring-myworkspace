package com.subin.springmyworkspace.fitness;

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
public class FitnessController {

	private FitnessRepository repo;

	@Autowired
	public FitnessController(FitnessRepository repo) {
		this.repo = repo;
	}

	// GET 프로토콜: //서버주소:포트/fitness
	@GetMapping(value = "/fitness")
	public List<Fitness> getFitnesstList() {
		return repo.findAll(Sort.by("id").descending());
	}

	@GetMapping(value = "/fitness/paging")
	public Page<Fitness> getFitnessListPaging(@RequestParam int page, @RequestParam int size) {

		return repo.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
	}

	@GetMapping(value = "/fitness/search")
	public Page<Fitness> getFitnessListSearch(@RequestParam int page, @RequestParam int size,
			@RequestParam String keyword) {
//		return repo.findByMemo(PageRequest.of(page, size, Sort.by("id").descending()), keyword);
		return repo.findByHeightContaining(PageRequest.of(page, size, Sort.by("id").descending()), keyword);

	}

	// 1건 추가
	@PostMapping(value = "/fitness")
	public Fitness addFitness(@RequestBody Fitness fitness, HttpServletResponse res) {
		// 메모가 빈 값이면, 400 에러처리
		// 데이터를 서버에서 처리하는 양식에 맞게 보내지 않았음.
		// 이름, 번호, 메일 中 1개라도 빈 값이 있다면 안됨.
		if (fitness.getHeight() == null || fitness.getWeight() == null || fitness.getHeight().equals("0")) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		fitness.setCreatedTime(new Date().getTime());
		return repo.save(fitness);
	}

	// 1건 조회
	@RequestMapping(method = RequestMethod.GET, value = "fitness/{id}")
	// @PathVariable: URL 경로에 변수를 넣어주는거
	public Fitness getFitness(@PathVariable int id, HttpServletResponse res) {

		// null-safe(객체참조가 null 또는 void 값을 갖지 않음을 객체 지향 프로그래밍 언어 내에서 보장) 방법으로 객체 처리
		Optional<Fitness> fitness = repo.findById(id);

		// 리소스가 없으면 404 에러를 띄워줌
		if (fitness.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		return fitness.get();
	}

	// 1건 삭제
	// DELETE /fitness/1 -> fitness목록에서 id가 1인 레코드 삭제
	@DeleteMapping(value = "fitness/{id}")
	public boolean removeFitness(@PathVariable int id, HttpServletResponse res) {
		Optional<Fitness> fitness = repo.findById(id);

		// 리소스가 없으면 404 에러를 띄워줌
		if (fitness.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}
		repo.deleteById(id);
		return true;
	}

	// 1건 수정
	@PutMapping(value = "/fitness/{id}")
	public Fitness modifyFitness(@PathVariable int id, @RequestBody Fitness fitness, HttpServletResponse res) {
		// 1. 기존 데이터 조회
		Optional<Fitness> findedFitness = repo.findById(id);

		// 2. (요청데이터 검증1) id에 해당하는 리소스가 없으면 404 에러를 띄워줌
		if (findedFitness.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		// 3. (요청데이터 검증2) 입력필드가 빈값이면 400에러를 띄워줌
		if (fitness.getHeight() == null || fitness.getWeight().equals("")) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		// 4. 데이터베이스에서 읽어온 기존 데이터에 변경할 필드만 수정함

		Fitness toUpdateFitness = findedFitness.get();
		toUpdateFitness.setHeight(fitness.getHeight());
		toUpdateFitness.setWeight(fitness.getWeight());
		toUpdateFitness.setSmm(fitness.getSmm());
		toUpdateFitness.setFat(fitness.getFat());
		// 5. 레코드 update

		return repo.save(toUpdateFitness);

	}

}
