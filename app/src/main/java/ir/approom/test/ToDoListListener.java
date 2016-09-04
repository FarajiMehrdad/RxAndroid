package ir.approom.test;

import ir.approom.test.model.ToDoList;

/**
 * Created by Mehrdad on 9/4/16.
 */
public interface ToDoListListener {

    void onToDoListChanged(ToDoList toDoList);
}
