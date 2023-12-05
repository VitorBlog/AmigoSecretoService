package dev.vitorpaulo.amigosecreto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Não foi possível autenticar.")
public class CannotAuthenticateException extends Exception {
}
