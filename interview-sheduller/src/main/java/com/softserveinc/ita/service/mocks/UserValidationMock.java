package com.softserveinc.ita.service.mocks;

import com.softserveinc.ita.service.UserValidation;
import org.springframework.stereotype.Service;

@Service
public class UserValidationMock implements UserValidation {
    public boolean userExists(String userId) {
        return userId.equals("testUserId");
    }
}