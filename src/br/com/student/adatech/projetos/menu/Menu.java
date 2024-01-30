package br.com.student.adatech.projetos.menu;

import br.com.student.adatech.projetos.models.Agenda;
import br.com.student.adatech.projetos.models.Contato;
import br.com.student.adatech.projetos.arquive.Arquivo;
import br.com.student.adatech.projetos.operacoes.OperacoesContato;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe que representa o menu do programa de agenda telefônica.
 */
public class Menu {

    public static final Scanner scanner = new Scanner(System.in);

    /**
     * Construtor que inicializa e carrega os contatos do arquivo.
     */
    public Menu(){
        Arquivo.carregarContatos();
    }

    /**
     * Inicia o programa exibindo opções de menu e permitindo ao usuário escolher ações.
     */
    public void iniciarPrograma(){
        escolherAcao();
        scanner.close();
    }
    /**
     * Exibe o menu principal e processa a escolha do usuário.
     * Permite a navegação entre as diferentes funcionalidades da agenda.
     */
    private void escolherAcao() {

        int opcao = 0;
        do {
            try {
                mostrarScreen();
                opcao = scanner.nextInt();
                scanner.nextLine();
                    switch (opcao) {
                        case 1:
                            OperacoesContato.adicionarContato();
                            break;
                        case 2:
                            OperacoesContato.editarContato();
                            break;
                        case 3:
                            OperacoesContato.removerContato();
                            break;
                        case 4:
                            System.out.println("Saindo...");
                            break;
                        default:
                            System.out.println("Opção inválida!");
                    }
                }
                catch (InputMismatchException e) {
                    System.out.println("Por favor, insira um número válido.");
                    scanner.nextLine();
                }
            } while (opcao != 4);
        }

    /**
     * Exibe a interface do menu principal e a lista de contatos.
     * Inclui as opções disponíveis para o usuário.
     */
    private void mostrarScreen() {
        System.out.println(""" 
        \n
        ##################
        ##### AGENDA #####
        ##################
        """);

        Agenda.exibirContatos();

        System.out.println("""
        >>>> Menu <<<<
        1 - Adicionar Contato
        2 - Editar Contato
        3 - Remover Contato
        4 - Sair
        Escolha uma opção:""");
    }

    /**
     * Exibe opções para editar as informações de um contato.
     * Retorna a opção escolhida pelo usuário.
     *
     * @return A opção de edição escolhida.
     */
    public static int editarContato() {
        System.out.println("""
                        Escolha a informação que gostaria de editar:

                        1. Nome
                        2. Sobrenome
                        3. Telefone

                        Digite sua opção:""");

        while (true) {
            try {
                int opcao = scanner.nextInt();
                scanner.nextLine();
                return opcao;
            } catch (InputMismatchException e) {
                System.out.println("Por favor, insira um número válido.");
                scanner.nextLine();
            }
        }
    }

    /**
     * Permite ao usuário editar os telefones de um contato específico.
     * Inclui opções para adicionar, editar ou remover números de telefone.
     *
     * @param contato O contato cujos telefones serão editados.
     */
    public static void editarTelefoneDoContato(Contato contato) {
        int opcao;
        do {
            System.out.println("Escolha uma ação para o telefone:");
            System.out.println("1 - Adicionar um novo número");
            System.out.println("2 - Editar um número existente");
            System.out.println("3 - Apagar um número existente");
            System.out.println("\nDigite sua opção:");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        OperacoesContato.adicionarNovoTelefone(contato);
                        break;
                    case 2:
                        OperacoesContato.editarTelefoneExistente(contato);
                        break;
                    case 3:
                        OperacoesContato.apagarTelefoneExistente(contato);
                        break;
                    default:
                        System.out.println("Opção inválida. Por favor, escolha uma opção válida (1, 2 ou 3).");
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor, insira um número válido.");
                scanner.nextLine();
                opcao = 0;
            }
        } while (opcao < 1 || opcao > 3);
    }
}
