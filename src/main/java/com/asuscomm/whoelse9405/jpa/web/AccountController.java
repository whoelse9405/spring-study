package com.asuscomm.whoelse9405.jpa.web;

import com.asuscomm.whoelse9405.jpa.domain.account.AccountService;
import com.asuscomm.whoelse9405.jpa.domain.account.model.Account;
import com.asuscomm.whoelse9405.jpa.domain.account.model.AccountDto;
import com.asuscomm.whoelse9405.jpa.domain.account.model.AccountDto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("accounts")
public class AccountController {

  @Autowired
  private AccountService accountService;

  @GetMapping(value = "")
  @ResponseStatus(value = HttpStatus.OK)
  public AccountDto.ListResponse getUsers(){
    return new AccountDto.ListResponse(accountService.findAll());
  }

  @GetMapping(value = "/{id}")
  @ResponseStatus(value = HttpStatus.OK)
  public AccountDto.Response getUser(@PathVariable(value = "id") final long accountId ){
    return new AccountDto.Response(accountService.findById(accountId));
  }

  @PostMapping(value = "")
  @ResponseStatus(value = HttpStatus.CREATED)
  public AccountDto.Response signUp(@RequestBody final AccountDto.SignUpRequest request ){
    return new AccountDto.Response(accountService.create(request));
  }

  @PutMapping(value = "/{id}")
  @ResponseStatus(value = HttpStatus.OK)
  public AccountDto.Response getUser(
      @PathVariable(value = "id") final long id,
      @RequestBody final AccountDto.MyAccountRequest request){
    return new AccountDto.Response(accountService.updateMyAccount(id, request));
  }

}
