package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Exceptions.CourseIDException;
import mk.ukim.finki.wp.lab.model.Exceptions.TeacherNotFound;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping(value = {"/courses"})
public class CourseController {
    private final CourseService courseService;
    private final TeacherService teacherService;

    public CourseController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @GetMapping
    public String getCoursesPage(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("coursesController", courseService.listAll());
        return "listCourses";
    }


    @PostMapping("/add-form")
    public String saveCourse(@RequestParam String courseName, @RequestParam String description,
                             @RequestParam String teacherId, HttpServletRequest request, Model model) {
        Course toEditCourse;
        Optional<Teacher> t;
        try {
            toEditCourse = (Course) request.getSession().getAttribute("editCourse");
            t = teacherService.findByID(teacherId);
            if (toEditCourse != null) {
                //edit on old course is needed, not adding a new Course
                this.courseService.deleteCourse(toEditCourse.getCourseId());
                request.getSession().setAttribute("editCourse", null);
            }
            courseService.addCourse(courseName, description, teacherId);
        } catch (CourseIDException | TeacherNotFound e) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", e.getMessage()+" in method /courses/add-form");
        }
        return "redirect:/courses";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id, Model model) {
        try {
            this.courseService.deleteCourse(id);
        } catch (CourseIDException e) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", e.getMessage()+" in method /courses/delete/{id}");
        }
        return "redirect:/courses";
    }

    @PostMapping({"/add/edit-form/{id}", "/add/edit-form"})
    public String getEditCoursePage(@PathVariable(required = false) Long id, Model model, HttpServletRequest req) {
        try {
            req.getSession().setAttribute("editCourse", courseService.getCourse(id));
            model.addAttribute("editCourse", courseService.getCourse(id));
        } catch (CourseIDException e) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", e.getMessage()+" in method /courses/add/edit-form/{id}");
        }
        model.addAttribute("teachers", teacherService.findAll());
        return "add-course";
    }


}
