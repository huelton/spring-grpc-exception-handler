package br.com.grpc.service;

import br.com.grpc.CreatePersonRequest;

public interface ValidateDataService {
    void validatePersonRequest(CreatePersonRequest request);
}
