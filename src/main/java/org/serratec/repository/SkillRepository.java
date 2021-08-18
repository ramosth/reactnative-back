package org.serratec.repository;

import java.util.Optional;

import org.serratec.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SkillRepository extends JpaRepository<Skill, Long>{

	boolean existsByName(String name);
	Optional<Skill> findByName(String name);
}
