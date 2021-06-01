package swp490.spa.dto.responses;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import swp490.spa.entities.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponse {
    private User user;
    private String customType;
}
