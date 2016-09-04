package ir.approom.test.model;

/**
 * Created by Mehrdad on 9/4/16.
 */
public class ToDo {

    String title;
    boolean isCompleted;

    // model for any item be addedd have title String and boolean for is complete or not
    public ToDo(String title, boolean isCompleted) {
        this.title = title;
        this.isCompleted = isCompleted;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
