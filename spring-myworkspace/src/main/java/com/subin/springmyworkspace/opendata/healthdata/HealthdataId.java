package com.subin.springmyworkspace.opendata.healthdata;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthdataId implements Serializable {
	private static final long serialVersionUID = 1743176488971107945L;
	private String itemF001; // 신장
	private String itemF002;
	private String testYm; // 측정년월

}
