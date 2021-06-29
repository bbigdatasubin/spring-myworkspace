package com.subin.springmyworkspace.todo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;

//Aggregation Root 객체
//집합뿌리객체

// 예) Order 주문정보
// 예) OrderDetail, OrderLineTime: 주문상세, 주문 제품목록
@Data
@Entity
public class Todo {

	// @Id -> 테이블의 PK(유일/대표 컬럼)
	@Id
	// @GeneratedValue -> 필드 값 생성 방법 정의, IDENTITY는 데이터베이스의 자동증가값을 사용
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String memo;
	private Long createdTime;

//  @JsonIgnore: JSON으로 변환할때보여주지않음
	@OneToMany // OneToMany(Order-OrderDetail), OnetoOne(User-Profile),
	// EAGER:상위객체 읽을 때 하위 객체 같이 읽음
	// LAZY: 하위객체를 사용할 때 하위 객체를 읽어옴, 기본 패치전략
	// ManytoMany(JoinColumn쓰면안됨)
	@JoinColumn(name = "todoId") // todoId(필드명), todo_id(컬럼명)
	private List<TodoComment> comments;

}
