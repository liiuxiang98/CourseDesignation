package com.liuxiang.pojo.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private String studentName;
    private Short gender;
    private LocalDate birthDate;
}
