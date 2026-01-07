package co.devskills.springbootboilerplate.service.impl;

import co.devskills.springbootboilerplate.dto.CreditData;
import co.devskills.springbootboilerplate.dto.UserData;
import co.devskills.springbootboilerplate.service.ExternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientException;
import co.devskills.springbootboilerplate.error.UpstreamServiceException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalServiceImpl implements ExternalService {

    private final RestTemplate restTemplate;

    @Value("${external.api.user-url}")
    private String userApiUrl;

    @Value("${external.api.credit-url}")
    private String creditApiUrl;

    @Override
    public UserData fetchUserData(String id) {
        try {
            log.info("Fetching user data for id={}", id);
            return restTemplate.getForObject(userApiUrl + id, UserData.class);
        } catch (RestClientException ex) {
            log.error("User API call failed for id={}", id, ex);
            throw new UpstreamServiceException("User service unavailable");
        }
    }

    @Override
    public List<CreditData> fetchCreditData(String id) {
        try {
            log.info("Fetching credit data for id={}", id);
            CreditData[] creditArray = restTemplate.getForObject(creditApiUrl + id, CreditData[].class);
            return (creditArray != null) ? Arrays.asList(creditArray) : Collections.emptyList();
        } catch (RestClientException ex) {
            log.error("Credit API call failed for id={}", id, ex);
            throw new UpstreamServiceException("Credit service unavailable");
        }
    }
}