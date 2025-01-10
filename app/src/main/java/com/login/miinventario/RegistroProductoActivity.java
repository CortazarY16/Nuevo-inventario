package com.login.miinventario;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistroProductoActivity extends AppCompatActivity {
    private EditText etNombre, etCantidad, etPrecio, etCosto;
    private Button btnGuardarProducto, btnRegresarMenu;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_producto);

        // Inicializar la base de datos
        dbHelper = new DatabaseHelper(this);

        // Enlazar los elementos del diseño
        etNombre = findViewById(R.id.etNombre);
        etCantidad = findViewById(R.id.etCantidad);
        etPrecio = findViewById(R.id.etPrecio);
        etCosto = findViewById(R.id.etCosto);
        btnGuardarProducto = findViewById(R.id.btnGuardarProducto);
        btnRegresarMenu = findViewById(R.id.btnRegresarMenu);
        btnRegresarMenu.setOnClickListener(v ->{

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });


        // Configurar el botón "Guardar Producto"
        btnGuardarProducto.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            String cantidadStr = etCantidad.getText().toString();
            String precioStr = etPrecio.getText().toString();
            String costoStr = etCosto.getText().toString();

            // Validar que los campos no estén vacíos
            if (nombre.isEmpty() || cantidadStr.isEmpty() || precioStr.isEmpty() || costoStr.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Convertir cantidad, precio y costo a números
            int cantidad = Integer.parseInt(cantidadStr);
            double precio = Double.parseDouble(precioStr);
            double costo = Double.parseDouble(costoStr);

            // Guardar en la base de datos
            guardarProducto(nombre, cantidad, precio, costo);
        });
    }

    private void guardarProducto(String nombre, int cantidad, double precio, double costo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("cantidad", cantidad);
        values.put("precio", precio);
        values.put("costo", costo);

        long id = db.insert("productos", null, values);
        if (id != -1) {
            Toast.makeText(this, "Producto registrado correctamente.", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        } else {
            Toast.makeText(this, "Error al registrar el producto.", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        etNombre.setText("");
        etCantidad.setText("");
        etPrecio.setText("");
        etCosto.setText("");
    }
}