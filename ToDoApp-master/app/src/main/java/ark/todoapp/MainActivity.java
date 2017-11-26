package ark.todoapp;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import ark.todoapp.sql.DBHelper;

public class MainActivity extends AppCompatActivity{
    ArrayList<ToDoList> toDoListArrayList = new ArrayList();
    ListView toDoListView;
    ToDoListAdapter adapter;
    FloatingActionButton btnFloat;
    EditText dialogEtName;
    EditText dialogEtColor;
    EditText dialogEtCategory;
    Button dialogBtnAdd;
    Button dialogBtnDel;
    Dialog dialog ;
    DBHelper dbHelper;
    int counter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnFloat = (FloatingActionButton) findViewById(R.id.btnFloat);

        toDoListView = (ListView) findViewById(R.id.to_do_list);
        dbHelper = new DBHelper(this);
        toDoListArrayList = dbHelper.getAllTODOLIST();
        adapter = new ToDoListAdapter(this, R.layout.item,toDoListArrayList,dbHelper);
        toDoListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        btnFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = makeDialog(MainActivity.this);
                dialog.setCancelable(false);
                dialog.show();

                dialogEtName = (EditText) dialog.findViewById(R.id.etName);
                dialogEtColor = (EditText) dialog.findViewById(R.id.etColor);
                dialogEtCategory = (EditText) dialog.findViewById(R.id.etCategory);
                dialogBtnAdd = (Button) dialog.findViewById(R.id.dialogBtnAdd);
                dialogBtnDel = (Button) dialog.findViewById(R.id.dialogBtnDel);

                dialogEtName.setText("");
                dialogEtColor.setText("");
                dialogEtCategory.setText("");
                dialogBtnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toDoListArrayList.add(new ToDoList(counter,dialogEtName.getText().toString(),dialogEtColor.getText().toString(),dialogEtCategory.getText().toString()));
                        dbHelper.insertContact(dialogEtName.getText().toString(),dialogEtColor.getText().toString(),dialogEtCategory.getText().toString());
                        adapter.notifyDataSetChanged();
                        counter++;

                        dialog.dismiss();
                    }
                });
                dialogBtnDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });

        dbHelper.close();
    }

    private Dialog makeDialog(Context context){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("ADD INFORMATION");

        return dialog;
    }
}
