package com.itmo.microservices.demo.delivery.impl.exeptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestExeption : RuntimeException {
    constructor()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
}