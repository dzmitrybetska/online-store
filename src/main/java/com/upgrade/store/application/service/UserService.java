package com.upgrade.store.application.service;

import com.upgrade.store.api.dto.request.RegisterRequest;
import com.upgrade.store.api.dto.response.UserShortResponse;

public interface UserService {

    UserShortResponse save(RegisterRequest registerRequest);
}
