package com.aaemu.login.service;

import com.aaemu.login.data.entity.AccountEntity;
import com.aaemu.login.service.model.Account;

import java.util.Optional;

/**
 * @author Shannon
 */
public interface Repository {

    Optional<AccountEntity> getAccount(Account account);
}
