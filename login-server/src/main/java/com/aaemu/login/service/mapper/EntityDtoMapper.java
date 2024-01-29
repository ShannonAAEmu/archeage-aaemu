package com.aaemu.login.service.mapper;

import com.aaemu.login.data.entity.Account;
import com.aaemu.login.service.model.AuthAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntityDtoMapper {

    Account toEntity(AuthAccount authAccount);

    AuthAccount toDto(Account account);
}
