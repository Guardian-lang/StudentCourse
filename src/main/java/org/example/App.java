package org.example;

import org.example.entity.Course;
import org.example.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class App {
    public static void main(String[] args) {
        findAllByCourseName("Java EE");
        deleteAllByMarks();
        deleteCourse("Java EE");
    }

    public static void findAllByCourseName(String course) {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                var result = session.createQuery("""
                        select s from Student s
                        left join s.course c
                        where c.name = :course
                        """, Student.class)
                        .setParameter("course", course)
                        .list();
                System.out.println(result);

                session.getTransaction().commit();
                session.close();
            }
        }
    }

    public static void deleteAllByMarks() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                var result1 = session.createQuery("""
                        select s from Student s
                        left join s.course c
                        where c.name = :course
                        """, Student.class)
                        .setParameter("course", "Java EE")
                        .list();
                for (int i = 0; i < result1.size(); i++) {
                    var buffer = session.createQuery("""
                        select avg(sp.mark) from StudentProfile sp
                        join Student s on sp.student = s
                        where s.id = :i
                        """, Integer.class)
                            .setParameter("i", i+1)
                            .list();
                    if (buffer.get(0) < 6) {
                        var result2 = session.createQuery("""
                                delete Student s
                                where s.id = :i
                                """, Student.class)
                                .setParameter("i", i+1);
                    }
                }

                session.getTransaction().commit();
                session.close();
            }
        }
    }

    public static void deleteCourse(String name) {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                var result = session.createQuery("""
                        delete Course c
                        where c.name = :name
                        """, Course.class)
                        .setParameter("name", name)
                        .list();
            }
        }
    }
}