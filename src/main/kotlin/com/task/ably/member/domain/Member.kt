package com.task.ably.member.domain

import com.task.ably.exception.WrongPasswordException
import javax.persistence.*

@Entity
class Member(
    @Column(unique = true, nullable = false)
    var email: String,

    @Column(unique = true, nullable = false)
    var nickname: String,

    @AttributeOverride(name = "value", column = Column(name = "password", nullable = false))
    @Embedded
    var password: Password,

    @Column(nullable = false)
    var name: String,

    @Column(unique = true, nullable = false)
    var phone: String,
) {
    fun isSamePassword(password: String) {
        if(Password(password).value != this.password.value) {
            throw WrongPasswordException()
        }
    }

    fun changePassword(password: String) {
        this.password = Password(password)
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}