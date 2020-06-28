package com.asuscomm.whoelse9405.jpa.domain.account;

import com.asuscomm.whoelse9405.jpa.domain.account.model.Account;
import com.asuscomm.whoelse9405.jpa.domain.account.model.AccountDto;
import com.asuscomm.whoelse9405.jpa.domain.account.model.AccountNotFoundException;
import com.asuscomm.whoelse9405.jpa.domain.account.model.AccountRepository;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

  @NonNull
  private final AccountRepository accountRepository;

  public Account create(AccountDto.SignUpRequest request){
    return accountRepository.save(request.toEntity());
  }

  @Transactional(readOnly = true)
  public List<Account> findAll(){
    return accountRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Account findById(long id){
    return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
  }

  public Account updateMyAccount(long id, AccountDto.MyAccountRequest request){
    final Account account = findById(id);
    account.updateMyAccount(request);

    return account;
  }


}
