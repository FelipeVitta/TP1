import java.io.IOException;
import java.io.RandomAccessFile;
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

                  System.out.println("\nDigite o ID do clube que deseja obter os dados: \n");
                  id = sc.nextInt();
                  System.out.println(crud.readClub(id));
               } catch (Exception e) {
                  System.out.println("Não foi possivel ler o arquivo");
                  e.printStackTrace();
               }

               break;

            // ATUALIZAR DADOS DE UM CLUBE

            case 3:

               try {

                  Clube clube = new Clube();
                  System.out.println("\nQual o ID do clube que você quer atualizar os dados?");
                  opcao = sc.nextInt();
                  System.out.println("Quer mesmo atualizar os dados desse clube?:\n");
                  clube = crud.readClub(opcao);
                  System.out.println(clube);
                  System.out.println("\n1-Sim");
                  System.out.println("\n2-Não");
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
               } catch (Exception e) {
                  System.out.println("ERRO");
                  e.printStackTrace();
               }
               break;

            // DELETAR UM CLUBE

            case 4:

               try {

                  System.out.println("\nDigite o ID do clube que você quer deletar");
                  id = sc.nextInt();
                  System.out.println("\nQuer mesmo deletar esse Clube?: ");
                  System.out.println(crud.readClub(id));
                  System.out.println("\n1-Sim");
                  System.out.println("\n2-Não");
                  op = sc.nextInt();
                  if (op == 1) {
                     crud.deleteClub(id);
                  } else if (op == 2) {
                     System.out.println("Clube não deletado");
                  } else {
                     System.out.println("Opção inválida");
                  }

               } catch (Exception e) {
                  System.out.println("ERRO");
                  e.printStackTrace();
               }

               break;

              //REALIZAR UMA PARTIDA
              
            case 5:
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
