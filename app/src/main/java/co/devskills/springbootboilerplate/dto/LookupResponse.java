package co.devskills.springbootboilerplate.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LookupResponse{
    private String id;
    private String name;
    @JsonProperty("credit_data")
    private List<CreditData> creditData;
}