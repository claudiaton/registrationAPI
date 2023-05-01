package registration.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class User {
    
    @NotEmpty(message = "Username must not be empty")
    @NotNull(message = "Username must not be null")
    private String username; 

    @NotEmpty(message = "Password must not be empty")
    @NotNull(message = "Password must not be null")
    @Size(message = "Password must have at least 8 characters",
    min = 8)
    private String password;

    @NotEmpty(message = "IP address must not be empty")
    @NotNull(message = "IP address must not be null")
    private String ipAddress;
}
