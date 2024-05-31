package com.Restaurant.application.utilities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidationUtils {

    public static boolean isValidDate(String dateStr, String formatPattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isValidateDateFormat(LocalDate date) {

        return isValidDate(date.toString(), "yyyy-MM-dd");
    }

    public static boolean isDateAfterToday(LocalDate date)
    {
        return !date.isBefore(LocalDate.now());
    }

    public static boolean isValidString(String name)
    {
        return name.matches("^[a-zA-Z\\s]+$");
    }

    public static boolean isValidCost(int cost)
    {
        return cost > 0;
    }

    public static boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }

    public static boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    public static boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasLetter = false;
        boolean hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }

            if (hasLetter && hasDigit) {
                return true;
            }
        }
        return false;
    }


}
