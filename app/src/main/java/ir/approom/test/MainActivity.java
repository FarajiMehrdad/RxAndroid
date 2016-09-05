package ir.approom.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import ir.approom.test.adapter.ToDoAdapter;
import ir.approom.test.model.ToDo;
import ir.approom.test.model.ToDoList;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText editText;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private Spinner spinner;


    private ToDoList toDoList;
    private ToDoListFilter listFilter;
    private ToDoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        spinner = (Spinner) findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.addButton);
        editText = (EditText) findViewById(R.id.editText);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        toDoList = new ToDoList();
        listFilter = new ToDoListFilter(toDoList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        // can impplement ToDoCompleteChangeListener in this activty then pass this activity to adapter
        // for listenr and context
        // So what is the best case?
         adapter = new ToDoAdapter(getApplicationContext(), new ToDoCompleteChangeListener() {
            @Override
            public void OnToDoCompletedChanged(ToDo toDo) {
                toDoList.toggle(toDo);
                adapter.onToDoListChanged(listFilter.getFilterData());
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        toDoList.setListener(adapter);


        spinner.setAdapter(new ArrayAdapter<>(
                this,android.R.layout.simple_list_item_1,new String[]{"All" , "Completes" , "InCompletes"}));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    listFilter.setFilterMode(position);
                Log.d("MainActivituy" , "the number of list size is " + listFilter.getFilterData().size());
                Log.d("MainActivituy" , "the filter mode is " + listFilter.filterMode);
                adapter.onToDoListChanged(listFilter.getFilterData());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                listFilter.setFilterMode(ToDoListFilter.ALL);
                Log.d("MainActivituy" , "the number of list size is " + listFilter.getFilterData().size());
                adapter.onToDoListChanged(listFilter.getFilterData());
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = String.valueOf(editText.getText());
                ToDo toDo = new ToDo(title, false);
                toDoList.add(toDo);

            }
        });


    }
}
