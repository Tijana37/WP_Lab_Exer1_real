package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Exceptions.CourseIDException;
import mk.ukim.finki.wp.lab.model.Exceptions.TeacherNotFound;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.Type;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.apache.coyote.Request;
import org.h2.engine.Session;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = {"/courses"})
public class CourseController {
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final StudentService studentService;

    public CourseController(CourseService courseService, TeacherService teacherService, StudentService studentService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    @GetMapping
    public String getCoursesPage(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("coursesController", courseService.listAll());
        return "listCourses";
    }

    @GetMapping("/submit/{id}")
    public String submitCourse(@PathVariable Long id, Model model, HttpServletRequest request) {
        model.addAttribute("students", this.studentService.listAll());
        //request.getSession().setAttribute("chosenCourseId", chosenCourse.getCourseId());

        try {
            Course chosenCourse = courseService.getCourse(id);
            // moze i preku model, ama deka prethodno mi e so sesiski atribut zatoa i sega vaka, da ne menuvam vo html
            // model.addAttribute("choosenCourse", id);
            request.getSession().setAttribute("chosenCourseName", chosenCourse.getName());
            request.getSession().setAttribute("chosenCourseId", chosenCourse.getCourseId());

        } catch (CourseIDException e) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", e.getMessage() + " in method /courses/submit");
        }

        return "listStudents";
    }


    @PostMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveCourse(@RequestParam String courseName, @RequestParam String description,
                             @RequestParam String teacherId, @RequestParam String type,
                             HttpServletRequest request, Model model) {
        Course toEditCourse;
        Optional<Teacher> t;
        List<Student> oldStudents = null;
        try {
            toEditCourse = (Course) request.getSession().getAttribute("editCourse");
            t = teacherService.findByID(Long.parseLong(teacherId));
            if (toEditCourse != null) {
                //edit on old course is needed, not adding a new Course
                oldStudents = toEditCourse.getStudents();
                this.courseService.deleteCourse(toEditCourse.getCourseId());
                request.getSession().setAttribute("editCourse", null);
            }
            courseService.addCourse(courseName, description, teacherId,type).setStudents(oldStudents);
        } catch (CourseIDException | TeacherNotFound e) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", e.getMessage() + " in method /courses/add-form");
        }
        return "redirect:/courses";
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteCourse(@PathVariable Long id, Model model) {
        try {
            this.courseService.deleteCourse(id);
        } catch (CourseIDException e) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", e.getMessage() + " in method /courses/delete/{id}");
        }
        return "redirect:/courses";
    }

    @PostMapping({"/add/edit-form/{id}", "/add/edit-form"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getEditCoursePage(@PathVariable(required = false) Long id, Model model, HttpServletRequest req) {
        try {
            if (id != null) { //bazata frla error koga prebaruvame primaren kluc null
                req.getSession().setAttribute("editCourse", courseService.getCourse(id));
                model.addAttribute("editCourse", courseService.getCourse(id));
            } else req.getSession().setAttribute("editCourse", null);

        } catch (CourseIDException e) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", e.getMessage() + " in method /courses/add/edit-form/{id}");
        }
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("types", Type.values());
        return "add-course";
    }


}
