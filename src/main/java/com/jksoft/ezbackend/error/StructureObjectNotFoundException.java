package com.jksoft.ezbackend.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Structure Object not Found")  // 404
public class StructureObjectNotFoundException extends RuntimeException  {

}
