package co.devskills.springbootboilerplate;
import co.devskills.springbootboilerplate.controller.LookupController;
import co.devskills.springbootboilerplate.dto.CreditData;
import co.devskills.springbootboilerplate.dto.LookupResponse;
import co.devskills.springbootboilerplate.error.CustomerNotFoundException;
import co.devskills.springbootboilerplate.error.UpstreamServiceException;
import co.devskills.springbootboilerplate.service.LookupService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LookupController.class)
class LookupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LookupService lookupService;

    @Test
    void shouldReturnLookupData() throws Exception {
        LookupResponse response = LookupResponse.builder()
                .id("1")
                .name("John Doe")
                .creditData(List.of(
                        new CreditData("c1", new BigDecimal("100"), "ACTIVE")
                ))
                .build();

        Mockito.when(lookupService.getLookupData("1")).thenReturn(response);

        mockMvc.perform(get("/api/v1/lookup/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.credit_data[0].credit_id").value("c1"))
                .andExpect(jsonPath("$.credit_data[0].amount").value(100))
                .andExpect(jsonPath("$.credit_data[0].status").value("ACTIVE"));
    }

    @Test
    void shouldReturn404WhenCustomerNotFound() throws Exception {
        Mockito.when(lookupService.getLookupData("99"))
                .thenThrow(new CustomerNotFoundException("99"));

        mockMvc.perform(get("/api/v1/lookup/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Customer not found with ID: 99"));
    }

    @Test
    void shouldReturn502WhenUpstreamFails() throws Exception {
        Mockito.when(lookupService.getLookupData("1"))
                .thenThrow(new UpstreamServiceException("User service unavailable"));

        mockMvc.perform(get("/api/v1/lookup/1"))
                .andExpect(status().isBadGateway())
                .andExpect(jsonPath("$.message")
                        .value("User service unavailable"));
    }
}