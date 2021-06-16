package com.subin.springmyworkspace.opendata.fitness;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FitnessId implements Serializable {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1743176488971107945L;

	private String ageDegree; // ����
	private String itemF001; // ����
	private String testYm; // �������

}
