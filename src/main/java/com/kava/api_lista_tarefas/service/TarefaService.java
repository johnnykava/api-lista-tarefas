package com.kava.api_lista_tarefas.service;

import com.kava.api_lista_tarefas.exception.ResourceNotFoundException;
import com.kava.api_lista_tarefas.model.Tarefa;
import com.kava.api_lista_tarefas.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TarefaService {
    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository){
        this.tarefaRepository = tarefaRepository;
    }

    public Tarefa criarTarefa(Tarefa tarefa){
        return tarefaRepository.save(tarefa);
    }

    public List<Tarefa> listarTarefas(){
        return tarefaRepository.findAll();
    }

    public Optional<Tarefa> buscarPorId(Long id){
        return tarefaRepository.findById(id);
    }

    public Tarefa atualizarTarefa(Long id, Tarefa tarefa){
        Optional<Tarefa> caixa = buscarPorId(id);

        Tarefa tarefaAntiga;

        if(caixa.isPresent()){
            tarefaAntiga = caixa.get();
        }
        else{
            throw new ResourceNotFoundException("Tarefa com ID " + id + " não encontrada.");
        }

        tarefaAntiga.setTitulo(tarefa.getTitulo());
        tarefaAntiga.setDescricao(tarefa.getDescricao()); // Correção
        tarefaAntiga.setConcluida(tarefa.isConcluida());   // Adição


        return tarefaRepository.save(tarefaAntiga);
    }

    public void deletarTarefa(Long id){
        if(!tarefaRepository.existsById(id)){
            throw new ResourceNotFoundException("Tarefa com ID " + id + " não encontrada.");
        }

        tarefaRepository.deleteById(id);
    }
}
