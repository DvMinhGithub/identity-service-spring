package com.mdv.identity_service.dto.response;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class AuthenticationResponse {
    boolean authenticated;
}
