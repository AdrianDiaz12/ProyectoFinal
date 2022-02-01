package com.idat.cine.models;

public class Pelicula {

    public String nombre;
    public String sinopsis;
    public String imagen;

    Pelicula(){}

    public Pelicula(String nombre, String sinopsis, String imagen) {
        this.nombre = nombre;
        this.sinopsis = sinopsis;
        this.imagen = imagen;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public String getImagen() {
        return imagen;
    }
}
