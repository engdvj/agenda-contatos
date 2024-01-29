package br.com.student.adatech.projetos.models;

import br.com.student.adatech.projetos.menu.Menu;
import br.com.student.adatech.projetos.arquive.Arquivo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Agenda {

    //Variaveis
    private static List<Contato> agenda = new ArrayList<>();

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

    //Outros métodos
    public static void adicionarContato() {
        List<Telefone> telefones = new ArrayList<>();

        System.out.println("Digite o nome do contato:");
        String nome = Menu.sc.nextLine();
        System.out.println("Digite o sobrenome do contato:");
        String sobreNome = Menu.sc.nextLine();

        coletarTelefones(telefones);

        Contato novoContato = new Contato(nome, sobreNome, telefones);

        Agenda.agenda.add(novoContato);
        Arquivo.adicionarContato(novoContato);
    }
    public static void adicionarContato(Contato contato) {
        agenda.add(contato);
    }
    public static void editarContato() {
        if (Agenda.agenda.isEmpty()) {
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
        if (Agenda.agenda.isEmpty()) {
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
                for (int i = 0; i < Agenda.agenda.size(); i++) {
                    Contato contato = Agenda.agenda.get(i);
                    if (contato.getId().equals(id)) {
                        Agenda.agenda.remove(i);
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
    public static boolean verificarTelefones(Telefone novoTelefone, List<Telefone> telefonesAdicionais) {
        List<Contato> contatos = Agenda.agenda;
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
    public static void adicionarNovoTelefone(Scanner sc, Contato contato) {
        System.out.println("Digite o DDD do novo telefone:");
        String ddd = sc.nextLine();
        System.out.println("Digite o número do novo telefone:");
        Long numero = sc.nextLong();
        sc.nextLine();

        Telefone novoTelefone = new Telefone(ddd, numero);
        contato.getTelefones().add(novoTelefone);

        // Adicionar o novo telefone ao arquivo
        Arquivo.adicionarTelefone(contato.getId(), novoTelefone);
    }
    public static void editarTelefoneExistente(Scanner sc, Contato contato) {
        List<Telefone> telefones = contato.getTelefones();

        if (telefones.isEmpty()) {
            System.out.println("Não há telefones para editar.");
            return;
        }

        contato.exibirTelefonesDoContato();

        int indice;
        do {
            System.out.println("Escolha o número do telefone para editar (1, 2, ...):");
            indice = sc.nextInt() - 1;
            sc.nextLine();
            if (indice < 0 || indice >= telefones.size()) {
                System.out.println("Índice inválido.");
            }
        } while (indice < 0 || indice >= telefones.size());

        System.out.println("Digite o novo DDD:");
        String novoDdd = sc.nextLine();
        System.out.println("Digite o novo número de telefone:");
        Long novoNumero = sc.nextLong();
        sc.nextLine();

        Telefone telefoneAtualizado = telefones.get(indice);

        telefoneAtualizado.setDdd(novoDdd);
        telefoneAtualizado.setNumero(novoNumero);

        Arquivo.atualizarTelefone(contato.getId(), telefoneAtualizado);
        System.out.println("Telefone atualizado com sucesso.");

    }
    public static void apagarTelefoneExistente(Scanner sc, Contato contato) {
        List<Telefone> telefones = contato.getTelefones();

        if (telefones.isEmpty()) {
            System.out.println("Não há telefones para apagar.");
            return;
        }

        contato.exibirTelefonesDoContato();
        int indice;
        do {
            System.out.println("Escolha o número do telefone para apagar (1, 2, ...):");
            indice = sc.nextInt() - 1;
            sc.nextLine();
            if (indice < 0 || indice >= telefones.size()) {
                System.out.println("Índice inválido.");
            }
        } while (indice < 0 || indice >= telefones.size());

        Telefone telefoneRemovido = telefones.remove(indice);
        Arquivo.removerTelefone(contato.getId(), telefoneRemovido);
        System.out.println("Telefone removido com sucesso.");
    }
    private static void coletarTelefones(List<Telefone> telefones) {
        boolean continuarColetando = true;

        do {
            System.out.println("Digite o DDD do telefone:");
            String ddd = Menu.sc.nextLine();
            System.out.println("Digite o número do telefone:");
            Long numero = Menu.sc.nextLong();
            Menu.sc.nextLine();

            Telefone novoTelefone = new Telefone(ddd, numero);

            // Verifica se o telefone já existe na agenda ou na lista atual
            if (verificaTelefone(telefones, novoTelefone)) continue; // Pula para a próxima iteração do loop

            telefones.add(novoTelefone);
            System.out.println("Telefone adicionado com sucesso.\n");

            System.out.println("Deseja adicionar outro número? (sim/não)");
            String resposta = Menu.sc.nextLine();

            if (!resposta.equalsIgnoreCase("sim")) {
                continuarColetando = false;
            }
        } while (continuarColetando);
    }
    private static boolean verificaTelefone(List<Telefone> telefones, Telefone novoTelefone) {
        if (Agenda.verificarTelefones(novoTelefone, telefones)) {
            System.out.println("Este telefone já está cadastrado. Por favor, insira um número diferente.");
            return true;
        }
        return false;
    }
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