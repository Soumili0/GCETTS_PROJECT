package com.ecom.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exam_form")
public class ExamForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String studentEmail;

    @Column(nullable = false)
    private String studentName;

    @Column(nullable = false)
    private String rollno;

    @Column(nullable = false)
    private String semester;

    @Column(nullable = false)
    private String subjects; // comma-separated or JSON string

    @Column
    private String remarks;

    @Column(nullable = false)
    private Double fee = 0.0;
}
