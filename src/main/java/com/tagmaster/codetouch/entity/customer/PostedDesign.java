package com.tagmaster.codetouch.entity.customer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "posted_design")
public class PostedDesign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postedDesignId;

    @ManyToOne
    @JoinColumn(name = "site_id", nullable = false)
    private Site siteId;

    private String header;
    private String page;
    private String footer;
}
