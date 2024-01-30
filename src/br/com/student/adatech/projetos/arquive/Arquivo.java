package br.com.student.adatech.projetos.arquive;

import br.com.student.adatech.projetos.models.Agenda;
import br.com.student.adatech.projetos.models.Contato;
import br.com.student.adatech.projetos.models.Telefone;
import br.com.student.adatech.projetos.operacoes.OperacoesContato;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que faz a leitura e escrita de informações da agenda telefônica em um arquivo.
 */
public class Arquivo {

    private static final String ARQUIVOS = "C:\\Users\\Davi Costa\\Desktop\\Projetos_ADA\\agenda_contatos\\arquivo\\Arquivo.txt";
    private static final String TELEFONES = "C:\\Users\\Davi Costa\\Desktop\\Projetos_ADA\\agenda_contatos\\arquivo\\Telefones.txt";

    /**
     * Carrega os contatos do arquivo e os adiciona à agenda.
     * Inicializa os geradores de ID para Contato e Telefone com base nos últimos IDs lidos do arquivo.
     * Lê os contatos e seus respectivos telefones dos arquivos e os adiciona à agenda.
     */
    public static void carregarContatos() {
        Contato.inicializarIdGenerator(Arquivo.lerUltimoIdContato());
        Telefone.setIDGenerator(Arquivo.lerUltimoIdTelefone());
        List<String> linhasContato = lerContatos();
        List<String> linhasTelefone = lerTelefones();

        for (String linhaContato : linhasContato) {
            String[] partesContato = linhaContato.split(" \\| ");
            long idContato = Long.parseLong(partesContato[0].trim());
            Contato contato = recuperarContato(partesContato, linhasTelefone, idContato);
            OperacoesContato.adicionarContato(contato);
        }
    }
    /**
     * Recupera um contato e seus telefones a partir de strings formatadas.
     *
     * @param partesContato  Array de strings contendo informações do contato.
     * @param linhasTelefone Lista de strings com informações dos telefones.
     * @param idContato      ID do contato a ser recuperado.
     * @return Um objeto Contato construído a partir das informações fornecidas.
     */
    private static Contato recuperarContato(String[] partesContato, List<String> linhasTelefone, Long idContato) {
        String nome = partesContato[1].trim();
        String sobreNome = partesContato[2].trim();

        List<Telefone> telefonesDoContato = new ArrayList<>();
        for (String linhaTelefone : linhasTelefone) {
            String[] partesTelefone = linhaTelefone.split(" \\| ");
            Long idContatoTelefone = Long.parseLong(partesTelefone[0].trim());
            if (idContato.equals(idContatoTelefone)) {
                Long idTelefone = Long.parseLong(partesTelefone[1].trim());
                String ddd = partesTelefone[2].trim();
                Long numero = Long.parseLong(partesTelefone[3].trim());
                Telefone telefone = new Telefone(idTelefone, ddd, numero); // Usando o novo construtor
                telefonesDoContato.add(telefone);
            }
        }

        return new Contato(idContato, nome, sobreNome, telefonesDoContato);
    }
    /**
     * Lê as linhas do arquivo de contatos e retorna uma lista de strings.
     *
     * @return Lista contendo cada linha do arquivo de contatos.
     */
    public static List<String> lerContatos() {
        File arquivo = new File(ARQUIVOS);
        return lerDoArquivo(arquivo);
    }

    /**
     * Lê as linhas do arquivo de telefones e retorna uma lista de strings.
     *
     * @return Lista contendo cada linha do arquivo de telefones.
     */
    public static List<String> lerTelefones() {
        File telefone = new File(TELEFONES);
        return lerDoArquivo(telefone);
    }

    /**
     * Lê as linhas de um arquivo especificado e as retorna como uma lista de strings.
     * Se o arquivo não existir, cria um novo arquivo vazio.
     *
     * @param arquivo O arquivo a ser lido.
     * @return Lista das linhas lidas do arquivo.
     */
    private static List<String> lerDoArquivo(File arquivo) {
        List<String> linhas = new ArrayList<>();
        try {
            // Verifica se o arquivo existe. Se não, cria um novo arquivo vazio.
            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }

            // Continua com a leitura do arquivo
            try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    linhas.add(linha);
                }
            }
        } catch (IOException ignored) {
        }
        return linhas;
    }

    /**
     * Lê o último ID de contato do arquivo correspondente.
     *
     * @return O maior ID encontrado no arquivo de contatos.
     */
    public static Long lerUltimoIdContato() {
        List<String> linhas = lerContatos();
        long maiorId = 0L;
        for (String linha : linhas) {
            try {
                String[] partes = linha.split(" \\| ");
                long id = Long.parseLong(partes[0].trim());
                if (id > maiorId) {
                    maiorId = id;
                }
            } catch (NumberFormatException e) {
                // Tratar exceção ou ignorar linha mal formatada
            }
        }
        return maiorId;
    }

    /**
     * Lê o último ID de telefone do arquivo correspondente.
     *
     * @return O maior ID encontrado no arquivo de telefones.
     */
    public static Long lerUltimoIdTelefone() {
        List<String> linhas = lerTelefones();
        long maiorId = 0L;
        for (String linha : linhas) {
            try {
                String[] partes = linha.split(" \\| ");
                long id = Long.parseLong(partes[1]); // Assumindo que o ID está na posição 1
                if (id > maiorId) {
                    maiorId = id;
                }
            } catch (NumberFormatException e) {
                // Tratar exceção, se necessário
            }
        }
        return maiorId;
    }

    /**
     * Adiciona um contato ao arquivo de contatos e seus telefones ao arquivo de telefones.
     * Formata os detalhes do contato e do telefone para gravação.
     *
     * @param contato O contato a ser adicionado.
     */
    public static void adicionarContato(Contato contato) {
        String detalhesContato = String.format("%02d", contato.getId()) +
                " | " + contato.getNome() +
                " | " + contato.getSobreNome();
        escreverNoArquivo(new File(ARQUIVOS), detalhesContato,true);

        for (Telefone telefone : contato.getTelefones()) {
            String detalhesTelefone = String.format("%02d", contato.getId()) +
                    " | " + String.format("%02d", telefone.getId()) +
                    " | " + telefone.getDdd() +
                    " | " + telefone.getNumero();
            escreverNoArquivo(new File(TELEFONES), detalhesTelefone,true);
        }
    }

    /**
     * Adiciona um telefone ao contato especificado.
     *
     * @param idContato o ID do contato
     * @param novoTelefone o telefone a ser adicionado
     */
    public static void adicionarTelefone(Long idContato, Telefone novoTelefone) {
        String detalhesTelefone = String.format("%02d", idContato) + " | " +
                String.format("%02d", novoTelefone.getId()) + " | " +
                novoTelefone.getDdd() + " | " +
                novoTelefone.getNumero();
        escreverNoArquivo(new File(TELEFONES), detalhesTelefone, true);
    }

    /**
     * Remove um telefone ao contato especificado.
     *
     * @param idContato o ID do contato
     * @param telefoneRemovido o telefone a ser adicionado
     */
    public static void removerTelefone(Long idContato, Telefone telefoneRemovido) {
        List<String> linhas = lerTelefones();
        List<String> linhasAtualizadas = new ArrayList<>();

        String idContatoFormatado = String.format("%02d", idContato);
        String idTelefoneFormatado = String.format("%02d", telefoneRemovido.getId());

        for (String linha : linhas) {
            if (!linha.startsWith(idContatoFormatado + " | " + idTelefoneFormatado)) {
                linhasAtualizadas.add(linha);
            }
        }

        escreverNoArquivo(new File(TELEFONES), linhasAtualizadas, false);
    }

    /**
     * Remove um contato especificado.
     *
     * @param idContato o ID do contato
     */
    public static void removerContato(Long idContato) {
        removerDoArquivo(new File(ARQUIVOS), idContato);
        removerTelefonesDoContato(idContato);
    }

    /**
     * Remove informações do arquivo sobre um contato especificado.
     *
     * @param arquivo o caminho do arquivo referente
     * @param idContato o ID do contato
     */
    private static void removerDoArquivo(File arquivo, Long idContato) {

        Contato contato = Agenda.getContatoPorId(idContato);

        if (contato == null) {
            return; // Se não encontrar o contato, não há nada a remover
        }

        List<String> linhas = lerDoArquivo(arquivo);
        List<String> linhasAtualizadas = new ArrayList<>();

        String idFormatado = String.format("%02d", idContato);

        for (String linha : linhas) {
            if (!linha.startsWith(idFormatado + " |")) {
                linhasAtualizadas.add(linha);
            }
        }

        // Reescreve o arquivo sem o contato removido
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, false))) {
            for (String linha : linhasAtualizadas) {
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException ignored) {

        }
    }

    /**
     * Remove um telefones de um contato especificado.
     *
     * @param idContato o ID do contato
     */
    private static void removerTelefonesDoContato(Long idContato) {
        // Primeiro, obtenha o nome do contato usando o idContato
        Contato contato = Agenda.getContatoPorId(idContato);
        if (contato == null) {
            return;
        }

        List<String> linhas = lerDoArquivo(new File(TELEFONES));
        List<String> linhasAtualizadas = new ArrayList<>();

        for (String linha : linhas) {
            String[] partes = linha.split(" \\| ");
            String nomeContatoLinha = partes[0].trim();
            String id = idContato.toString();
            if(idContato<10){
                id = String.format("0"+id);
            }
            if (!nomeContatoLinha.equals(id)){
                linhasAtualizadas.add(linha);
            }
        }

        // Reescreve o arquivo de telefones sem os telefones do contato removido
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TELEFONES, false))) {
            for (String linhaAtualizada : linhasAtualizadas) {
                writer.write(linhaAtualizada);
                writer.newLine();
            }
        } catch (IOException ignored) {

        }
    }

    /**
     * Altera informações de um contato no arquivo.
     *
     * @param contatoAtualizado o contato a ser alterado
     */
    public static void atualizarContato(Contato contatoAtualizado) {
        File arquivoContatos = new File(ARQUIVOS);
        List<String> linhas = lerDoArquivo(arquivoContatos);
        List<String> linhasAtualizadas = new ArrayList<>();

        String idFormatado = String.format("%02d", contatoAtualizado.getId());

        for (String linha : linhas) {
            if (linha.startsWith(idFormatado + " |")) {
                // Substituir a linha com as novas informações do contato
                String novaLinha = idFormatado + " | " + contatoAtualizado.getNome() + " | " + contatoAtualizado.getSobreNome();
                linhasAtualizadas.add(novaLinha);
            } else {
                linhasAtualizadas.add(linha);
            }
        }

        // Reescrever o arquivo com as linhas atualizadas
        escreverNoArquivo(arquivoContatos, linhasAtualizadas, false);
    }

    /**
     * Altera informações de um telefone de um contato no arquivo.
     *
     * @param telefoneAtualizado o contato a ser alterado
     */
    public static void atualizarTelefone(Long idContato, Telefone telefoneAtualizado) {

        File arquivoTelefones = new File(TELEFONES);
        List<String> linhas = lerDoArquivo(arquivoTelefones);
        List<String> linhasAtualizadas = new ArrayList<>();

        String idContatoFormatado = String.format("%02d", idContato);
        String idTelefoneFormatado = String.format("%02d", telefoneAtualizado.getId());



        for (String linha : linhas) {
            String[] partesLinha = linha.split(" \\| ");
            if (partesLinha[0].trim().equals(idContatoFormatado) && partesLinha[1].trim().equals(idTelefoneFormatado)) {
                // Substituir a linha com as novas informações do telefone
                String novaLinha = idContatoFormatado + " | " +
                        idTelefoneFormatado + " | " +
                        telefoneAtualizado.getDdd() + " | " +
                        telefoneAtualizado.getNumero();
                linhasAtualizadas.add(novaLinha);
            } else {
                linhasAtualizadas.add(linha);
            }
        }
        // Reescrever o arquivo de telefones com as linhas atualizadas
        escreverNoArquivo(arquivoTelefones, linhasAtualizadas, false);
        System.out.println("Linha de telefone atualizada!");
    }

    /**
     * Escreve o conteúdo em um arquivo.
     *
     * @param arquivo  o arquivo onde o conteúdo será escrito
     * @param conteudo o conteúdo a ser escrito no arquivo
     * @param append   se true, o conteúdo será adicionado ao final do arquivo, caso contrário, o arquivo será sobrescrito
     */
    private static void escreverNoArquivo(File arquivo, Object conteudo, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, append))) {
            if (conteudo instanceof String) {
                writer.write((String) conteudo);
                writer.newLine();
            } else if (conteudo instanceof List<?>) {
                for (Object item : (List<?>) conteudo) {
                    if (item instanceof String) {
                        writer.write((String) item);
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}