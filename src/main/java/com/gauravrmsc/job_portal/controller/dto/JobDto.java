package com.gauravrmsc.job_portal.controller.dto;

import com.gauravrmsc.job_portal.repository.Entity.types.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {
    private Long jobId;
    @NotNull(message = "Job Title Cannot Be Empty")
    private String jobTitle;
    @NotNull(message = "Company Name Cannot Be Empty")
    private String companyName;
    private String description;
    @NotNull(message = "Job Location is required")
    private Location location;
    @NotNull(message = "Creation Date is required")
    private Long creationDate;
    @NotNull(message = "Expiry Time is required")
    private Integer expiry;
    private List<Long> skills;
}
