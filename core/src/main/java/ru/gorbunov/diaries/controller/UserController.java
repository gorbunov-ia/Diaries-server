package ru.gorbunov.diaries.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gorbunov.diaries.controller.dto.UserDto;
import ru.gorbunov.diaries.service.UserService;

import java.util.Optional;

/**
 * Controller for user page.
 *
 * @author Gorbunov.ia
 */
@RestController
@RequestMapping(path = "api/user")
public class UserController {

    /**
     * Service for interaction with user.
     */
    private final UserService userService;

    /**
     * Base constructor.
     *
     * @param userService service for interaction with user
     */
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Method to get current user info.
     *
     * @return template name
     */
    @PostMapping(path = "/login")
    public ResponseEntity<UserDto> getCurrentUser() {
        Optional<UserDto> user = userService.getUser();
        return user.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
