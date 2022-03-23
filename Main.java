import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;
import java.util.RandomAccess;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.stream.StreamSupport;

import javax.lang.model.util.ElementScanner14;
import javax.xml.catalog.CatalogException;

public class Main {

   public static void main(String[] args) {

      Scanner sc = new Scanner(System.in);
      CRUD crud = new CRUD();
      int opcao, op;
      int id, pontos, partidas;
      String nome, cnpj, cidade;

      System.out.println("\n MENU \n");
      System.out.println("O que você gostaria de fazer?");
      System.out.println("\n1 - Criar um clube");
      System.out.println("\n2 - Ler dados de um clube");
      System.out.println("\n3 - Atualizar dados de um clube");
      System.out.println("\n4 - Deletar um clube");
      System.out.println("\n5 - Realizar uma partida\n");
      opcao = sc.nextInt();
      try {

         switch (opcao) {

            // CRIAR UM CLUBE

            case 1:

               System.out.println("\nDigite o nome do Clube\n");
               nome = sc.nextLine();
               nome = sc.nextLine();
               System.out.println("\nDigite o CNPJ do Clube\n");
               cnpj = sc.nextLine();
               System.out.println("\nDigite a cidade do Clube\n");
               cidade = sc.nextLine();
               crud.createClub(new Clube(nome, cnpj, cidade));

               break;

            // LER DADOS DE UM CLUBE

            case 2:
               try {
                  RandomAccessFile arq = new RandomAccessFile("dados/clubes.db", "rw");
                  int ultimoId = arq.readInt();
                  System.out.println("\n");
                  for (int i = 1; i <= ultimoId; i++) {
                     Clube b = crud.readClub(i);
                     if (b.getIdClube() != 0) {
                        System.out.println("ID:" + b.getIdClube() + "  Nome: " + b.getNome());
                     }
                  }

                  System.out.println("\nDigite o ID do clube que deseja obter os dados: \n");
                  id = sc.nextInt();
                  System.out.println(crud.readClub(id));
                  arq.close();
               } catch (Exception e) {
                  System.out.println("Não foi possivel ler o arquivo");
                  e.printStackTrace();
               }

               break;

            // ATUALIZAR DADOS DE UM CLUBE

            case 3:

               try {

                  RandomAccessFile arq = new RandomAccessFile("dados/clubes.db", "rw");
                  int ultimoId = arq.readInt();
                  System.out.println("\n");
                  for (int i = 1; i <= ultimoId; i++) {
                     Clube b = crud.readClub(i);
                     if (b.getIdClube() != 0) {
                        System.out.println("ID:" + b.getIdClube() + "  Nome: " + b.getNome());
                     }
                  }

                  Clube clube = new Clube();
                  System.out.println("\nQual o ID do clube que você quer atualizar os dados?");
                  opcao = sc.nextInt();
                  System.out.println("Quer mesmo atualizar os dados desse clube?: ");
                  clube = crud.readClub(opcao);
                  System.out.println(clube);
                  System.out.println("\n1-Sim");
                  System.out.println("2-Não");
                  clube.setIdClube(opcao);
                  op = sc.nextInt();
                  if (op == 1) {
                     System.out.println("\nDigite o novo nome: \n");
                     nome = sc.nextLine();
                     nome = sc.nextLine();
                     System.out.println("\nDigite o novo CNPJ: \n");
                     cnpj = sc.nextLine();
                     System.out.println("\nDigite a cidade: \n");
                     cidade = sc.nextLine();

                     partidas = clube.getPontos();
                     pontos = clube.getPontos();
                     crud.updateClub(clube, nome, cnpj, cidade, pontos, partidas);
                  } else if (op == 2) {
                     System.out.println("Informações do clube mantidas");
                  } else {
                     System.out.println("Opção inválida");
                  }
                  arq.close();
               } catch (Exception e) {
                  System.out.println("ERRO");
                  e.printStackTrace();
               }
               break;

            // DELETAR UM CLUBE

            case 4:

               try {

                  RandomAccessFile arq = new RandomAccessFile("dados/clubes.db", "rw");
                  int ultimoId = arq.readInt();
                  System.out.println("\n");
                  for (int i = 1; i <= ultimoId; i++) {
                     Clube b = crud.readClub(i);
                     if (b.getIdClube() != 0) {
                        System.out.println("ID:" + b.getIdClube() + "  Nome: " + b.getNome());
                     }
                  }

                  System.out.println("\nDigite o ID do clube que você quer deletar");
                  id = sc.nextInt();
                  System.out.println("Quer mesmo deletar esse Clube?: ");
                  System.out.println(crud.readClub(id));
                  System.out.println("\n1-Sim");
                  System.out.println("2-Não\n");
                  op = sc.nextInt();
                  if (op == 1) {
                     crud.deleteClub(id);
                  } else if (op == 2) {
                     System.out.println("Clube não deletado");
                  } else {
                     System.out.println("Opção inválida");
                  }
                  arq.close();
               } catch (Exception e) {
                  System.out.println("ERRO");
                  e.printStackTrace();
               }

               break;

            // REALIZAR UMA PARTIDA

            case 5:
               try {

                  RandomAccessFile arq = new RandomAccessFile("dados/clubes.db", "rw");
                  int ultimoId = arq.readInt();
                  System.out.println("\n");
                  for (int i = 1; i <= ultimoId; i++) {
                     Clube b = crud.readClub(i);
                     if (b.getIdClube() != 0) {
                        System.out.println("ID:" + b.getIdClube() + "  Nome: " + b.getNome());
                     }
                  }

                  int id1, id2;
                  int gols1, gols2;
                  Clube clube1 = new Clube();
                  Clube clube2 = new Clube();
                  System.out.println("\nDigite o ID dos dois Clubes que gostaria de realizar a partida\n");

                  id1 = sc.nextInt();
                  id2 = sc.nextInt();

                  clube1 = crud.readClub(id1);
                  clube2 = crud.readClub(id2);

                  if (clube1.getNome() != null && clube2.getNome() != null) {

                     System.out.println("Quantos gols o " + clube1.getNome() + " fez?");
                     gols1 = sc.nextInt();
                     System.out.println("Quantos gols o " + clube2.getNome() + " fez?");
                     gols2 = sc.nextInt();

                     crud.teamMatch(clube1, gols1, clube2, gols2);
                  } else {
                     System.out.println("Um dos Clubes não existe");
                  }

               } catch (Exception e) {
                  System.out.println("ERRO");
                  e.printStackTrace();
               }
               break;

            default:
               System.out.println("\nValor inválido");
               break;

         }

      } catch (IOException e) {
         e.printStackTrace();
      }

   }

}