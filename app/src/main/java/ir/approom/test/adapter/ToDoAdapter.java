package ir.approom.test.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import ir.approom.test.R;
import ir.approom.test.ToDoCompleteChangeListener;
import ir.approom.test.ToDoListListener;
import ir.approom.test.model.ToDo;
import ir.approom.test.model.ToDoList;

/**
 * Created by Mehrdad on 9/4/16.
 */
public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> implements ToDoListListener{


    private ToDoList mToDoList;
    private Context mContext;


    // user can interact with  checkbox and change the completeion of toDoItem
    // So we should tell to dataModel that item change.
    private ToDoCompleteChangeListener toDoCompleteChangeListener;

    // how inject ToDolist to adapter?!
    // how inject Context to adapter? or only  inject LayoutInflater is better than inject context?!
    public ToDoAdapter( Context context , ToDoCompleteChangeListener changeListener){
        this.mToDoList = new ToDoList();
        this.mContext = context;
        this.toDoCompleteChangeListener = changeListener;
    }

    @Override
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d("Adapter" , "onCreateViewHolder called");
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.to_do_item,parent, false);
        ToDoViewHolder viewHolder = new ToDoViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ToDoViewHolder holder, final int position) {

        final ToDo toDo = mToDoList.get(position);
        holder.checkBox.setText(toDo.getTitle());
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(toDo.isCompleted());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                Log.d("Adapter" , "task changed and is " + b);
                if (toDoCompleteChangeListener != null) {
                    toDoCompleteChangeListener.OnToDoCompletedChanged(toDo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mToDoList.size();
    }

    @Override
    public void onToDoListChanged(ToDoList updateList) {
        this.mToDoList = updateList;
        Log.d("Adapter" , "onToDoListChanged called");
        notifyDataSetChanged();
    }

    static class ToDoViewHolder extends RecyclerView.ViewHolder{

        CheckBox checkBox;

        public ToDoViewHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkTextView);

        }
    }

}
