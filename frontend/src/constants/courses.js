export const COURSE_OPTIONS = [
  { courseId: 1, courseName: '高等数学' },
  { courseId: 2, courseName: '大学英语' },
  { courseId: 3, courseName: '计算机基础' },
  { courseId: 4, courseName: '数据结构' }
]

export const GENDER_OPTIONS = [
  { label: '男', value: 0 },
  { label: '女', value: 1 }
]

export function getGenderLabel(gender) {
  return gender === 0 ? '男' : gender === 1 ? '女' : '-'
}

export function getCourseName(courseId) {
  const course = COURSE_OPTIONS.find((item) => item.courseId === Number(courseId))
  return course ? course.courseName : `课程 ${courseId}`
}
