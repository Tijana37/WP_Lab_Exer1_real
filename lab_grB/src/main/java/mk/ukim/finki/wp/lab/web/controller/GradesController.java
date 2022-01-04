package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Exceptions.CourseIDException;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.GradeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

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
//        if(gradeService.findAllGrades()!=null) {
//            Iterable<Grade> allGrades = (Iterable<Grade>) model.getAttribute("allGrades");
//            model.addAttribute("hasMoreGrades", true);
//            List<Grade> pageGrade = Collections.singletonList(allGrades.iterator().next());
//            model.addAttribute("pageGrade", pageGrade);
//            ((Iterable<?>) model.getAttribute("allGrades")).iterator().remove();
//        }
        return "grades";
    }


    @GetMapping("/next")
    public String showNextGrade(Model model){
//        model.addAttribute("allGrades", gradeService.findAllGrades());
        Iterable<Grade> allGrades = (Iterable<Grade>) model.getAttribute("allGrades");

//        if(allGrades.iterator().hasNext()){
//            List<Grade> pageGrade = Collections.singletonList(allGrades.iterator().next());
//            model.addAttribute("pageGrade", pageGrade);
//            if(allGrades.iterator().hasNext()){
//                model.getAttribute("allGrades").iterator().remove();
//
//                model.addAttribute("hasMoreGrades", true);
//            }
//            else model.addAttribute("hasMoreGrades", false);
//
//        }
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
                                                   @RequestParam String onlyGrade,
                                                   Model model,
                                                   HttpServletRequest request) throws CourseIDException {
        LocalDateTime beginGrade_ = LocalDateTime.parse(beginGrade);
        LocalDateTime endGrade_ = LocalDateTime.parse(endGrade);
        Long courseId = (Long) request.getSession().getAttribute("chosenCourseId");
        Character grade = onlyGrade.charAt(0);

        HashMap<Student, Character> map = gradeService.findGradesBetweenForCourse(beginGrade_, endGrade_, courseId);
        Set<Student> studentSet = new HashSet<>(map.keySet());
        if (grade!=null){
            for(Student s: studentSet)
                if(map.get(s)!=grade){
                    map.remove(s);
                }

        }
        model.addAttribute("courseToShow",request.getSession().getAttribute("courseToShow"));
        model.addAttribute("allStudentsInChosenCourse", map.keySet().toArray());
        model.addAttribute("grades", map);
        return "studentsInCourse";
    }
}
