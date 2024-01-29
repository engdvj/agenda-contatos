package br.com.student.adatech.projetos.util;

import br.com.student.adatech.projetos.models.Agenda;
import br.com.student.adatech.projetos.models.Contato;
import br.com.student.adatech.projetos.models.Telefone;
import br.com.student.adatech.projetos.menu.Menu;


import java.util.List;

/**
 * Classe de utilidades para a agenda telefônica.
 * Contém métodos para verificar telefones e coletar telefones.
 */
public class Util {

    /**
     * Verifica se um telefone já existe em uma lista de telefones adicionais ou na agenda.
     *
     * @param novoTelefone       O telefone a ser verificado.
     * @param telefonesAdicionais Lista de telefones adicionais para verificar.
     * @return Retorna true se o telefone já existir, false caso contrário.
     * @throws IllegalArgumentException se o novoTelefone ou telefonesAdicionais for nulo.
     */
    public static boolean verificarTelefone(Telefone novoTelefone, List<Telefone> telefonesAdicionais) {
        if (novoTelefone == null || telefonesAdicionais == null) {
            throw new IllegalArgumentException("Telefone e lista de telefones não podem ser nulos.");
        }

        for (Telefone tel : telefonesAdicionais) {
            if (tel.equals(novoTelefone)) {
                return true;
            }
        }

        List<Contato> contatos = Agenda.getAgenda();
        for (Contato contato : contatos) {
            List<Telefone> telefonesDoContato = contato.getTelefones();
            if (telefonesDoContato != null) {
                for (Telefone telefone : telefonesDoContato) {
                    if (telefone.equals(novoTelefone)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Coleta telefones do usuário e adiciona a uma lista de telefones.
     * Valida a entrada do usuário e trata possíveis erros de formatação.
     *
     * @param telefones Lista de telefones onde os novos telefones serão adicionados.
     */
    public static void coletarTelefones(List<Telefone> telefones) {
        boolean continuarColetando = true;

        do {
            try {
                System.out.println("Digite o DDD do telefone:");
                String ddd = Menu.scanner.nextLine();

                System.out.println("Digite o número do telefone:");
                long numero = Long.parseLong(Menu.scanner.nextLine());

                Telefone novoTelefone = new Telefone(ddd, numero);

                if (verificarTelefone(novoTelefone, telefones)) {
                    System.out.println("Telefone já cadastrado.");
                    continue;
                }

                telefones.add(novoTelefone);
                System.out.println("Telefone adicionado com sucesso.\n");

            } catch (NumberFormatException e) {
                System.out.println("Número de telefone inválido. Por favor, tente novamente.");
            }

            System.out.println("Deseja adicionar outro número? (sim/não)");
            continuarColetando = Menu.scanner.nextLine().equalsIgnoreCase("sim");

        } while (continuarColetando);
    }
}