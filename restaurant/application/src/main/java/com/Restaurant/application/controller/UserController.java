package com.Restaurant.application.controller;


import com.Restaurant.application.api.UserAPI.*;
import com.Restaurant.application.auth.AuthenticationRequest;
import com.Restaurant.application.auth.AuthenticationResponse;
import com.Restaurant.application.auth.RegisterRequest;
import com.Restaurant.application.dto.SearchRequest;
import com.Restaurant.application.response.ApiOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurant/user")
@RequiredArgsConstructor
public class UserController {

    private final registerUserAPI registerUserAPI;
    private final authenticateUserAPI authenticateUserAPI;
    private final getUserAPI getUserAPI;
    private final updateUserAPI updateUserAPI;
    private final getAllUserAPI getAllUserAPI;
    private final deleteUserAPI deleteUserAPI;
    private final findAllByCriteriaAPI findAllByCriteriaAPI;


    @PostMapping("/register")
    public ApiOutput<?> register(
            @RequestBody RegisterRequest request
    ){
        return registerUserAPI.registerUserApi(request);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticateUserAPI.authenticateUserApi(request));
    }

    @GetMapping("/fetch")
    public ApiOutput<?> getUser()
    {
        return getUserAPI.getUserApi();
    }

    @GetMapping("/getall")
    public ApiOutput<?> getAllUsers()
    {
        return getAllUserAPI.getAllUserApi();
    }

    @GetMapping("/getbyfilter")
    public ApiOutput<?> findByCriteria(@RequestBody SearchRequest request)
    {
        return findAllByCriteriaAPI.findAllByCriteriaApi(request);
    }

    @DeleteMapping("/delete/{id}")
    public ApiOutput<?> deleteUser(@PathVariable Long id)
    {
        return deleteUserAPI.deleteUserApi(id);
    }

    @PutMapping("/update")
    public ApiOutput<?> updateUser(
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String phoneNumber
    ) {
        return updateUserAPI.updateUserApi(firstname, lastname, email, password, phoneNumber);
    }

}
