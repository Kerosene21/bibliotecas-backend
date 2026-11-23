package co.edu.javeriana.bibliotecas.repository;

import co.edu.javeriana.bibliotecas.entity.Biblioteca;
import co.edu.javeriana.bibliotecas.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    boolean existsByTituloAndBiblioteca(String titulo, Biblioteca biblioteca);
}
