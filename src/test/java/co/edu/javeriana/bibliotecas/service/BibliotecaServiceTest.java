package co.edu.javeriana.bibliotecas.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import co.edu.javeriana.bibliotecas.config.ModelMapperConfig;
import co.edu.javeriana.bibliotecas.dto.BibliotecaDto;
import co.edu.javeriana.bibliotecas.dto.LibroDto;
import co.edu.javeriana.bibliotecas.dto.RegistroBibliotecaDto;
import co.edu.javeriana.bibliotecas.entity.Biblioteca;
import co.edu.javeriana.bibliotecas.entity.Libro;
import co.edu.javeriana.bibliotecas.repository.BibliotecaRepository;
import java.util.List;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BibliotecaServiceTest {

    @Mock
    private BibliotecaRepository bibliotecaRepository;

    private BibliotecaService bibliotecaService;

    @BeforeEach
    void crearServicio() {
        bibliotecaService = new BibliotecaService(bibliotecaRepository, new ModelMapperConfig().modelMapper());
    }

    @Test
    void registrarGuardaLaBibliotecaSinEspaciosSobrantes() {
        when(bibliotecaRepository.save(any(Biblioteca.class))).thenAnswer(invocacion -> {
            Biblioteca biblioteca = invocacion.getArgument(0);
            assertThat(biblioteca.getId()).isNull();
            assertThat(biblioteca.getLibros()).isEmpty();
            biblioteca.setId(5L);
            return biblioteca;
        });

        BibliotecaDto resultado = bibliotecaService.registrar(
                new RegistroBibliotecaDto("  Biblioteca Sur ", "Calle 1 # 2-3", " Cali"));

        assertThat(resultado.getId()).isEqualTo(5L);
        assertThat(resultado.getNombre()).isEqualTo("Biblioteca Sur");
        assertThat(resultado.getDireccion()).isEqualTo("Calle 1 # 2-3");
        assertThat(resultado.getCiudad()).isEqualTo("Cali");
        assertThat(resultado.getLibros()).isNotNull().isEmpty();
    }

    @Test
    void consultarTodasIncluyeLosLibrosDeCadaBiblioteca() {
        Biblioteca central = new Biblioteca("Biblioteca Central", "Carrera 7", "Bogotá");
        central.setId(1L);
        Libro libro = new Libro("Cien años de soledad", "Gabriel García Márquez", 1967, central);
        libro.setId(10L);
        central.getLibros().add(libro);
        Biblioteca sur = new Biblioteca("Biblioteca Sur", "Calle 1", "Cali");
        sur.setId(2L);
        when(bibliotecaRepository.findAllByOrderByIdAsc()).thenReturn(List.of(central, sur));

        List<BibliotecaDto> resultado = bibliotecaService.consultarTodas();

        assertThat(resultado).extracting(BibliotecaDto::getNombre).containsExactly("Biblioteca Central", "Biblioteca Sur");
        assertThat(resultado.getFirst().getLibros()).hasSize(1);
        LibroDto libroDto = resultado.getFirst().getLibros().getFirst();
        assertThat(libroDto.getId()).isEqualTo(10L);
        assertThat(libroDto.getTitulo()).isEqualTo("Cien años de soledad");
        assertThat(libroDto.getAutor()).isEqualTo("Gabriel García Márquez");
        assertThat(libroDto.getAnioPublicacion()).isEqualTo(1967);
        assertThat(resultado.get(1).getLibros()).isNotNull().isEmpty();
    }

    @Test
    void consultarTodasNoAccedeALaBibliotecaReferenciadaPorCadaLibro() {
        Biblioteca bibliotecaNoCargada = mock(Biblioteca.class, invocacion -> {
            throw new LazyInitializationException("La biblioteca del libro no debe cargarse");
        });
        Biblioteca central = new Biblioteca("Biblioteca Central", "Carrera 7", "Bogotá");
        central.setId(1L);
        Libro libro = new Libro("Cien años de soledad", "Gabriel García Márquez", 1967, bibliotecaNoCargada);
        libro.setId(10L);
        central.getLibros().add(libro);
        when(bibliotecaRepository.findAllByOrderByIdAsc()).thenReturn(List.of(central));

        List<BibliotecaDto> resultado = bibliotecaService.consultarTodas();

        assertThat(resultado.getFirst().getLibros())
                .extracting(LibroDto::getTitulo)
                .containsExactly("Cien años de soledad");
    }
}
