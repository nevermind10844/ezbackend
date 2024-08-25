package com.jksoft.ezbackend.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="the id provided in the path does not match id given in object")  // 400
public class BadRequestException extends RuntimeException  {

}
