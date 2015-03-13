package com.hs2solutions.dropkick.helloworld.db;

import java.util.List;

import org.hibernate.SessionFactory;

import com.hs2solutions.dropkick.helloworld.core.Person;

import io.dropwizard.hibernate.AbstractDAO;

public class PersonDAO extends AbstractDAO<Person> {
    public PersonDAO(SessionFactory factory) {
        super(factory);
    }

    public Person findById(Long id) {
        return get(id);
    }

    public long create(Person person) {
        return persist(person).getId();
    }

    public List<Person> findAll() {
        return list(namedQuery("com.hs2solutions.dropkick.helloworld.core.Person.findAll"));
    }
}
