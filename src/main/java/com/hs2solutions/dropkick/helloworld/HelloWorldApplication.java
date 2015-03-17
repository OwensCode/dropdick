package com.hs2solutions.dropkick.helloworld;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.basic.BasicAuthFactory;

import com.hs2solutions.dropkick.helloworld.auth.ExampleAuthenticator;
import com.hs2solutions.dropkick.helloworld.core.Person;
import com.hs2solutions.dropkick.helloworld.core.User;
import com.hs2solutions.dropkick.helloworld.db.PersonDAO;
import com.hs2solutions.dropkick.helloworld.health.TemplateHealthCheck;
import com.hs2solutions.dropkick.helloworld.resources.HelloWorldResource;
import com.hs2solutions.dropkick.helloworld.resources.PeopleResource;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

	private final HibernateBundle<HelloWorldConfiguration> hibernateBundle;

	public HelloWorldApplication() {
		this.hibernateBundle = instantiateHibernateBundle();
	}

	public static void main(String[] args) throws Exception {
		new HelloWorldApplication().run(args);
	}

	@Override
	public String getName() {
		return "hello-world";
	}

	@Override
	public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
		bootstrap.addBundle(instantiateMigrationsBundle());
		bootstrap.addBundle(this.hibernateBundle);
	}

	@Override
	public void run(HelloWorldConfiguration configuration,
			Environment environment) throws Exception {

		setupAuthentication(environment);
		startHelloWorldResource(configuration, environment);
		startPeopleResource(configuration, environment);
	}
	
	private void setupAuthentication(Environment environment) {
		environment.jersey().register(
				AuthFactory.binder(new BasicAuthFactory<User>(
						new ExampleAuthenticator(), "SECRET REALM",
						User.class)));
	}

	private void startHelloWorldResource(HelloWorldConfiguration configuration,
			Environment environment) {

		HelloWorldResource resource = new HelloWorldResource(
				configuration.getTemplate(), configuration.getDefaultName());
		
		environment.jersey().register(resource);

		TemplateHealthCheck healthCheck = new TemplateHealthCheck(
				configuration.getTemplate());

		environment.healthChecks().register("template", healthCheck);
	}

	private void startPeopleResource(HelloWorldConfiguration configuration,
			Environment environment) {

		final PersonDAO dao = new PersonDAO(hibernateBundle.getSessionFactory());

		environment.jersey().register(new PeopleResource(dao));

	}

	private MigrationsBundle<HelloWorldConfiguration> instantiateMigrationsBundle() {
		return new MigrationsBundle<HelloWorldConfiguration>() {
			@Override
			public DataSourceFactory getDataSourceFactory(
					HelloWorldConfiguration configuration) {
				return configuration.getDataSourceFactory();
			}
		};
	}

	private HibernateBundle<HelloWorldConfiguration> instantiateHibernateBundle() {
		return new HibernateBundle<HelloWorldConfiguration>(Person.class) {
			@Override
			public DataSourceFactory getDataSourceFactory(
					HelloWorldConfiguration configuration) {
				return configuration.getDataSourceFactory();
			}
		};
	}
}
