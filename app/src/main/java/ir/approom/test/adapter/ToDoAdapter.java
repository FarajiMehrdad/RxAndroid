package ir.approom.test.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import ir.approom.test.R;
import ir.approom.test.ToDoListListener;
import ir.approom.test.model.ToDoList;

/**
 * Created by Mehrdad on 9/4/16.
 */
public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> implements ToDoListListener{


    private ToDoList mToDoList;
    private Context mContext;

    public ToDoAdapter(ToDoList toDos , Context context){
        this.mToDoList = toDos;
        this.mContext = context;

    }

    @Override
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d("Adapter" , "onCreateViewHolder called");
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.to_do_item,parent, false);
        ToDoViewHolder viewHolder = new ToDoViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ToDoViewHolder holder, int position) {

        holder.checkBox.setChecked(mToDoList.get(position).isCompleted());
        holder.checkBox.setText(mToDoList.get(position).getTitle());
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
