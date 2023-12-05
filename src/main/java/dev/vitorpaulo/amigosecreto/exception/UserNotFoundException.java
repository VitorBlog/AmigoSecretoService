package dev.vitorpaulo.amigosecreto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Não foi possível autenticar.")
public class UserNotFoundException extends Exception {
}
