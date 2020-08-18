package stephen.herokuapp.sample.api.server.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import stephen.herokuapp.sample.api.server.ApiServerApplication;
import stephen.herokuapp.sample.api.server.dto.UserDTO;
import stephen.herokuapp.sample.api.server.worker.UserWorker;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {ApiServerApplication.class})
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserWorker userWorker;

    @Test
    void getUser() throws Exception {

        var expectedResult = List.of(
                new UserDTO().withFirstName("user 1"),
                new UserDTO().withFirstName("user 2"),
                new UserDTO().withFirstName("user 3")
        );

        Mockito.when(userWorker.getUsers()).thenReturn(expectedResult);

        MvcResult mvcResult = mockMvc.perform(
                get("/api/v1.0/user/users")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
        )
                .andExpect(status().isOk())
                .andReturn();

        List<UserDTO> payload = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<UserDTO>>() {
                }
        );

        Assert.assertEquals(3, payload.size());
        Assert.assertEquals(expectedResult.get(0).getFirstName(), payload.get(0).getFirstName());
    }
}