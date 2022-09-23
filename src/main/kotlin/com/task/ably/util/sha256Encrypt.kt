package com.task.ably.util

import java.security.MessageDigest

fun sha256Encrypt(plainText: String): String = bytesToHex(SHA256.digest(plainText.toByteArray()))

private val SHA256: MessageDigest = MessageDigest.getInstance("SHA-256")

private fun bytesToHex(bytes: ByteArray): String =
    bytes.fold("") { previous, current -> previous + "%02x".format(current) }