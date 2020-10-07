package com.gauravrmsc.job_portal.repository;

import com.gauravrmsc.job_portal.repository.Entity.Job;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends CrudRepository<Job, Long> {

    @Query(value = "select job.* from job inner join search_index on search_index.job_id = job.id where search_index.skill_id in (:Skills) and location_hash = :locationHash group by job.id order by count(*) desc, job.creation_date desc", nativeQuery = true)
    List<Job> searchJobsByTagsAndLocation(@Param("locationHash") String locationHash, @Param("Skills") List<Long> skillsIds);

    @Query(value = "select distinct job.* from job inner join search_index on search_index.job_id = job.id where location_hash = :locationHash order by job.creation_date desc", nativeQuery = true)
    List<Job> searchJobsByLocation(@Param("locationHash") String locationHash);
}
