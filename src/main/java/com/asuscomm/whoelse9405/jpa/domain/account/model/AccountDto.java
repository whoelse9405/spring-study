package com.asuscomm.whoelse9405.jpa.domain.account.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AccountDto {
  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class SignUpRequest{
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String address1;
    private String address2;
    private String zip;

    @Builder
    public SignUpRequest(String email, String firstName, String lastName, String password, String address1, String address2, String zip){
      this.email = email;
      this.firstName = firstName;
      this.lastName = lastName;
      this.password = password;
      this.address1 = address1;
      this.address2 = address2;
      this.zip = zip;
    }

    public Account toEntity(){
      return Account.builder()
          .email(this.email)
          .firstName(this.firstName)
          .lastName(this.lastName)
          .password(this.password)
          .address1(this.address1)
          .address2(this.address2)
          .zip(this.zip)
          .build();
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class MyAccountRequest {
    private String address1;
    private String address2;
    private String zip;

    @Builder
    public MyAccountRequest(String address1, String address2, String zip){
      this.address1 = address1;
      this.address2 = address2;
      this.zip = zip;
    }
  }

  @Getter
  public static class Response{
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String address1;
    private String address2;
    private Date createdAt;
    private Date updatedAt;
    private String zip;

    public Response(Account account){
      this.email = account.getEmail();
      this.firstName = account.getFirstName();
      this.lastName = account.getLastName();
      this.password = account.getPassword();
      this.address1 = account.getAddress1();
      this.address2 = account.getAddress2();
      this.createdAt = account.getCreatedAt();
      this.updatedAt = account.getUpdatedAt();
      this.zip = account.getZip();
    }

  }

  @Getter
  public static class ListResponse{
    private List<Response> accounts;

    public ListResponse(List<Account> accounts){
      this.accounts = new ArrayList<>();

      for(Account account : accounts){
        this.accounts.add(new Response(account));
      }
    }
  }

}
