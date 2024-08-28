package br.insper.aposta.aposta;

import br.insper.aposta.partida.PartidaNaoEncontradaException;
import br.insper.aposta.partida.PartidaNaoRealizadaException;
import br.insper.aposta.partida.RetornarPartidaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ApostaService {

    @Autowired
    private ApostaRepository apostaRepository;

    public Aposta salvar(Aposta aposta) {
        aposta.setId(UUID.randomUUID().toString());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RetornarPartidaDTO> partida = restTemplate.getForEntity(
                "http://localhost:8080/partida/" + aposta.getIdPartida(),
                RetornarPartidaDTO.class);

        if (partida.getStatusCode().is2xxSuccessful())  {
            aposta.setStatus("REALIZADA");
            aposta.setDataAposta(LocalDateTime.now());

            return apostaRepository.save(aposta);
        } else {
            throw new PartidaNaoEncontradaException("Partida não encontrada");
        }

    }

    public List<Aposta> listar() {
        return apostaRepository.findAll();
    }

    public Aposta getAposta(String idAposta) {

        Aposta aposta = apostaRepository.findById(idAposta).get();

        if (aposta == null) {
            throw new ApostaNaoEncontradaException("Aposta não encontrada");
        }

        if (!aposta.getStatus().equals("REALIZADA")) {
            return aposta;
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RetornarPartidaDTO> partida = restTemplate.getForEntity(
                "http://localhost:8080/partida/" + aposta.getIdPartida(),
                RetornarPartidaDTO.class);

        if (partida.getStatusCode().is2xxSuccessful())  {
            RetornarPartidaDTO partidaDTO = partida.getBody();

            if (partidaDTO.getStatus().equals("REALIZADA")) {

                if (aposta.getResultado().equals("EMPATE") && partidaDTO.isEmpate()) {
                    aposta.setStatus("GANHOU");
                }

                if (aposta.getResultado().equals("VITORIA_MANDANTE") && partidaDTO.isVitoriaMandante()) {
                    aposta.setStatus("GANHOU");
                }

                if (aposta.getResultado().equals("EMPATE") && partidaDTO.isVitoriaVisitante()) {
                    aposta.setStatus("GANHOU");
                }

                if (aposta.getStatus().equals("REALIZADA")) {
                    aposta.setStatus("PERDEU");
                }
            } else {
                throw new PartidaNaoRealizadaException("Partida não realizada");
            }
            return apostaRepository.save(aposta);

        } else {
            throw new PartidaNaoEncontradaException("Partida não encontrada");
        }

    }
}
