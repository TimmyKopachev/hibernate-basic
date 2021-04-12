package org.dzmitry.kapachou.hibernate.service;

import lombok.AllArgsConstructor;
import org.dzmitry.kapachou.hibernate.jpa.AbstractCrudService;
import org.dzmitry.kapachou.hibernate.jpa.BaseRepository;
import org.dzmitry.kapachou.hibernate.model.Task;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskService extends AbstractCrudService<Task> {

    private final TaskRepository taskRepository;

    @Override
    protected BaseRepository<Task> getRepository() {
        return taskRepository;
    }
}
