package ru.evgeniyvasilev.helloworld.Entity;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="title")
    private String title;

    @Column(name="likes")
    private int likes = 0;

    public int like() {
        return ++likes;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int unlike() throws Exception {
        if (likes <= 0) {
            throw new Exception("Cannot unlike the unliked post");
        }
        return --likes;
    }

    public int getLikes() {
        return likes;
    }

    public String getTitle() {
        return title;
    }
}
