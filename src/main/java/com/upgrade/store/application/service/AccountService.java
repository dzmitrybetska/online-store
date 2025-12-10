package com.upgrade.store.application.service;

import com.upgrade.store.api.dto.request.RegisterRequest;
import com.upgrade.store.api.dto.request.UpdateAccountRequest;
import com.upgrade.store.api.dto.response.AccountResponse;
import com.upgrade.store.api.dto.response.AccountShortResponse;

public interface AccountService {

    AccountShortResponse saveAccount(RegisterRequest registerRequest, Long userId);

    AccountResponse getAccountById(Long accountId);

    AccountShortResponse updateAccount(Long accountId, UpdateAccountRequest updateAccountRequest);

    void deleteAccount(Long accountId);
}
