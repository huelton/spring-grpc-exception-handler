package br.com.grpc.service.impl;

import br.com.grpc.constants.AppConstants;
import br.com.grpc.dto.PersonInputDTO;
import br.com.grpc.dto.PersonOutputDTO;
import br.com.grpc.entity.Person;
import br.com.grpc.exceptions.AlreadyExistsException;
import br.com.grpc.repository.PersonRepository;
import br.com.grpc.service.PersonService;

import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public PersonOutputDTO create(PersonInputDTO person) {

        if (this.personRepository.findByName(person.getName()).isPresent()) {
            throw new AlreadyExistsException(AppConstants.NAME_ALREADY_EXISTS);
        }

        Person save = this.personRepository.save(new Person(person.getName(), person.getEmail()));

        return PersonOutputDTO.builder()
                .setId(save.getId())
                .setName(save.getName())
                .setEmail(save.getEmail())
                .build();
    }
}
