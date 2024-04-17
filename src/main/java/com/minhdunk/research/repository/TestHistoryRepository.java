package com.minhdunk.research.repository;

import com.minhdunk.research.entity.TestHistory;
import com.minhdunk.research.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestHistoryRepository extends JpaRepository<TestHistory, Long> {

    @Query("SELECT th FROM TestHistory th")
    List<TestHistory> findByTestId(Long testId);

    @Query("SELECT th FROM TestHistory th WHERE th.test.id = ?1 AND th.submitter.id = ?2")
    List<TestHistory> findByTestIdAndSubmitterId(Long testId, Long id);

    @Query("SELECT DISTINCT th.submitter.id FROM TestHistory th WHERE th.submitter IN ?1")
    List<Long> getTestHistoryByStudentList(List<User> students);
    @Query("SELECT th FROM TestHistory th WHERE th.submitAt IN (SELECT MAX(th2.submitAt) FROM TestHistory th2 WHERE th2.submitter = th.submitter AND th2.submitter IN :submitters GROUP BY th2.submitter) AND th.submitter IN :submitterIds")
    List<TestHistory> findLatestTestHistoryPerSubmitterInListOfSubmitter(List<User> submitters);

}
