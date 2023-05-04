package org.example;

import lombok.Cleanup;
import org.example.entity.Course;
import org.example.entity.Student;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

public class StudentCourseTest {

    @Test
    public void addStudentTest() {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var course = Course.builder()
                .name("Java EE")
                .build();
        var student = Student.builder()
                .name("Андрей")
                .build();

        course.addStudent(student);

        session.save(course);
        session.getTransaction().commit();
        session.close();;
    }
}
