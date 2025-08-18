package com.batalha;

import java.util.ArrayList;
import java.util.List;

public class Jogo {

    private List<Jogador> jogadores;

    /**
     * Construtor do Jogo.
     * Inicializa a lista e adiciona os dois jogadores, um de cada classe.
     */
    public Jogo() {
        jogadores = new ArrayList<>();

        // Adiciona um guerreiro e um mago, conforme as imagens fornecidas
        jogadores.add(new Jogador("guerreiro"));
        jogadores.add(new Jogador("mago"));
    }

    /**
     * Retorna a lista de jogadores da partida.
     * @return uma List<Jogador>
     */
    public List<Jogador> getJogadores() {
        return jogadores;
    }
}