package com.batalha;

import java.util.Random;

public class Jogador {

    // Constantes para balanceamento do jogo
    private static final int HP_INICIAL = 100;
    private static final int DANO_ATAQUE = 12;
    private static final int PONTOS_CURA = 10;
    private static final int GANHO_XP_POR_ACAO = 5;
    private static final double CHANCE_ENVENENAR = 0.3; // 30% de chance

    private String classe;
    private int hp;
    private int xp;
    private boolean envenenado;
    private boolean temAntidoto;
    private Random random = new Random();

    public Jogador(String classe) {
        this.classe = classe;
        this.hp = HP_INICIAL;
        this.xp = 0;
        this.envenenado = false;
        this.temAntidoto = true; // Cada jogador começa com um antídoto
    }

    // --- AÇÕES DO JOGADOR ---

    /**
     * Ataca outro jogador.
     * Causa dano, concede XP e tem chance de envenenar o alvo.
     * @param alvo O jogador que receberá o ataque.
     */
    public void atacar(Jogador alvo) {
        if (this.hp <= 0) return; // Jogador morto não pode atacar

        int danoReal = DANO_ATAQUE + (xp / 10); // Dano aumenta um pouco com XP
        alvo.receberDano(danoReal);
        this.ganharXp();

        // Chance de envenenar o alvo
        if (random.nextDouble() < CHANCE_ENVENENAR) {
            alvo.setEnvenenado(true);
        }
    }

    /**
     * Cura outro jogador.
     * Restaura HP e concede XP.
     * @param alvo O jogador que será curado.
     */
    public void curar(Jogador alvo) {
        if (this.hp <= 0) return; // Jogador morto não pode curar

        alvo.receberCura(PONTOS_CURA);
        this.ganharXp();
    }

    /**
     * Usa seu antídoto em outro jogador para remover o veneno.
     * @param alvo O jogador que será curado do veneno.
     */
    public void darAntidoto(Jogador alvo) {
        if (this.hp <= 0 || !this.temAntidoto) return;

        alvo.setEnvenenado(false);
        this.temAntidoto = false; // Perde o antídoto após o uso
        this.ganharXp();
    }


    // --- MÉTODOS INTERNOS ---

    private void receberDano(int quantidade) {
        if (this.hp > 0) { // Só recebe dano se estiver vivo
            this.hp -= quantidade;
            if (this.hp < 0) {
                this.hp = 0;
            }
        }
    }

    private void receberCura(int quantidade) {
        if (this.hp > 0) { // Só pode curar se estiver vivo
            this.hp += quantidade;
            if (this.hp > HP_INICIAL) {
                this.hp = HP_INICIAL; // Não pode ter mais vida que o inicial
            }
        }
    }

    private void ganharXp() {
        this.xp += GANHO_XP_POR_ACAO;
    }

    // --- GETTERS E SETTERS USADOS PELO CONTROLLER ---

    public int getHp() {
        return hp;
    }

    public int getXp() {
        return xp;
    }

    public boolean isEnvenenado() {
        return envenenado;
    }

    public void setEnvenenado(boolean envenenado) {
        this.envenenado = envenenado;
    }

    public boolean temAntidoto() {
        return temAntidoto;
    }

    public String getClasse() {
        return classe;
    }
}