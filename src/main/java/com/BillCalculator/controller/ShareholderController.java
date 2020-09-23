package com.BillCalculator.controller;

import com.BillCalculator.dto.ShareholderRequest;
import com.BillCalculator.dto.ShareholderResponse;
import com.BillCalculator.service.ShareholderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shareholder")
public class ShareholderController {

    private final ShareholderService shareholderService;

    @PostMapping
    public ResponseEntity<ShareholderResponse> createShareholder(@Valid @RequestBody ShareholderRequest shareHolderRequest) {
        return ResponseEntity.ok(shareholderService.createShareholderOfUser(shareHolderRequest));
    }

}
