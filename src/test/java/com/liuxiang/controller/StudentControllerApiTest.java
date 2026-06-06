package com.liuxiang.controller;

import com.liuxiang.support.BaseApiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StudentControllerApiTest extends BaseApiTest {

    @Test
    @DisplayName("POST /add/info - 添加学生成功")
    void addStudent_success() throws Exception {
        String body = "{"
                + "\"studentName\":\"李四\","
                + "\"gender\":1,"
                + "\"birthDate\":\"2004-06-15\","
                + "\"classId\":\"102\","
                + "\"phone\":\"13900139000\""
                + "}";

        mockMvc.perform(post("/add/info")
                        .contentType(JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));
    }

    @Test
    @DisplayName("GET /getStudent - 按学号查询学生成功")
    void getStudent_success() throws Exception {
        mockMvc.perform(get("/getStudent").param("studentId", String.valueOf(SEED_STUDENT_ID)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.data.studentId").value(SEED_STUDENT_ID))
                .andExpect(jsonPath("$.data.studentName").value("张三"))
                .andExpect(jsonPath("$.data.gender").value(0))
                .andExpect(jsonPath("$.data.phone").value("13800138000"));
    }

    @Test
    @DisplayName("GET /getStudent - 查询不存在学号返回空数据")
    void getStudent_notFound() throws Exception {
        mockMvc.perform(get("/getStudent").param("studentId", "9999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.data").value(nullValue()));
    }

    @Test
    @DisplayName("PUT /updateStudent - 修改学生信息成功")
    void updateStudent_success() throws Exception {
        String body = String.format(
                "{"
                        + "\"studentId\":%d,"
                        + "\"studentName\":\"张三三\","
                        + "\"gender\":0,"
                        + "\"birthDate\":\"2004-01-01\","
                        + "\"classId\":\"101\","
                        + "\"phone\":\"13800138001\""
                        + "}",
                SEED_STUDENT_ID);

        mockMvc.perform(put("/updateStudent")
                        .contentType(JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1));

        mockMvc.perform(get("/getStudent").param("studentId", String.valueOf(SEED_STUDENT_ID)))
                .andExpect(jsonPath("$.data.studentName").value("张三三"))
                .andExpect(jsonPath("$.data.phone").value("13800138001"));
    }

    @Test
    @DisplayName("DELETE /delete - 删除学生成功")
    void deleteStudent_success() throws Exception {
        mockMvc.perform(delete("/delete").param("studentId", String.valueOf(SEED_STUDENT_ID)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1));

        mockMvc.perform(get("/getStudent").param("studentId", String.valueOf(SEED_STUDENT_ID)))
                .andExpect(jsonPath("$.data").value(nullValue()));
    }

    @Test
    @DisplayName("POST /add/grade - 录入成绩成功")
    void addGrade_success() throws Exception {
        String body = String.format(
                "{"
                        + "\"studentId\":%d,"
                        + "\"courseId\":3,"
                        + "\"score\":85.50"
                        + "}",
                SEED_STUDENT_ID);

        mockMvc.perform(post("/add/grade")
                        .contentType(JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1));

        mockMvc.perform(get("/list/grade").param("studentId", String.valueOf(SEED_STUDENT_ID)))
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.data[*].courseName", hasItem("计算机基础")));
    }

    @Test
    @DisplayName("GET /list/grade - 按学号查询成绩")
    void getGradeByStudentId_success() throws Exception {
        mockMvc.perform(get("/list/grade").param("studentId", String.valueOf(SEED_STUDENT_ID)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].courseName").value("高等数学"))
                .andExpect(jsonPath("$.data[0].score").value(88.50))
                .andExpect(jsonPath("$.data[1].courseName").value("大学英语"));
    }

    @Test
    @DisplayName("GET /list/grade/name - 按姓名模糊查询成绩")
    void getGradeByStudentName_fuzzy() throws Exception {
        mockMvc.perform(get("/list/grade/name").param("studentName", "张"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.data", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$.data[0].studentId").value(SEED_STUDENT_ID))
                .andExpect(jsonPath("$.data[0].courseId").value(1));
    }

    @Test
    @DisplayName("GET /list/grade/chart - 查询图表数据")
    void getGradeChart_success() throws Exception {
        mockMvc.perform(get("/list/grade/chart").param("studentId", String.valueOf(SEED_STUDENT_ID)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].studentName").value("张三"))
                .andExpect(jsonPath("$.data[0].courseName").value("高等数学"))
                .andExpect(jsonPath("$.data[0].score").value(88.50));
    }

    @Test
    @DisplayName("学生完整流程 - 添加、查询、修改、录成绩、查图表")
    void studentFullFlow() throws Exception {
        String addBody = "{"
                + "\"studentName\":\"流程测试\","
                + "\"gender\":0,"
                + "\"birthDate\":\"2003-03-03\","
                + "\"classId\":\"201\","
                + "\"phone\":\"13700137000\""
                + "}";

        mockMvc.perform(post("/add/info").contentType(JSON).content(addBody))
                .andExpect(jsonPath("$.code").value(1));

        Integer studentId = jdbcTemplate.queryForObject(
                "SELECT student_id FROM students WHERE phone = ?",
                Integer.class,
                "13700137000");

        mockMvc.perform(get("/getStudent").param("studentId", String.valueOf(studentId)))
                .andExpect(jsonPath("$.data.studentName").value("流程测试"));

        String updateBody = String.format(
                "{"
                        + "\"studentId\":%d,"
                        + "\"studentName\":\"流程测试-已修改\","
                        + "\"gender\":0,"
                        + "\"birthDate\":\"2003-03-03\","
                        + "\"classId\":\"201\","
                        + "\"phone\":\"13700137001\""
                        + "}",
                studentId);

        mockMvc.perform(put("/updateStudent").contentType(JSON).content(updateBody))
                .andExpect(jsonPath("$.code").value(1));

        String gradeBody = String.format(
                "{"
                        + "\"studentId\":%d,"
                        + "\"courseId\":1,"
                        + "\"score\":95.00"
                        + "}",
                studentId);

        mockMvc.perform(post("/add/grade").contentType(JSON).content(gradeBody))
                .andExpect(jsonPath("$.code").value(1));

        mockMvc.perform(get("/list/grade/chart").param("studentId", String.valueOf(studentId)))
                .andExpect(jsonPath("$.data[0].studentName").value("流程测试-已修改"))
                .andExpect(jsonPath("$.data[0].score").value(95.00));
    }
}
