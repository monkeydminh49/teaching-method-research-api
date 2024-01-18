package com.minhdunk.research.repository;

import com.minhdunk.research.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, String> {

    Optional<Media> findByName(String fileName);
}
