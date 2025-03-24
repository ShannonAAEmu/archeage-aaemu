package com.aaemu.login.service.impl;

import com.aaemu.login.data.entity.AccountEntity;
import com.aaemu.login.service.Repository;
import com.aaemu.login.service.model.Account;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Shannon
 */
@Service
public class RepositoryImpl implements Repository {

    @Override
    public Optional<AccountEntity> getAccount(Account account) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1);
        accountEntity.setDev(false);
        accountEntity.setActive(true);
        return Optional.of(accountEntity);
    }
}
