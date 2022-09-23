package com.task.ably.member.domain

enum class LoginType {
    EMAIL,
    PHONE,
    NICKNAME;
    companion object {
        fun confirmType(id: String): LoginType {
            return when {
                emailRegex.matches(id) -> EMAIL
                phoneRegex.matches(id) -> PHONE
                else -> NICKNAME
            }
        }
    }
}

private val phoneRegex = Regex("^\\d{3}-\\d{4}-\\d{4}$")
private val emailRegex = Regex("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$")