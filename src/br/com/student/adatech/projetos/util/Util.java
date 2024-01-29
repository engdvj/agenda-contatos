package br.com.student.adatech.projetos.util;

import br.com.student.adatech.projetos.menu.Menu;
import br.com.student.adatech.projetos.models.Agenda;
import br.com.student.adatech.projetos.models.Contato;
import br.com.student.adatech.projetos.models.Telefone;

import java.util.List;

public class Util {
    public static boolean verificarTelefone(Telefone novoTelefone, List<Telefone> telefonesAdicionais) {
        // Verifica na lista de telefones adicionais
        for (Telefone tel : telefonesAdicionais) {
            if (tel.equals(novoTelefone)) {
                return true;
            }
        }

        // Verifica em todos os contatos da agenda
        List<Contato> contatos = Agenda.getAgenda();
        for (Contato contato : contatos) {
            for (Telefone telefone : contato.getTelefones()) {
                if (telefone.equals(novoTelefone)) {
                    return true;
                }
            }
        }

        // Se não encontrou duplicatas, retorna false
        return false;
    }
    public static void coletarTelefones(List<Telefone> telefones) {
        boolean continuarColetando = true;

        do {
            System.out.println("Digite o DDD do telefone:");
            String ddd = Menu.sc.nextLine();
            System.out.println("Digite o número do telefone:");
            Long numero = Menu.sc.nextLong();
            Menu.sc.nextLine();

            Telefone novoTelefone = new Telefone(ddd, numero);

            // Verifica se o telefone já existe na agenda ou na lista atual
            if (verificarTelefone(novoTelefone, telefones)) continue; // Pula para a próxima iteração do loop

            telefones.add(novoTelefone);
            System.out.println("Telefone adicionado com sucesso.\n");

            System.out.println("Deseja adicionar outro número? (sim/não)");
            String resposta = Menu.sc.nextLine();

            if (!resposta.equalsIgnoreCase("sim")) {
                continuarColetando = false;
            }
        } while (continuarColetando);
    }


}