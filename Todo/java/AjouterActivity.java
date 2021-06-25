package ma.tdi.tinghir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ma.tdi.tinghir.dao.Data;
import ma.tdi.tinghir.models.Todo;

public class AjouterActivity extends AppCompatActivity {

    Button btnAjouter;
    EditText etId, etTitre, etDescription, etExpiration;
    Spinner spStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);

        etId = (EditText)findViewById(R.id.etId);
        etTitre = (EditText)findViewById(R.id.etTitre);
        etDescription = (EditText)findViewById(R.id.etDescription);
        etExpiration = (EditText)findViewById(R.id.etDateExpiration);

        spStatus = (Spinner)findViewById(R.id.spStatus);

        btnAjouter = (Button)findViewById(R.id.btnAjouter);
        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ok = true;
                long id = -1;
                try {
                    id = Long.parseLong(etId.getText().toString().trim());
                } catch (Exception ex) {
                    ok = false;
                    etId.setText("");
                }
                String description = etDescription.getText().toString().trim();

                String titre = etTitre.getText().toString().trim();
                if(titre.equals("")) {
                    ok = false;
                    etTitre.setText("");
                }

                Date dtExpiration = null;
                SimpleDateFormat formtter = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    dtExpiration = formtter.parse(etExpiration.getText().toString().trim());
                } catch (ParseException ex) {
                    ok = false;
                    etExpiration.setText("");
                }

                String status = spStatus.getSelectedItem().toString();

                if(ok) {
                    try {
                        Todo todo = new Todo(id, titre, description, dtExpiration, status);
                        Data.setActivity(AjouterActivity.this);
                        Data.ajouter(todo);
                        Toast
                            .makeText(AjouterActivity.this, R.string.todo_bien_ajoute, Toast.LENGTH_LONG)
                                .show();
                        Intent intent = new Intent(
                                AjouterActivity.this,
                                MainActivity.class
                        );
                        startActivity(intent);
                    } catch (IllegalArgumentException ex) {
                        Toast
                            .makeText(AjouterActivity.this, ex.getMessage(), Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    Toast
                    .makeText(AjouterActivity.this, R.string.toast_ok_false, Toast.LENGTH_LONG)
                    .show();
                }

            }
        });
    }
}