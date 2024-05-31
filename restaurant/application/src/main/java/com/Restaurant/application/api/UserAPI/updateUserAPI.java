package com.Restaurant.application.api.UserAPI;

import com.Restaurant.application.dto.UserDto;
import com.Restaurant.application.exception.exceptions.InvalidEntityDataException;
import com.Restaurant.application.response.ApiOutput;
import com.Restaurant.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static com.Restaurant.application.utilities.ValidationUtils.*;

@Component
@RequiredArgsConstructor
public class updateUserAPI {
    private final UserService userService;

    public ApiOutput<?> updateUserApi(String firstname, String lastname, String newEmail, String password, String phoneNumber)
    {
        try{
            validateDetails(firstname,lastname,newEmail,password,phoneNumber);
            UserDto updated = userService.updateUser(firstname,lastname,newEmail,password,phoneNumber);
            return new ApiOutput<>(HttpStatus.OK.value(),"User updated successfully",updated);
        } catch (InvalidEntityDataException e){
            return new ApiOutput<>(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);
        }
    }

    private void validateDetails(String firstname, String lastname, String newEmail, String password, String phoneNumber) {
        if (firstname != null && !firstname.isEmpty()) {
            if (!isAlpha(firstname)) {
                throw new InvalidEntityDataException("First name must contain only alphabets");
            }
        }

        if (lastname != null && !lastname.isEmpty()) {
            if (!isAlpha(lastname)) {
                throw new InvalidEntityDataException("Last name must contain only alphabets");
            }
        }

        if (newEmail != null && !newEmail.isEmpty()) {
            if (!isValidEmail(newEmail)) {
                throw new InvalidEntityDataException("Invalid email format");
            }
        }

        if (password != null && !password.isEmpty()) {
            if (!isValidPassword(password)) {
                throw new InvalidEntityDataException("Password must be at least 8 characters long and contain a mix of letters and numbers");
            }
        }

    }
}
