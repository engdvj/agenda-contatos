package br.com.student.adatech.projetos.models;

import java.util.ArrayList;
import java.util.List;

public class Agenda {

    //Variaveis
    private static final List<Contato> agenda = new ArrayList<>();

    //Construtor
    public Agenda() {}

    //Getters
    public static Contato getContatoPorId(Long id) {
        for (Contato contato : agenda) {
            if (contato.getId().equals(id)) {
                return contato;
            }
        }
        System.out.println("Contato não encontrado.");
        return null;
    }
    public static List<Contato> getAgenda() {return agenda; }

    //Outros métodos
    public static void exibirContatos() {
        // Exibe os contatos da agenda
        List<Contato> contatos = Agenda.agenda;
        if (contatos.isEmpty()) {
            System.out.println("Lista vazia 💤\n");
        } else {
            System.out.println(">>>> Contatos <<<<");
            for (Contato contato : contatos) {
                System.out.println(contato.getId() + " | " + contato.getNome() + " " + contato.getSobreNome());
            }
            System.out.println();
        }
    }
}