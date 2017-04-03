package com.gmail.at.sichyuriyy.onlinestore.entity;

import java.util.Objects;

/**
 * Created by Yuriy on 3/27/2017.
 */
public class Category {

    private Long id;
    private String title;

    public Category() {
    }

    public Category(Long id) {
        this.id = id;
    }

    public Category(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(title, category.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
