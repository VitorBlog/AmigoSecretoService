package dev.vitorpaulo.amigosecreto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Conta já cadastrada.")
public class UserAlreadyExistsException extends Exception {
}
