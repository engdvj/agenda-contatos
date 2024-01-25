import Informacoes.Agenda;
import Informacoes.Contato;
import Informacoes.Telefone;
import Arquivo.Arquivo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Agenda agenda = new Agenda();

        Arquivo.carregarContatos(agenda);
        escolherAcao(sc, agenda);

        sc.close();
    }
    private static void escolherAcao(Scanner sc, Agenda agenda) {
        int opcao;

        do {
            imprimirMenu(agenda);
            opcao = sc.nextInt();
            sc.nextLine();
            switch (opcao) {
                case 1:
                    adicionarContato(sc, agenda);
                    break;
                case 2:
                    editarContato(sc, agenda);
                    break;
                case 3:
                    removerContato(sc, agenda);
                    break;
                case 4:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 4);
    }
    private static void imprimirMenu(Agenda agenda) {
        System.out.println(""" 
        \n
        ##################
        ##### AGENDA #####
        ##################
        """);

        exibirContatos(agenda);

        System.out.println("""
        >>>> Menu <<<<
        1 - Adicionar Contato
        2 - Editar Contato
        3 - Remover Contato
        4 - Sair
        Escolha uma opção:""");
    }
    private static void exibirContatos(Agenda agenda) {
        // Exibe os contatos da agenda
        List<Contato> contatos = agenda.getAgenda();
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
    private static void adicionarContato(Scanner sc, Agenda agenda) {
        List<Telefone> telefones = new ArrayList<>();

        System.out.println("Digite o nome do contato:");
        String nome = sc.nextLine();
        System.out.println("Digite o sobrenome do contato:");
        String sobreNome = sc.nextLine();

        coletarTelefones(sc, agenda, telefones);

        Contato novoContato = new Contato(nome, sobreNome, telefones);

        agenda.addContato(novoContato);
        Arquivo.adicionarContato(novoContato);
    }
    private static void editarContato(Scanner sc, Agenda agenda) {
        if (agenda.getAgenda().isEmpty()) {
            System.out.println("Não existem contatos para editar!");
            return;
        }

        long id;
        Contato contato;
        do {
            System.out.println("Digite o ID do contato a ser editado:");
            id = sc.nextLong();
            contato = agenda.getContatoPorId(id);

            if (contato == null) {
                System.out.println("Contato não encontrado. Por favor, tente novamente.");
            }
        } while (contato == null);

        System.out.println("""
                        Escolha a informação que gostaria de editar:
                        
                        1. Nome
                        2. Sobrenome
                        3. Telefone
                        
                        Digite sua opção:""");
        int opcao = sc.nextInt();
        sc.nextLine();

        switch (opcao) {
            case 1:
                System.out.println("Digite o novo nome:");
                String novoNome = sc.nextLine();
                contato.setNome(novoNome);
                Arquivo.atualizarContato(contato);
                break;
            case 2:
                System.out.println("Digite o novo sobrenome:");
                String novoSobrenome = sc.nextLine();
                contato.setSobreNome(novoSobrenome);
                Arquivo.atualizarContato(contato);
                break;
            case 3:
                editarTelefoneDoContato(sc, agenda, contato);
                break;
            default:
                System.out.println("Opção inválida.");
                break;
        }
    }
    private static void removerContato(Scanner sc, Agenda agenda) {
        if (agenda.getAgenda().isEmpty()) {
            System.out.println("Não existem contatos para remover!");
            return;
        }

        Long id;
        boolean contatoRemovido = false;
        do {
            System.out.println("Digite o ID do contato a ser removido:");
            id = sc.nextLong();

            // Primeiro verificar se o contato existe na Agenda
            if (agenda.getContatoPorId(id) != null) {
                Arquivo.removerContato(id, agenda); // Chamada ao método de Arquivo
                agenda.removerContato(id);
                System.out.println("Contato removido com sucesso.");
                contatoRemovido = true;
            } else {
                System.out.println("Contato com ID " + id + " não encontrado. Por favor, tente novamente.");
            }
        } while (!contatoRemovido);
    }
    private static void editarTelefoneDoContato(Scanner sc, Agenda agenda, Contato contato) {
        int opcao;
        do {
            System.out.println("Escolha uma ação para o telefone:");
            System.out.println("1 - Adicionar um novo número");
            System.out.println("2 - Editar um número existente");
            System.out.println("3 - Apagar um número existente");
            System.out.println("\nDigite sua opção:");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    adicionarNovoTelefone(sc, agenda, contato);
                    break;
                case 2:
                    editarTelefoneExistente(sc, contato);
                    break;
                case 3:
                    apagarTelefoneExistente(sc, contato);
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida (1, 2 ou 3).");
                    break;
            }
        } while (opcao < 1 || opcao > 3);
    }
    private static void adicionarNovoTelefone(Scanner sc, Agenda agenda, Contato contato) {
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
    private static void editarTelefoneExistente(Scanner sc, Contato contato) {
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

        System.out.println(telefoneAtualizado.getId());

        System.out.println("ID "+ indice + " DDD1 "+ telefoneAtualizado.getDdd() + " NUM1 " + telefoneAtualizado.getNumero());

        telefoneAtualizado.setDdd(novoDdd);
        telefoneAtualizado.setNumero(novoNumero);

        System.out.println("ID "+ indice + " DDD2 "+ telefoneAtualizado.getDdd() + " NUM2 " + telefoneAtualizado.getNumero());


        Arquivo.atualizarTelefone(contato.getId(), telefoneAtualizado);
        System.out.println("Telefone atualizado com sucesso.");

    }
    private static void apagarTelefoneExistente(Scanner sc, Contato contato) {
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
    private static void coletarTelefones(Scanner sc, Agenda agenda, List<Telefone> telefones) {
        boolean continuarColetando = true;

        do {
            System.out.println("Digite o DDD do telefone:");
            String ddd = sc.nextLine();
            System.out.println("Digite o número do telefone:");
            Long numero = sc.nextLong();
            sc.nextLine();

            Telefone novoTelefone = new Telefone(ddd, numero);

            // Verifica se o telefone já existe na agenda ou na lista atual
            if (verificaTelefone(agenda, telefones, novoTelefone)) continue; // Pula para a próxima iteração do loop

            telefones.add(novoTelefone);
            System.out.println("Telefone adicionado com sucesso.\n");

            System.out.println("Deseja adicionar outro número? (sim/não)");
            String resposta = sc.nextLine();

            if (!resposta.equalsIgnoreCase("sim")) {
                continuarColetando = false;
            }
        } while (continuarColetando);
    }
    private static boolean verificaTelefone(Agenda agenda, List<Telefone> telefones, Telefone novoTelefone) {
        if (agenda.verificarTelefones(novoTelefone, telefones)) {
            System.out.println("Este telefone já está cadastrado. Por favor, insira um número diferente.");
            return true;
        }
        return false;
    }
}
