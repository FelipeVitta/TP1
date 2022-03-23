import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;

import javax.swing.event.InternalFrameEvent;

public class CRUD {

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
            arq.seek(0);
            ultimoID = arq.readInt();
            a.setIdClube(ultimoID + 1);
            a.setPontos(0);
            a.setPartidasJogadas(0);
            arq.seek(0);
            arq.writeInt(a.getIdClube());
            ba = a.toByteArray();
            arq.seek(arq.length());
            arq.writeChars("'");
            arq.writeInt(ba.length);
            arq.write(ba);
            System.out.println(a);

            arq.close();

        } catch (Exception e) {
            System.out.println("\nERRO: Não foi possivel criar o Clube\n");
            e.printStackTrace();
        }

    }

    public Clube readClub(int a) throws Exception {

        char lapide;
        byte[] ba;
        int tamanhoReg, ultimoID, id;

        RandomAccessFile arq = new RandomAccessFile("dados/clubes.db", "rw");
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        Clube c = new Clube();
        ultimoID = arq.readInt();

        if (a > ultimoID) {
            System.out.println("Esse Clube ainda não existe");
        } else {

            long tamanhoArq = arq.length();
            
           
            // enquanto não for o final do arquivo, ler o próximo
            while (arq.getFilePointer() < tamanhoArq) {

                lapide = arq.readChar();
                if (lapide != '*') {
                    tamanhoReg = arq.readInt();
                    id = arq.readInt();
                    if (a == id) {
                        c.setNome(arq.readUTF());
                        c.setCnpj(arq.readUTF());
                        c.setCidade(arq.readUTF());
                        c.setPartidasJogadas(arq.readInt());
                        c.setPontos(arq.readInt());
                        break;
                    } else {
                        ba = new byte[tamanhoReg - 4];
                        arq.read(ba);

                    }
                } else {
                    tamanhoReg = arq.readInt();                                      
                    ba = new byte[tamanhoReg];
                    arq.read(ba);

                }

            }

        }

        arq.close();

        return c;

    }

    public void deleteClub(int c) {
        try {
            byte[] ba;
            char lapide;
            int tamanhoReg, id;

            RandomAccessFile arq = new RandomAccessFile("dados/clubes.db", "rw");
            arq.seek(4);
            long tamanhoArq = arq.length();

            while (arq.getFilePointer() < tamanhoArq) {
                long pos = arq.getFilePointer();
                lapide = arq.readChar();
                if (lapide != '*') {
                    tamanhoReg = arq.readInt();
                    id = arq.readInt();
                    if (c == id) {
                        arq.seek(pos);
                        arq.writeChars("*");
                        break;
                    } else {
                        ba = new byte[tamanhoReg - 4];
                        arq.read(ba);
                    }

                } else {
                    tamanhoReg = arq.readInt();
                    ba = new byte[tamanhoReg];
                    arq.read(ba);
                }

            }

        } catch (Exception e) {
            System.out.println("ERRO: Não foi possivel deletar o arquivo");
            e.printStackTrace();
        }

    }

    public void updateClub(Clube c, String nome, String cnpj, String cidade, int partidas, int pontos) {
        try {
            RandomAccessFile arq = new RandomAccessFile("dados/clubes.db", "rw");
            long tamanhoArq = arq.length();
            long pos;
            char lapide;
            int tamanhoReg, id;
            byte[] bytee, ba;
            arq.seek(4);
            while (arq.getFilePointer() < tamanhoArq) {
                pos = arq.getFilePointer();
                lapide = arq.readChar();
                if (lapide != '*') {
                    tamanhoReg = arq.readInt();
                    id = arq.readInt();
                    if (c.getIdClube() == id) {
                        Clube clube = new Clube(c.getIdClube(), nome, cnpj, cidade, partidas, pontos);
                        bytee = clube.toByteArray();
                        if (bytee.length < tamanhoReg) {
                            arq.seek(pos);
                            arq.writeChars("'");
                            arq.writeInt(tamanhoReg);
                            arq.write(bytee);
                            break;
                        } else {
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
                        ba = new byte[tamanhoReg - 4];
                        arq.read(ba);
                    }

                } else {
                    tamanhoReg = arq.readInt();
                    ba = new byte[tamanhoReg];
                    arq.read(ba);
                }

            }
            
            arq.close();

        } catch (Exception e) {
            System.out.println("Não foi possivel atualizar as informações do Clube");
            e.printStackTrace();
        }

    }

}
