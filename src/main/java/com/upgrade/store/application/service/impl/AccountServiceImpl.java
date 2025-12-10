package com.upgrade.store.application.service.impl;

import com.upgrade.store.api.dto.mapper.AccountMapper;
import com.upgrade.store.api.dto.request.RegisterRequest;
import com.upgrade.store.api.dto.request.UpdateAccountRequest;
import com.upgrade.store.api.dto.response.AccountResponse;
import com.upgrade.store.api.dto.response.AccountShortResponse;
import com.upgrade.store.api.exception.AccountAlreadyExistsException;
import com.upgrade.store.api.exception.EntityNotFoundException;
import com.upgrade.store.application.service.AccountService;
import com.upgrade.store.domain.model.Account;
import com.upgrade.store.domain.model.User;
import com.upgrade.store.domain.repository.AccountRepository;
import com.upgrade.store.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public AccountShortResponse saveAccount(RegisterRequest registerRequest, Long userId) {
        log.debug("[SERVICE] Attempting to save new account for user with email: [{}], and ID [{}]",
                registerRequest.email(), userId);

        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.warn("[SERVICE] User with ID [{}] not found", userId);
            return new EntityNotFoundException("User with ID " + userId + " not found");
        });

        if (accountRepository.existsByUserId(userId)) {
            throw new AccountAlreadyExistsException("User with id " + userId + " already has an account");
        }

        Account account = accountMapper.mapToEntity(registerRequest);
        account.setUser(user);
        Account savedAccount = accountRepository.save(account);

        log.info("[SERVICE] Created new account with ID [{}] for user with ID [{}]", savedAccount.getId(), userId);
        return accountMapper.mapToShortDto(savedAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse getAccountById(Long accountId) {
        log.debug("[SERVICE] Fetching account by ID [{}]", accountId);
        Account account = accountRepository.findById(accountId).orElseThrow(() -> {
            log.warn("Account with ID [{}] not found", accountId);
            return new EntityNotFoundException("Account with ID " + accountId + " not found");
        });

        log.info("[SERVICE] Account with ID [{}] for user: [{}] [{}] was found successfully",
                accountId, account.getFirstName(), account.getLastName());
        return accountMapper.mapToDto(account);
    }

    @Override
    @Transactional
    public AccountShortResponse updateAccount(Long accountId, UpdateAccountRequest accountRequest) {
        log.debug("[SERVICE] Attempting to update account with ID [{}] using request: {}", accountId, accountRequest);

        Account account = accountRepository.findById(accountId).orElseThrow(() -> {
            log.warn("Account with ID [{}] not found", accountId);
            return new EntityNotFoundException("Account with ID " + accountId + " not found");
        });

        accountMapper.update(accountRequest, account);
        Account updatedAccount = accountRepository.save(account);

        log.debug("[SERVICE] Saving updated account entity: {}", updatedAccount);
        log.info("[SERVICE] Updated account with ID [{}]", updatedAccount.getId());
        return accountMapper.mapToShortDto(updatedAccount);
    }

    @Override
    @Transactional
    public void deleteAccount(Long accountId) {
        log.debug("[SERVICE] Attempting to delete account with ID [{}]", accountId);

        Account account = accountRepository.findById(accountId).orElseThrow(() -> {
            log.warn("Account with ID [{}] not found", accountId);
            return new EntityNotFoundException("Account with ID " + accountId + " not found");
        });

        accountRepository.delete(account);

        log.info("[SERVICE] Deleted account with ID [{}]", account.getId());
    }
}
