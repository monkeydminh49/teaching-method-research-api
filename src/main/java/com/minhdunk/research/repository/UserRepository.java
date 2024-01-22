package com.minhdunk.research.repository;

import com.minhdunk.research.dto.UserOutputDTO;
import com.minhdunk.research.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> findUsersByClassesId(Long id);

    @Query("SELECT new com.minhdunk.research.dto.UserOutputDTO(u.id, u.firstName, u.lastName, u.username, u.role, u.dateOfBirth) " +
            "FROM User u JOIN u.classes c WHERE c.id = ?1")
    List<UserOutputDTO> getUserOutputDTOsByClassesId(Long id);
}