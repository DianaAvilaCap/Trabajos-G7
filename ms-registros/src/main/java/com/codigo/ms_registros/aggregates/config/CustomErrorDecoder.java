package com.codigo.ms_registros.aggregates.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        HttpStatus status = HttpStatus.valueOf(response.status());

        //Evaluación de estados HTTP
        return switch (status) {
            case UNPROCESSABLE_ENTITY -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Error 422: Ruc enviado no cumple con el formato.");
            case NOT_FOUND -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Error 404: Recurso o empresa no encontrado.");
            case UNAUTHORIZED -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Error 401: Token no válido.");
            // Otros estados 500
            default -> new Exception("Error: " + response.reason());
        };
    }
}
