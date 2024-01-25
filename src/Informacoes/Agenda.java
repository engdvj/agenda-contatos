package Informacoes;

import java.util.ArrayList;
import java.util.List;

public class Agenda {

    //Variaveis
    private final List<Contato> contatos;

    //Construtor
    public Agenda() {
        this.contatos = new ArrayList<>();
    }

    //Getters
    public List<Contato> getAgenda() {
        return new ArrayList<>(contatos);
    }
    public Contato getContatoPorId(Long id) {
        for (Contato contato : this.contatos) {
            if (contato.getId().equals(id)) {
                return contato;
            }
        }
        System.out.println("Contato não encontrado.");
        return null;
    }

    //Outros métodos
    public void addContato(Contato novoContato) {
        contatos.add(novoContato);
    }
    public void removerContato(Long id) {
        for (int i = 0; i < contatos.size(); i++) {
            Contato contato = contatos.get(i);
            if (contato.getId().equals(id)) {
                contatos.remove(i);
                return;
            }
        }
    }
    public boolean verificarTelefones(Telefone novoTelefone, List<Telefone> telefonesAdicionais) {
        List<Contato> contatos = this.contatos;
        for (Contato contato : contatos) {
            for (Telefone telefone : contato.getTelefones()) {
                if (telefone.equals(novoTelefone)) {
                    return true;
                }
            }
        }
        for (Telefone tel : telefonesAdicionais) {
            if (tel.equals(novoTelefone)) {
                return true;
            }
        }

        return false;
    }

}