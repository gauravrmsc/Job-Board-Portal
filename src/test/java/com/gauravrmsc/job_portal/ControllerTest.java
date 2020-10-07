package com.gauravrmsc.job_portal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gauravrmsc.job_portal.controller.JobsController;
import com.gauravrmsc.job_portal.controller.dto.JobDto;
import com.gauravrmsc.job_portal.repository.Entity.Job;
import com.gauravrmsc.job_portal.repository.Entity.SearchIndex;
import com.gauravrmsc.job_portal.repository.Entity.Skill;
import com.gauravrmsc.job_portal.repository.Entity.types.Location;
import com.gauravrmsc.job_portal.service.JobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest()
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JobService jobService;
    @InjectMocks
    private JobsController jobsController;
    private static final String BASE_URI = "http://localhost:8082";
    private static final String POST_JOB_PATH = "/jobs/postJob";
    private static final String SEARCH_JOB_PATH = "/jobs/search";

    private static JobDto jobDto;
    private static String searchQuery;
    private static Job job;

    @BeforeEach
    void init() {
        long jobId = 1;
        String jobTitle = "Java Developer";
        String jobDescription = "Java Developer";
        String companyName = "Google";
        double longitude = 72.877;
        double latitude = 19.122544;
        long skillId1 = 1l, skillId2 = 2l;
        String locationName = "Mumbai";
        int expiry = 10;
        Skill skill1 = new Skill(skillId1, "Java Developer");
        Skill skill2 = new Skill(skillId2, "PytonDeveloper");
        SearchIndex index1 = new SearchIndex();
        index1.setSkill(skill1);
        SearchIndex index2 = new SearchIndex();
        index2.setSkill(skill2);
        Location location = new Location(latitude, longitude, locationName);
        Date date = new Date();
        jobDto = new JobDto(jobId, jobTitle, companyName, jobDescription, location, date.getTime(), expiry, Arrays.asList(skillId1, skillId2));
        job = new Job(jobId, jobTitle, companyName, jobDescription, date, expiry, location, Arrays.asList(index1, index2));
//        ArrayList<Long> skillIds = new ArrayList<>();
//        skillIds.add(skillId1);
//        skillIds.add(skillId2);
//        searchQuery = new SearchQuery(latitude, longitude, skillIds);
        searchQuery = String.format("?longitude=%f&latitude=%f&skillIds=&d&skillIds=%d", latitude, longitude, skillId1, skillId2);

    }

    @Test
    public void searchJobsHappyPath() throws Exception {
        when(jobService.searchJobs(any(Location.class), any(List.class))).thenReturn(Arrays.asList(job));
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URI + SEARCH_JOB_PATH + searchQuery)).andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        String searchResult = response.getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        String jobDetails = objectMapper.writeValueAsString(Arrays.asList(jobDto));
        assertEquals(jobDetails, searchResult);
    }

    @Test
    public void searchJobsErrorPath() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URI + SEARCH_JOB_PATH)).andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void postJobHappyPath() throws Exception {
        Job tempJob = job;
        when(jobService.addJob(any(Job.class))).thenReturn(job);
        jobDto.setJobId(null);
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(jobDto);
        MockHttpServletResponse response = mockMvc.perform(post(BASE_URI + POST_JOB_PATH).content(requestBody).header("content-type", "application/json")).andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        long newJobId = mapper.readValue(response.getContentAsString(), Integer.class);
        assertEquals(job.getId(), newJobId);
    }

    @Test
    public void postJobErrorPath() throws Exception {
        jobDto.setJobId(null);
        jobDto.setJobTitle(null);
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(jobDto);
        MockHttpServletResponse response = mockMvc.perform(post(BASE_URI + POST_JOB_PATH).content(requestBody).header("content-type", "application/json")).andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }


}
