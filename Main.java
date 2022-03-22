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
      int opcao;
      int id;
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
               
               case 2:
               
               System.out.println("\nDigite o ID do clube que deseja obter os dados: \n");
               id = sc.nextInt();
               crud.readClub(id);   

               break;
               
               case 3:
               break;

               case 4:
               
               int op;
               System.out.println("\nDigite o ID do clube que você quer deletar");
               id = sc.nextInt();
               System.out.println("\nQuer mesmo deletar esse Clube?: ");
               crud.readClub(id);
               System.out.println("\n1-Sim");
               System.out.println("\n2-Não");
               op = sc.nextInt();
               if(op == 1){
                  crud.deleteClub(id);
               }else if(op == 2){
                 System.out.println("Clube não deletado");
               }else{
                  System.out.println("Opção inválida");
               }
              
   
               break;

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