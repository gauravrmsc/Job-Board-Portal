package com.gauravrmsc.job_portal.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchQuery {
    @NotNull(message = "Latitude of the location is required")
    private Double latitude;
    @NotNull(message = "Longitude of the location is required")
    private Double longitude;
    ArrayList<Long> skillIds;
}
