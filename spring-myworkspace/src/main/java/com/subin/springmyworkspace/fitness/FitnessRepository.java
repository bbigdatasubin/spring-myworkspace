package com.subin.springmyworkspace.fitness;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FitnessRepository extends JpaRepository<Fitness, Integer> {

	Page<Fitness> findByHeight(Pageable page, String height);

	Page<Fitness> findByWeight(Pageable page, String weight);

	Page<Fitness> findBySmm(Pageable page, String smm);

	Page<Fitness> findByFat(Pageable page, String fat);

	// SELECT * FROM fitness WHERE name LIKE '%매개변수%';

	Page<Fitness> findByHeightContaining(Pageable page, String height);

	Page<Fitness> findByWeightContaining(Pageable page, String weight);

	Page<Fitness> findBySmmContaining(Pageable page, String smm);

	Page<Fitness> findByFatContaining(Pageable page, String fat);

}
