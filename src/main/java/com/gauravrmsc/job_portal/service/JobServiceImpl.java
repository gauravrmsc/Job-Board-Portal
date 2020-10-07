package com.gauravrmsc.job_portal.service;

import ch.hsr.geohash.GeoHash;
import com.gauravrmsc.job_portal.repository.Entity.Job;
import com.gauravrmsc.job_portal.repository.Entity.SearchIndex;
import com.gauravrmsc.job_portal.repository.Entity.Skill;
import com.gauravrmsc.job_portal.repository.Entity.types.Location;
import com.gauravrmsc.job_portal.repository.JobRepository;
import com.gauravrmsc.job_portal.repository.SearchIndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Contains business logic for Job related queries like post a new Job, Search Jobs
 */
@Service
public class JobServiceImpl implements JobService {
    @Autowired
    JobRepository jobRepository;
    @Autowired
    SearchIndexRepository searchIndexRepository;

    @Override
    public Job addJob(Job job) {
        List<SearchIndex> searchIndices = job.getSearchIndices();
//        if (searchIndices == null || searchIndices.size() == 0) {
//            searchIndices.add()
//        }
        job = jobRepository.save(job);
        for (SearchIndex searchIndex : searchIndices) {
            searchIndex.setJob(job);
        }
        searchIndexRepository.saveAll(searchIndices);
        return job;
    }

    @Override
    public List<Job> searchJobs(Location location, List<Long> skills) {
        String locationHash = getLocationHash(location.getLatitude(), location.getLongitude());
        List<Job> jobs = jobRepository.searchJobsByTagsAndLocation(locationHash, skills);
        Date today = new Date();
        for (int i = 0; i < jobs.size(); i++) {
            Job job = jobs.get(i);
            if (!isValid(job)) {
                jobs.remove(i);
                removeJob(job);
                System.out.println("Expired");
            }
        }
        return jobs;
    }

    @Override
    public List<Job> searchJobs(Location location) {
        String locationHash = getLocationHash(location.getLatitude(), location.getLongitude());
        List<Job> jobs = jobRepository.searchJobsByLocation(locationHash);
        for (int i = 0; i < jobs.size(); i++) {
            Job job = jobs.get(i);
            if (!isValid(job)) {
                jobs.remove(i);
                removeJob(job);
            }
        }
        return jobs;
    }

    private boolean isValid(Job job) {
        Date today = new Date();
        Date expiryDate = new Date();
        long expiryTime = job.getExpiry() * 24 * 60 * 60 * 1000;
        expiryDate.setTime(job.getCreationDate().getTime() + expiryTime);
        if (expiryDate.before(today)) {
            return false;
        }
        return true;
    }

    @Override
    public void removeJob(Job job) {
        jobRepository.delete(job);
    }

    @Override
    public SearchIndex createSearchIndex(Location location, long skillId) {
        SearchIndex searchIndex = new SearchIndex();
        Skill skill = new Skill();
        skill.setId(skillId);
        searchIndex.setSkill(skill);
        String locationHash = getLocationHash(location.getLatitude(), location.getLongitude());
        searchIndex.setLocationHash(locationHash);
        return searchIndex;
    }

    private String getLocationHash(double longitude, double latitude) {
        GeoHash locationHash = GeoHash.withCharacterPrecision(latitude, longitude, 2);
        return locationHash.toBase32();
    }


}
