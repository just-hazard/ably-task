package com.task.ably.member.ui

import com.task.ably.member.application.MemberService
import com.task.ably.member.dto.CreateMemberRequest
import com.task.ably.member.dto.GetMemberResponse
import com.task.ably.member.dto.LoginRequest
import com.task.ably.member.dto.PatchPasswordRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("apis")
class MemberController(
    private val service: MemberService
) {

    @PostMapping("/members")
    fun createMember(@RequestBody @Valid request: CreateMemberRequest): ResponseEntity<Void> {
        val id = service.create(
            request.email,
            request.nickname,
            request.password,
            request.name,
            request.phone
        )

        return ResponseEntity.created(URI.create("apis/members/$id")).build()
    }

    @PostMapping("/login")
    fun login(@RequestBody @Valid request: LoginRequest): ResponseEntity<String> {
        return ResponseEntity.ok(service.login(request))
    }

    @GetMapping("/members/{id}")
    fun getMemberInformation(@PathVariable id: Long): ResponseEntity<GetMemberResponse> {
        return ResponseEntity.ok().body(service.getInformation(id))
    }

    @PatchMapping("/members")
    fun changePassword(@RequestBody request: PatchPasswordRequest): ResponseEntity<Void> {
        service.changePassword(request.phone, request.password)
        return ResponseEntity.noContent().build()
    }
}