package br.com.grpc.resources;

import br.com.grpc.CreatePersonRequest;
import br.com.grpc.CreatePersonResponse;
import br.com.grpc.CreatePersonServiceGrpc;
import br.com.grpc.dto.PersonInputDTO;
import br.com.grpc.dto.PersonOutputDTO;
import br.com.grpc.service.PersonService;
import br.com.grpc.service.ValidateDataService;

import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class PersonResource
        extends CreatePersonServiceGrpc.CreatePersonServiceImplBase {

    private final PersonService personService;
    private final ValidateDataService validateDataService;

    public PersonResource(PersonService personService, ValidateDataService validateDataService) {
        this.personService = personService;
        this.validateDataService = validateDataService;
    }

    @Override
    public void create(CreatePersonRequest request, StreamObserver<CreatePersonResponse> responseObserver) {
            validateDataService.validatePersonRequest(request);

            PersonOutputDTO outputDTO = personService.create(PersonInputDTO.builder()
                    .setName(request.getName().getValue())
                    .setEmail(request.getEmail().getValue()))
                    .build();

            responseObserver.onNext(CreatePersonResponse
                    .newBuilder()
                    .setId(Int64Value.of(outputDTO.getId()))
                    .setName(StringValue.newBuilder().setValue(outputDTO.getName()).build())
                    .setEmail(StringValue.newBuilder().setValue(outputDTO.getEmail()).build())
                    .build());

            responseObserver.onCompleted();
    }
}
