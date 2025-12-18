package com.ecom.entities;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")

public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
    private int id;
	@Column
    private String name;
    @Column
    private String rollno;
	@Column
    private String email;
	@Column
    private String password;
	@Column
    private String phoneno;

    // Getter and Setter for id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    // Getter and Setter for name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for rollno
    public String getRollno() {
        return rollno;
    }
    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter and Setter for phoneno
    public String getPhoneno() {
        return phoneno;
    }
    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    // Fees fields
    @Column(name = "fees_due")
    private java.math.BigDecimal feesDue = java.math.BigDecimal.ZERO;

    @Column(name = "fees_paid")
    private boolean feesPaid = false;

    public java.math.BigDecimal getFeesDue() { return feesDue; }
    public void setFeesDue(java.math.BigDecimal feesDue) { this.feesDue = feesDue; }
    public boolean isFeesPaid() { return feesPaid; }
    public void setFeesPaid(boolean feesPaid) { this.feesPaid = feesPaid; }

    // --- Added for teacher dashboard ---
    @Column
    private String department;
    @Column
    private String subject;
    @Column
    private String attendance;
    @Column
    private String marks;
    @Column
    private String totalMarks;
    @Column
    private String totalClass;

    // Added for student stream selection
    @Column
    private String stream;

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getAttendance() { return attendance; }
    public void setAttendance(String attendance) { this.attendance = attendance; }
    public String getMarks() { return marks; }
    public void setMarks(String marks) { this.marks = marks; }
    public String getTotalMarks() { return totalMarks; }
    public void setTotalMarks(String totalMarks) { this.totalMarks = totalMarks; }
    public String getTotalClass() { return totalClass; }
    public void setTotalClass(String totalClass) { this.totalClass = totalClass; }
}
