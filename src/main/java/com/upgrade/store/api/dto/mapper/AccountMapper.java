package com.upgrade.store.api.dto.mapper;

import com.upgrade.store.api.dto.request.RegisterRequest;
import com.upgrade.store.api.dto.request.UpdateAccountRequest;
import com.upgrade.store.api.dto.response.AccountResponse;
import com.upgrade.store.api.dto.response.AccountShortResponse;
import com.upgrade.store.domain.model.Account;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true),
        uses = {UserMapper.class})
public interface AccountMapper {

    Account mapToEntity(RegisterRequest registerRequest);

    @Mapping(target = "userResponse", source = "user")
    AccountResponse mapToDto(Account account);

    AccountShortResponse mapToShortDto(Account account);

    void update(UpdateAccountRequest accountRequest, @MappingTarget Account account);
}
