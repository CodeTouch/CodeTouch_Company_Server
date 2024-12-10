package com.tagmaster.codetouch.entity.customer;

import com.tagmaster.codetouch.entity.company.CompanyUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class CustomerUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private int siteId;
    private String email;
    private String password;
    private String nickname;
    private String name;
    private String phone;
    private LocalDate birth;
    private int gender;
    @Column(columnDefinition = "json")
    private String address;
    private Integer role;
    private int mileage;
    private Integer agree;
    private int businessNum;
    private int reportNum;

}
