package br.com.challenge.analise.transacoes.financeiras.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="informacoes_importacoes")
public class InfoImportacao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dataTransacao;
    private LocalDateTime dataImportacao;

    @ManyToOne
    private Usuario usuario;

    public InfoImportacao() {
    }

    public InfoImportacao(LocalDate dataTransacao, LocalDateTime dataImportacao, Usuario usuario) {
        this.dataTransacao = dataTransacao;
        this.dataImportacao = dataImportacao;
        this.usuario = usuario;
    }
}
