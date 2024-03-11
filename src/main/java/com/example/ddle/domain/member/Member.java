package com.example.ddle.domain.member;

import com.example.ddle.domain.BaseTimeEntity;
import com.example.ddle.enums.RoleType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
@Entity
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId")
    private Integer id;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 100, nullable = false, unique = true)
    private String nickName;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Column(length = 11, unique = true)
    private String phone;

    @Embedded
    private Address address;

    @Builder
    public Member(String email, String password, String nickName, RoleType roleType, String phone, Address address){
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.roleType = roleType;
        this.phone = phone;
        this.address = address;
    }

    public Member update(String password, String nickName, Address address, String phone) {
        this.password = password;
        this.nickName = nickName;
        this.address = address;
        this.phone = phone;

        return this;
    }

    public String getRoleKey(){
        return this.roleType.getKey();
    }
}
