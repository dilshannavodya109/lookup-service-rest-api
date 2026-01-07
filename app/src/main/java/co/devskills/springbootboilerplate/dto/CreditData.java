package co.devskills.springbootboilerplate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditData {
    @JsonProperty("credit_id")
    private String creditId;
    private BigDecimal amount;
    private String status;

}