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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        EditText etUsuario = findViewById(R.id.etUsuario);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnIngresar = findViewById(R.id.btnLogin);

        btnIngresar.setOnClickListener(v -> {
            String usuario = etUsuario.getText().toString();
            String contrasenia = etPassword.getText().toString().trim();
            if (usuario.isEmpty() || contrasenia.isEmpty()) {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
            else if (usuario.equals("Admin") && contrasenia.equals("4dm1nRmb")) { // Lógica de inicio de sesión
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
            else if (usuario.equals("1") && contrasenia.equals("1")) { // Lógica de inicio de sesión
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
            else {
                Toast.makeText(this, "Error de igreso", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainlogin), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}