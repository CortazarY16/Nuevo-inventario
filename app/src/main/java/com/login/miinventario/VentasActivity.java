package com.login.miinventario;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class VentasActivity extends AppCompatActivity {

    private ListView lvVentas;
    private Button btnRegistrarVenta;
    private DatabaseHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> ventasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);

        lvVentas = findViewById(R.id.lvVentas);
        btnRegistrarVenta = findViewById(R.id.btnRegistrarVenta);

        dbHelper = new DatabaseHelper(this);
        ventasList = new ArrayList<>();

        cargarVentas();

        // Botón para registrar una nueva venta
        btnRegistrarVenta.setOnClickListener(v -> {
            Intent intent = new Intent(VentasActivity.this, RegistrarVentaActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarVentas();
    }

    private void cargarVentas() {
        ventasList.clear();
        Cursor cursor = dbHelper.obtenerVentasConDetalles();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String producto = cursor.getString(cursor.getColumnIndex("producto"));
                int cantidad = cursor.getInt(cursor.getColumnIndex("cantidad"));
                String fecha = cursor.getString(cursor.getColumnIndex("fecha"));

                String detalleVenta = "\n"+getString(R.string.Produto) + producto + "\n"+getString(R.string.Cantidad)+" "+ cantidad + "\n"+getString(R.string.Fecha)+" "+ fecha+"\n";
                ventasList.add(detalleVenta);
            } while (cursor.moveToNext());

            cursor.close();
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ventasList);
        lvVentas.setAdapter(adapter);
    }



}
