package com.liuxiang.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student{
    private Integer studentId;
    private String studentName;
    private Short gender;
    private LocalDate birthDate;
    private String classId;
    private String phone;
}
