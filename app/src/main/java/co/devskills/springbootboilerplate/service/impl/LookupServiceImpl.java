package co.devskills.springbootboilerplate.service;

import java.util.Optional;
import co.devskills.springbootboilerplate.dto.LookupResponse;
import co.devskills.springbootboilerplate.dto.UserData;
import co.devskills.springbootboilerplate.service.ExternalService;
import co.devskills.springbootboilerplate.error.CustomerNotFoundException;
import co.devskills.springbootboilerplate.error.UpstreamServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import co.devskills.springbootboilerplate.dto.CreditData;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LookupServiceImpl implements LookupService {
    private final ExternalService externalService;
    @Override
    public LookupResponse getLookupData(String userId) {
        UserData user = Optional.ofNullable(externalService.fetchUserData(userId))
        .orElseThrow(() -> new CustomerNotFoundException(userId));

        List<CreditData> creditList = externalService.fetchCreditData(userId);

        return LookupResponse.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .creditData(creditList)
                    .build();        
    }
}