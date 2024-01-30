package br.com.student.adatech.projetos.models;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Classe que representa um telefone na agenda telefônica.
 * Cada telefone tem um identificador único, DDD e número.
 */
public class Telefone {

    private static AtomicLong TELEFONE_ID_GENERATOR;

    private final Long id;
    private String ddd;
    private Long numero;

    /**
     * Construtor para criar um novo telefone com um ID gerado automaticamente.
     *
     * @param ddd    O DDD do telefone.
     * @param numero O número do telefone.
     */
    public Telefone(String ddd, Long numero) {
        validarDados(ddd, numero);
        this.id = TELEFONE_ID_GENERATOR.incrementAndGet();
        this.ddd = ddd;
        this.numero = numero;
    }

    /**
     * Construtor para criar um telefone com um ID específico.
     * Útil para recriar telefones a partir de dados armazenados.
     *
     * @param id     O ID do telefone.
     * @param ddd    O DDD do telefone.
     * @param numero O número do telefone.
     */
    public Telefone(Long id, String ddd, Long numero) {
        this.id = id;
        this.ddd = ddd;
        this.numero = numero;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }
    public String getDdd() {
        return ddd;
    }
    public Long getNumero() {
        return numero;
    }
    public void setDdd(String ddd) {
        this.ddd = ddd;
    }
    public void setNumero(Long numero) {
        this.numero = numero;
    }

    /**
     * Configura o gerador de ID para um valor inicial específico.
     * Útil para configurar o estado inicial em situações como carregamento de dados.
     *
     * @param ultimoId O último ID a ser configurado no gerador.
     */
    public static void setIDGenerator(Long ultimoId) {
        TELEFONE_ID_GENERATOR = new AtomicLong(ultimoId);
    }

    public static void decrementarId() {
        TELEFONE_ID_GENERATOR.getAndUpdate(currentId -> currentId > 0 ? currentId - 1 : 0);
    }

    /**
     * Verifica a validade do DDD e do número do telefone.
     * Lança IllegalArgumentException se o DDD for nulo/vazio ou o número for nulo.
     *
     * @param ddd    O DDD do telefone.
     * @param numero O número do telefone.
     * @throws IllegalArgumentException Se os dados forem inválidos.
     */
    private void validarDados(String ddd, Long numero) {
        if (ddd == null || ddd.trim().isEmpty()) {
            throw new IllegalArgumentException("DDD não pode ser nulo ou vazio.");
        }
        if (numero == null) {
            throw new IllegalArgumentException("Número não pode ser nulo.");
        }
    }
    /**
     * Método para comparar este Telefone com outro objeto para igualdade.
     *
     * @param obj O objeto a ser comparado.
     * @return true se os objetos são iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Telefone telefone = (Telefone) obj;
        return ddd != null && numero != null && ddd.equals(telefone.ddd) && numero.equals(telefone.numero);
    }

    /**
     * Retorna a representação em String deste Telefone.
     *
     * @return A representação em String do Telefone.
     */
    @Override
    public String toString() {
        return "(" + ddd + ") " + numero;
    }

}