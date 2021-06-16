package com.subin.springmyworkspace.opendata.fitness;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FitnessController {

	private FitnessRepository repo;

	public FitnessController(FitnessRepository repo) {
		this.repo = repo;
	}

	@RequestMapping(value = "/opendata/fitness/Fitness", method = RequestMethod.GET)
	public List<Fitness> getListByDataType() {
		Order[] orders = { new Order(Sort.Direction.DESC, "ageDegree"), new Order(Sort.Direction.ASC, "testYm") };

		return repo.findAll(PageRequest.of(0, 24, Sort.by(orders))).toList();
	}
}