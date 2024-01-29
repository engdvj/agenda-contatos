package br.com.student.adatech.projetos.models;

import br.com.student.adatech.projetos.arquive.Arquivo;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Contato {

    //Variaveis
    private static final AtomicLong ID_GENERATOR = new AtomicLong(Arquivo.lerUltimoIdContato());
    private Long id;
    private String nome;
    private String sobreNome;
    private List<Telefone> telefones;

    //Construtor
    public Contato(String nome, String sobreNome, List<Telefone> telefones) {
        this.id = ID_GENERATOR.incrementAndGet();
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.telefones = telefones;
    }
    public Contato(Long id, String nome, String sobreNome, List<Telefone> telefones) {
        this.id = id;
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.telefones = telefones;
    }

    // Getters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getSobreNome() { return sobreNome; }
    public List<Telefone> getTelefones() { return telefones; }

    // Setters
    public void setNome(String nome) { this.nome = nome; }
    public void setSobreNome(String sobreNome) { this.sobreNome = sobreNome; }

    // Outros métodos
    public void exibirTelefonesDoContato() {
        List<Telefone> telefones = this.telefones;
        for (int i = 0; i < telefones.size(); i++) {
            Telefone tel = telefones.get(i);
            System.out.println((i + 1) + ": (" + tel.getDdd() + ") " + tel.getNumero());
        }
    }
    public static void inicializarIdGenerator(long ultimoId) {
        ID_GENERATOR.set(ultimoId);
    }

}