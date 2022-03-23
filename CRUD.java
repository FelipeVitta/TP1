import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;

import javax.swing.event.InternalFrameEvent;

public class CRUD {

    //METODO PARA ADICIONAR UM NOVO CLUBE AO ARQUIVO 

    public void createClub(Clube a) throws IOException {
        try {

            int ultimoID;
            byte[] ba;

            RandomAccessFile arq = new RandomAccessFile("dados/clubes.db", "rw");

            // se o arquivo estiver vazio, escrever o ultimo id usado como 0
            if (arq.length() == 0) {
                arq.seek(0);
                arq.writeInt(0);
            }
            //movendo o ponteiro para o inicio do arquivo
            arq.seek(0);
            //lendo o cabeçalho
            ultimoID = arq.readInt();
            //definindo o id do clube como o ultimo id usado + 1
            a.setIdClube(ultimoID + 1);
            //definindo as partidas jogadas e os pontos do novo clube como 0
            a.setPontos(0);
            a.setPartidasJogadas(0);
            //movendo o ponteiro para o inicio do arquivo
            arq.seek(0);
            //escrevendo o id do novo clube criado (ultimo id)
            arq.writeInt(a.getIdClube());
            ba = a.toByteArray();
            //escrevendo as informações do novo clube no arquivo
            arq.seek(arq.length());
            arq.writeChars("'");
            arq.writeInt(ba.length);
            arq.write(ba);
            System.out.println(a);

            arq.close();

            //tratamento de erros
        } catch (Exception e) {
            System.out.println("\nERRO: Não foi possivel criar o Clube\n");
            e.printStackTrace();
        }

    }

    //METODO QUE RETORNA AS INFORMAÇÕES DO CLUBE COM O MESMO ID INFORMADO PELO USUARIO

    public Clube readClub(int a) throws Exception {

        char lapide;
        byte[] ba;
        int tamanhoReg, ultimoID, id;

        RandomAccessFile arq = new RandomAccessFile("dados/clubes.db", "rw");
        Clube c = new Clube();
        //lendo o cabeçalho
        ultimoID = arq.readInt();
         //se o id informado for menos que o ultimo id do arquivo, o clube ainda não existe
        if (a > ultimoID) {
            System.out.println("Esse Clube ainda não existe");
        } else {

            long tamanhoArq = arq.length();

            // enquanto não for o final do arquivo, ler o próximo
            while (arq.getFilePointer() < tamanhoArq) {
                //lendo a lapide
                lapide = arq.readChar();
                if (lapide != '*') {
                    //lendo o tamanho do registro e o id
                    tamanhoReg = arq.readInt();
                    id = arq.readInt();
                    //se o id informado for igual id do arquivo
                    if (a == id) {
                        //lendo as informações do clube e setando no objeto criado
                        c.setIdClube(id);
                        c.setNome(arq.readUTF());
                        c.setCnpj(arq.readUTF());
                        c.setCidade(arq.readUTF());
                        c.setPartidasJogadas(arq.readInt());
                        c.setPontos(arq.readInt());
                        break;
                    } else {
                        //lendo o resto do registro
                        ba = new byte[tamanhoReg - 4];
                        arq.read(ba);

                    }
                } else {
                    //lendo o resto do registro
                    tamanhoReg = arq.readInt();
                    ba = new byte[tamanhoReg];
                    arq.read(ba);

                }

            }

        }

        arq.close();

        //retornando o objeto criado
        return c;

    }

    //METODO PARA DELETAR UM CLUBE (COLOCAR LAPIDE) DO ARQUIVO DE ACORDO COM O ID PASSADO
    public void deleteClub(int c) {
        try {
            byte[] ba;
            char lapide;
            int tamanhoReg, id;

            RandomAccessFile arq = new RandomAccessFile("dados/clubes.db", "rw");
            //colocando o ponteiro na posição depois do cabeçalho
            arq.seek(4);
            //lendo o tamanho do arquivo inteiro
            long tamanhoArq = arq.length();

            //enquanto não for o final do arquivo
            while (arq.getFilePointer() < tamanhoArq) {
                //lendo a posicao atual do ponteiro
                long pos = arq.getFilePointer();
                //lendo a lápide
                lapide = arq.readChar();
                //se o arquivo não foi excluido
                if (lapide != '*') {
                    //lendo o tamanho do registro e o id
                    tamanhoReg = arq.readInt();
                    id = arq.readInt();
                    //se o id do clube for igual ao id informado
                    if (c == id) {
                        //indo para a posicao do ponteiro e colocando lapide (excluindo)
                        arq.seek(pos);
                        arq.writeChars("*");
                        break;
                    } else {
                        //lendo o registro (volta ao loop)
                        ba = new byte[tamanhoReg - 4];
                        arq.read(ba);
                    }

                } else {
                    //lendo o registro (volta ao loop)
                    tamanhoReg = arq.readInt();
                    ba = new byte[tamanhoReg];
                    arq.read(ba);
                }

            }

            //tratamento de erros
        } catch (Exception e) {
            System.out.println("ERRO: Não foi possivel deletar o arquivo");
            e.printStackTrace();
        }

    }

    //METODO QUE RECEBE UM CLUBE E ATUALIZA SEUS VALORES DE ACORDO COM O QUE O USUÁRIO DIGITOU

    public void updateClub(Clube c, String nome, String cnpj, String cidade, int partidas, int pontos) {
        try {
            RandomAccessFile arq = new RandomAccessFile("dados/clubes.db", "rw");
            //captando o tamanho do arquivo inteiro
            long tamanhoArq = arq.length();
            //variaveis usadas no metodo
            long pos;
            char lapide;
            int tamanhoReg, id;
            byte[] bytee, ba;
            //colocando o ponteiro do arquivo para a posição 4 (depois do cabeçalho)
            arq.seek(4);
            //enquanto não for o final do arquivo
            while (arq.getFilePointer() < tamanhoArq) {
                pos = arq.getFilePointer();
                //lendo a lapide
                lapide = arq.readChar();
                if (lapide != '*') {
                    //lendo o tamanho do registro e o id
                    tamanhoReg = arq.readInt();
                    id = arq.readInt();
                    //se o valor digitado pelo usuario for igual ao id do clube
                    if (c.getIdClube() == id) {
                        //atualizando os valores do registro
                        Clube clube = new Clube(c.getIdClube(), nome, cnpj, cidade, partidas, pontos);
                        bytee = clube.toByteArray();
                        //se o tamanho do novo registro for menor que o tamanho do registro antigo
                        if (bytee.length < tamanhoReg) {
                            //sobrescrevendo o novo registro no antigo
                            arq.seek(pos);
                            arq.writeChars("'");
                            arq.writeInt(tamanhoReg);
                            arq.write(bytee);
                            break;
                        } else {
                            //colocando uma lapide no registro antigo e
                            //indo para o final do arquivo para escrever o novo registro
                            arq.seek(pos);
                            arq.writeChars("*");
                            arq.seek(arq.length());
                            arq.writeChars("'");
                            arq.writeInt(bytee.length);
                            arq.write(bytee);
                            System.out.println(clube);
                            break;
                        }
                    } else {
                        //lendo o resto do arquivo menos os bytes do id lido (voltando ao loop)
                        ba = new byte[tamanhoReg - 4];
                        arq.read(ba);
                    }

                } else {
                    //lendo o registro (voltando ao loop)
                    tamanhoReg = arq.readInt();
                    ba = new byte[tamanhoReg];
                    arq.read(ba);
                }

            }

            arq.close();

        //tratando erros
        } catch (Exception e) {
            System.out.println("Não foi possivel atualizar as informações do Clube");
            e.printStackTrace();
        }

    }

    //MÉTODO QUE RECEBE DOIS CLUBES E DOIS INTEIROS (GOLS) E ATUALIZA OS VALORES DE ACORDO COM O RESULTADO

    public void teamMatch(Clube a, int gol1, Clube b, int gol2) throws Exception {

        RandomAccessFile arq = new RandomAccessFile("dados/clubes.db", "rw");
        // SE O PRIMEIRO TIME GANHAR, ADICIONAR +1 PARTIDA AOS DOIS CLUBES E ADICIONAR +
        // 3 PONTOS PARA DO CLUBE a
        if (gol1 > gol2) {
            updateClub(a, a.getNome(), a.getCnpj(), a.getCidade(), a.getPartidasJogadas() + 1, a.getPontos() + 3);
            updateClub(b, b.getNome(), b.getCnpj(), b.getCidade(), b.getPartidasJogadas() + 1, b.getPontos());
            System.out.println("\nO " + a.getNome() + " ganhou a partida!");
            // SE O SEGUNDO TIME GANHAR, ADICIONAR +1 PARTIDA AOS DOIS CLUBE E ADICIONAR + 3
            // PONTOS PARA O CLUBE b
        } else if (gol1 < gol2) {
            updateClub(b, b.getNome(), b.getCnpj(), b.getCidade(), b.getPartidasJogadas() + 1, b.getPontos() + 3);
            updateClub(a, a.getNome(), a.getCnpj(), a.getCidade(), a.getPartidasJogadas() + 1, a.getPontos());
            System.out.println("\nO " + b.getNome() + " ganhou a partida!");
            // SE A PARTIDA EMPATAR, ADICIONAR +1 PARTIDA AOS DOIS CLUBES E +1 PONTO AOS
            // DOIS CLUBES
        } else if (gol1 == gol2) {
            updateClub(a, a.getNome(), a.getCnpj(), a.getCidade(), a.getPartidasJogadas() + 1, a.getPontos() + 1);
            updateClub(b, b.getNome(), b.getCnpj(), b.getCidade(), b.getPartidasJogadas() + 1, b.getPontos() + 1);
            System.out.println("\nO jogo empatou!");
        }

        arq.close();

    }

}
