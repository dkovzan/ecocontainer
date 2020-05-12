package com.econtainer.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.econtainer.base.model.User;

/**
 * @author DubininAY
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
