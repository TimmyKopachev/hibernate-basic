package org.dzmitry.kapachou.hibernate.service;

import lombok.AllArgsConstructor;
import org.dzmitry.kapachou.hibernate.jpa.AbstractCrudService;
import org.dzmitry.kapachou.hibernate.jpa.BaseRepository;
import org.dzmitry.kapachou.hibernate.model.Student;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentService extends AbstractCrudService<Student> {

    private final StudentRepository studentRepository;

    @Override
    protected BaseRepository<Student> getRepository() {
        return studentRepository;
    }
}
