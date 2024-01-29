package br.com.student.adatech.projetos.models;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Classe que representa uma agenda telefônica.
 * Contém métodos para manipular contatos dentro da agenda.
 */
public class Agenda {

    private static final List<Contato> agenda = new ArrayList<>();

    /**
     * Construtor para criar uma nova agenda.
     */
    public Agenda() {}

    /**
     * Obtém um contato pelo seu ID.
     *
     * @param id O ID do contato.
     * @return Retorna o contato encontrado ou lança uma exceção se não encontrado.
     * @throws NoSuchElementException se o contato com o ID fornecido não for encontrado.
     */
    public static Contato getContatoPorId(Long id) throws NoSuchElementException {
        for (Contato contato : agenda) {
            if (contato.getId().equals(id)) {
                return contato;
            }
        }
        throw new NoSuchElementException("Contato com ID " + id + " não encontrado.");
    }

    /**
     * Retorna a lista de contatos na agenda.
     *
     * @return A lista de contatos.
     */
    public static List<Contato> getAgenda() {
        return agenda;
    }

    /**
     * Exibe todos os contatos na agenda.
     * Se a agenda estiver vazia, exibe uma mensagem informando isso.
     */
    public static void exibirContatos() {
        if (agenda.isEmpty()) {
            System.out.println("Lista de contatos vazia!\n");
            return;
        }

        System.out.println(">>>> Contatos <<<<");
        for (Contato contato : agenda) {
            System.out.printf("%d | %s %s\n", contato.getId(), contato.getNome(), contato.getSobreNome());
        }
        System.out.println();
    }
}