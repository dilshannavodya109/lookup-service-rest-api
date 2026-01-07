package co.devskills.springbootboilerplate.controller;

import co.devskills.springbootboilerplate.service.LookupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.devskills.springbootboilerplate.dto.LookupResponse;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lookup")
public class LookupController {

    private final LookupService lookupService;

    @GetMapping("/{userId}")
    public ResponseEntity<LookupResponse> getLookupData(@PathVariable String userId) {
        LookupResponse lookupResponse = lookupService.getLookupData(userId);
        return ResponseEntity.ok(lookupResponse);  
    }

}