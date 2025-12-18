package com.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ecom.entities.Admin;
import com.ecom.entities.Student;
import com.ecom.entities.Teacher;
import com.ecom.service.AdminService;
import com.ecom.service.StudentService;
import com.ecom.service.TeacherService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    private boolean checkAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("admin") != null;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpServletRequest request) {
        if (!checkAdmin(request)) return "redirect:/Admin_login";
        return "admin/dashboard";
    }

    // Students
    @GetMapping("/students")
    public String studentsList(Model model, HttpServletRequest request,
                               @RequestParam(value = "q", required = false) String q,
                               @RequestParam(value = "fee", required = false) String fee) {
        if (!checkAdmin(request)) return "redirect:/Admin_login";
        List<Student> students = studentService.searchStudents(q, fee);
        if (students == null) students = new java.util.ArrayList<>();
        model.addAttribute("students", students);
        model.addAttribute("q", q);
        model.addAttribute("fee", fee);
        return "admin/students/list";
    }

    @GetMapping("/students/new")
    public String newStudentForm(Model model, HttpServletRequest request) {
        if (!checkAdmin(request)) return "redirect:/Admin_login";
        model.addAttribute("student", new Student());
        return "admin/students/form";
    }

    @PostMapping("/students")
    public String createStudent(@ModelAttribute("student") Student student, HttpServletRequest request) {
        if (!checkAdmin(request)) return "redirect:/Admin_login";
        if (student.getId() != 0) {
            studentService.updateStudent(student);
        } else {
            studentService.registerUser(student);
        }
        return "redirect:/admin/students";
    }

    @GetMapping("/students/{id}/edit")
    public String editStudent(@PathVariable int id, Model model, HttpServletRequest request) {
        if (!checkAdmin(request)) return "redirect:/Admin_login";
        Student s = studentService.findById(id);
        model.addAttribute("student", s);
        return "admin/students/form";
    }

    @PostMapping("/students/{id}/delete")
    public String deleteStudent(@PathVariable int id, HttpServletRequest request) {
        if (!checkAdmin(request)) return "redirect:/Admin_login";
        studentService.deleteStudent(id);
        return "redirect:/admin/students";
    }

    // Teachers
    @GetMapping("/teachers")
    public String teachersList(Model model, HttpServletRequest request) {
        if (!checkAdmin(request)) return "redirect:/Admin_login";
        model.addAttribute("teachers", teacherService.findAll());
        return "admin/teachers/list";
    }

    @GetMapping("/teachers/new")
    public String newTeacherForm(Model model, HttpServletRequest request) {
        if (!checkAdmin(request)) return "redirect:/Admin_login";
        model.addAttribute("teacher", new Teacher());
        return "admin/teachers/form";
    }

    @PostMapping("/teachers")
    public String createTeacher(@ModelAttribute("teacher") Teacher teacher, HttpServletRequest request) {
        if (!checkAdmin(request)) return "redirect:/Admin_login";
        if (teacher.getId() != 0) {
            teacherService.updateTeacher(teacher);
        } else {
            teacherService.registerTeacher(teacher);
        }
        return "redirect:/admin/teachers";
    }

    @GetMapping("/teachers/{id}/edit")
    public String editTeacher(@PathVariable int id, Model model, HttpServletRequest request) {
        if (!checkAdmin(request)) return "redirect:/Admin_login";
        Teacher t = teacherService.findById(id);
        model.addAttribute("teacher", t);
        return "admin/teachers/form";
    }

    @PostMapping("/teachers/{id}/delete")
    public String deleteTeacher(@PathVariable int id, HttpServletRequest request) {
        if (!checkAdmin(request)) return "redirect:/Admin_login";
        teacherService.deleteTeacher(id);
        return "redirect:/admin/teachers";
    }
}
