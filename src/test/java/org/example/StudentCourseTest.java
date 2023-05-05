package org.example;

import lombok.Cleanup;
import org.example.entity.Course;
import org.example.entity.Student;
import org.example.entity.Trainer;
import org.example.entity.TrainerCourse;
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
                .name("Алексей")
                .build();

        course.addStudent(student);

        session.save(course);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void setTrainerCourseTest() {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var trainer = Trainer.builder()
                .name("Андрей")
                .build();
        session.save(trainer);
        var course1 = Course.builder()
                .name("Java EE")
                .build();
        session.save(course1);
        var trainerCourse1 = new TrainerCourse();
        trainerCourse1.setTrainer(trainer);
        trainerCourse1.setCourse(course1);
        session.save(trainerCourse1);
        var course2 = Course.builder()
                .name("Go")
                .build();
        session.save(course2);
        var trainerCourse2 = new TrainerCourse();
        trainerCourse2.setTrainer(trainer);
        trainerCourse2.setCourse(course2);
        session.save(trainerCourse2);
        var course3 = Course.builder()
                .name("Python")
                .build();
        session.save(course3);
        var trainerCourse3 = new TrainerCourse();
        trainerCourse3.setTrainer(trainer);
        trainerCourse3.setCourse(course3);
        session.save(trainerCourse3);

        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void updateCourseTest() {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        var result = session.createQuery("""
                        update Course set
                        name = :name1
                        where name = :name2
                        """, Course.class)
                .setParameter("name1", "Go")
                .setParameter("name2", "Java EE")
                .list();

        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void deleteCourseTest() {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        var result = session.createQuery("""
                        delete Course
                        where name = :name
                        """, Course.class)
                .setParameter("name", "Java EE")
                .list();

        session.getTransaction().commit();
        session.close();
    }
}
