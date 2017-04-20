package org.camunda.bpm.extension.cloud.workload.service.task.query.repository;

import org.camunda.bpm.extension.cloud.workload.service.task.query.TaskQueryObject;
import org.camunda.bpm.extension.cloud.workload.service.task.query.TaskQueryObjectStateEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskQueryObjectRepository extends JpaRepository<TaskQueryObject, String> {

  List<TaskQueryObject> findByEventType(TaskQueryObjectStateEnum state);
}
