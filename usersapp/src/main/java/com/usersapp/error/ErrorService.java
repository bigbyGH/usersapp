package com.usersapp.error;

import org.springframework.stereotype.Service;

@Service
public class ErrorService {

    public ErrorDTO getErrorDTO() {
        return new ErrorDTO();
    }
}
