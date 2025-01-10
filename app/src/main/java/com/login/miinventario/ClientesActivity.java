package com.login.miinventario;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientesActivity extends AppCompatActivity {

    private ListView lvClientes;
    private Button btnAgregarCliente, btnRegresarMenuClientes;
    private DatabaseHelper dbHelper;
    private ArrayList<HashMap<String, String>> clientesList;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        lvClientes = findViewById(R.id.lvClientes);
        btnAgregarCliente = findViewById(R.id.btnAgregarCliente);
        btnRegresarMenuClientes = findViewById(R.id.btnRegresarMenuClientes);

        dbHelper = new DatabaseHelper(this);
        clientesList = new ArrayList<>();

        cargarClientes();

        // Agregar cliente nuevo
        btnAgregarCliente.setOnClickListener(v -> {
            Intent intent = new Intent(ClientesActivity.this, RegistroClientesActivity.class);
            startActivity(intent);
        });


        // Regresar al menú principal
        btnRegresarMenuClientes.setOnClickListener(v -> {
            Intent intent = new Intent(ClientesActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Manejar clics en los elementos de la lista
        lvClientes.setOnItemClickListener((parent, view, position, id) -> {
            HashMap<String, String> cliente = clientesList.get(position);
            mostrarOpcionesCliente(Integer.parseInt(cliente.get("id")));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarClientes();
    }

    private void cargarClientes() {
        clientesList.clear();
        Cursor cursor = dbHelper.obtenerClientes();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                String telefono = cursor.getString(cursor.getColumnIndex("telefono"));

                HashMap<String, String> cliente = new HashMap<>();
                cliente.put("id", String.valueOf(id));
                cliente.put("nombre", nombre);
                cliente.put("telefono", telefono);

                clientesList.add(cliente);
            } while (cursor.moveToNext());
            cursor.close();
        }

        adapter = new SimpleAdapter(
                this,
                clientesList,
                android.R.layout.simple_list_item_2,
                new String[]{"nombre", "telefono"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );

        lvClientes.setAdapter(adapter);
    }

    private void mostrarOpcionesCliente(int clienteId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona una opción");
        builder.setItems(new CharSequence[]{"Editar", "Eliminar"}, (dialog, which) -> {
            if (which == 0) {
                // Editar cliente
                Intent intent = new Intent(ClientesActivity.this, RegistroClientesActivity.class);
                intent.putExtra("CLIENTE_ID", clienteId);
                startActivity(intent);
            } else if (which == 1) {
                // Eliminar cliente
                eliminarCliente(clienteId);
            }
        });
        builder.show();
    }

    private void eliminarCliente(int clienteId) {
        int resultado = dbHelper.eliminarCliente(clienteId);
        if (resultado > 0) {
            Toast.makeText(this, "Cliente eliminado.", Toast.LENGTH_SHORT).show();
            cargarClientes();
        } else {
            Toast.makeText(this, "Error al eliminar el cliente.", Toast.LENGTH_SHORT).show();
        }
    }
}
