package com.stackroute.hackathon.repositorycontracts;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.hackathon.domains.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
