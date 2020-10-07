package com.gauravrmsc.job_portal.repository;

import com.gauravrmsc.job_portal.repository.Entity.Skill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends CrudRepository<Skill, Long> {
}
