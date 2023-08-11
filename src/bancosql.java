//bancoSQL para conectar com o banco de dados

import java.util.*;//scanner, arraylist, formatter
import java.io.*; //file
import javax.swing.*;//JOptionPane
import java.sql.*;//sql

class BancoxSql
{
   private File arqtest;
   private Scanner arqler, tecla;
   private ArrayList Lista;

   /*No banco:
    id integer primary key autoincrement,
    memoria integer,
    nome varchar(40),
    fab varchar(40),
    preco double,
    anoLancamento varchar(40),
    foto blob,
    modelo varchar(40),

    Para fabricante, tipo enum('NVIDIA','AMD','INTEL','OUTROS')
   */
   private String sid, sanoLancamento, snome, sfab, smemoria, spreco, smodelo;
   private String nomearq, nomebanco, nometabela;
   private byte[] fotogpu;
   
   private Connection con;   
   private String url,drive,fsql;
   private ResultSet rs;
   private PreparedStatement pstmt;//sera usado com frase sql 
   
   BancoxSql(String nomeban)//construtor
   {
        con=null; 
        rs=null;
        pstmt=null;

        
        url="jdbc:sqlite:"+nomeban;
        drive="org.sqlite.JDBC";//drive de conexao
        
        Lista=new ArrayList<>();
        nomebanco=nomeban;
        nometabela="tabpets";
   }
   
   
   public void conectar()
   {
       try {
           Class.forName(drive);
           con=DriverManager.getConnection(url);
           JOptionPane.showMessageDialog(null,"Conexão realizada !!!!");
        } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"erro.conexao="+e);
                return;
       }
   }//fim conectar
   
    public void desconectar()
    {
      try {
       if (this.con.isClosed() == false) {
            this.con.close();
       }
      } catch (Exception e) {
         JOptionPane.showMessageDialog(null,"erroaodesconectar"+e); 
         return;
      }
      JOptionPane.showMessageDialog(null,"desconectou!!!");
}//fim desconectar


public static void main(String args[])
{  
        BancoxSql b=new BancoxSql("gpu.db");
        b.conectar();
        b.desconectar(); 
}//main

}//class
