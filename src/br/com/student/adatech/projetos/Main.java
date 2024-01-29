package br.com.student.adatech.projetos;

import br.com.student.adatech.projetos.menu.Menu;

/**
 * Classe principal da aplicação da agenda telefônica.
 * Inicia o programa e exibe o menu principal.
 */
public class Main {
    public static void main(String[] args) {

        Menu menu = new Menu();
        menu.iniciarPrograma();
    }
}