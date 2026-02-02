package main;

import javax.persistence.*;

import model.Point;

import java.util.*;

public class Main {
	public static void main(String[] args) {

		// Open a database connection
		// (create a new database if it doesn't exist yet):
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("objects/points.odb");
		EntityManager em = emf.createEntityManager();

		// Create 10 Point objects in the database:
		em.getTransaction().begin();
		for (int i = 0; i < 10; i++) {
			Point p = new Point(i, i);
			em.persist(p);
		}
		em.getTransaction().commit();

		// Read retrieve all the Point objects from the database:
		TypedQuery<Point> query = em.createQuery("SELECT p FROM Point p", Point.class);
		List<Point> results = query.getResultList();
		for (Point p : results) {
			System.out.println(p);
		}

		// Read find the number of Point objects in the database:
		Query q1 = em.createQuery("SELECT COUNT(p) FROM Point p");
		System.out.println("Total Points: " + q1.getSingleResult());

		// Read find the average X value:
		Query q2 = em.createQuery("SELECT AVG(p.x) FROM Point p");
		System.out.println("Average X: " + q2.getSingleResult());

		// Update add 100 to x for points where x < 5
		em.getTransaction().begin();
		Query update = em.createQuery("UPDATE Point p SET p.x = p.x + 100 WHERE p.x < :threshold");
		update.setParameter("threshold", 5);
		int updatedCount = update.executeUpdate();
		em.getTransaction().commit();
		em.clear();
		System.out.println("Updated points: " + updatedCount);

		// Delete remove points where x >= 103
		em.getTransaction().begin();
		Query delete = em.createQuery("DELETE FROM Point p WHERE p.x >= :minX");
		delete.setParameter("minX", 103);
		int deletedCount = delete.executeUpdate();
		em.getTransaction().commit();
		em.clear();
		System.out.println("Deleted points: " + deletedCount);

		// Read retrieve all the Point objects from the database:
		TypedQuery<Point> queryAfter = em.createQuery("SELECT p FROM Point p", Point.class);
		List<Point> resultsAfter = queryAfter.getResultList();
		for (Point p : resultsAfter) {
			System.out.println(p);
		}

		// Close the database connection:
		em.close();
		emf.close();
	}
}