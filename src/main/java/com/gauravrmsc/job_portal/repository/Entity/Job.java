package com.gauravrmsc.job_portal.repository.Entity;

import com.gauravrmsc.job_portal.repository.Entity.types.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String jobTitle;
    String companyName;
    @Column(columnDefinition = "TEXT")
    String description;
    Date creationDate;
    int expiry;
    @Embedded
    Location Location;
    @OneToMany(mappedBy = "job", cascade = CascadeType.REMOVE)
    List<SearchIndex> searchIndices;

}
