package repository;


import org.springframework.data.jpa.repository.JpaRepository;

import entitiy.User;

public interface PrimaryUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}