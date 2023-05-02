package org.example;

import org.example.entity.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class App 
{
    public static void main( String[] args ) {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                var course = session.get(Course.class, 1L);
                System.out.println(course.getStudents());

                session.getTransaction().commit();
                session.close();
            }
        }
    }
}
