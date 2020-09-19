package ru.evgeniyvasilev.helloworld.RestControllers;

import org.springframework.web.bind.annotation.*;
import ru.evgeniyvasilev.helloworld.Entity.Post;
import ru.evgeniyvasilev.helloworld.RestServiceApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@RestController
public class PostController {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("JavaHelps");

    @RequestMapping(value="/post/{id}", method=RequestMethod.GET)
    public Post getPost(@PathVariable("id") Integer id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            Post post = (Post) entityManager
                    .createQuery("SELECT p FROM Post p WHERE id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
            return post;
        } finally {
            entityManager.close();
        }
    }

    @RequestMapping(value="/post/{id}/like", method=RequestMethod.POST)
    public Post likePost(@PathVariable("id") Integer id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            Post post = getPost(id);

            post.like();
            entityManager.persist(post);

            return getPost(id);
        } finally {
            entityManager.close();
        }
    }
}
