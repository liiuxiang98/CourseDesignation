import request from './request'

export const addStudent = (data) => request.post('/add/info', data)

export const getStudent = (studentId) =>
  request.get('/getStudent', { params: { studentId } })

export const updateStudent = (data) => request.put('/updateStudent', data)

export const deleteStudent = (studentId) =>
  request.delete('/delete', { params: { studentId } })

export const addGrade = (data) => request.post('/add/grade', data)

export const getGradeByStudentId = (studentId) =>
  request.get('/list/grade', { params: { studentId } })

export const getGradeByStudentName = (studentName) =>
  request.get('/list/grade/name', { params: { studentName } })

export const getGradeChart = (studentId) =>
  request.get('/list/grade/chart', { params: { studentId } })
