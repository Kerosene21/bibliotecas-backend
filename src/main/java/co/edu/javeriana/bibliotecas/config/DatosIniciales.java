package co.edu.javeriana.bibliotecas.config;

import co.edu.javeriana.bibliotecas.entity.Biblioteca;
import co.edu.javeriana.bibliotecas.entity.Libro;
import co.edu.javeriana.bibliotecas.repository.BibliotecaRepository;
import co.edu.javeriana.bibliotecas.repository.LibroRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatosIniciales implements CommandLineRunner {

    private final BibliotecaRepository bibliotecaRepository;
    private final LibroRepository libroRepository;

    public DatosIniciales(BibliotecaRepository bibliotecaRepository, LibroRepository libroRepository) {
        this.bibliotecaRepository = bibliotecaRepository;
        this.libroRepository = libroRepository;
    }

    @Override
    public void run(String... args) {
        Biblioteca central = obtenerOCrear("Biblioteca Central", "Carrera 7 # 40-62", "Bogotá");
        registrarLibroSiNoExiste("Cien años de soledad", "Gabriel García Márquez", 1967, central);

        Biblioteca norte = obtenerOCrear("Biblioteca Norte", "Calle 170 # 45-20", "Bogotá");
        registrarLibroSiNoExiste("El coronel no tiene quien le escriba", "Gabriel García Márquez", 1961, norte);
    }

    private Biblioteca obtenerOCrear(String nombre, String direccion, String ciudad) {
        return bibliotecaRepository.findFirstByNombreOrderByIdAsc(nombre)
                .orElseGet(() -> bibliotecaRepository.save(new Biblioteca(nombre, direccion, ciudad)));
    }

    private void registrarLibroSiNoExiste(String titulo, String autor, int anioPublicacion, Biblioteca biblioteca) {
        if (!libroRepository.existsByTituloAndBiblioteca(titulo, biblioteca)) {
            libroRepository.save(new Libro(titulo, autor, anioPublicacion, biblioteca));
        }
    }
}
