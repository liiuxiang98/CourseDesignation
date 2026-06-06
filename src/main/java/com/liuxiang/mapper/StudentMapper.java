package com.liuxiang.mapper;
import com.liuxiang.pojo.Student;
import com.liuxiang.pojo.dto.ChartVO;
import com.liuxiang.pojo.dto.ScoreDTO;
import com.liuxiang.pojo.dto.ScoreVO;
import com.liuxiang.pojo.dto.StudentDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface StudentMapper{
    @Insert("insert into students(student_name, gender, birth_date,class_id,phone) values(#{studentName},#{gender},#{birthDate},#{classId},#{phone})")
    void addStudent(Student student);

    void addGrade(ScoreDTO score);

    List<ScoreVO> getGradeByStudentId(Integer studentId);

    //根据学生姓名查询学生成绩（模糊查询）
    List<ScoreDTO> getGradeByStudentName(@Param("studentName") String studentName);

    List<ChartVO> getGradeChart(Integer studentId);

    void deleteStudent(Integer studentId);

    Student getStudent(Integer studentId);

    void updateStudent(Student student);
}
