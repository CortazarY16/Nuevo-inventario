package com.login.miinventario;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ProductosExistentesActivity extends AppCompatActivity {

    private ListView lvProductos;
    private Button btnRegresarMenu;
    private DatabaseHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> productosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_existentes);

        // Inicializar vistas
        lvProductos = findViewById(R.id.lvProductos);
        btnRegresarMenu = findViewById(R.id.btnRegresarMenu);

        // Inicializar base de datos y lista
        dbHelper = new DatabaseHelper(this);
        productosList = new ArrayList<>();

        // Cargar los productos en el ListView
        cargarProductos();

        // Configurar el botón para regresar al menú principal
        btnRegresarMenu.setOnClickListener(v -> {
            Intent intent = new Intent(ProductosExistentesActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void cargarProductos() {
        // Obtener productos de la base de datos
        Cursor cursor = dbHelper.obtenerProductos();
        productosList.clear();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Obtener datos de cada producto
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                int cantidad = cursor.getInt(cursor.getColumnIndex("cantidad"));
                double precio = cursor.getDouble(cursor.getColumnIndex("precio"));
                double costo = cursor.getDouble(cursor.getColumnIndex("costo"));

                // Formatear los datos para mostrar en la lista
                String producto = "\n"+ getString(R.string.Nombre)+" " + nombre + "\n"+getString(R.string.lvCantidad)+" " + cantidad + "\n"+getString(R.string.Precio)+" " + precio
                        +"\n"+getString(R.string.Costo)+ " "+ costo +"\n";
                productosList.add(producto);
            } while (cursor.moveToNext());

            cursor.close();
        }

        // Configurar el adaptador para el ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productosList);
        lvProductos.setAdapter(adapter);
    }
}