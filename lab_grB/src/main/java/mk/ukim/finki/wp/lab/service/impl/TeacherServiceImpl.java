package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Exceptions.TeacherNotFound;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.TeacherFullname;
import mk.ukim.finki.wp.lab.repository.impl.TeacherRepository;
import mk.ukim.finki.wp.lab.repository.jpa.TeacherRepositoryJPA;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {
    //public final TeacherRepository teacherRepository;
    public final TeacherRepositoryJPA teacherRepository;

    public TeacherServiceImpl(TeacherRepositoryJPA teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Optional<Teacher> findByID(Long id) {
        //return teacherRepository.findAll().stream().filter(t->t.getId().equals(Long.parseLong(id))).findFirst();
        return teacherRepository.findById(id);
    }

    @Override
    public void delete(Long id) throws TeacherNotFound {
        //teacherRepository.deleteByID(id)
        if (this.findByID(id).isPresent())
            teacherRepository.delete(this.findByID(id).get());
        else
            throw new TeacherNotFound(id + "in class TeacherServiceImpl");
    }

    @Override
    public Teacher addTeacher(String name, String surname, LocalDate dateOfEmployment) {
        //teacherRepository.addTeacher(name, surname);
        //teacherRepository.save(new Teacher(name,surname));
        TeacherFullname teacherFullname = new TeacherFullname();
        teacherFullname.setName(name);
        teacherFullname.setSurname(surname);
        Teacher t = new Teacher(teacherFullname,dateOfEmployment);
        teacherRepository.save(t);
        return t;
    }


}
