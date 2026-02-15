package main;

import javax.persistence.*;

import model.Point;

import java.util.*;

public class Main {
    public static void main(String[] args) {
    	
        // Open a database connection
        // (create a new database if it doesn't exist yet):
        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory(
                "objects/points.odb");
        EntityManager em = emf.createEntityManager();

        // CREATE example:
        // Store 10 Point objects in the database:
        em.getTransaction().begin();
        for (int i = 0; i < 10; i++) {
            Point p = new Point(i, i);
            em.persist(p);
        }
        em.getTransaction().commit();

        // READ examples:
        // Find the number of Point objects in the database:
        Query q1 = em.createQuery("SELECT COUNT(p) FROM Point p");
        System.out.println("Total Points: " + q1.getSingleResult());

        // Find the average X value:
        Query q2 = em.createQuery("SELECT AVG(p.x) FROM Point p");
        System.out.println("Average X: " + q2.getSingleResult());

        // Retrieve all the Point objects from the database:
        TypedQuery<Point> query =
            em.createQuery("SELECT p FROM Point p", Point.class);
        List<Point> results = query.getResultList();
        for (Point p : results) {
            System.out.println(p);
        }

        // SELECT p FROM Point p WHERE p.x > :minX AND p.y < :maxY
        System.out.println("\nPoints with x > 3 and y < 8:");
        TypedQuery<Point> q3 = em.createQuery(
            "SELECT p FROM Point p WHERE p.x > :minX AND p.y < :maxY",
            Point.class);
        q3.setParameter("minX", 3);
        q3.setParameter("maxY", 8);
        List<Point> filtered = q3.getResultList();
        for (Point p : filtered) {
            System.out.println(p);
        }

        // UPDATE examples
        // Use a JPQL UPDATE to avoid calling entity setters (which may not exist)
        System.out.println("\nUpdating Points with x==5 to (50,50)");
        em.getTransaction().begin();
        Query updateQ = em.createQuery(
            "UPDATE Point p SET p.x = :newX, p.y = :newY WHERE p.x = :val");
        updateQ.setParameter("newX", 50);
        updateQ.setParameter("newY", 50);
        updateQ.setParameter("val", 5);
        int updatedCount = updateQ.executeUpdate();
        em.getTransaction().commit();
        System.out.println("Updated " + updatedCount + " point(s).");

        // DELETE examples:
        // Delete all Points with x < 2
        System.out.println("\nDeleting Points with x < 2");
        em.getTransaction().begin();
        Query deleteQuery = em.createQuery("DELETE FROM Point p WHERE p.x < :limit");
        deleteQuery.setParameter("limit", 2);
        int deletedCount = deleteQuery.executeUpdate();
        em.getTransaction().commit();
        System.out.println("Deleted " + deletedCount + " point(s).");
        
        // Retrieve and print all remaining Point objects:
        List<Point> remaining = em.createQuery("SELECT p FROM Point p", Point.class)
                                   .getResultList();
        System.out.println("\nRemaining Points:");
        for (Point p : remaining) {
            System.out.println(p);
        }

        // Close the database connection:
        em.close();
        emf.close();
    }
}