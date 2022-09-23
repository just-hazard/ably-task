package com.task.ably.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

class InvalidAuthenticationException(
    message: String = "인증번호가 일치하지 않습니다."
): IllegalArgumentException(message)