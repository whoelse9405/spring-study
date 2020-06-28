package com.asuscomm.whoelse9405.jpa.domain.account.model;

public class AccountNotFoundException extends RuntimeException {

  public AccountNotFoundException(long id){
    super(id+" is not exited");
  }
}
