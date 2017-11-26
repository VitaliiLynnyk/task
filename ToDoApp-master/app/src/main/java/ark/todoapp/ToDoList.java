package ark.todoapp;

import android.support.annotation.NonNull;

/**
 * Created by linni on 10/9/2017.
 */

public class ToDoList{
    private int id;
    private String name;
    private String color;
    private String category;

    public ToDoList(int id, String name, String color, String category) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}