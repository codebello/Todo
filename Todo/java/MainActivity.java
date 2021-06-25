package ma.tdi.tinghir;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Date;

import ma.tdi.tinghir.adapters.TodosAdapter;
import ma.tdi.tinghir.dao.Data;
import ma.tdi.tinghir.models.Todo;

public class MainActivity extends AppCompatActivity {

    Button btnAjouter;
    ListView lvTodos;
    TextView tvPasTodos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAjouter = (Button)findViewById(R.id.btnAjouter);
        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainActivity.this,
                        AjouterActivity.class
                );
                startActivity(intent);
            }
        });

        lvTodos = (ListView) findViewById(R.id.lvTodos);
        tvPasTodos = (TextView)findViewById(R.id.tvPasTodos);

        Data.setActivity(this);
        if(Data.existe(15) == null && Data.existe(16) == null && Data.existe(17) == null) {
            Data.ajouter(new Todo(15, "aa", "ghvghfv", new Date(), "Nouvelle"));
            Data.ajouter(new Todo(16, "aa", "ghvghfv", new Date(), "En cours"));
            Data.ajouter(new Todo(17, "aa", "ghvghfv", new Date(), "Terminée"));
        }

        if(Data.getTodos().size() > 0) {
            lvTodos.setVisibility(View.VISIBLE);
            tvPasTodos.setVisibility(View.INVISIBLE);
            TodosAdapter adapter = new TodosAdapter(
                    MainActivity.this,
                    R.layout.lv_todo_item,
                    Data.getTodos()
            );
            lvTodos.setAdapter(adapter);
        } else {
            showMessageEmpty();
        }

        lvTodos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builer = new AlertDialog.Builder(MainActivity.this);
                builer
                        .setMessage(R.string.msg_confirmation)
                        .setTitle(R.string.title_confirmation)
                        .setPositiveButton(R.string.confirmation_oui, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Data.supprimer(Data.getTodos().get(position).getId());
                                Toast
                                    .makeText(MainActivity.this, R.string.todo_bien_supprime, Toast.LENGTH_LONG)
                                        .show();
                                ((TodosAdapter)lvTodos.getAdapter()).notifyDataSetChanged();
                                if(Data.getTodos().size() == 0) {
                                    showMessageEmpty();
                                } else {
                                    lvTodos.setVisibility(View.VISIBLE);
                                    tvPasTodos.setVisibility(View.INVISIBLE);
                                }
                            }
                        })
                        .setNegativeButton(R.string.confirmation_non, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alert = builer.create();
                alert.show();
                return true;
            }
        });


    }
    private void showMessageEmpty() {
        lvTodos.setVisibility(View.INVISIBLE);
        tvPasTodos.setVisibility(View.VISIBLE);
        lvTodos.setAdapter(null);
    }
}