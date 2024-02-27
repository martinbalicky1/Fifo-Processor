package org.mb.repository;

import org.mb.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(int id);
    List<User> findAll();
    void deleteById(int id);
    void deleteAll();
}
