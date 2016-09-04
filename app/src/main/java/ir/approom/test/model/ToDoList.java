package ir.approom.test.model;

import java.util.ArrayList;
import java.util.List;

import ir.approom.test.ToDoListListener;

/**
 * Created by Mehrdad on 9/4/16.
 */
public class ToDoList {

    private List<ToDo> toDoList;
    private ToDoListListener toDoListListener;

    public ToDoList() {

        this.toDoList = new ArrayList<>();
        fillList();
    }

    public void setListener(ToDoListListener listener) {

        this.toDoListListener = listener;
    }

    public ToDoList(ArrayList<ToDo> toDos) {
        this.toDoList = toDos;
    }

    public void add(ToDo toDo) {

        this.toDoList.add(toDo);
        if (toDoListListener !=null){
            toDoListListener.onToDoListChanged(this);
        }

    }

    public ToDo get(int position) {
        return this.toDoList.get(position);
    }

    public int size() {
        return this.toDoList.size();
    }


    void fillList() {

        ToDo toDo = new ToDo("title1", false);
        ToDo toDo1 = new ToDo("title2", true);
        ToDo toDo2 = new ToDo("title3", true);
        ToDo toDo3 = new ToDo("title4", false);
        ToDo toDo4 = new ToDo("title5", true);
        ToDo toDo5 = new ToDo("title6", false);

        toDoList.add(toDo);
        toDoList.add(toDo1);
        toDoList.add(toDo2);
        toDoList.add(toDo3);
        toDoList.add(toDo4);
        toDoList.add(toDo5);

    }


}



