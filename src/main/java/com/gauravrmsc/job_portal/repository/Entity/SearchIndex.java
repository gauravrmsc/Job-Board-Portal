package com.gauravrmsc.job_portal.repository.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class SearchIndex {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @OneToOne
    @JoinColumn(name = "skill_id")
    Skill skill;
    String locationHash;
    @ManyToOne
    @JoinColumn(name = "job_id")
    Job job;
}
