package br.com.grpc.service;

import br.com.grpc.dto.PersonInputDTO;
import br.com.grpc.dto.PersonOutputDTO;

public interface PersonService {
    PersonOutputDTO create(PersonInputDTO person);
}
