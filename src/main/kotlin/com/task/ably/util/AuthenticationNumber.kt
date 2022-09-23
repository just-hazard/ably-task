package com.task.ably.util

object AuthenticationNumber {

    private const val RANDOM_NUMBER_LOOP_START_INDEX = 1
    private const val RANDOM_NUMBER_LOOP_END_INDEX = 4
    private const val RANDOM_NUMBER_MIN = 0
    private const val RANDOM_NUMBER_MAX = 9

    fun generateAuthenticationNumber() : String {
        var authenticationNumber = ""

        for(i in RANDOM_NUMBER_LOOP_START_INDEX..RANDOM_NUMBER_LOOP_END_INDEX) {
            authenticationNumber += (RANDOM_NUMBER_MIN..RANDOM_NUMBER_MAX).random().toString()
        }
        return authenticationNumber
    }
}