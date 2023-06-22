package com.example.examenprimerparcial.config.Models;

public class Contactos {
    private Integer id;
    private String nombre;
    private String pais;
    private Integer telefono;
    private String nota;
    private String foto;


    public Contactos(Integer id, String nombre, String pais, Integer telefono, String nota, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
        this.telefono = telefono;
        this.nota = nota;
        this.foto = foto;
    }

    public Contactos() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}



