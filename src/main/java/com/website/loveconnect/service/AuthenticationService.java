package com.website.loveconnect.service;

import com.nimbusds.jose.JOSEException;
import com.website.loveconnect.dto.request.AuthenticationRequest;
import com.website.loveconnect.dto.request.IntrospectRequest;
import com.website.loveconnect.dto.response.AuthenticationResponse;
import com.website.loveconnect.dto.response.IntrospectResponse;

import javax.naming.AuthenticationException;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws AuthenticationException, JOSEException;
    IntrospectResponse introspect(IntrospectRequest introspectRequest) throws AuthenticationException,JOSEException;
}
