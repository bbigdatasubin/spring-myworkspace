package com.subin.springmyworkspace.opendata.weatherUV;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherUVController {

	private WeatherUVRepository repo;

	public WeatherUVController(WeatherUVRepository repo) {
		this.repo = repo;
	}

	@Cacheable(cacheNames = "weatherUV", key = "0")

	@RequestMapping(value = "/opendata/weatherUV/WeatherUV", method = RequestMethod.GET)
	public List<WeatherUV> getListByDataType() {
		Order[] orders = { new Order(Sort.Direction.DESC, "date"), new Order(Sort.Direction.ASC, "areaNo") };

		return repo.findAll(PageRequest.of(0, 50, Sort.by(orders))).toList();
	}
}