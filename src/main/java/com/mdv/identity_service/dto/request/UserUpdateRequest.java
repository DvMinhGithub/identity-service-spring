package com.mdv.identity_service.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.mdv.identity_service.validator.DobConstraint;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String password;
    String firstName;
    String lastName;

    @DobConstraint(minAge = 18, message = "User must be at least 18 years old")
    LocalDate dob;
    List<String> roles;
}
