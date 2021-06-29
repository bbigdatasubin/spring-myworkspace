//package com.subin.springmyworkspace.opendata.covid;
//
//import java.util.List;
//
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.domain.Sort.Order;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class CovidController {
//
//	private CovidRepository repo;
//
//	public CovidController(CovidRepository repo) {
//		this.repo = repo;
//	}
//
//	@RequestMapping(value = "/opendata/covid/Covid", method = RequestMethod.GET)
//	public List<Covid> getListByDataType() {
//		Order[] orders = { new Order(Sort.Direction.DESC, "createDt"), new Order(Sort.Direction.ASC, "seq") };
//
//		return repo.findAll(PageRequest.of(0, 24, Sort.by(orders))).toList();
//	}
//}
