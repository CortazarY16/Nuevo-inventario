package com.login.miinventario;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistrarVentaActivity extends AppCompatActivity {

    private EditText etProducto, etCantidad, etFecha;
    private Button btnGuardarVenta;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_venta);

        etProducto = findViewById(R.id.etProducto);
        etCantidad = findViewById(R.id.etCantidad);
        etFecha = findViewById(R.id.etFecha);
        btnGuardarVenta = findViewById(R.id.btnGuardarVenta);

        dbHelper = new DatabaseHelper(this);

        btnGuardarVenta.setOnClickListener(v -> {
            String producto = etProducto.getText().toString();
            String cantidadStr = etCantidad.getText().toString();
            String fecha = etFecha.getText().toString();

            if (producto.isEmpty() || cantidadStr.isEmpty() || fecha.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int cantidad = Integer.parseInt(cantidadStr);
            long id = dbHelper.insertarVenta(producto, cantidad, fecha);

            if (id != -1) {
                Toast.makeText(this, "Venta registrada correctamente", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al registrar la venta", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
