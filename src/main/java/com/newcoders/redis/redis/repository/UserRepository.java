package com.newcoders.redis.redis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newcoders.redis.redis.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
