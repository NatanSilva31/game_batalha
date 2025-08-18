package com.batalha;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Controller implements Initializable {

    @FXML private Label hp1, xp1, envenenado1;
    @FXML private Label hp2, xp2, envenenado2;
    @FXML private ImageView imagem1, imagem2;
    @FXML private Label mensagemVitoria;

    // Apenas uma declaração para cada botão, usando os fx:id que definimos no FXML
    @FXML private Button j1HitJ2, j1HealJ2, j1AntidotoJ2;
    @FXML private Button j2HitJ1, j2HealJ1, j2AntidotoJ1;

    private final Jogo jogo = new Jogo();
    private Jogador j1;
    private Jogador j2;
    private boolean jogoAcabou = false;

    // --- Métodos de Ação ---
    public void j1HitJ2() { j1.atacar(j2); updateView(); }
    public void j1HealJ2() { j1.curar(j2); updateView(); }
    public void j1AntidotoJ2() { j1.darAntidoto(j2); updateView(); }
    public void j2HitJ1() { j2.atacar(j1); updateView(); }
    public void j2HealJ1() { j2.curar(j1); updateView(); }
    public void j2AntidotoJ1() { j2.darAntidoto(j1); updateView(); }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        j1 = jogo.getJogadores().get(0);
        j2 = jogo.getJogadores().get(1);
        updateView();
    }

    private void updateView() {
        if (jogoAcabou) return;

        hp1.setText("HP: " + j1.getHp());
        xp1.setText("XP: " + j1.getXp());
        envenenado1.setText("Env.: " + j1.isEnvenenado());

        hp2.setText("HP: " + j2.getHp());
        xp2.setText("XP: " + j2.getXp());
        envenenado2.setText("Env.: " + j2.isEnvenenado());

        // Usando os nomes corretos dos botões que existem no FXML
        j1AntidotoJ2.setDisable(!j1.temAntidoto());
        j2AntidotoJ1.setDisable(!j2.temAntidoto());

        carregarImagem(j1, imagem1);
        carregarImagem(j2, imagem2);

        verificarCondicaoDeVitoria();
    }

    private void verificarCondicaoDeVitoria() {
        String vencedor = "";

        if (j2.getHp() <= 0) {
            vencedor = j1.getClasse();
        } else if (j1.getHp() <= 0) {
            vencedor = j2.getClasse();
        }

        if (!vencedor.isEmpty()) {
            mensagemVitoria.setText(vencedor.substring(0, 1).toUpperCase() + vencedor.substring(1) + " Venceu!");
            mensagemVitoria.setVisible(true);
            desabilitarAcoes();
            jogoAcabou = true;
        }
    }

    private void desabilitarAcoes() {
        j1HitJ2.setDisable(true);
        j1HealJ2.setDisable(true);
        j1AntidotoJ2.setDisable(true);
        j2HitJ1.setDisable(true);
        j2HealJ1.setDisable(true);
        j2AntidotoJ1.setDisable(true);
    }

    private void carregarImagem(Jogador jogador, ImageView imagem) {
        String estado = "vivo";
        if (jogador.isEnvenenado()) estado = "envenenado";
        if (jogador.getHp() <= 0) estado = "morto";
        String imagePath = "/com/batalha/" + jogador.getClasse() + "-" + estado + ".jpg";
        Image img = new Image(getClass().getResourceAsStream(imagePath));
        imagem.setImage(img);
    }
}