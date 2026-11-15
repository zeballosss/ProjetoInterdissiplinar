package br.com.etecfer.enciclopediacopa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "jogadores")
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jogador_seq_generator")
    @SequenceGenerator(name = "jogador_seq_generator", sequenceName = "jogador_seq", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Informe o nome do jogador.")
    @Size(max = 100, message = "O nome deve ter no maximo 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "Informe a selecao do jogador.")
    @Size(max = 80, message = "A selecao deve ter no maximo 80 caracteres.")
    @Column(nullable = false, length = 80)
    private String selecao;

    @NotBlank(message = "Informe a posicao do jogador.")
    @Size(max = 60, message = "A posicao deve ter no maximo 60 caracteres.")
    @Column(nullable = false, length = 60)
    private String posicao;

    private Integer numeroCamisa;

    @Size(max = 100, message = "O clube deve ter no maximo 100 caracteres.")
    @Column(length = 100)
    private String clube;

    @Size(max = 1200, message = "A biografia deve ter no maximo 1200 caracteres.")
    @Column(columnDefinition = "TEXT")
    private String biografia;

    @Size(max = 500, message = "O endereco da imagem deve ter no maximo 500 caracteres.")
    @Column(length = 500)
    private String imagemUrl;

    public Jogador() {
    }

    public Jogador(String nome, String selecao, String posicao, Integer numeroCamisa,
                   String clube, String biografia) {
        this.nome = nome;
        this.selecao = selecao;
        this.posicao = posicao;
        this.numeroCamisa = numeroCamisa;
        this.clube = clube;
        this.biografia = biografia;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSelecao() {
        return selecao;
    }

    public void setSelecao(String selecao) {
        this.selecao = selecao;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public Integer getNumeroCamisa() {
        return numeroCamisa;
    }

    public void setNumeroCamisa(Integer numeroCamisa) {
        this.numeroCamisa = numeroCamisa;
    }

    public String getClube() {
        return clube;
    }

    public void setClube(String clube) {
        this.clube = clube;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }
}
