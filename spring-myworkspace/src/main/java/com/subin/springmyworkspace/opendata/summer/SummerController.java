package com.subin.springmyworkspace.opendata.summer;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SummerController {

	private SummerRepository repo;

	public SummerController(SummerRepository repo) {
		this.repo = repo;
	}

	@Cacheable(cacheNames = "summer", key = "0")
	@RequestMapping(value = "/opendata/summer/Summer", method = RequestMethod.GET)
	public List<Summer> getListByDataType() {
		Order[] orders = { new Order(Sort.Direction.DESC, "date") };

		return repo.findAll(PageRequest.of(0, 24, Sort.by(orders))).toList();
	}
}