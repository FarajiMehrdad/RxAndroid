package ir.approom.test;

import ir.approom.test.model.ToDo;
import ir.approom.test.model.ToDoList;

/**
 * Created by Mehrdad on 9/5/16.
 */
public class ToDoListFilter implements ToDoListListener{

    public static final int ALL = 0;
    public static final int COMPLETE = 1;
    public static final int IN_COMPLETE = 2;

    public int filterMode = ALL;
    private ToDoList toDoList = new ToDoList();

    public ToDoListFilter(ToDoList toDos){

        this.toDoList = toDos;
    }


    void setFilterMode(int mode){
        this.filterMode = mode;
    }

    ToDoList getFilterData(){
        switch (filterMode){

            case ALL:
                return toDoList;
            case IN_COMPLETE:
                ToDoList inCompleteList = new ToDoList(false);
                for (ToDo todo : toDoList.getToDoList()) {
                    if (!todo.isCompleted()){
                        inCompleteList.add(todo);
                    }
                }
                return inCompleteList;
            case COMPLETE:
                ToDoList completeList = new ToDoList(false);
                for (ToDo todo : toDoList.getToDoList()) {
                    if (todo.isCompleted()){
                        completeList.add(todo);
                    }
                }
                return completeList;
            default:
                return toDoList;

        }
    }


    @Override
    public void onToDoListChanged(ToDoList toDoList) {
        this.toDoList = toDoList;
    }
}
