package com.login.miinventario;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class RegistrarVentaActivity extends AppCompatActivity {

    private Spinner spProducto;
    private TextView tvCantidadDisponible;
    private EditText etCantidadVendida, etFechaVenta;
    private Button btnRegistrarVenta;
    private DatabaseHelper dbHelper;

    private ArrayList<String> productosList;
    private HashMap<String, Integer> productoIdMap;
    private HashMap<String, Integer> productoCantidadMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_venta);

        spProducto = findViewById(R.id.spProducto);
        tvCantidadDisponible = findViewById(R.id.tvCantidadDisponible);
        etCantidadVendida = findViewById(R.id.etCantidadVendida);
        etFechaVenta = findViewById(R.id.etFechaVenta);
        btnRegistrarVenta = findViewById(R.id.btnRegistrarVenta);

        dbHelper = new DatabaseHelper(this);
        productosList = new ArrayList<>();
        productoIdMap = new HashMap<>();
        productoCantidadMap = new HashMap<>();

        cargarProductos();

        spProducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String productoSeleccionado = productosList.get(position);
                int cantidadDisponible = productoCantidadMap.get(productoSeleccionado);
                tvCantidadDisponible.setText(getString(R.string.lvCant)+" " + cantidadDisponible);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tvCantidadDisponible.setText(getString(R.string.lvCant)+" 0");
            }
        });

        btnRegistrarVenta.setOnClickListener(v -> registrarVenta());
    }

    private void cargarProductos() {
        Cursor cursor = dbHelper.obtenerProductosConCantidad();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                int cantidad = cursor.getInt(cursor.getColumnIndex("cantidad"));

                productosList.add(nombre);
                productoIdMap.put(nombre, id);
                productoCantidadMap.put(nombre, cantidad);
            } while (cursor.moveToNext());

            cursor.close();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, productosList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProducto.setAdapter(adapter);
    }

    private void registrarVenta() {
        String productoSeleccionado = (String) spProducto.getSelectedItem();
        int productoId = productoIdMap.get(productoSeleccionado);
        int cantidadDisponible = productoCantidadMap.get(productoSeleccionado);

        String cantidadVendidaStr = etCantidadVendida.getText().toString();
        String fechaVenta = etFechaVenta.getText().toString();

        if (cantidadVendidaStr.isEmpty() || fechaVenta.isEmpty()) {
            Toast.makeText(this, getString(R.string.ErrorCampos), Toast.LENGTH_SHORT).show();
            return;
        }

        int cantidadVendida = Integer.parseInt(cantidadVendidaStr);

        if (cantidadVendida > cantidadDisponible) {
            Toast.makeText(this, getString(R.string.ErrorCM), Toast.LENGTH_SHORT).show();
            return;
        }

        // Registrar la venta y actualizar inventario
        long ventaId = dbHelper.insertarVenta(productoSeleccionado, cantidadVendida, fechaVenta);

        if (ventaId != -1) {
            dbHelper.actualizarProducto(productoId, productoSeleccionado, cantidadDisponible - cantidadVendida, 0, 0);
            Toast.makeText(this, getString(R.string.MsVenta), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, getString(R.string.ErrorRV), Toast.LENGTH_SHORT).show();
        }
    }
}

