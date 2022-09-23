package com.task.ably.member.domain

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.task.ably.util.sha256Encrypt
import javax.persistence.Embeddable

@JsonSerialize(using = PasswordSerializer::class)
@JsonDeserialize(using = PasswordDeserializer::class)
@Embeddable
data class Password(var value: String) {
    init {
        value = sha256Encrypt(value)
    }
}

private class PasswordDeserializer : JsonDeserializer<Password>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Password = Password(p.text)
}

private class PasswordSerializer : JsonSerializer<Password>() {
    override fun serialize(password: Password, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeString(password.value)
    }
}
