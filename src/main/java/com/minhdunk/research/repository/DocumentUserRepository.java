package com.minhdunk.research.repository;

import com.minhdunk.research.entity.DocumentUser;
import com.minhdunk.research.utils.DocumentUserKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentUserRepository extends JpaRepository<DocumentUser, DocumentUserKey> {

}
