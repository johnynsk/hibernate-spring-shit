package ru.evgeniyvasilev.helloworld;

import ru.evgeniyvasilev.helloworld.Entity.Post;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {

    private static EntityManager manager;
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("JavaHelps");

    /**
     * Create a new Student.
     *
     * @param title
     */
    public static Post createPost(String title) {
        // Create an EntityManager
        EntityTransaction transaction = null;
        Post post;
        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Create a new Student object
            post = new Post();
            post.setTitle(title);

            // Save the student object
            manager.persist(post);

            // Commit the transaction
            transaction.commit();
            return post;
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
            throw ex;
        } finally {
            // Close the EntityManager
        }
    }

    public static Post likePost(Post post) {
        post.like();
        manager.persist(post);

        return post;
    }

    /**
     * Read all the Students.
     *
     * @return a List of Students
     */
    public static List readAll() {

        List posts = null;

        // Create an EntityManager
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Get a List of Students
            posts = manager.createQuery("SELECT s FROM Post s",
                    Post.class).getResultList();

            // Commit the transaction
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
        } finally {
            // Close the EntityManager
        }
        return posts;
    }

    public static void main(String[] args) {
        try {
            manager = ENTITY_MANAGER_FACTORY.createEntityManager();
            Post post = createPost("some post title");
            post = createPost("some post title");
            post = createPost("some post title");
            likePost(post);
            likePost(post);
            likePost(post);
            List<Post> posts = readAll();
            for (Post currentPost: posts) {
                System.out.println(String.format("%s: %s", currentPost.getTitle(), currentPost.getLikes()));
            }

        } catch (Exception ignore) {
            System.out.println(ignore.toString());
        } finally {
            manager.close();
        }
    }
}
