package com.example.imblue.view.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.imblue.view.MainActivity;
import com.example.imblue.R;

import java.text.DateFormat;
import java.util.Date;

public class PensamientoFragment extends Fragment {
    private String idPensamiento;
    private String titulo;
    private String descripcion;
    private Date fecha;
    private String color;
    private View rootView;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView fechaTextView;
    private ConstraintLayout colorLayout;
    private Button eliminarButton, editarButton;

    public PensamientoFragment() {
        // Required empty public constructor
    }

    public static PensamientoFragment newInstance(String id, String titulo, String descripcion,
                                                  Date fecha, String color) {
        PensamientoFragment fragment = new PensamientoFragment();
        fragment.setIdPensamiento(id);
        fragment.setTitulo(titulo);
        fragment.setDescripcion(descripcion);
        fragment.setFecha(fecha);
        fragment.setColor(color);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_pensamiento, container, false);
        titleTextView = rootView.findViewById(R.id.title_pensmiento);
        descriptionTextView = rootView.findViewById(R.id.description_pensamiento);
        fechaTextView = rootView.findViewById(R.id.fecha_pensamiento);
        colorLayout = rootView.findViewById(R.id.layout_color);
        eliminarButton = rootView.findViewById(R.id.button_eliminar);
        editarButton = rootView.findViewById(R.id.button_editar);

        String fechaFormat = DateFormat.getDateInstance().format(fecha);

        titleTextView.setText(titulo);
        descriptionTextView.setText(descripcion);
        fechaTextView.setText(fechaFormat);
        colorLayout.setBackgroundColor(Color.parseColor("#" + color));

        // Events
        eliminarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).eliminarPensamiento(getIdPensamiento());
            }
        });
        editarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).editarPensamiento(getIdPensamiento());
            }
        });

        return rootView;
    }

    // Getters and setters
    public String getIdPensamiento() {
        return idPensamiento;
    }

    public void setIdPensamiento(String idPensamiento) {
        this.idPensamiento = idPensamiento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
