package com.login.miinventario;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistroClientesActivity extends AppCompatActivity {

    private EditText etNombreCliente, etTelefonoCliente;
    private Button btnGuardarCliente;
    private DatabaseHelper dbHelper;
    private boolean isEditMode = false;
    private int clienteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_clientes);

        etNombreCliente = findViewById(R.id.etNombreCliente);
        etTelefonoCliente = findViewById(R.id.etTelefonoCliente);
        btnGuardarCliente = findViewById(R.id.btnGuardarCliente);

        dbHelper = new DatabaseHelper(this);

        // Verificar si es modo de edición
        Intent intent = getIntent();
        if (intent.hasExtra("CLIENTE_ID")) {
            isEditMode = true;
            clienteId = intent.getIntExtra("CLIENTE_ID", -1);
            cargarDatosCliente(clienteId);
        }

        btnGuardarCliente.setOnClickListener(v -> {
            String nombre = etNombreCliente.getText().toString();
            String telefono = etTelefonoCliente.getText().toString();

            if (nombre.isEmpty() || telefono.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isEditMode) {
                // Actualizar cliente existente
                int resultado = dbHelper.actualizarCliente(clienteId, nombre, telefono);
                if (resultado > 0) {
                    Toast.makeText(this, "Cliente actualizado correctamente.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Error al actualizar el cliente.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Insertar nuevo cliente
                long id = dbHelper.insertarCliente(nombre, telefono);
                if (id != -1) {
                    Toast.makeText(this, "Cliente registrado correctamente.", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                } else {
                    Toast.makeText(this, "Error al registrar el cliente.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cargarDatosCliente(int id) {
        Cursor cursor = dbHelper.obtenerClientePorId(id);
        if (cursor != null && cursor.moveToFirst()) {
            String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
            String telefono = cursor.getString(cursor.getColumnIndex("telefono"));

            etNombreCliente.setText(nombre);
            etTelefonoCliente.setText(telefono);

            cursor.close();
        }
    }

    private void limpiarCampos() {
        etNombreCliente.setText("");
        etTelefonoCliente.setText("");
    }
}
