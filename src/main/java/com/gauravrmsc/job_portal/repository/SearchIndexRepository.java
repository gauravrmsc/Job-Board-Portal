package com.gauravrmsc.job_portal.repository;

import com.gauravrmsc.job_portal.repository.Entity.SearchIndex;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchIndexRepository extends CrudRepository<SearchIndex, Long> {
}
