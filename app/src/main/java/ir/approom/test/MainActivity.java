package ir.approom.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ir.approom.test.adapter.ToDoAdapter;
import ir.approom.test.model.ToDo;
import ir.approom.test.model.ToDoList;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText editText;
    private RecyclerView recyclerView;
    private ToDoList toDoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.addButton);
        editText = (EditText) findViewById(R.id.editText);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        toDoList = new ToDoList();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        ToDoAdapter adapter = new ToDoAdapter(toDoList, getApplicationContext());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        toDoList.setListener(adapter);
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
