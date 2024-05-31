package com.Restaurant.application.api.UserAPI;

import com.Restaurant.application.auth.RegisterRequest;
import com.Restaurant.application.dto.UserDto;
import com.Restaurant.application.exception.exceptions.InvalidEntityDataException;
import com.Restaurant.application.response.ApiOutput;
import com.Restaurant.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static com.Restaurant.application.utilities.ValidationUtils.*;


@Component @RequiredArgsConstructor
public class registerUserAPI {

    private final UserService userService;

    public ApiOutput<?> registerUserApi(RegisterRequest user) {
        try{
            validateUser(user);
            UserDto addedUser = userService.register(user);
            return new ApiOutput<>(HttpStatus.OK.value(),"User registered successfully",addedUser);
        } catch (InvalidEntityDataException e){
            return new ApiOutput<>(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);
        }
    }

    private void validateUser(RegisterRequest user) {
        if (user.getFirstname() == null || user.getFirstname().isEmpty()) {
            throw new InvalidEntityDataException("First name is required");
        }

        if (!isAlpha(user.getFirstname())) {
            throw new InvalidEntityDataException("First name must contain only alphabets");
        }

        if (user.getLastname() == null || user.getLastname().isEmpty()) {
            throw new InvalidEntityDataException("Last name is required");
        }

        if (!isAlpha(user.getLastname())) {
            throw new InvalidEntityDataException("Last name must contain only alphabets");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new InvalidEntityDataException("Email is required");
        }

        if (!isValidEmail(user.getEmail())) {
            throw new InvalidEntityDataException("Invalid email format");
        }

        validateEmailByRole(user);


        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new InvalidEntityDataException("Password is required");
        }

        if (!isValidPassword(user.getPassword())) {
            throw new InvalidEntityDataException("Password must be at least 8 characters long and contain a mix of letters and numbers");
        }

        if (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty()) {
            throw new InvalidEntityDataException("Phone number is required");
        }

        if (user.getRole() == null) {
            throw new InvalidEntityDataException("Role is required");
        }

    }

    private void validateEmailByRole(RegisterRequest user) {
        String email = user.getEmail();
        String role = user.getRole().name();

        if (role.equals("ADMIN") && !email.endsWith("@admin.com")) {
            throw new InvalidEntityDataException("Admin email must end with '@admin.com'");
        }

        if (role.equals("STAFF") && !email.endsWith("@staff.com")) {
            throw new InvalidEntityDataException("Staff email must end with '@staff.com'");
        }
    }


}


