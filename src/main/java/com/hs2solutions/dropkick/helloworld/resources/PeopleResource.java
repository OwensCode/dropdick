package com.hs2solutions.dropkick.helloworld.resources;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.hs2solutions.dropkick.helloworld.core.Person;
import com.hs2solutions.dropkick.helloworld.db.PersonDAO;

import java.util.List;

@Path("/people")
@Produces(MediaType.APPLICATION_JSON)
public class PeopleResource {

    private final PersonDAO personDao;

    public PeopleResource(PersonDAO peopleDAO) {
        this.personDao = peopleDAO;
    }

    @POST
    @UnitOfWork
    public Person createPerson(Person person) {
        long id = personDao.create(person);
        return personDao.findById(id);
    }

    @GET
    @Timed
    @UnitOfWork
    public List<Person> listPeople() {
        return personDao.findAll();
    }
}

