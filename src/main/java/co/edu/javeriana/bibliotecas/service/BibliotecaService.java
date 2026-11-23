package co.edu.javeriana.bibliotecas.service;

import co.edu.javeriana.bibliotecas.dto.BibliotecaDto;
import co.edu.javeriana.bibliotecas.dto.RegistroBibliotecaDto;
import co.edu.javeriana.bibliotecas.entity.Biblioteca;
import co.edu.javeriana.bibliotecas.repository.BibliotecaRepository;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BibliotecaService {

    private final BibliotecaRepository bibliotecaRepository;
    private final ModelMapper modelMapper;

    public BibliotecaService(BibliotecaRepository bibliotecaRepository, ModelMapper modelMapper) {
        this.bibliotecaRepository = bibliotecaRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public BibliotecaDto registrar(RegistroBibliotecaDto registro) {
        Biblioteca biblioteca = modelMapper.map(registro, Biblioteca.class);
        biblioteca.setNombre(biblioteca.getNombre().trim());
        biblioteca.setDireccion(biblioteca.getDireccion().trim());
        biblioteca.setCiudad(biblioteca.getCiudad().trim());
        return modelMapper.map(bibliotecaRepository.save(biblioteca), BibliotecaDto.class);
    }

    @Transactional(readOnly = true)
    public List<BibliotecaDto> consultarTodas() {
        return bibliotecaRepository.findAllByOrderByIdAsc().stream()
                .map(biblioteca -> modelMapper.map(biblioteca, BibliotecaDto.class))
                .toList();
    }
}
