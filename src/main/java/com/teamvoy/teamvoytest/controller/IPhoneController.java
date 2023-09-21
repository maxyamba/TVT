package com.teamvoy.teamvoytest.controller;

import com.teamvoy.teamvoytest.model.dto.IPhoneDto;
import com.teamvoy.teamvoytest.model.request.IPhoneRequest;
import com.teamvoy.teamvoytest.service.IPhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/iphones")
@RequiredArgsConstructor
public class IPhoneController {

    private final IPhoneService iPhoneService;

    @PostMapping
    public ResponseEntity<IPhoneDto> addToStore(@RequestBody IPhoneRequest request) {
        return ResponseEntity.ok(iPhoneService.createAndAddToStore(request));
    }

    @GetMapping
    public ResponseEntity<List<IPhoneDto>> showAllIPhones() {
        return ResponseEntity.ok(iPhoneService.showAllIPhones());
    }
}
