package com.login.miinventario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Nombre y versión de la base de datos
    private static final String DATABASE_NAME = "inventario.db";
    private static final int DATABASE_VERSION = 3;

    // Tablas
    private static final String TABLE_PRODUCTOS = "productos";

    // Columnas de la tabla `productos`
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_CANTIDAD = "cantidad";
    private static final String COLUMN_PRECIO = "precio";
    private static final String COLUMN_COSTO = "costo";

    // Nombre de la tabla y columnas
    private static final String TABLE_VENTAS = "ventas";
    private static final String COLUMN_VENTA_ID = "id";
    private static final String COLUMN_PRODUCTO = "producto";
    private static final String COLUMN_CANTIDA = "cantidad";
    private static final String COLUMN_FECHA = "fecha";
    // Nombre de la tabla y columnas
    private static final String TABLE_CLIENTES = "clientes";
    private static final String COLUMN_CLIENTE_ID = "id";
    private static final String COLUMN_CLIENTE_NOMBRE = "nombre";
    private static final String COLUMN_CLIENTE_TELEFONO = "telefono";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla de productos
        String CREATE_PRODUCTOS_TABLE = "CREATE TABLE " + TABLE_PRODUCTOS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NOMBRE + " TEXT, "
                + COLUMN_CANTIDAD + " INTEGER, "
                + COLUMN_PRECIO + " REAL, "
                + COLUMN_COSTO + " REAL)";
        db.execSQL(CREATE_PRODUCTOS_TABLE);

        String CREATE_VENTAS_TABLE = "CREATE TABLE " + TABLE_VENTAS + " ("
                + COLUMN_VENTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PRODUCTO + " TEXT, "
                + COLUMN_CANTIDA + " INTEGER, "
                + COLUMN_FECHA + " TEXT)";
        db.execSQL(CREATE_VENTAS_TABLE);
// Crear tabla de clientes
        String CREATE_CLIENTES_TABLE = "CREATE TABLE " + TABLE_CLIENTES + " ("
                + COLUMN_CLIENTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CLIENTE_NOMBRE + " TEXT, "
                + COLUMN_CLIENTE_TELEFONO + " TEXT)";
        db.execSQL(CREATE_CLIENTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar la tabla anterior si existe
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENTAS);
        // Crear la tabla nuevamente
        onCreate(db);

    }

    // Método para insertar un producto en la base de datos
    public long insertarProducto(String nombre, int cantidad, double precio, double costo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_CANTIDAD, cantidad);
        values.put(COLUMN_PRECIO, precio);
        values.put(COLUMN_COSTO, costo);

        // Insertar el producto y devolver el ID generado
        return db.insert(TABLE_PRODUCTOS, null, values);
    }

    // Método para obtener todos los productos
    public Cursor obtenerProductos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUCTOS, null);
    }

    // Método para actualizar un producto
    public int actualizarProducto(int id, String nombre, int cantidad, double precio, double costo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_CANTIDAD, cantidad);
        values.put(COLUMN_PRECIO, precio);
        values.put(COLUMN_COSTO, costo);

        // Actualizar producto basado en su ID
        return db.update(TABLE_PRODUCTOS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Método para eliminar un producto
    public int eliminarProducto(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PRODUCTOS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Método para insertar una venta
    public long insertarVenta(String producto, int cantidad, String fecha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTO, producto);
        values.put(COLUMN_CANTIDAD, cantidad);
        values.put(COLUMN_FECHA, fecha);

        return db.insert(TABLE_VENTAS, null, values);
    }

    // Método para obtener todas las ventas (opcional)
    public Cursor obtenerVentas() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_VENTAS, null);
    }
    // Insertar un cliente
    public long insertarCliente(String nombre, String telefono) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("telefono", telefono);

        return db.insert("clientes", null, values);
    }

    public Cursor obtenerClientePorId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM clientes WHERE id = ?", new String[]{String.valueOf(id)});
    }
    public Cursor obtenerClientes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM clientes", null);
    }

    public int actualizarCliente(int id, String nombre, String telefono) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("telefono", telefono);

        return db.update("clientes", values, "id = ?", new String[]{String.valueOf(id)});
    }

    public int eliminarCliente(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("clientes", "id = ?", new String[]{String.valueOf(id)});
    }


}


