package org.camunda.bpm.extension.cloud.workload.query.service.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskQueryObjectRepository extends JpaRepository<TaskQueryObject, String> {

  List<TaskQueryObject> findByEventType(TaskQueryObjectStateEnum state);
}
