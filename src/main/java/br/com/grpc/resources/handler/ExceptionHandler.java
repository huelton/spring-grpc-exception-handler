package br.com.grpc.resources.handler;

import static br.com.grpc.constants.AppConstants.INTERNAL_SERVER_ERROR;

import br.com.grpc.exceptions.BusinessException;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class ExceptionHandler {

    @GrpcExceptionHandler(BusinessException.class)
    public StatusRuntimeException handleBusinessException(BusinessException e) {
        return e.getStatus()
                .withCause(e.getCause()).withDescription(e.getMessage()).asRuntimeException();
    }

    @GrpcExceptionHandler(Exception.class)
    public StatusException handleException(Exception e) {
        return Status.INTERNAL.withCause(e).withDescription(INTERNAL_SERVER_ERROR).asException();
    }
}
