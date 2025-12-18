        
package com.ecom.controller;
import com.ecom.service.StudentResultService;
import com.ecom.service.StudentMarksService;
import com.ecom.entities.StudentResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ecom.entities.Teacher;
import com.ecom.entities.Student;
import com.ecom.entities.Admin;
import com.ecom.service.TeacherService;
import com.ecom.service.StudentService;
import com.ecom.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
@Controller
public class MyController {
        // Direct /register mapping: show error message and register page
        @GetMapping("/register")
        public String directRegisterPage(Model model) {
            model.addAttribute("errorMsg", "You do not have registered access. Please contact your college admin.");
            model.addAttribute("form", new RegistrationForm());
            return "register";
        }
    // Save all student marks fields to student_marks table
    @PostMapping("/teacher/save-student-marks")
    public String saveStudentMarks(
        @RequestParam String rollNo,
        @RequestParam String studentName,
        @RequestParam String subject,
        @RequestParam String marksObtained,
        @RequestParam String totalMarks,
        @RequestParam String totalClass,
        @RequestParam String attendance,
        org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes
    ) {
        com.ecom.entities.StudentMarks marks = new com.ecom.entities.StudentMarks();
        marks.setRollNo(rollNo);
        marks.setStudentName(studentName);
        marks.setSubject(subject);
        // Convert String to Integer for numeric fields
        try {
            marks.setMarksObtained(Integer.parseInt(marksObtained));
        } catch (NumberFormatException e) {
            marks.setMarksObtained(null);
        }
        try {
            marks.setTotalMarks(Integer.parseInt(totalMarks));
        } catch (NumberFormatException e) {
            marks.setTotalMarks(null);
        }
        try {
            marks.setTotalClass(Integer.parseInt(totalClass));
        } catch (NumberFormatException e) {
            marks.setTotalClass(null);
        }
        try {
            marks.setAttendance(Integer.parseInt(attendance));
        } catch (NumberFormatException e) {
            marks.setAttendance(null);
        }
        System.out.println("[DEBUG] Saving marks: rollNo=" + rollNo + ", subject=" + subject + ", marksObtained=" + marks.getMarksObtained() + ", totalMarks=" + marks.getTotalMarks());
        studentMarksService.saveOrUpdate(marks);
        redirectAttributes.addFlashAttribute("message", "Marks saved successfully!");
        return "redirect:/teacher/dashboard";
    }
    // --- Teacher: Submit Marks ---
    // Show marks submission form
    @GetMapping("/teacher/submit-marks")
    public String showMarksForm(Model model) {
        model.addAttribute("marksForm", new com.ecom.entities.StudentMarks());
        return "submit_marks"; // Create submit_marks.html in templates
    }

    // Handle marks submission
    @PostMapping("/teacher/submit-marks")
    public String submitMarks(@ModelAttribute("marksForm") com.ecom.entities.StudentMarks marks,
                              org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        studentMarksService.saveOrUpdate(marks);
        redirectAttributes.addFlashAttribute("message", "Marks submitted successfully!");
        return "redirect:/teacher/submit-marks";
    }

    @Autowired
    private StudentResultService studentResultService;

    @Autowired
    private StudentMarksService studentMarksService;

    // Student Result Page
    @GetMapping("/student/result")
    public String studentResultPage(Model model, @SessionAttribute(value = "rollNumber", required = false) String rollNumber) {
        Student student = null;
        if (rollNumber != null) {
            student = studentService.findByRollno(rollNumber);
        }
        if (student != null) {
            model.addAttribute("student", student);
            // Fetch all marks for this student by roll number
            java.util.List<com.ecom.entities.StudentMarks> results = studentMarksService.findAll();
            java.util.Map<String, com.ecom.entities.StudentMarks> subjectToMarks = new java.util.HashMap<>();
            for (com.ecom.entities.StudentMarks m : results) {
                if (m.getRollNo() != null && m.getRollNo().equals(student.getRollno()) && m.getSubject() != null) {
                    // Always keep the latest (overwrite if duplicate subject)
                    subjectToMarks.put(m.getSubject(), m);
                }
            }
            java.util.List<com.ecom.entities.StudentMarks> studentResults = new java.util.ArrayList<>(subjectToMarks.values());
            System.out.println("[DEBUG] Student results for rollNo=" + student.getRollno() + ":");
            for (com.ecom.entities.StudentMarks m : studentResults) {
                System.out.println("[DEBUG] Subject=" + m.getSubject() + ", marksObtained=" + m.getMarksObtained() + ", totalMarks=" + m.getTotalMarks());
            }
            model.addAttribute("results", studentResults);
        }
        return "student_result";
    }

    // Student Login Page
    @GetMapping("/student_login")
    public String studentLoginPage() {
        return "student_login";
    }

    // STUDENT LOGIN FORM (by roll number and password)
    @PostMapping("/student/loginForm")
    public String studentLoginForm(@RequestParam String rollNumber,
                                   @RequestParam String password,
                                   Model model,
                                   HttpServletRequest request) {
        Student student = studentService.findByRollnoAndPassword(rollNumber, password);
        if (student != null) {
            model.addAttribute("modelName", student.getName());
            // Set roll number in session
            HttpSession session = request.getSession(true);
            session.setAttribute("modelName", student.getName());
            session.setAttribute("rollNumber", student.getRollno());
            return "student_dashboard";
        } else {
            model.addAttribute("errorMsg", "Invalid roll number or password");
            return "student_login";
        }
    }

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private AdminService adminService;

    // ROLE SELECT / HOME
    @GetMapping("/roleSelect")
    public String roleSelect() {
        return "role-select";
    }

    // Help / Contact page
    @GetMapping({"/help","/contact-us"})
    public String helpPage() {
        // return the contact-us Thymeleaf template
        return "contact-us";
    }

    // About page
    @GetMapping("/about")
    public String aboutPage(Model model) {
        // return the about Thymeleaf template
        return "about";
    }

    // OPEN REGISTER PAGE (role param optional)
    @GetMapping("/regPage")
    public String openRegPage(@RequestParam(required = false) String role, Model model) {
        model.addAttribute("form", new RegistrationForm());
        model.addAttribute("role", role); // pre-select in template
        return "register";
    }

    // SUBMIT REGISTER FORM (single handler for all roles)
    @PostMapping("/regform")
    public String submitRegForm(@ModelAttribute("form") RegistrationForm form, Model model) {
        String role = form.getRole();
        boolean status = false;
        if ("TEACHER".equalsIgnoreCase(role)) {
            Teacher t = new Teacher();
            t.setName(form.getName());
            t.setEmail(form.getEmail());
            t.setPassword(form.getPassword());
            // set other teacher fields if needed
            status = teacherService.registerTeacher(t);
        } else if ("STUDENT".equalsIgnoreCase(role)) {
            // Student self-registration is disabled. Only admin can add students.
            model.addAttribute("errorMsg", "Student registration is disabled. Please contact admin.");
            return "register";
        } else {
            model.addAttribute("errorMsg", "Unknown role selected");
            return "register";
        }

        if (status) {
            // redirect to role-specific login page
            if ("TEACHER".equalsIgnoreCase(role)) {
                return "redirect:/loginPage?role=TEACHER";
            } else if ("STUDENT".equalsIgnoreCase(role)) {
                return "redirect:/loginPage?role=STUDENT";
            }
            // If another role, fall through to show register page with role
        } else {
            model.addAttribute("errorMsg", "Registration failed");
        }
        model.addAttribute("role", role);
        return "register";
    }

    // Redirect /login to teacher login page by default
    @GetMapping("/login")
    public String redirectToTeacherLogin() {
        return "redirect:/teacher/login";
    }
    

    // OPEN LOGIN PAGE (role-aware)
    @GetMapping("/loginPage")
    public String openLoginPage(@RequestParam(required = false) String role, Model model) {
        model.addAttribute("role", role);
        return "login";
    }

    // SUBMIT LOGIN FORM (single handler)
    @PostMapping("/loginForm")
    public String submitLoginForm(@RequestParam String email,
                                  @RequestParam String password,
                                  @RequestParam String role,
                                  Model model) {

        if ("TEACHER".equalsIgnoreCase(role)) {
            Teacher validUser = teacherService.loginUser(email, password);
            if (validUser != null) {
                // Set session attributes for dashboard
                HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
                session.setAttribute("modelName", validUser.getName());
                session.setAttribute("modelEmail", validUser.getEmail());
                return "redirect:/teacher/dashboard";
            }
        } else if ("STUDENT".equalsIgnoreCase(role)) {
            Student validUser = studentService.loginUser(email, password);
            if (validUser != null) {
                model.addAttribute("modelName", validUser.getName());
                model.addAttribute("modelEmail", validUser.getEmail());
                return "student_dashboard";
            }
        }

        model.addAttribute("errorMsg", "Invalid email or password");
        model.addAttribute("role", role);
        return "login";
    }

    // ADMIN: show login (email + phone)
    @GetMapping({"/admin/login","/Admin_login"})
    public String adminLoginPage(Model model, @RequestParam(required = false) String msg) {
        model.addAttribute("showRegister", false);
        if (msg != null) {
            model.addAttribute("errorMsg", msg);
        }
        return "Admin_login"; // JSP view
    }

    // Handle form POST from JSP login (Adm_Form)
    @PostMapping("/Adm_Form")
    public String adminLoginFormSubmit(@RequestParam String email, @RequestParam("password") String password, Model model, HttpServletRequest request) {
        try {
            Admin admin = adminService.loginAdmin(email, password);
            if (admin != null) {
                // create session and auto-login
                HttpSession session = request.getSession(true);
                session.setAttribute("admin", admin);
                // Redirect to admin dashboard after login
                return "redirect:/admin/dashboard";
            }
            model.addAttribute("errorMsg", "Account not found or invalid credentials.");
            model.addAttribute("showRegister", true);
            return "Admin_login";
        } catch (org.springframework.dao.DataAccessException ex) {
            // handle DB issues (e.g., duplicate data) gracefully
            ex.printStackTrace();
            model.addAttribute("errorMsg", "Internal error while authenticating. Please contact admin.");
            model.addAttribute("showRegister", true);
            return "Admin_login";
        }
    }

    // Admin register page (JSP)
    @GetMapping({"/admin/register","/Admin_Register"})
    public String adminRegisterPage(Model model) {
        return "Admin_Register"; // JSP view
    }

    // Handle register POST from JSP (Ad_Form)
    @PostMapping("/Ad_Form")
    public String adminRegisterFormSubmit(@RequestParam String name,
                                          @RequestParam String email,
                                          @RequestParam("phoneno") String phoneno,
                                          @RequestParam("password") String password,
                                          Model model,
                                          HttpServletRequest request) {
        Admin admin = new Admin();
        admin.setName(name);
        admin.setEmail(email);
        admin.setPhone(phoneno);
        admin.setRole("ADMIN");
        admin.setPassword(password); // will be encoded in service
        boolean ok = adminService.registerAdmin(admin);
        if (ok) {
            // verify saved
            Admin saved = adminService.findByEmail(admin.getEmail());
            if (saved != null) {
                // Registration successful, redirect to admin login page
                return "redirect:/Admin_login?msg=Registration successful. Please login.";
            } else {
                model.addAttribute("errorMsg", "Registration appears to have failed to persist. Check DB settings.");
                return "Admin_Register";
            }
        }
        model.addAttribute("errorMsg", "Registration failed. Try again.");
        return "Admin_Register";
    }


    // Logout unchanged
    @GetMapping("/logout")
    public String logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        return "login";
    }
    // Student Dashboard Page
    @GetMapping("/student_dashboard")
    public String studentDashboard(Model model, @SessionAttribute(value = "modelName", required = false) String name,
                                  @SessionAttribute(value = "rollNumber", required = false) String rollNumber) {
        model.addAttribute("modelName", name);
        model.addAttribute("rollNumber", rollNumber);
        // Fetch full student details from DB using roll number
        if (rollNumber != null) {
            Student student = studentService.findByRollno(rollNumber);
            if (student != null) {
                model.addAttribute("studentDetails", student);
            } else {
                model.addAttribute("errorMsg", "No student details found for your account. Please contact admin.");
                System.out.println("DEBUG: No student found for roll number: " + rollNumber);
            }
        } else {
            model.addAttribute("errorMsg", "No roll number found in session. Please log in again.");
            System.out.println("DEBUG: No roll number in session for student dashboard.");
        }
        return "student_dashboard";
    }
        // Student Details Page
    @GetMapping("/student/details")
    public String studentDetailsPage(Model model, @SessionAttribute(value = "rollNumber", required = false) String rollNumber) {
        if (rollNumber != null) {
            System.out.println("Looking for student with roll number: " + rollNumber);
            Student student = studentService.findByRollno(rollNumber);
            System.out.println("Student found: " + (student != null ? student.getName() : "null"));
            model.addAttribute("student", student);
        } else {
            System.out.println("No roll number in session for student details page.");
        }
        return "student_details";
    }
    @Autowired
    private com.ecom.service.ExamFormService examFormService;
    // Exam Form Fillup Page
    @GetMapping("/student/exam-form")
    public String showExamForm(Model model, @SessionAttribute(value = "modelEmail", required = false) String email) {
        if (email != null) {
            com.ecom.entities.Student student = studentService.findByEmail(email);
            model.addAttribute("student", student);
        }
        model.addAttribute("examForm", new com.ecom.entities.ExamForm());
        return "exam_form";
    }

    // Handle Exam Form Submission
    @PostMapping("/student/exam-form")
    public String submitExamForm(@ModelAttribute("examForm") com.ecom.entities.ExamForm form,
                                 @SessionAttribute(value = "modelEmail", required = false) String email,
                                 org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        if (email != null) {
            form.setStudentEmail(email);
            com.ecom.entities.Student student = studentService.findByEmail(email);
            if (student != null) {
                form.setStudentName(student.getName());
                form.setRollno(student.getRollno());
            }
        }
        examFormService.submitForm(form);
        redirectAttributes.addFlashAttribute("message", "Exam form submitted successfully!");
        return "redirect:/student/exam-form/view";
    }
    // Show bulk marks/attendance entry page for teachers
    @GetMapping("/teacher/bulk-marks")
    public String showBulkMarksForm(Model model) {
        java.util.List<Student> students = studentService.findAll();
        MarksForm marksForm = new MarksForm();
        java.util.List<com.ecom.entities.StudentMarks> marksList = new java.util.ArrayList<>();
        for (Student s : students) {
            com.ecom.entities.StudentMarks m = new com.ecom.entities.StudentMarks();
            m.setRollNo(s.getRollno());
            m.setStudentName(s.getName());
            m.setDepartment(s.getDepartment());
            m.setSubject("");
            m.setTotalMarks(null);
            m.setMarksObtained(null);
            m.setTotalClass(null);
            m.setAttendance(null);
            marksList.add(m);
        }
        marksForm.setStudents(marksList);
        model.addAttribute("students", students);
        model.addAttribute("marksForm", marksForm);
        return "teacher_bulk_marks";
    }

    // View Exam Form Submission
    @GetMapping("/student/exam-form/view")
    public String viewExamForm(Model model, @SessionAttribute(value = "modelEmail", required = false) String email) {
        if (email != null) {
            java.util.List<com.ecom.entities.ExamForm> forms = examFormService.getFormsByStudentEmail(email);
            model.addAttribute("forms", forms);
        }
        // message attribute (if present) will be shown in the view
        return "exam_form_view";
    }
        // DTO for bulk marks/attendance submission
    public static class MarksForm {
        private java.util.List<com.ecom.entities.StudentMarks> students;
        public java.util.List<com.ecom.entities.StudentMarks> getStudents() { return students; }
        public void setStudents(java.util.List<com.ecom.entities.StudentMarks> students) { this.students = students; }
    }

    // Bulk submit marks and attendance for all students
    @PostMapping("/teacher/submit-marks-bulk")
    public String submitMarksBulk(@ModelAttribute MarksForm marksForm) {
        if (marksForm != null && marksForm.getStudents() != null) {
            for (com.ecom.entities.StudentMarks marks : marksForm.getStudents()) {
                studentMarksService.saveOrUpdate(marks);
            }
        }
        return "redirect:/teacher/dashboard";
    }
 // DTO for student marks upload form
    public static class StudentMarksUploadForm {
        private String studentName;
        private String rollNo;
        private String department;
        private String stream;
        private String subject;
        private Integer totalMarks;
        private Integer marksObtained;

        // Getters and setters
        public String getStudentName() { return studentName; }
        public void setStudentName(String studentName) { this.studentName = studentName; }
        public String getRollNo() { return rollNo; }
        public void setRollNo(String rollNo) { this.rollNo = rollNo; }
        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }
        public String getStream() { return stream; }
        public void setStream(String stream) { this.stream = stream; }
        public String getSubject() { return subject; }
        public void setSubject(String subject) { this.subject = subject; }
        public Integer getTotalMarks() { return totalMarks; }
        public void setTotalMarks(Integer totalMarks) { this.totalMarks = totalMarks; }
        public Integer getMarksObtained() { return marksObtained; }
        public void setMarksObtained(Integer marksObtained) { this.marksObtained = marksObtained; }
    }

    // Show student marks upload form
    @GetMapping("/teacher/marks-upload")
    public String showStudentMarksUploadForm(Model model) {
        model.addAttribute("marksForm", new StudentMarksUploadForm());
        return "teacher_marks_upload";
    }

    // Handle student marks upload form submission
    @PostMapping("/teacher/marks-upload")
    public String handleStudentMarksUpload(@ModelAttribute("marksForm") StudentMarksUploadForm form,
                                           org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        com.ecom.entities.StudentMarks marks = new com.ecom.entities.StudentMarks();
        marks.setStudentName(form.getStudentName());
        marks.setRollNo(form.getRollNo());
        marks.setDepartment(form.getDepartment());
        marks.setStream(form.getStream());
        marks.setSubject(form.getSubject());
        marks.setTotalMarks(form.getTotalMarks());
        marks.setMarksObtained(form.getMarksObtained());
        studentMarksService.saveOrUpdate(marks);
        redirectAttributes.addFlashAttribute("message", "Student marks uploaded successfully!");
        return "redirect:/teacher/marks-upload";
    }
// Dedicated Teacher Dashboard Page
    @GetMapping("/teacher/dashboard")
    public String teacherDashboard(Model model, @SessionAttribute(value = "modelName", required = false) String name,
                                  @SessionAttribute(value = "modelEmail", required = false) String email) {
        model.addAttribute("modelName", name);
        model.addAttribute("modelEmail", email);
        java.util.List<Student> students = studentService.findAll();
        // Marks form setup
        MarksForm marksForm = new MarksForm();
        java.util.List<com.ecom.entities.StudentMarks> marksList = new java.util.ArrayList<>();
        // Attendance form setup
        AttendanceForm attendanceForm = new AttendanceForm();
        java.util.List<com.ecom.entities.StudentMarks> attendanceList = new java.util.ArrayList<>();
        for (Student s : students) {
            com.ecom.entities.StudentMarks m = new com.ecom.entities.StudentMarks();
            m.setRollNo(s.getRollno());
            m.setStudentName(s.getName());
            m.setDepartment(s.getDepartment());
            m.setStream(s.getStream());
            m.setSubject("");
            m.setTotalMarks(null);
            m.setMarksObtained(null);
            marksList.add(m);
            // Attendance fields
            com.ecom.entities.StudentMarks a = new com.ecom.entities.StudentMarks();
            a.setRollNo(s.getRollno());
            a.setStudentName(s.getName());
            a.setStream(s.getStream());
            a.setSubject("");
            a.setTotalClass(null);
            a.setAttendance(null);
            attendanceList.add(a);
        }
        marksForm.setStudents(marksList);
        attendanceForm.setStudents(attendanceList);
        model.addAttribute("students", students);
        model.addAttribute("marksForm", marksForm);
        model.addAttribute("attendanceForm", attendanceForm);
        return "teacher_dashboard";
    }

    // Bulk submit attendance for all students
    @PostMapping("/teacher/submit-attendance-bulk")
    public String submitAttendanceBulk(@ModelAttribute AttendanceForm attendanceForm) {
        if (attendanceForm != null && attendanceForm.getStudents() != null) {
            for (com.ecom.entities.StudentMarks att : attendanceForm.getStudents()) {
                studentMarksService.saveOrUpdate(att);
            }
        }
        return "redirect:/teacher/dashboard";
    }

    // DTO for bulk attendance submission
    public static class AttendanceForm {
        private java.util.List<com.ecom.entities.StudentMarks> students;
        public java.util.List<com.ecom.entities.StudentMarks> getStudents() { return students; }
        public void setStudents(java.util.List<com.ecom.entities.StudentMarks> students) { this.students = students; }
    }
    
    // Simple DTO for unified binding
    public static class RegistrationForm {
        private String name;
        private String email;
        private String password;
        private String role;
        // getters / setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }
}