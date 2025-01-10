package com.login.miinventario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Enlazar los botones con el XML
        Button btnRegistrarProducto = findViewById(R.id.btnRegistrarProducto);
        Button btnProductosExistentes = findViewById(R.id.btnProductosExistentes);
        Button btnVentas = findViewById(R.id.btnVentas);
        Button btnRegistroClientes = findViewById(R.id.btnRegistroClientes);
        Button btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        // Evento de click para ir a la pantalla de registro de productos

        // Asignar acciones a los botones
        btnRegistrarProducto.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegistroProductoActivity.class);
            startActivity(intent);
        });

        btnProductosExistentes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProductosExistentesActivity.class);
            startActivity(intent);
        });

        btnVentas.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VentasActivity.class);
            startActivity(intent);
        });

        btnRegistroClientes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ClientesActivity.class);
            startActivity(intent);
        });


        btnCerrarSesion.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Finaliza la actividad actual para evitar regresar a esta al presionar "atrás"
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.menu), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}