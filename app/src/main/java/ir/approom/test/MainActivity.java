package ir.approom.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxAdapterView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.approom.test.adapter.ToDoAdapter;
import ir.approom.test.model.ToDo;
import ir.approom.test.model.ToDoList;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.subscriptions.CompositeSubscription;

import static ir.approom.test.model.FilterMode.ALL;
import static ir.approom.test.model.FilterMode.COMPLETE;
import static ir.approom.test.model.FilterMode.IN_COMPLETE;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.addButton)
     Button button;

    @BindView(R.id.editText)
     EditText editText;

    @BindView(R.id.recyclerView)
     RecyclerView recyclerView;

    @BindView(R.id.spinner)
     Spinner spinner;


    private ToDoList toDoList;
    private ToDoAdapter adapter;

    private CompositeSubscription subscription = new CompositeSubscription();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);




        toDoList = new ToDoList();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        // can impplement ToDoCompleteChangeListener in this activty then pass this activity to adapter
        // for listenr and context
        // So what is the best case?
        adapter = new ToDoAdapter(getApplicationContext(),toDoList);


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);


        subscription.add(
                RxView.clicks(button).map(new Func1<Void, String>() {
                    @Override
                    public String call(Void aVoid) {
                        return editText.getText().toString();
                    }
                }).filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String title) {
                        Log.d("MainActivity", "the title is " + title);
                        return !TextUtils.isEmpty(title);
                    }
                }).subscribe(new Action1<String>() {
                    @Override
                    public void call(String title) {

                        ToDo toDo = new ToDo(title, false);
                        toDoList.add(toDo);
                    }
                }));


        spinner.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, new String[]{"All", "Completes", "InCompletes"}));


        subscription.add(
                Observable.combineLatest(RxAdapterView.itemSelections(spinner).skip(1), toDoList.asObservable(),
                        new Func2<Integer, ToDoList, List<ToDo>>() {
                            @Override
                            public List<ToDo> call(Integer integer, ToDoList toDoList) {

                                switch (integer) {
                                    case IN_COMPLETE:
                                        return toDoList.getIncompleteTask();
                                    case COMPLETE:
                                        return toDoList.getcompleteTask();
                                    default:
                                        return toDoList.getAllToDoList();
                                }

                            }
                        }).subscribe(adapter));


        spinner.setSelection(ALL);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }
}
