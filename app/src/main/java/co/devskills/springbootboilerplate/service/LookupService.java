package co.devskills.springbootboilerplate.service;

import co.devskills.springbootboilerplate.dto.LookupResponse;

public interface LookupService {
    LookupResponse getLookupData(String userId);
}