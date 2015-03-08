package com.hs2solutions.dropkick.helloworld;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.hs2solutions.dropkick.helloworld.resources.HelloWorldResource;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

	public static void main(String[] args) throws Exception {
		new HelloWorldApplication().run(args);
	}

	@Override
	public String getName() {
		return "hello-world";
	}

	@Override
	public void initialize(Bootstrap<HelloWorldConfiguration> arg0) {
	}

	@Override
	public void run(HelloWorldConfiguration configuration,
			Environment environment) throws Exception {

		HelloWorldResource resource = new HelloWorldResource(
				configuration.getTemplate(), configuration.getDefaultName());
		
		environment.jersey().register(resource);
	}
}
