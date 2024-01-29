package br.com.student.adatech.projetos.operacoes;

import br.com.student.adatech.projetos.models.Agenda;
import br.com.student.adatech.projetos.models.Contato;
import br.com.student.adatech.projetos.models.Telefone;
import br.com.student.adatech.projetos.arquive.Arquivo;
import br.com.student.adatech.projetos.menu.Menu;
import br.com.student.adatech.projetos.util.Util;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Classe que representa as operações realizadas nos contatos telefônicos.
 * Contém métodos para manipular os contatos dentro da agenda.
 */
public class OperacoesContato {

    /**
     * Adiciona um novo contato à agenda.
     * Coleta informações do usuário, incluindo nome, sobrenome e telefones,
     * e cria um novo contato que é adicionado à agenda e ao arquivo.
     */

    public static void adicionarContato() {
        List<Telefone> telefones = new ArrayList<>();

        System.out.println("Digite o nome do contato:");
        String nome = Menu.scanner.nextLine();
        while (nome.isEmpty()) {
            System.out.println("O nome não pode ser vazio. Por favor, digite o nome do contato:");
            nome = Menu.scanner.nextLine();
        }

        System.out.println("Digite o sobrenome do contato:");
        String sobreNome = Menu.scanner.nextLine();
        while (sobreNome.isEmpty()) {
            System.out.println("O sobrenome não pode ser vazio. Por favor, digite o sobrenome do contato:");
            sobreNome = Menu.scanner.nextLine();
        }

        Util.coletarTelefones(telefones);

        try {
            Contato novoContato = new Contato(nome, sobreNome, telefones);
            Agenda.getAgenda().add(novoContato);
            Arquivo.adicionarContato(novoContato);
        } catch (Exception e) {
            System.out.println("Erro ao adicionar contato: " + e.getMessage());
        }
    }
    public static void adicionarContato(Contato contato) {
        Agenda.getAgenda().add(contato);
    }

    /**
     * Permite editar um contato existente na agenda.
     * O usuário pode alterar o nome, sobrenome ou telefones do contato.
     */
    public static void editarContato() {
        if (Agenda.getAgenda().isEmpty()) {
            System.out.println("Não existem contatos para editar!");
            return;
        }

        Contato contato;
        long id;
        do {
            System.out.println("Digite o ID do contato a ser editado:");
            while (!Menu.scanner.hasNextLong()) {
                System.out.println("Por favor, digite um número válido para o ID.");
                Menu.scanner.next(); // Descarta a entrada incorreta
            }
            id = Menu.scanner.nextLong();
            contato = Agenda.getContatoPorId(id);

            if (contato == null) {
                System.out.println("Contato não encontrado. Por favor, tente novamente.");
            }
        } while (contato == null);

        int opcao = Menu.editarContato();
        switch (opcao) {
            case 1:
                System.out.println("Digite o novo nome:");
                contato.setNome(Menu.scanner.nextLine());
                break;
            case 2:
                System.out.println("Digite o novo sobrenome:");
                contato.setSobreNome(Menu.scanner.nextLine());
                break;
            case 3:
                Menu.editarTelefoneDoContato(contato);
                break;
            default:
                System.out.println("Opção inválida.");
                break;
        }

        Arquivo.atualizarContato(contato);
    }

    /**
     * Permite remover um contato existente na agenda, com todas as informações do mesmo.
     */
    public static void removerContato() {
        if (Agenda.getAgenda().isEmpty()) {
            System.out.println("Não existem contatos para remover!");
            return;
        }

        Long id;
        boolean contatoRemovido = false;
        do {
            System.out.println("Digite o ID do contato a ser removido:");
            while (!Menu.scanner.hasNextLong()) {
                System.out.println("Por favor, insira um número válido para o ID.");
                Menu.scanner.next(); // Descarta entrada incorreta
            }
            id = Menu.scanner.nextLong();

            Contato contato = Agenda.getContatoPorId(id);
            if (contato != null) {
                Arquivo.removerContato(id);
                Agenda.getAgenda().remove(contato);
                contatoRemovido = true;
                System.out.println("Contato removido com sucesso.");
            } else {
                System.out.println("Contato com ID " + id + " não encontrado. Por favor, tente novamente.");
            }
        } while (!contatoRemovido);
    }

    /**
     * Permite adicionar um novo telefone a um contato existente na agenda.
     */
    public static void adicionarNovoTelefone(Contato contato) {
        try {
            System.out.println("Digite o DDD do novo telefone:");
            String ddd = Menu.scanner.nextLine();
            System.out.println("Digite o número do novo telefone:");

            Long numero;
            while (true) {
                if (Menu.scanner.hasNextLong()) {
                    numero = Menu.scanner.nextLong();
                    Menu.scanner.nextLine(); // Limpa o buffer do scanner
                    break;
                } else {
                    System.out.println("Por favor, digite um número válido.");
                    Menu.scanner.next(); // Descarta entrada incorreta
                }
            }

            Telefone novoTelefone = new Telefone(ddd, numero);
            if (!Util.verificarTelefone(novoTelefone, contato.getTelefones())) {
                contato.getTelefones().add(novoTelefone);
                Arquivo.adicionarTelefone(contato.getId(), novoTelefone);
            } else {
                System.out.println("Telefone já cadastrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao adicionar novo telefone: " + e.getMessage());
        }
    }

    /**
     * Permite editar um telefone existente de um contato da agenda.
     */
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
            while (!Menu.scanner.hasNextInt()) {
                System.out.println("Por favor, insira um número inteiro válido.");
                Menu.scanner.next(); // Descarta entrada incorreta
            }
            indice = Menu.scanner.nextInt() - 1;
            Menu.scanner.nextLine(); // Limpa o buffer do scanner
            if (indice < 0 || indice >= telefones.size()) {
                System.out.println("Índice inválido. Tente novamente.");
            }
        } while (indice < 0 || indice >= telefones.size());

        try {
            System.out.println("Digite o novo DDD:");
            String novoDdd = Menu.scanner.nextLine();
            System.out.println("Digite o novo número de telefone:");
            long novoNumero;
            if (Menu.scanner.hasNextLong()) {
                novoNumero = Menu.scanner.nextLong();
                Menu.scanner.nextLine(); // Limpa o buffer do scanner
            } else {
                throw new InputMismatchException("Número de telefone inválido.");
            }

            Telefone telefoneAtualizado = telefones.get(indice);
            telefoneAtualizado.setDdd(novoDdd);
            telefoneAtualizado.setNumero(novoNumero);

            Arquivo.atualizarTelefone(contato.getId(), telefoneAtualizado);
            System.out.println("Telefone atualizado com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao editar telefone: " + e.getMessage());
        }
    }

    /**
     * Permite apagar um telefone existente de um contato da agenda.
     */
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
            while (!Menu.scanner.hasNextInt()) {
                System.out.println("Por favor, insira um número inteiro válido.");
                Menu.scanner.next(); // Descarta entrada incorreta
            }
            indice = Menu.scanner.nextInt() - 1;
            Menu.scanner.nextLine(); // Limpa o buffer do scanner
            if (indice < 0 || indice >= telefones.size()) {
                System.out.println("Índice inválido. Tente novamente.");
            }
        } while (indice < 0 || indice >= telefones.size());

        try {
            Telefone telefoneRemovido = telefones.remove(indice);
            Arquivo.removerTelefone(contato.getId(), telefoneRemovido);
            System.out.println("Telefone removido com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao remover telefone: " + e.getMessage());
        }
    }

}