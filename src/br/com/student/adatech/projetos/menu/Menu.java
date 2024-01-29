package br.com.student.adatech.projetos.menu;

import br.com.student.adatech.projetos.models.Agenda;
import br.com.student.adatech.projetos.models.Contato;
import br.com.student.adatech.projetos.arquive.Arquivo;


import java.util.Scanner;

public class Menu {

    //Variaveis
    public static Scanner sc;

    //Construtor
    public Menu(){
        Arquivo.carregarContatos();
        Menu.sc = new Scanner(System.in);
    }

    //Outros métodos
    public void iniciarPrograma(){
        escolherAcao();
        sc.close();
    }
    private void escolherAcao() {
        int opcao;

        do {
            mostrarScreen();
            opcao = sc.nextInt();
            sc.nextLine();
            switch (opcao) {
                case 1:
                    Agenda.adicionarContato();
                    break;
                case 2:
                    Agenda.editarContato();
                    break;
                case 3:
                    Agenda.removerContato();
                    break;
                case 4:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 4);
    }
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
    public static int editarContato() {
        System.out.println("""
                        Escolha a informação que gostaria de editar:
                        
                        1. Nome
                        2. Sobrenome
                        3. Telefone
                        
                        Digite sua opção:""");
        int opcao = sc.nextInt();
        sc.nextLine();
        return opcao;
    }
    public static void editarTelefoneDoContato(Contato contato) {
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
                    Agenda.adicionarNovoTelefone(sc, contato);
                    break;
                case 2:
                    Agenda.editarTelefoneExistente(sc, contato);
                    break;
                case 3:
                    Agenda.apagarTelefoneExistente(sc, contato);
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida (1, 2 ou 3).");
                    break;
            }
        } while (opcao < 1 || opcao > 3);
    }
}
