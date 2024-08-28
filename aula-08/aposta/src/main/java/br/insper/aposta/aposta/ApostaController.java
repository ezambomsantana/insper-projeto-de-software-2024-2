package br.insper.aposta.aposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aposta")
public class ApostaController {

    @Autowired
    private ApostaService apostaService;

    @GetMapping
    public List<Aposta> listar() {
        return apostaService.listar();
    }

    @PostMapping
    public void salvar(@RequestBody Aposta aposta) {
        apostaService.salvar(aposta);
    }

    @GetMapping("/{idAposta}")
    public Aposta getAposta(String idAposta) {
        return apostaService.getAposta(idAposta);
    }
}
