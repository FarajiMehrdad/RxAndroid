package ir.approom.test.model;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.subjects.ReplaySubject;

/**
 * Created by Mehrdad on 9/4/16.
 */
public class ToDoList implements Action1<ToDo>{

    private List<ToDo> toDoList;
    private ReplaySubject<ToDoList> notifier = ReplaySubject.create();


    public ToDoList() {

        this.toDoList = new ArrayList<>();
        fillList();
    }


    public void add(ToDo toDo) {

        this.toDoList.add(toDo);
        notifier.onNext(this);

    }

    public void remove(ToDo toDo) {
        this.toDoList.remove(toDo);

    }

    private void toggle(ToDo toDo) {
        this.toDoList.get(toDoList.indexOf(toDo))
                .setCompleted(!toDo.isCompleted());
        notifier.onNext(this);

    }


    public Observable<ToDoList> asObservable(){
        return notifier;
    }


    public List<ToDo> getAllToDoList() {
        return toDoList;
    }

    public List<ToDo> getIncompleteTask(){

        List<ToDo> inCompleteList = new ArrayList<>();
        for (ToDo todo : toDoList) {
            if (!todo.isCompleted()){
                inCompleteList.add(todo);
            }
        }
        return inCompleteList;
    }

    public List<ToDo> getcompleteTask(){

        List<ToDo> completeList = new ArrayList<>();
        for (ToDo todo : toDoList) {
            if (todo.isCompleted()){
                completeList.add(todo);
            }
        }
        return completeList;
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
        notifier.onNext(this);

    }


    @Override
    public void call(ToDo toDo) {
        toggle(toDo);
    }
}



