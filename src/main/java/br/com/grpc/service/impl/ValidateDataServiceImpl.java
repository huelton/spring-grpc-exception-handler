package br.com.grpc.service.impl;

import br.com.grpc.CreatePersonRequest;
import br.com.grpc.constants.AppConstants;
import br.com.grpc.exceptions.InvalidArgumentException;
import br.com.grpc.service.ValidateDataService;

import org.springframework.stereotype.Service;

@Service
public class ValidateDataServiceImpl implements ValidateDataService {

    @Override
    public void validatePersonRequest(CreatePersonRequest request) {
        if (!request.hasEmail() || request.getEmail().getValue().isBlank())
            throw new InvalidArgumentException(AppConstants.EMAIL_IS_REQUIRED);

        if (!request.hasName() || request.getName().getValue().isBlank())
            throw new InvalidArgumentException(AppConstants.NAME_IS_REQUIRED);
    }
}
