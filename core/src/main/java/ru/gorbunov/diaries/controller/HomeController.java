package ru.gorbunov.diaries.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for home page.
 *
 * @author Gorbunov.ia
 */
@RestController
public class HomeController {

    /**
     * Redirect to home page.
     *
     * @return home page
     */
    @GetMapping
    public String redirect() {
        return "Welcome to Diaries application";
    }

}
