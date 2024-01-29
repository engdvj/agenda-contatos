package br.com.student.adatech.projetos.operacoes;

import br.com.student.adatech.projetos.models.Agenda;
import br.com.student.adatech.projetos.models.Contato;
import br.com.student.adatech.projetos.models.Telefone;
import br.com.student.adatech.projetos.arquive.Arquivo;
import br.com.student.adatech.projetos.menu.Menu;
import br.com.student.adatech.projetos.util.Util;


import java.util.ArrayList;
import java.util.List;

public class OperacoesContato {

    public static void adicionarContato() {
        List<Telefone> telefones = new ArrayList<>();

        System.out.println("Digite o nome do contato:");
        String nome = Menu.sc.nextLine();
        System.out.println("Digite o sobrenome do contato:");
        String sobreNome = Menu.sc.nextLine();

        Util.coletarTelefones(telefones);

        Contato novoContato = new Contato(nome, sobreNome, telefones);

        Agenda.getAgenda().add(novoContato);
        Arquivo.adicionarContato(novoContato);
    }
    public static void adicionarContato(Contato contato) {
        Agenda.getAgenda().add(contato);
    }
    public static void editarContato() {
        if (Agenda.getAgenda().isEmpty()) {
            System.out.println("Não existem contatos para editar!");
            return;
        }

        long id;
        Contato contato;
        do {
            System.out.println("Digite o ID do contato a ser editado:");
            id = Menu.sc.nextLong();
            contato = Agenda.getContatoPorId(id);

            if (contato == null) {
                System.out.println("Contato não encontrado. Por favor, tente novamente.");
            }
        } while (contato == null);

        int opcao = Menu.editarContato();

        switch (opcao) {
            case 1:
                System.out.println("Digite o novo nome:");
                String novoNome = Menu.sc.nextLine();
                contato.setNome(novoNome);
                Arquivo.atualizarContato(contato);
                break;
            case 2:
                System.out.println("Digite o novo sobrenome:");
                String novoSobrenome = Menu.sc.nextLine();
                contato.setSobreNome(novoSobrenome);
                Arquivo.atualizarContato(contato);
                break;
            case 3:
                Menu.editarTelefoneDoContato(contato);
                break;
            default:
                System.out.println("Opção inválida.");
                break;
        }
    }
    public static void removerContato() {
        if (Agenda.getAgenda().isEmpty()) {
            System.out.println("Não existem contatos para remover!");
            return;
        }

        Long id;
        boolean contatoRemovido = false;
        do {
            System.out.println("Digite o ID do contato a ser removido:");
            id = Menu.sc.nextLong();

            // Primeiro verificar se o contato existe na Agenda
            if (Agenda.getContatoPorId(id) != null) {
                Arquivo.removerContato(id); // Chamada ao método de Arquivo
                for (int i = 0; i < Agenda.getAgenda().size(); i++) {
                    Contato contato = Agenda.getAgenda().get(i);
                    if (contato.getId().equals(id)) {
                        Agenda.getAgenda().remove(i);
                        break;
                    }
                }
                contatoRemovido = true;
            } else {
                System.out.println("Contato com ID " + id + " não encontrado. Por favor, tente novamente.");
            }
        } while (!contatoRemovido);
        System.out.println("Contato removido com sucesso.");

    }
    public static void adicionarNovoTelefone(Contato contato) {
        System.out.println("Digite o DDD do novo telefone:");
        String ddd = Menu.sc.nextLine();
        System.out.println("Digite o número do novo telefone:");
        Long numero = Menu.sc.nextLong();
        Menu.sc.nextLine();

        Telefone novoTelefone = new Telefone(ddd, numero);
        contato.getTelefones().add(novoTelefone);

        // Adicionar o novo telefone ao arquivo
        Arquivo.adicionarTelefone(contato.getId(), novoTelefone);
    }
    public static void editarTelefoneExistente(Contato contato) {
        List<Telefone> telefones = contato.getTelefones();

        if (telefones.isEmpty()) {
            System.out.println("Não há telefones para editar.");
            return;
        }

        contato.exibirTelefonesDoContato();

        int indice;
        do {
            System.out.println("Escolha o número do telefone para editar (1, 2, ...):");
            indice = Menu.sc.nextInt() - 1;
            Menu.sc.nextLine();
            if (indice < 0 || indice >= telefones.size()) {
                System.out.println("Índice inválido.");
            }
        } while (indice < 0 || indice >= telefones.size());

        System.out.println("Digite o novo DDD:");
        String novoDdd = Menu.sc.nextLine();
        System.out.println("Digite o novo número de telefone:");
        Long novoNumero = Menu.sc.nextLong();
        Menu.sc.nextLine();

        Telefone telefoneAtualizado = telefones.get(indice);

        telefoneAtualizado.setDdd(novoDdd);
        telefoneAtualizado.setNumero(novoNumero);

        Arquivo.atualizarTelefone(contato.getId(), telefoneAtualizado);
        System.out.println("Telefone atualizado com sucesso.");

    }
    public static void apagarTelefoneExistente(Contato contato) {
        List<Telefone> telefones = contato.getTelefones();

        if (telefones.isEmpty()) {
            System.out.println("Não há telefones para apagar.");
            return;
        }

        contato.exibirTelefonesDoContato();
        int indice;
        do {
            System.out.println("Escolha o número do telefone para apagar (1, 2, ...):");
            indice = Menu.sc.nextInt() - 1;
            Menu.sc.nextLine();
            if (indice < 0 || indice >= telefones.size()) {
                System.out.println("Índice inválido.");
            }
        } while (indice < 0 || indice >= telefones.size());

        Telefone telefoneRemovido = telefones.remove(indice);
        Arquivo.removerTelefone(contato.getId(), telefoneRemovido);
        System.out.println("Telefone removido com sucesso.");
    }

}