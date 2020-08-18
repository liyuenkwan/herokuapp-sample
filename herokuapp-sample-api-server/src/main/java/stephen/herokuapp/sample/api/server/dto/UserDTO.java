package stephen.herokuapp.sample.api.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@EqualsAndHashCode
@Builder
@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @EqualsAndHashCode.Include
    private Long id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String email;
    @JsonProperty("ip_address")
    private String ipAddress;
    private Double latitude;
    private Double longitude;
}
