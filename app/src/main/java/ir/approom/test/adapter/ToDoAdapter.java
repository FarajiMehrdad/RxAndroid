package ir.approom.test.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.jakewharton.rxbinding.widget.RxCompoundButton;

import java.util.Collections;
import java.util.List;

import ir.approom.test.R;
import ir.approom.test.model.ToDo;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Mehrdad on 9/4/16.
 */
public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> implements Action1<List<ToDo>> {


    private List<ToDo> mToDoList;
    private Context mContext;


    // user can interact with  checkbox and change the completeion of toDoItem
    // So we should tell to dataModel that item change.
    private Action1<ToDo> subscriber;

    // how inject ToDolist to adapter?!
    // how inject Context to adapter? or only  inject LayoutInflater is better than inject context?!
    public ToDoAdapter(Context context, Action1<ToDo> subscriber) {
        this.mToDoList = Collections.emptyList();
        this.mContext = context;
        this.subscriber = subscriber;
    }

    @Override
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d("Adapter", "onCreateViewHolder called");
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.to_do_item, parent, false);
        ToDoViewHolder viewHolder = new ToDoViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ToDoViewHolder holder, final int position) {

        final ToDo toDo = mToDoList.get(position);
        holder.checkBox.setText(toDo.getTitle());
        holder.checkBox.setChecked(toDo.isCompleted());

        holder.subscription = RxCompoundButton.checkedChanges(holder.checkBox).skip(1).
                map(new Func1<Boolean, ToDo>() {
                    @Override
                    public ToDo call(Boolean aBoolean) {
                        return toDo;
                    }
                }).subscribe(subscriber);

    }

    @Override
    public int getItemCount() {
        return this.mToDoList.size();
    }


    @Override
    public void call(List<ToDo> toDos) {
        this.mToDoList = toDos;
        Log.d("Adapter", "onToDoListChanged called");
        notifyDataSetChanged();
    }

    static class ToDoViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        Subscription subscription;

        public ToDoViewHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkTextView);

        }
    }


    @Override
    public void onViewDetachedFromWindow(ToDoViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.subscription.unsubscribe();
    }
}
