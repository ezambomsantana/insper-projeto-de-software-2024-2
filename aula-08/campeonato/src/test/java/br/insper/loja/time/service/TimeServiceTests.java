package br.insper.loja.time.service;

import br.insper.loja.time.exception.TimeNaoEncontradoException;
import br.insper.loja.time.model.Time;
import br.insper.loja.time.repository.TimeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TimeServiceTests {


    @InjectMocks
    private TimeService timeService;

    @Mock
    private TimeRepository timeRepository;

    @Test
    public void testListarTimesWhenEstadoIsNull() {

        // preparacao
        Mockito.when(timeRepository.findAll()).thenReturn(new ArrayList<>());

        // chamada do codigo testado
        List<Time> times = timeService.listarTimes(null);

        // verificacao dos resultados
        Assertions.assertTrue(times.isEmpty());
    }

    @Test
    public void testListarTimesWhenEstadoIsNotNull() {

        List<Time> lista = new ArrayList<>();

        Time time = new Time();
        time.setEstado("SP");
        time.setIdentificador("time-1");
        lista.add(time);

        // preparacao
        Mockito.when(timeRepository.findByEstado("SP")).thenReturn(lista);

        // chamada do codigo testado
        List<Time> times = timeService.listarTimes("SP");

        // verificacao dos resultados
        Assertions.assertTrue(times.size() == 1);
        Assertions.assertEquals("SP", times.getFirst().getEstado());
        Assertions.assertEquals("time-1", times.getFirst().getIdentificador());
    }

    @Test
    public void testGetTimesWhenTimeIsNotNull() {

        Time time = new Time();
        time.setEstado("SP");
        time.setIdentificador("time-1");

        // preparacao
        Mockito.when(timeRepository.findById(1)).thenReturn(java.util.Optional.of(time));

        Time timeretorno = timeService.getTime(1);

        Assertions.assertNotNull(timeretorno);
        Assertions.assertEquals("SP", timeretorno.getEstado());
        Assertions.assertEquals("time-1", timeretorno.getIdentificador());

    }

    @Test
    public void testGetTimesWhenTimeIsNull() {

        // preparacao
        Mockito.when(timeRepository.findById(1)).thenReturn(java.util.Optional.empty());

        Assertions.assertThrows(TimeNaoEncontradoException.class, () -> {
            timeService.getTime(1);
        });


    }

    @Test
    public void testCadastrarTimeWithValidData() {
        Time time = new Time();
        time.setNome("Valid Name");
        time.setIdentificador("valid-id");

        // Preparation
        Mockito.when(timeRepository.save(time)).thenReturn(time);

        // Call the method being tested
        Time savedTime = timeService.cadastrarTime(time);

        // Verify the results
        Assertions.assertNotNull(savedTime);
        Assertions.assertEquals("Valid Name", savedTime.getNome());
        Assertions.assertEquals("valid-id", savedTime.getIdentificador());
    }

    @Test
    public void testCadastrarTimeWithEmptyNome() {
        Time time = new Time();
        time.setNome("");
        time.setIdentificador("valid-id");

        // Verify that an exception is thrown
        Assertions.assertThrows(RuntimeException.class, () -> {
            timeService.cadastrarTime(time);
        });
    }

    @Test
    public void testCadastrarTimeWithEmptyIdentificador() {
        Time time = new Time();
        time.setNome("Valid Name");
        time.setIdentificador("");

        // Verify that an exception is thrown
        Assertions.assertThrows(RuntimeException.class, () -> {
            timeService.cadastrarTime(time);
        });
    }


}
