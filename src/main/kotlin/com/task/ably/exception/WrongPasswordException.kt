package com.task.ably.exception

class WrongPasswordException(
    message: String = "잘못된 비밀번호 입니다."
): IllegalArgumentException(message)