package com.hs2solutions.dropkick.helloworld.auth;

import com.google.common.base.Optional;
import com.hs2solutions.dropkick.helloworld.core.User;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class ExampleAuthenticator implements
		Authenticator<BasicCredentials, User> {

	@Override
	public Optional<User> authenticate(BasicCredentials credentials)
			throws AuthenticationException {
		
		if ("secret".equals(credentials.getPassword())) {
			return Optional.of(new User(credentials.getUsername()));
		}
		
		return Optional.absent();
	}
}
