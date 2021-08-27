package com.example.imblue.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.imblue.R;
import com.example.imblue.controller.MainActivityController;
import com.example.imblue.model.pojo.Categoria;
import com.example.imblue.model.pojo.Pensamiento;
import com.example.imblue.view.fragments.PensamientoFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText tituloText, descripcionText;
    private Button reportarButton;
    private FloatingActionButton undoButton, redoButton;
    private MainActivityController mainActivityController;
    private Spinner categoriasSpinner;
    private boolean isEdit = false;
    private String idEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Data
        tituloText = findViewById(R.id.input_title);
        descripcionText = findViewById(R.id.input_description);
        reportarButton = findViewById(R.id.button_reportar);
        undoButton = findViewById(R.id.button_undo);
        redoButton = findViewById(R.id.button_redo);
        categoriasSpinner = findViewById(R.id.spinner_categorias);

        mainActivityController = new MainActivityController();

        // Events
        reportarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportarPensamiento();
            }
        });
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deshacer();
            }
        });
        redoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rehacer();
            }
        });

        // Data Spinner categories
        mainActivityController.checkOrCreateCategorias(this);
        String[] arraySpinner = mainActivityController.getCategoriasByNombre(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriasSpinner.setAdapter(adapter);

        // Show pensamientos
        showPensamientos();
    }

    public void showPensamientos() {
        String id;
        String titulo;
        String descripcion;
        Date fecha;
        String categoriaId;
        String color;
        Pensamiento pensamiento;
        Categoria categoria;

        List<Pensamiento> pensamientos = mainActivityController.getPensamientos(this);

        // Remove previous fragments
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }

        for (int i = 0; i < pensamientos.size(); i++) {
            pensamiento = pensamientos.get(i);
            id = pensamiento.getId();
            titulo = pensamiento.getTitulo();
            descripcion = pensamiento.getDescripcion();
            fecha = pensamiento.getFecha();
            categoriaId = pensamiento.getCategoriaId();
            categoria = mainActivityController.getCategoriaById(this, categoriaId);
            color = categoria.getColor();

            getSupportFragmentManager().beginTransaction().add(R.id.linear_layout_pensamientos,
                    PensamientoFragment.newInstance(id, titulo, descripcion, fecha, color)).commit();
        }
    }

    public void reportarPensamiento() {
        if (isEdit()){
            mainActivityController.editar(this, tituloText.getText().toString(),
                    descripcionText.getText().toString(), getIdEdit());
        } else {
            mainActivityController.reportar(this, tituloText.getText().toString(),
                    descripcionText.getText().toString(), categoriasSpinner.getSelectedItem().toString());
        }
    }

    public void eliminarPensamiento(String id) {
        mainActivityController.eliminar(this, id);
    }

    public void editarPensamiento(String id) {
        Pensamiento pensamiento = mainActivityController.getPensamientoById(this, id);
        tituloText.setText(pensamiento.getTitulo());
        descripcionText.setText(pensamiento.getDescripcion());
        categoriasSpinner.setVisibility(View.GONE);
        reportarButton.setText(R.string.editar);
        setEdit(true);
        setIdEdit(id);
    }

    public void reporteSucceed() {
        notification("¡Pensamiento reportado!");
        tituloText.setText("");
        descripcionText.setText("");
        showPensamientos();
    }

    public void deleteSucceed() {
        notification("¡Pensamiento eliminado!");
        showPensamientos();
    }

    public void editSucceed() {
        notification("¡Pensamiento editado!");
        tituloText.setText("");
        descripcionText.setText("");
        categoriasSpinner.setVisibility(View.VISIBLE);
        reportarButton.setText(R.string.reportar);
        setEdit(false);
        setIdEdit(null);
        showPensamientos();
    }

    public void error(String msg) {
        // Builder pattern design
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg)
                .setTitle("Ooops!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Close dialog
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void notification(String msg) {
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public String getIdEdit() {
        return idEdit;
    }

    public void setIdEdit(String idEdit) {
        this.idEdit = idEdit;
    }

    public void rehacer() {
        mainActivityController.rehacer();
        showPensamientos();
    }

    public void deshacer() {
        mainActivityController.deshacer();
        showPensamientos();
    }

}