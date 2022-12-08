package br.com.grpc.service.impl;

import br.com.grpc.constants.AppConstants;
import br.com.grpc.dto.PersonInputDTO;
import br.com.grpc.dto.PersonOutputDTO;
import br.com.grpc.entity.Person;
import br.com.grpc.exceptions.AlreadyExistsException;
import br.com.grpc.repository.PersonRepository;
import br.com.grpc.service.PersonService;
import br.com.grpc.service.impl.PersonServiceImpl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

    private final PersonRepository personRepository = mock(PersonRepository.class);
    private final PersonService personService = new PersonServiceImpl(personRepository);

    @Test
    @DisplayName("should create person returns a PersonOutputDTO in success")
    void createPersonSuccessTest() {

        when(personRepository.findByName(anyString()))
                .thenReturn(Optional.empty());

        when(personRepository.save(any()))
                .thenReturn(personStub());

        PersonOutputDTO outputDTO = personService.create(
                PersonInputDTO.builder()
                        .setName("any name")
                        .setEmail("any@mail.com").build());

        assertNotNull(outputDTO);
        assertEquals(outputDTO.getName(), personStub().getName());
        assertEquals(outputDTO.getEmail(), personStub().getEmail());
    }

    @Test
    @DisplayName("should create person returns a AlreadyExistsException if name is duplicated")
    void createPersonThrowsAlreadyExistsExceptionTest() {

        when(personRepository.findByName(anyString()))
                .thenReturn(Optional.of(personStub()));

        when(personRepository.save(any()))
                .thenReturn(personStub());

        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> personService.create(
                PersonInputDTO.builder()
                        .setName("any name")
                        .setEmail("any@mail.com").build()));

        assertEquals(exception.getMessage(), AppConstants.NAME_ALREADY_EXISTS);
    }

    private Person personStub() {
        return new Person("any name", "any@mail.com");
    }

}
