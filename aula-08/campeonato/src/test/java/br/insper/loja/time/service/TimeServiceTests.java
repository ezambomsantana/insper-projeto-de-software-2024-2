package br.insper.loja.time.service;

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


}
