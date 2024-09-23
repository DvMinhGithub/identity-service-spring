package com.mdv.identity_service.service;

import java.util.Date;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mdv.identity_service.dto.request.AuthenticationRequest;
import com.mdv.identity_service.dto.request.IntrospectRequest;
import com.mdv.identity_service.dto.response.AuthenticationResponse;
import com.mdv.identity_service.dto.response.IntrospectResponse;
import com.mdv.identity_service.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class AuthenticationService {
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerkey}")
    protected String SINGER_KEY;

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();

        JWSVerifier verifier = new MACVerifier(SINGER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expireTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verify = signedJWT.verify(verifier);

        return IntrospectResponse.builder()
                .valid(verify && expireTime.after(new Date()))
                .build();
    }

    public AuthenticationResponse authenticated(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not exist"));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new RuntimeException("Invalid password");
        }

        String token = generateToken(user.getUsername());

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(authenticated)
                .build();
    }

    private String generateToken(String username) {
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256).build();

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("mdv")
                .issueTime(new Date())
                .expirationTime(new Date(new Date().getTime() + 60 * 1000))
                .build();

        Payload payload = new Payload(claims.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SINGER_KEY));
            return jwsObject.serialize();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
