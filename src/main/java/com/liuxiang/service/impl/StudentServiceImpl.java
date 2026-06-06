package com.liuxiang.service.impl;
import com.liuxiang.mapper.StudentMapper;
import com.liuxiang.pojo.Student;
import com.liuxiang.pojo.dto.ChartVO;
import com.liuxiang.pojo.dto.ScoreDTO;
import com.liuxiang.pojo.dto.ScoreVO;
import com.liuxiang.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    //添加学生
    @Override
    public void addStudent(Student student) {
        studentMapper.addStudent(student);
    }
    @Override
    public void addGrade(ScoreDTO score){
        studentMapper.addGrade(score);
    }
    @Override
    public List<ScoreVO> getGradeByStudentId(Integer studentId) {
        return studentMapper.getGradeByStudentId(studentId);
    }
    @Override
    public List<ScoreDTO> getGradeByStudentName(String studentName) {
        return studentMapper.getGradeByStudentName(studentName);
    }
    @Override
    public List<ChartVO> getGradeChart(Integer studentId) {
        return studentMapper.getGradeChart(studentId);
    }
    @Override
    public void deleteStudent(Integer studentId) {
        studentMapper.deleteStudent(studentId);
    }
    @Override
    public Student getStudent(Integer studentId) {
        return studentMapper.getStudent(studentId);
    }
    @Override
    public void updateStudent(Student student) {
        studentMapper.updateStudent(student);
    }
}
