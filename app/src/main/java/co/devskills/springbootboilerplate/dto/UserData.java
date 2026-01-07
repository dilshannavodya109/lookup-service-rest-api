package co.devskills.springbootboilerplate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    private String id;
    private String name;
}