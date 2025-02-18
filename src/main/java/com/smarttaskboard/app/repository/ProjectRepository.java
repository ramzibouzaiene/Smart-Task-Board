package com.smarttaskboard.app.repository;

import com.smarttaskboard.app.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByNameIgnoreCase(String projectName);
}
