package com.tagmaster.codetouch.entity.customer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "site")
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int siteId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private CustomerUser userId;

    private String siteName;
    private String favicon;
    private String mainImage;
    private Integer payState;
    private LocalDate expiry;

}
