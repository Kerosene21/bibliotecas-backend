package co.edu.javeriana.bibliotecas.repository;

import co.edu.javeriana.bibliotecas.entity.Biblioteca;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BibliotecaRepository extends JpaRepository<Biblioteca, Long> {

    @EntityGraph(attributePaths = "libros")
    List<Biblioteca> findAllByOrderByIdAsc();

    Optional<Biblioteca> findFirstByNombreOrderByIdAsc(String nombre);
}
