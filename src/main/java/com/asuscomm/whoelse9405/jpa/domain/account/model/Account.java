package com.asuscomm.whoelse9405.jpa.domain.account.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "account")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "address1", nullable = false)
  private String address1;

  @Column(name = "address2", nullable = false)
  private String address2;

  @Column(name="zip", nullable = false)
  private String zip;

  @Column(name="created_at")
  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private Date createdAt;

  @Column(name="updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  @UpdateTimestamp
  private Date updatedAt;

  @Builder
  public Account(String email,String firstName, String lastName, String password,String address1, String address2, String zip){
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.address1 = address1;
    this.address2 = address2;
    this.zip = zip;
  }

  public void updateMyAccount(AccountDto.MyAccountRequest dto){
    this.address1 = dto.getAddress1();
    this.address2 = dto.getAddress2();
    this.zip = dto.getZip();
  }

}
