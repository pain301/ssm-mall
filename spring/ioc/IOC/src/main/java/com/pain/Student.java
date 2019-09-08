package com.pain;

import java.util.*;

/**
 * Created by Administrator on 2017/8/26.
 */
public class Student {
    private List<String> courses = new ArrayList<String>();
    private List<String> books = new ArrayList<String>();
    private Set<String> favorites = new HashSet<String>();

    @Override
    public String toString() {
        return "Student{" +
                "courses=" + courses +
                ", books=" + books +
                ", favorites=" + favorites +
                ", scores=" + scores +
                ", emails=" + emails +
                '}';
    }

    private Map<String, Double> scores = new HashMap<String, Double>();

    public Properties getEmails() {
        return emails;
    }

    public void setEmails(Properties emails) {
        this.emails = emails;
    }

    private Properties emails = new Properties();

    public Map<String, Double> getScores() {
        return scores;
    }

    public void setScores(Map<String, Double> scores) {
        this.scores = scores;
    }

    public List<String> getBooks() {
        return books;
    }

    public void setBooks(List<String> books) {
        this.books = books;
    }

    public Set<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<String> favorites) {
        this.favorites = favorites;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

}
