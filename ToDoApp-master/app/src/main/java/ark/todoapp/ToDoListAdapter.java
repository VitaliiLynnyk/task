package ark.todoapp;

/**
 * Created by linni on 10/14/2017.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.ArrayList;

import ark.todoapp.sql.DBHelper;

class ToDoListAdapter extends ArrayAdapter<ToDoList> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ToDoList> toDoListList;
    private DBHelper dbHelper;
    ToDoListAdapter(Context context, int resource, ArrayList<ToDoList> ToDoList, DBHelper dbHelper) {
        super(context, resource, ToDoList);
        this.toDoListList = ToDoList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.dbHelper = dbHelper;
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if (view == null){
            view = inflater.inflate(R.layout.item, parent, false);
        }

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView color = (TextView) view.findViewById(R.id.color);

        CheckBox  checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        Button btnDel = (Button) view.findViewById(R.id.btnDel);

        name.setText(toDoListList.get(position).getName());
        color.setText(toDoListList.get(position).getColor());

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteContact(toDoListList.get(position).getId());
                toDoListList.remove(position);
                notifyDataSetChanged();
            }
        });

        dbHelper.close();
        return view;
    }
}