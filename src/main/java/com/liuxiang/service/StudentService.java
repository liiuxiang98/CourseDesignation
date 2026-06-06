package com.liuxiang.service;
import com.liuxiang.pojo.Student;
import com.liuxiang.pojo.dto.ChartVO;
import com.liuxiang.pojo.dto.ScoreDTO;
import com.liuxiang.pojo.dto.ScoreVO;
import com.liuxiang.pojo.dto.StudentDTO;
import java.util.List;
public interface StudentService {

    void addStudent(Student student);

    void addGrade(ScoreDTO score);

    List<ScoreVO> getGradeByStudentId(Integer studentId);

    List<ScoreDTO> getGradeByStudentName(String studentName);

    List<ChartVO> getGradeChart(Integer studentId);

    void deleteStudent(Integer studentId);

    Student getStudent(Integer studentId);

    void updateStudent(Student student);
}
