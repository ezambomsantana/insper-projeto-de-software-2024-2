package br.insper.functional.musicas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MusicaService {

    @Autowired
    private MusicaRepository musicaRepository;
    public List<MusicaDTO> getAllMusicas() {
        List<Musica> musicas = musicaRepository.findAll();
        List<MusicaDTO> dtos = new ArrayList<>();
        for (Musica musica : musicas) {
            dtos.add(MusicaDTO.convert(musica));
        }
        return dtos;
    }


}
