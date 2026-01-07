package co.devskills;

import co.devskills.springbootboilerplate.dto.CreditData;
import co.devskills.springbootboilerplate.dto.LookupResponse;
import co.devskills.springbootboilerplate.dto.UserData;
import co.devskills.springbootboilerplate.error.CustomerNotFoundException;
import co.devskills.springbootboilerplate.service.LookupService;
import co.devskills.springbootboilerplate.service.LookupServiceImpl;
import co.devskills.springbootboilerplate.service.ExternalService;
import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LookupServiceImplTest {

    private LookupService lookupService;
    private ExternalService externalApiService;

    @BeforeEach
    void setUp() {
        externalApiService = Mockito.mock(ExternalService.class);
        lookupService = new LookupServiceImpl(externalApiService);
    }

    @Test
    void shouldReturnAggregatedLookupResponse() {
        UserData user = new UserData("1", "John Doe");
        List<CreditData> credits = List.of(
                new CreditData("c1", new BigDecimal("100"), "ACTIVE")
        );

        Mockito.when(externalApiService.fetchUserData("1")).thenReturn(user);
        Mockito.when(externalApiService.fetchCreditData("1")).thenReturn(credits);

        LookupResponse response = lookupService.getLookupData("1");

        assertThat(response.getId()).isEqualTo("1");
        assertThat(response.getName()).isEqualTo("John Doe");
        assertThat(response.getCreditData()).hasSize(1);
    }

    @Test
    void shouldThrowCustomerNotFoundWhenUserIsMissing() {
        Mockito.when(externalApiService.fetchUserData("99")).thenReturn(null);

        assertThatThrownBy(() -> lookupService.getLookupData("99"))
                .isInstanceOf(CustomerNotFoundException.class);
                // .hasMessageContaining("Customer with ID 99 not found");
    }
}
