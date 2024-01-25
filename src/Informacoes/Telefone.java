package Informacoes;
import Arquivo.Arquivo;

import java.util.concurrent.atomic.AtomicLong;

public class Telefone {

    //Variaveis
    private static AtomicLong TELEFONE_ID_GENERATOR;
    private final Long id;
    private String ddd;
    private Long numero;

    //Construtores
    public Telefone(String ddd, Long numero) {
        if (TELEFONE_ID_GENERATOR == null) {
            Long ultimoId = Arquivo.lerUltimoIdTelefone();
            TELEFONE_ID_GENERATOR = new AtomicLong(ultimoId);
        }
        this.id = TELEFONE_ID_GENERATOR.incrementAndGet();
        this.ddd = ddd;
        this.numero = numero;
    }
    public Telefone(Long id, String ddd, Long numero) {
        this.id = id;
        this.ddd = ddd;
        this.numero = numero;
    }

    // Getters
    public Long getId() {
        return id;
    }
    public String getDdd() {
        return ddd;
    }
    public Long getNumero() {
        return numero;
    }

    // Setters
    public void setDdd(String ddd) {
        this.ddd = ddd;
    }
    public void setNumero(Long numero) {
        this.numero = numero;
    }

    // Outros métodos
    public static void setIDGenerator(Long ultimoId) {
        TELEFONE_ID_GENERATOR = new AtomicLong(ultimoId);
    }

    // Reescrita
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Telefone telefone = (Telefone) obj;
        return ddd.equals(telefone.ddd) && numero.equals(telefone.numero);
    }
    @Override
    public String toString() {
        return "(" + ddd + ") " + numero;
    }


}