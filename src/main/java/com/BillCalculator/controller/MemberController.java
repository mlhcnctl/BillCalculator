package com.BillCalculator.controller;

import com.BillCalculator.dto.RequestModel;
import com.BillCalculator.dto.ResponseModel;
import com.BillCalculator.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ResponseModel> create(RequestModel requestModel) {
        return ResponseEntity.ok(memberService.createMember(requestModel));
    }

}
