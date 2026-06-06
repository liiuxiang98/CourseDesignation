package com.liuxiang.controller;
import com.liuxiang.pojo.Result;
import com.liuxiang.pojo.Student;
import com.liuxiang.pojo.dto.ChartVO;
import com.liuxiang.pojo.dto.ScoreDTO;
import com.liuxiang.pojo.dto.ScoreVO;
import com.liuxiang.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Slf4j
@CrossOrigin
@RestController
public class StudentController{
    @Autowired
    private StudentService studentService;
    //添加学生功能
    @PostMapping("/add/info")
    public Result addStudent(@RequestBody Student student){
        log.info("执行了添加学生功能");
        studentService.addStudent(student);
        return Result.success();
    }
    //添加学生成绩功能
    @PostMapping("/add/grade")
    public Result addGrade(@RequestBody ScoreDTO score){
        log.info("执行了添加学生成绩功能");
        studentService.addGrade(score);
        return Result.success();
    }
    //根据学生学号查询学生成绩
    @GetMapping("/list/grade")
    public Result getGradeByStudentId(@RequestParam("studentId") Integer studentId){
        log.info("执行了根据学生学号查询学生成绩功能");
        List<ScoreVO> ScoreVO=studentService.getGradeByStudentId(studentId);
        return Result.success(ScoreVO);
    }
    //根据学生姓名查询学生成绩（模糊查询）
    @GetMapping("/list/grade/name")
    public Result getGradeByStudentName(@RequestParam("studentName") String studentName){
        log.info("执行了根据学生姓名查询学生成绩功能");
        List<ScoreDTO> ScoreDTO=studentService.getGradeByStudentName(studentName);
        return Result.success(ScoreDTO);
    }
    //实现单个学生成绩柱状图
    @GetMapping("/list/grade/chart")
    public Result getGradeChart(@RequestParam("studentId") Integer studentId){
        log.info("执行了学生成绩柱状图功能");
        List<ChartVO> chartVO=studentService.getGradeChart(studentId);
        return Result.success(chartVO);
    }
    //学生信息的删除功能
    @DeleteMapping("/delete")
    public Result deleteStudent(@RequestParam("studentId") Integer studentId){
        log.info("执行了学生信息删除功能");
        studentService.deleteStudent(studentId);
        return Result.success();
    }
    //查询学生信息
    @GetMapping("/getStudent")
    public Result getStudent(@RequestParam("studentId") Integer studentId){
        log.info("执行了学生信息查询功能");
        Student student=studentService.getStudent(studentId);
        return Result.success(student);
    }
    //修改学生信息
    @PutMapping("/updateStudent")
    public Result updateStudent(@RequestBody Student student){
        log.info("执行了学生信息修改功能");
        studentService.updateStudent(student);
        return Result.success();
    }
}
