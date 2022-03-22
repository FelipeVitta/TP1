import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Clube {


    protected int idClube;
    protected String nome;
    protected String cnpj;
    protected String cidade;
    protected int partidasJogadas;
    protected int pontos;

    public Clube(String nome, String cnpj, String cidade){
        this.nome = nome;
        this.cnpj = cnpj;
        this.cidade = cidade;
    }

    public Clube(){

    }


    public int getIdClube() {
        return idClube;
    }

    public void setIdClube(int idClube) {
        this.idClube = idClube;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public int getPartidasJogadas() {
        return partidasJogadas;
    }

    public void setPartidasJogadas(int partidasJogadas) {
        this.partidasJogadas = partidasJogadas;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public String toString(){
        return  "\nNome: " + nome + "\nCNPJ: " + cnpj + "\nCidade: " + cidade + "\nPartidas Jogadas: " + partidasJogadas + "\nPontos: " + pontos;
    }

     
    public byte[] toByteArray() throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();   //escreve pra um vetor de bytes em memoria
        DataOutputStream dos = new DataOutputStream(baos);        //fluxo de saida que converte os valores (string, int, etc) em bytes
        dos.writeInt(idClube);        
        dos.writeUTF(nome);              
        dos.writeUTF(cnpj);
        dos.writeUTF(cidade);
        dos.writeInt(partidasJogadas);
        dos.writeInt(pontos);
        return baos.toByteArray();           
     }
 

    
}