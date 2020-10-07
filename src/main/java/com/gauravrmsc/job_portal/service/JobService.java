package com.gauravrmsc.job_portal.service;

import com.gauravrmsc.job_portal.repository.Entity.Job;
import com.gauravrmsc.job_portal.repository.Entity.SearchIndex;
import com.gauravrmsc.job_portal.repository.Entity.types.Location;

import java.util.List;

public interface JobService {
    Job addJob(Job job);

    List<Job> searchJobs(Location location, List<Long> skills);

    List<Job> searchJobs(Location location);

    void removeJob(Job job);

    SearchIndex createSearchIndex(Location location, long skillId);
}
