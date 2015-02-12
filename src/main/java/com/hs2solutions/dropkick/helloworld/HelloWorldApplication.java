package com.hs2solutions.dropkick.helloworld;

import com.hs2solutions.dropkick.helloworld.health.TemplateHealthCheck;
import com.hs2solutions.dropkick.helloworld.resources.HelloWorldResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
		// TODO Auto-generated method stub

	}

	@Override
	public void run(HelloWorldConfiguration configuration,
			Environment environment) throws Exception {

		final HelloWorldResource resource = new HelloWorldResource(
				configuration.getTemplate(), configuration.getDefaultName());
		environment.jersey().register(resource);

		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(
				configuration.getTemplate());
		environment.healthChecks().register("template", healthCheck);
		environment.jersey().register(resource);
	}

}
