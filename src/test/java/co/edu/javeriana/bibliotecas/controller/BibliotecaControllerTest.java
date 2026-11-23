package co.edu.javeriana.bibliotecas.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import co.edu.javeriana.bibliotecas.dto.BibliotecaDto;
import co.edu.javeriana.bibliotecas.dto.LibroDto;
import co.edu.javeriana.bibliotecas.dto.RegistroBibliotecaDto;
import co.edu.javeriana.bibliotecas.service.BibliotecaService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BibliotecaController.class)
class BibliotecaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BibliotecaService bibliotecaService;

    @Test
    void registrarDevuelve201ConLaBibliotecaCreada() throws Exception {
        when(bibliotecaService.registrar(any(RegistroBibliotecaDto.class)))
                .thenReturn(new BibliotecaDto(3L, "Biblioteca Sur", "Calle 1 # 2-3", "Cali", List.of()));

        mockMvc.perform(post("/bibliotecas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nombre":"Biblioteca Sur","direccion":"Calle 1 # 2-3","ciudad":"Cali"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/bibliotecas/3"))
                .andExpect(content().json("""
                        {"id":3,"nombre":"Biblioteca Sur","direccion":"Calle 1 # 2-3","ciudad":"Cali","libros":[]}
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void registrarRechazaCamposVaciosCon400() throws Exception {
        mockMvc.perform(post("/bibliotecas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nombre":"","direccion":"   ","ciudad":null}
                                """))
                .andExpect(status().isBadRequest());

        verify(bibliotecaService, never()).registrar(any());
    }

    @Test
    void consultarDevuelveBibliotecasConSusLibros() throws Exception {
        LibroDto libro = new LibroDto(1L, "Cien años de soledad", "Gabriel García Márquez", 1967);
        when(bibliotecaService.consultarTodas()).thenReturn(List.of(
                new BibliotecaDto(1L, "Biblioteca Central", "Carrera 7", "Bogotá", List.of(libro)),
                new BibliotecaDto(2L, "Biblioteca Sur", "Calle 1", "Cali", List.of())));

        mockMvc.perform(get("/bibliotecas"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                          {"id":1,"nombre":"Biblioteca Central","direccion":"Carrera 7","ciudad":"Bogotá",
                           "libros":[{"id":1,"titulo":"Cien años de soledad","autor":"Gabriel García Márquez","anioPublicacion":1967}]},
                          {"id":2,"nombre":"Biblioteca Sur","direccion":"Calle 1","ciudad":"Cali","libros":[]}
                        ]
                        """, JsonCompareMode.STRICT));
    }
}
