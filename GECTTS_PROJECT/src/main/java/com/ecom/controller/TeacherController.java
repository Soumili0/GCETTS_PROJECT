package com.ecom.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecom.entities.Teacher;
import com.ecom.service.TeacherService;
import com.ecom.service.StudentService;
import com.ecom.entities.Student;

@Controller
class TeacherController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    // Show teacher login page
    @GetMapping("/teacher/login")
    public String showTeacherLogin() {
        return "Teacher_Login";
    }

    // Handle teacher login POST
    @PostMapping("/teacher/login")
    public String processTeacherLogin(@RequestParam("email") String email,
                                      @RequestParam("password") String password,
                                      HttpServletRequest request,
                                      Model model) {
        Teacher teacher = teacherService.loginUser(email, password);
        if (teacher != null) {
            HttpSession session = request.getSession();
            session.setAttribute("teacher", teacher);
            return "redirect:/teacher/dashboard";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "Teacher_Login";
        }
    }

    // Removed duplicate /teacher/dashboard mapping to resolve ambiguous mapping error.

    // FIXED: Set department method inside the class
    @PostMapping("/teacher/set-department")
    public String setDepartment(@RequestParam("department") String department, HttpSession session) {
        session.setAttribute("selectedDepartment", department);
        return "redirect:/teacher/dashboard";
    }
    // Update total marks
    @PostMapping("/teacher/update-total-marks")
    public String updateTotalMarks(@RequestParam("studentId") int studentId,
                                   @RequestParam("totalMarks") String totalMarks) {
        Student student = studentService.findById(studentId);
        if (student != null) {
            student.setTotalMarks(totalMarks);
            studentService.updateStudent(student);
        }
        return "redirect:/teacher/dashboard";
    }

    // Update marks obtained
    @PostMapping("/teacher/update-marks")
    public String updateMarks(@RequestParam("studentId") int studentId,
                              @RequestParam("marks") String marks) {
        Student student = studentService.findById(studentId);
        if (student != null) {
            student.setMarks(marks);
            studentService.updateStudent(student);
        }
        return "redirect:/teacher/dashboard";
    }

    // Update total class
    @PostMapping("/teacher/update-total-class")
    public String updateTotalClass(@RequestParam("studentId") int studentId,
                                   @RequestParam("totalClass") String totalClass) {
        Student student = studentService.findById(studentId);
        if (student != null) {
            student.setTotalClass(totalClass);
            studentService.updateStudent(student);
        }
        return "redirect:/teacher/dashboard";
    }

    // Update attendance
    @PostMapping("/teacher/update-attendance")
    public String updateAttendance(@RequestParam("studentId") int studentId,
                                   @RequestParam("attendance") String attendance) {
        Student student = studentService.findById(studentId);
        if (student != null) {
            student.setAttendance(attendance);
            studentService.updateStudent(student);
        }
        return "redirect:/teacher/dashboard";
    }
}