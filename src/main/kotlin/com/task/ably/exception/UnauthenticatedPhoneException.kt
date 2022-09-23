package com.task.ably.exception

class UnauthenticatedPhoneException(
    message: String = "인증되지 않은 핸드폰입니다"
): NoSuchElementException(message)