package com.task.ably.exception

class IdNotFoundException(
    message: String = "아이디가 존재하지 않습니다."
): IllegalArgumentException(message)