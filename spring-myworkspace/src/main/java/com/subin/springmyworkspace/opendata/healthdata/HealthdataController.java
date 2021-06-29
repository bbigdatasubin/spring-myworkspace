package com.subin.springmyworkspace.opendata.healthdata;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthdataController {

	private HealthdataRepository repo;

	public HealthdataController(HealthdataRepository repo) {
		this.repo = repo;
	}

	@Cacheable(cacheNames = "healthdata", key = "0")
	@RequestMapping(value = "/opendata/healthdata/Healthdata", method = RequestMethod.GET)
	public List<Healthdata> getListByDataType() {
		Order[] orders = { new Order(Sort.Direction.DESC, "testYm"), new Order(Sort.Direction.DESC, "itemF001"),
				new Order(Sort.Direction.DESC, "itemF002") };

		return repo.findAll(PageRequest.of(0, 827, Sort.by(orders))).toList();
	}
}