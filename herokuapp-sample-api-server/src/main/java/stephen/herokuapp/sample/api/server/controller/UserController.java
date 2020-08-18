package stephen.herokuapp.sample.api.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import stephen.herokuapp.sample.api.server.dto.UserDTO;
import stephen.herokuapp.sample.api.server.worker.UserWorker;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/user")
public class UserController {

    @Autowired
    private UserWorker userWorker;

    @GetMapping("users")
    @ResponseBody
    public List<UserDTO> getUsers() {
        return userWorker.getUsers();
    }
}
