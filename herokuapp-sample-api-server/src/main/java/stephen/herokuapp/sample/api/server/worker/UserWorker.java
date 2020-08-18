package stephen.herokuapp.sample.api.server.worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import stephen.herokuapp.sample.api.server.dto.UserDTO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserWorker {

    @Value("${herokuapp.uri}")
    private String uri;

    @Value("${herokuapp.londonLongitude}")
    private Double londonLongitude;

    @Value("${herokuapp.londonLatitude}")
    private Double londonLatitude;

    @Value("${herokuapp.distanceFromLondon}")
    private Double distanceFromLondon;

    @Autowired
    private RestTemplate restTemplate;

    public List<UserDTO> getUsers() {
        String city = "London";
        List<UserDTO> usersLiveInCity = getUsersLiveInCity(city);
        List<UserDTO> usersAreNearBy = getUsersAreNearBy(londonLongitude, londonLatitude, distanceFromLondon);

        return Stream.concat(usersLiveInCity.stream(), usersAreNearBy.stream())
                .collect(Collectors.toList());
    }

    private List<UserDTO> getUsersAreNearBy(Double longitude, Double latitude, Double distance) {
        UserDTO[] userDTOs = restTemplate.getForObject(uri + "/users", UserDTO[].class);

        return Arrays.stream(userDTOs)
                .filter(userDTO -> {
                    return Math.abs(userDTO.getLatitude() - latitude) <= distance
                            && Math.abs(userDTO.getLongitude() - longitude) <= distance;
                })
                .collect(Collectors.toList());
    }

    private List<UserDTO> getUsersLiveInCity(String city) {
        UserDTO[] userDTOs = restTemplate.getForObject(uri + "/city/{city}/users", UserDTO[].class, city);

        return Arrays.stream(userDTOs)
                .collect(Collectors.toList());
    }
}
