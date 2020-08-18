package stephen.herokuapp.sample.api.server.worker;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import stephen.herokuapp.sample.api.server.dto.UserDTO;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserWorkerTest {

    @InjectMocks
    private UserWorker userWorker;

    @Mock
    private RestTemplate restTemplate;

    private String uri = "http://example.com/";

    @Test
    void getUsers() {
        UserDTO[] usersAreNearBy = {
                new UserDTO().withLatitude(1d).withLongitude(1d),
                new UserDTO().withLatitude(90d).withLongitude(100d),
                new UserDTO().withLatitude(49d).withLongitude(101d),
                new UserDTO().withLatitude(50d).withLongitude(99d),
        };
        UserDTO[] usersLiveInCity = {new UserDTO(), new UserDTO(), new UserDTO()};
        String city = "London";

        ReflectionTestUtils.setField(userWorker, "uri", uri);
        ReflectionTestUtils.setField(userWorker, "londonLatitude", 50d);
        ReflectionTestUtils.setField(userWorker, "londonLongitude", 100d);
        ReflectionTestUtils.setField(userWorker, "distanceFromLondon", 1d);

        Mockito.when(restTemplate.getForObject(uri + "/users", UserDTO[].class))
                .thenReturn(usersAreNearBy);

        Mockito.when(restTemplate.getForObject(uri + "/city/{city}/users", UserDTO[].class, city))
                .thenReturn(usersLiveInCity);

        List<UserDTO> result = userWorker.getUsers();
        Assert.assertEquals(5, result.size());
    }
}