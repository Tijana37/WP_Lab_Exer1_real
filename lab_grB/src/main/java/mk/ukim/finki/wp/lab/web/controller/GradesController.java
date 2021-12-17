package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Exceptions.CourseIDException;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.GradeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;

@Controller
@RequestMapping(value="/grades")
public class GradesController {
    private final GradeService gradeService;

    public GradesController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping
    public String showAllGrades(Model model){
        model.addAttribute("allGrades", gradeService.findAllGrades());
        return "grades";
    }

    @PostMapping
    public String gradesBetween(@RequestParam String beginGrade,
                                @RequestParam String endGrade,
                                Model model){
        LocalDateTime beginGrade_ = LocalDateTime.parse(beginGrade);
        LocalDateTime endGrade_ = LocalDateTime.parse(endGrade);
        model.addAttribute("allGrades", gradeService.findGradesBetween(beginGrade_, endGrade_));
        return "grades";
    }

    @PostMapping("/studentsInCourseBetween")
    public String gradesBetweenForStudentsInCourse(@RequestParam String beginGrade,
                                                   @RequestParam String endGrade,
                                                   Model model,
                                                   HttpServletRequest request) throws CourseIDException {
        LocalDateTime beginGrade_ = LocalDateTime.parse(beginGrade);
        LocalDateTime endGrade_ = LocalDateTime.parse(endGrade);
        Long courseId = (Long) request.getSession().getAttribute("chosenCourseId");

        HashMap<Student, Character> map = gradeService.findGradesBetweenForCourse(beginGrade_, endGrade_, courseId);
        model.addAttribute("courseToShow",request.getSession().getAttribute("courseToShow"));
        model.addAttribute("allStudentsInChosenCourse", map.keySet().toArray());
        model.addAttribute("grades", map);
        return "studentsInCourse";
    }
}
