package com.gauravrmsc.job_portal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gauravrmsc.job_portal.controller.dto.JobDto;
import com.gauravrmsc.job_portal.controller.dto.SearchQuery;
import com.gauravrmsc.job_portal.repository.Entity.Job;
import com.gauravrmsc.job_portal.repository.Entity.SearchIndex;
import com.gauravrmsc.job_portal.repository.Entity.types.Location;
import com.gauravrmsc.job_portal.service.JobService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Handles Post Job and Search Job requests
 */
@RestController
@RequestMapping("/jobs")
public class JobsController {

    public static String SERVER_ERROR_MESSAGE = "The server is currently facing some issue\n Try After some time";
    @Autowired
    JobService jobService;

    /**
     * @param searchQuery Object with location and Search Keywords Details
     * @return List of jobs
     */
    @GetMapping("/search")
    public ResponseEntity searchJobs(@Valid SearchQuery searchQuery) {
        ObjectMapper objectMapper = new ObjectMapper();
        Location location = new Location(searchQuery.getLatitude(), searchQuery.getLongitude(), null);
        List<Job> jobs = null;
        if (searchQuery.getSkillIds() == null || searchQuery.getSkillIds().size() == 0) {
            jobs = jobService.searchJobs(location);
        } else {
            jobs = jobService.searchJobs(location, searchQuery.getSkillIds());
        }
        if (jobs == null) {
            return ResponseEntity.status(500).body(SERVER_ERROR_MESSAGE);
        }
        List<JobDto> jobDtos = new ArrayList<>();
        for (Job job : jobs) {
            jobDtos.add(toJobDto(job));
        }
        return ResponseEntity.ok(jobDtos);
    }

    /**
     * @param jobDto Dto with new Job Details
     * @return Server Response. If the post is created successfully then Id of the post will be returned
     */
    @PostMapping("/postJob")
    public ResponseEntity addJob(@Valid @RequestBody JobDto jobDto) {
        Job job = toJobEntity(jobDto);
        job = jobService.addJob(job);
        if (job.getId() == null) {
            return ResponseEntity.status(500).body(SERVER_ERROR_MESSAGE);
        }
        return ResponseEntity.ok(job.getId());
    }

    private Job toJobEntity(JobDto jobDto) {
        ModelMapper mapper = new ModelMapper();
        Job job = mapper.map(jobDto, Job.class);
        List<SearchIndex> searchIndices = new ArrayList<>();
        Location location = jobDto.getLocation();
        if (jobDto.getSkills() == null || jobDto.getSkills().size() == 0) {
            jobDto.setSkills(Arrays.asList(-1l));
        }
        for (long skill : jobDto.getSkills()) {
            SearchIndex searchIndex = jobService.createSearchIndex(location, skill);
            searchIndices.add(searchIndex);
        }
        job.setSearchIndices(searchIndices);
        Date date = new Date();
        date.setTime(jobDto.getCreationDate());
        job.setCreationDate(date);
        return job;
    }

    private JobDto toJobDto(Job job) {
        ModelMapper mapper = new ModelMapper();
        JobDto jobDto = mapper.map(job, JobDto.class);
        List<SearchIndex> searchIndices = job.getSearchIndices();
        List<Long> skillIds = new ArrayList<>();
        for (SearchIndex searchIndex : searchIndices) {
            skillIds.add(searchIndex.getSkill().getId());
        }
        jobDto.setSkills(skillIds);
        return jobDto;
    }

}
