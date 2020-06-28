package com.asuscomm.whoelse9405.jpa.web;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.asuscomm.whoelse9405.jpa.domain.account.AccountService;
import com.asuscomm.whoelse9405.jpa.domain.account.model.Account;
import com.asuscomm.whoelse9405.jpa.domain.account.model.AccountDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

  @Mock
  private AccountService accountService;

  private ObjectMapper objectMapper = new ObjectMapper();

  private MockMvc mockMvc;

  @InjectMocks
  private AccountController accountController;

  @Before
  public void setUp() throws Exception {
    mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
  }

  @Test
  public void signUp() throws Exception {
    //given
    final AccountDto.SignUpRequest request = buildSignUpRequest();
    given(accountService.create(any())).willReturn(request.toEntity());

    //when
    final ResultActions resultActions = requestSignUp(request);

    //then
    resultActions
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.address1", is(request.getAddress1())))
        .andExpect(jsonPath("$.address2", is(request.getAddress2())))
        .andExpect(jsonPath("$.zip", is(request.getZip())))
        .andExpect(jsonPath("$.email", is(request.getEmail())))
        .andExpect(jsonPath("$.firstName", is(request.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(request.getLastName())));
  }

  @Test
  public void getUser() throws Exception {
    //given
    final AccountDto.SignUpRequest request = buildSignUpRequest();
    given(accountService.findById(anyLong())).willReturn(request.toEntity());

    //when
    final ResultActions resultActions = requestGetUser();

    //then
    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.address1", is(request.getAddress1())))
        .andExpect(jsonPath("$.address2", is(request.getAddress2())))
        .andExpect(jsonPath("$.zip", is(request.getZip())))
        .andExpect(jsonPath("$.email", is(request.getEmail())))
        .andExpect(jsonPath("$.firstName", is(request.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(request.getLastName())));
  }

  @Test
  public void updateMyAccount() throws Exception {
    //given
    final AccountDto.MyAccountRequest request = buildMyAccountRequest();
    final Account account = Account.builder()
        .address1(request.getAddress1())
        .address2(request.getAddress2())
        .zip(request.getZip())
        .build();

    given(accountService.updateMyAccount(anyLong(), any(AccountDto.MyAccountRequest.class))).willReturn(account);

    //when
    final ResultActions resultActions = requestMyAccount(request);

    //then
    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.address1", is(request.getAddress1())))
        .andExpect(jsonPath("$.address2", is(request.getAddress2())))
        .andExpect(jsonPath("$.zip", is(request.getZip())));

  }

  private ResultActions requestMyAccount(AccountDto.MyAccountRequest request) throws Exception {
    return mockMvc.perform(put("/accounts/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andDo(print());
  }

  private AccountDto.MyAccountRequest buildMyAccountRequest() {
    return AccountDto.MyAccountRequest.builder()
        .address1("주소수정")
        .address2("주소수정2")
        .zip("061-233-444")
        .build();
  }

  private ResultActions requestGetUser() throws Exception {
    return mockMvc.perform(get("/accounts/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print());
  }

  private ResultActions requestSignUp(AccountDto.SignUpRequest request) throws Exception {
    return mockMvc.perform(post("/accounts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andDo(print());
  }

  private AccountDto.SignUpRequest buildSignUpRequest() {
    return AccountDto.SignUpRequest.builder()
        .email("email")
        .password("password111")
        .firstName("dongjin")
        .lastName("lee")
        .address1("경기도")
        .address2("용인시")
        .zip("052-2344")
        .build();
  }
}
