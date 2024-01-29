package br.com.student.adatech.projetos.models;

import br.com.student.adatech.projetos.arquive.Arquivo;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Classe que representa um contato na agenda telefônica.
 * Cada contato tem um identificador único, nome, sobrenome e uma lista de telefones.
 */
public class Contato {

    private static final AtomicLong ID_GENERATOR = new AtomicLong(Arquivo.lerUltimoIdContato());

    private Long id;
    private String nome;
    private String sobreNome;
    private List<Telefone> telefones;

    /**
     * Construtor para criar um novo contato com um ID gerado automaticamente.
     *
     * @param nome       O nome do contato.
     * @param sobreNome  O sobrenome do contato.
     * @param telefones  A lista de telefones do contato.
     */
    public Contato(String nome, String sobreNome, List<Telefone> telefones) {
        this.id = ID_GENERATOR.incrementAndGet();
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.telefones = telefones;
    }

    /**
     * Construtor para criar um contato com um ID específico.
     * Útil para recriar contatos a partir de dados armazenados.
     *
     * @param id         O ID do contato.
     * @param nome       O nome do contato.
     * @param sobreNome  O sobrenome do contato.
     * @param telefones  A lista de telefones do contato.
     */
    public Contato(Long id, String nome, String sobreNome, List<Telefone> telefones) {
        this.id = id;
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.telefones = telefones;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getSobreNome() { return sobreNome; }
    public List<Telefone> getTelefones() { return telefones; }
    public void setNome(String nome) { this.nome = nome; }
    public void setSobreNome(String sobreNome) { this.sobreNome = sobreNome; }

    /**
     * Exibe todos os telefones associados a este contato.
     * Formata a saída para mostrar o índice, o DDD e o número do telefone.
     */
    public void exibirTelefonesDoContato() {
        if (telefones == null || telefones.isEmpty()) {
            System.out.println("Nenhum telefone cadastrado para este contato.");
            return;
        }

        System.out.println("Telefones do Contato:");
        for (int i = 0; i < telefones.size(); i++) {
            Telefone tel = telefones.get(i);
            System.out.printf("%d: (%s) %s%n", i + 1, tel.getDdd(), tel.getNumero());
        }
    }

    /**
     * Inicializa o gerador de ID com um valor específico.
     * Útil para configurar o estado inicial em situações como carregamento de dados.
     *
     * @param ultimoId O último ID a ser configurado no gerador.
     */
    public static void inicializarIdGenerator(long ultimoId) {
        ID_GENERATOR.set(ultimoId);
    }

}