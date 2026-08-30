package com.realtime_monitorig.tenant_managment.exceptions;

import com.realtime_monitorig.tenant_managment.entity.ErrorResponse;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void tenantNotFound_returns404() {
        TenantNotFoundException exception = new TenantNotFoundException("not here");

        ResponseEntity<?> response = handler.handleTenantNotFoundException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        ErrorResponse body = (ErrorResponse) response.getBody();
        assertThat(body.getMessage()).isEqualTo("not here");
        assertThat(body.getDetails()).isEqualTo("Product Not Found");
    }

    @Test
    void arrayIndexOutOfBounds_returns404() {
        ArrayIndexOutOfBoundsException exception = new ArrayIndexOutOfBoundsException("bad index");

        ResponseEntity<?> response = handler.handleTenantNotFoundException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        ErrorResponse body = (ErrorResponse) response.getBody();
        assertThat(body.getMessage()).isEqualTo("bad index");
    }

    @Test
    void validationException_returnsFieldErrors() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("obj", "name", "tenant name is required");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));

        Map<String, String> errors = handler.handleValidationExceptions(ex);

        assertThat(errors).containsEntry("name", "tenant name is required");
    }
}