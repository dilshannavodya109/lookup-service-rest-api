package co.devskills.springbootboilerplate.service;

import co.devskills.springbootboilerplate.dto.CreditData;
import co.devskills.springbootboilerplate.dto.UserData;
import java.util.List;

public interface ExternalService {
    UserData fetchUserData(String userId);
    List<CreditData> fetchCreditData(String id);

} 