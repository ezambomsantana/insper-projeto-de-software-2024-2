package br.insper.aposta.aposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ApostaService {

    @Autowired
    private ApostaRepository apostaRepository;

    public void salvar(Aposta aposta) {
        aposta.setId(UUID.randomUUID().toString());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RetornarPartidaDTO> partida = restTemplate.getForEntity(
                "http://3.88.6.154:8080/partida/" + aposta.getIdPartida(),
                RetornarPartidaDTO.class);



        if (partida.getStatusCode().is2xxSuccessful())  {
            if (partida.getBody().getStatus().equalsIgnoreCase("REALIZADA")) {
                throw new RuntimeException("Partida já realizada");
            }
            apostaRepository.save(aposta);
        }



    }

    public List<Aposta> listar(String status) {
        if (status == null || status.isEmpty()) {
            return apostaRepository.findAll();
        } else {
            return apostaRepository.findByStatus(status);
        }
    }

    public Aposta verificaaposta(String id){

        Aposta aposta = apostaRepository.findById(id).orElse(null);

        aposta.setId(UUID.randomUUID().toString());

        RestTemplate restTemplate = new RestTemplate();
        RetornarPartidaDTO partida =  restTemplate.getForObject(
                "http://3.88.6.154:8080/partida/" + aposta.getIdPartida() ,
                RetornarPartidaDTO.class);

        if (partida != null){
            if (partida.getStatus().equalsIgnoreCase("REALIZADA")){
                String result = aposta.getResultado();
                if (Objects.equals(partida.getPlacarMandante(), partida.getPlacarVisitante())){
                    if (result.equalsIgnoreCase("EMPATE")){
                        aposta.setStatus("GANHOU");
                    } else{
                        aposta.setStatus("PERDEU");
                    }
                } else if (partida.getPlacarMandante() > partida.getPlacarVisitante()){
                    if (result.equalsIgnoreCase("VITORIA_MANDANTE")){
                        aposta.setStatus("GANHOU");
                    } else{
                        aposta.setStatus("PERDEU");
                    }
                } else{
                    if (result.equalsIgnoreCase("VITORIA_VISITANTE")){
                        aposta.setStatus("GANHOU");
                    } else{
                        aposta.setStatus("PERDEU");
                    }
                }
            } else{
                throw new RuntimeException("Partida não acabou");
            }
        }else{
            throw new RuntimeException("Partida não encontrada");
        }

        return aposta;
    }

}
