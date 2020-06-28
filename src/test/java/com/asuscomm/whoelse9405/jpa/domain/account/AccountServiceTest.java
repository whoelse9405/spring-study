package com.asuscomm.whoelse9405.jpa.domain.account;

import static org.junit.jupiter.api.Assertions.*;

import com.asuscomm.whoelse9405.jpa.domain.account.model.Account;
import com.asuscomm.whoelse9405.jpa.domain.account.model.AccountDto;
import com.asuscomm.whoelse9405.jpa.domain.account.model.AccountNotFoundException;
import com.asuscomm.whoelse9405.jpa.domain.account.model.AccountRepository;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;



@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

  @Mock
  private AccountRepository accountRepository;

  @InjectMocks
  private AccountService accountService;


  @Test
  public void create_회원가입_성공() {
    //given
    final AccountDto.SignUpRequest request = buildSignUpReqquest();
    given(accountRepository.save(any(Account.class))).willReturn(request.toEntity());

    //when
    final Account account = accountService.create(request);

    //then
    verify(accountRepository, atLeastOnce()).save(any(Account.class));
    assertThatEqual(request, account);
  }

  @Test
  public void findById_존재하는경우_회원리턴() {
    //given
    final AccountDto.SignUpRequest request = buildSignUpReqquest();
    given(accountRepository.findById(anyLong()))
        .willReturn(Optional.ofNullable(request.toEntity()));

    //when
    final Account account = accountService.findById(anyLong());

    //then
    verify(accountRepository, atLeastOnce()).findById(anyLong());
    assertThatEqual(request, account);
  }

  @Test(expected = AccountNotFoundException.class)
  public void findById_존재하지않은경우_AccountNotFoundException() {
    //given
    given(accountRepository.findById(anyLong()))
        .willReturn(Optional.ofNullable(null));

    //when
    accountService.findById(anyLong());
  }

  @Test
  public void updateMyAccount() {
    //given
    final AccountDto.SignUpRequest createRequest = buildSignUpReqquest();
    final AccountDto.MyAccountRequest ModificationRequest = buildMyAccountReqquest();
    given(accountRepository.findById(anyLong()))
        .willReturn(Optional.ofNullable(createRequest.toEntity()));

    //when
    final Account account = accountService.updateMyAccount(anyLong(), ModificationRequest);

    //then
    assertThat(ModificationRequest.getAddress1(), is(account.getAddress1()));
    assertThat(ModificationRequest.getAddress2(), is(account.getAddress2()));
    assertThat(ModificationRequest.getZip(), is(account.getZip()));
  }

  private AccountDto.MyAccountRequest buildMyAccountReqquest() {
    return AccountDto.MyAccountRequest.builder()
        .address1("주소수정")
        .address2("주소수정2")
        .zip("061-233-444")
        .build();
  }

  private void assertThatEqual(AccountDto.SignUpRequest signUpRequest, Account account) {
    assertThat(signUpRequest.getAddress1(), is(account.getAddress1()));
    assertThat(signUpRequest.getAddress2(), is(account.getAddress2()));
    assertThat(signUpRequest.getZip(), is(account.getZip()));
    assertThat(signUpRequest.getEmail(), is(account.getEmail()));
    assertThat(signUpRequest.getFirstName(), is(account.getFirstName()));
    assertThat(signUpRequest.getLastName(), is(account.getLastName()));
    assertThat(signUpRequest.getPassword(), is(account.getPassword()));
  }

  private AccountDto.SignUpRequest buildSignUpReqquest() {
    return AccountDto.SignUpRequest.builder()
        .email("email")
        .password("password111")
        .firstName("동진")
        .lastName("이")
        .address1("경기도 용인시")
        .address2("덕영대로 2077번길 20")
        .zip("052-2344")
        .build();
  }


}
