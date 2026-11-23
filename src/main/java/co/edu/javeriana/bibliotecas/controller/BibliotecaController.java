package co.edu.javeriana.bibliotecas.controller;

import co.edu.javeriana.bibliotecas.dto.BibliotecaDto;
import co.edu.javeriana.bibliotecas.dto.RegistroBibliotecaDto;
import co.edu.javeriana.bibliotecas.service.BibliotecaService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bibliotecas")
public class BibliotecaController {

    private final BibliotecaService bibliotecaService;

    public BibliotecaController(BibliotecaService bibliotecaService) {
        this.bibliotecaService = bibliotecaService;
    }

    @PostMapping
    public ResponseEntity<BibliotecaDto> registrar(@Valid @RequestBody RegistroBibliotecaDto registro) {
        BibliotecaDto biblioteca = bibliotecaService.registrar(registro);
        return ResponseEntity.created(URI.create("/bibliotecas/" + biblioteca.getId())).body(biblioteca);
    }

    @GetMapping
    public List<BibliotecaDto> consultarTodas() {
        return bibliotecaService.consultarTodas();
    }
}
