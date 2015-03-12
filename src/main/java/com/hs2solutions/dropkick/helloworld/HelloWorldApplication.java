package com.hs2solutions.dropkick.helloworld;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.hs2solutions.dropkick.helloworld.health.TemplateHealthCheck;
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
	public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
		// A bundle is a reusable group of functionality, used to define
		// an application’s behavior. Part of what this bundle does is to add a
		// DbCommand to the application, which allows you to perform migration.
		bootstrap.addBundle(new MigrationsBundle<HelloWorldConfiguration>() {
			@Override
			public DataSourceFactory getDataSourceFactory(
					HelloWorldConfiguration configuration) {
				return configuration.getDataSourceFactory();
			}
		});
	}

	@Override
	public void run(HelloWorldConfiguration configuration,
			Environment environment) throws Exception {

		HelloWorldResource resource = new HelloWorldResource(
				configuration.getTemplate(), configuration.getDefaultName());

		environment.jersey().register(resource);

		TemplateHealthCheck healthCheck = new TemplateHealthCheck(
				configuration.getTemplate());

		environment.healthChecks().register("template", healthCheck);
	}
}
