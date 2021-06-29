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

	// GET ��������: //�����ּ�:��Ʈ/fitness
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

	// 1�� �߰�
	@PostMapping(value = "/fitness")
	public Fitness addFitness(@RequestBody Fitness fitness, HttpServletResponse res) {
		// �޸� �� ���̸�, 400 ����ó��
		// �����͸� �������� ó���ϴ� ��Ŀ� �°� ������ �ʾ���.
		// �̸�, ��ȣ, ���� �� 1���� �� ���� �ִٸ� �ȵ�.
		if (fitness.getHeight() == null || fitness.getWeight() == null || fitness.getHeight().equals("0")) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		fitness.setCreatedTime(new Date().getTime());
		return repo.save(fitness);
	}

	// 1�� ��ȸ
	@RequestMapping(method = RequestMethod.GET, value = "fitness/{id}")
	// @PathVariable: URL ��ο� ������ �־��ִ°�
	public Fitness getFitness(@PathVariable int id, HttpServletResponse res) {

		// null-safe(��ü������ null �Ǵ� void ���� ���� ������ ��ü ���� ���α׷��� ��� ������ ����) ������� ��ü ó��
		Optional<Fitness> fitness = repo.findById(id);

		// ���ҽ��� ������ 404 ������ �����
		if (fitness.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		return fitness.get();
	}

	// 1�� ����
	// DELETE /fitness/1 -> fitness��Ͽ��� id�� 1�� ���ڵ� ����
	@DeleteMapping(value = "fitness/{id}")
	public boolean removeFitness(@PathVariable int id, HttpServletResponse res) {
		Optional<Fitness> fitness = repo.findById(id);

		// ���ҽ��� ������ 404 ������ �����
		if (fitness.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}
		repo.deleteById(id);
		return true;
	}

	// 1�� ����
	@PutMapping(value = "/fitness/{id}")
	public Fitness modifyFitness(@PathVariable int id, @RequestBody Fitness fitness, HttpServletResponse res) {
		// 1. ���� ������ ��ȸ
		Optional<Fitness> findedFitness = repo.findById(id);

		// 2. (��û������ ����1) id�� �ش��ϴ� ���ҽ��� ������ 404 ������ �����
		if (findedFitness.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		// 3. (��û������ ����2) �Է��ʵ尡 ���̸� 400������ �����
		if (fitness.getHeight() == null || fitness.getWeight().equals("")) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		// 4. �����ͺ��̽����� �о�� ���� �����Ϳ� ������ �ʵ常 ������

		Fitness toUpdateFitness = findedFitness.get();
		toUpdateFitness.setHeight(fitness.getHeight());
		toUpdateFitness.setWeight(fitness.getWeight());
		toUpdateFitness.setSmm(fitness.getSmm());
		toUpdateFitness.setFat(fitness.getFat());
		// 5. ���ڵ� update

		return repo.save(toUpdateFitness);

	}

}
