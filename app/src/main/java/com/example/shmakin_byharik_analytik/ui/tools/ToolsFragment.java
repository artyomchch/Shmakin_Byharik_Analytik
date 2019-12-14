package com.example.shmakin_byharik_analytik.ui.tools;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.shmakin_byharik_analytik.R;
import com.example.shmakin_byharik_analytik.WorkActivity;

import static android.content.Context.MODE_PRIVATE;

public class ToolsFragment extends Fragment implements View.OnClickListener{

    TextView text;
    Button lookdb;


    private ToolsViewModel toolsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);

        text = (TextView) root.findViewById(R.id.textView);
        lookdb= root.findViewById(R.id.look);
        lookdb.setOnClickListener(this);
        final TextView textView = root.findViewById(R.id.text_tools);
        toolsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.look:
                saveLook();
                break;
            //case R.id.looper:
            //  goToActive(Looper1.class);
            //  break;
        }

    }


    public void saveLook(){
        SQLiteDatabase db = getActivity().getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users (name TEXT, age INTEGER)");
        db.execSQL("INSERT INTO users VALUES ('Nikita Karpov', 21);");
        db.execSQL("INSERT INTO users VALUES ('Kirill Bytenko', 21);");
        db.execSQL("INSERT INTO users VALUES ('Nikita Shmakov', 21);");

        Cursor query = db.rawQuery("SELECT * FROM users;", null);

        if(query.moveToFirst()){
            do{
                String name = query.getString(0);
                int age = query.getInt(1);
                //String nickname = query.getString(2);
                text.append("Name: " + name + " Age: " + age + "\n");
            }
            while(query.moveToNext());
        }
        query.close();
        db.close();
    }
}