package co.edu.javeriana.bibliotecas.dto;

import java.util.ArrayList;
import java.util.List;

public class BibliotecaDto {

    private Long id;
    private String nombre;
    private String direccion;
    private String ciudad;
    private List<LibroDto> libros = new ArrayList<>();

    public BibliotecaDto() {
    }

    public BibliotecaDto(Long id, String nombre, String direccion, String ciudad, List<LibroDto> libros) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.libros = libros;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public List<LibroDto> getLibros() {
        return libros;
    }

    public void setLibros(List<LibroDto> libros) {
        this.libros = libros;
    }
}
