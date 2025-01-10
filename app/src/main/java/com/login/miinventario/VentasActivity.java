package com.login.miinventario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class VentasActivity extends AppCompatActivity {

    private EditText etNombreProducto, etCantidadVendida, etFechaVenta;
    private Button btnRegistrarVenta, btnRegresarMenuVentas;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);

        // Inicializar vistas
        etNombreProducto = findViewById(R.id.etNombreProducto);
        etCantidadVendida = findViewById(R.id.etCantidadVendida);
        etFechaVenta = findViewById(R.id.etFechaVenta);
        btnRegistrarVenta = findViewById(R.id.btnRegistrarVenta);
        btnRegresarMenuVentas = findViewById(R.id.btnRegresarMenuVentas);

        // Inicializar base de datos
        dbHelper = new DatabaseHelper(this);

        // Configurar el botón "Registrar Venta"
        btnRegistrarVenta.setOnClickListener(v -> {
            String nombreProducto = etNombreProducto.getText().toString();
            String cantidadStr = etCantidadVendida.getText().toString();
            String fechaVenta = etFechaVenta.getText().toString();

            // Validar que los campos no estén vacíos
            if (nombreProducto.isEmpty() || cantidadStr.isEmpty() || fechaVenta.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Convertir cantidad a número
            int cantidadVendida = Integer.parseInt(cantidadStr);

            // Registrar la venta en la base de datos
            long resultado = dbHelper.insertarVenta(nombreProducto, cantidadVendida, fechaVenta);
            if (resultado != -1) {
                Toast.makeText(this, "Venta registrada correctamente.", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            } else {
                Toast.makeText(this, "Error al registrar la venta.", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar el botón "Regresar al Menú Principal"
        btnRegresarMenuVentas.setOnClickListener(v -> {
            Intent intent = new Intent(VentasActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void limpiarCampos() {
        etNombreProducto.setText("");
        etCantidadVendida.setText("");
        etFechaVenta.setText("");
    }
}
