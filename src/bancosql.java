//bancoSQL para conectar com o banco de dados

import java.util.*;//scanner, arraylist, formatter
import java.io.*; //file
import javax.swing.*;//JOptionPane
import java.sql.*;//sql
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

class BancoxSql
{
   private File arqtest;
   private Scanner arqler, tecla;
   private static ArrayList<GPU> Lista;

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
   private static byte[] fotogpu;
   
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
        nometabela="gpu";
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

public static byte[] imageToByte(String image)
{
   InputStream is = null;
   byte[] buffer = null;
   try {
      is = new FileInputStream(image);
      buffer = new byte[is.available()];
      is.read(buffer);


   } catch (Exception e) {
      e.printStackTrace();
   }
   return buffer;
}

   public boolean procura(GPU xp)
   {
   boolean flag=false;
   fsql="select * from gpu where id=?";
   try {
   pstmt = con.prepareStatement(fsql);
   pstmt.setLong(1,xp.getId()); 
   rs = pstmt.executeQuery();
   if(rs.next()) //achou
   {
      JOptionPane.showMessageDialog(null,"Achou    Id="+rs.getLong("id"));
      flag=true;
   }
   else
   {
   JOptionPane.showMessageDialog(null," nao Achou    Id="+xp.getId());
   }
   rs.close();
   pstmt.close();
   
   return flag;
   } catch (Exception el) {
         JOptionPane.showMessageDialog(null,"Erro procura="+el);
            return false;
   }
}

public void altera(GPU gp)
{
   fsql = "update gpu set nome=?, fab=?, memoria=?," +
   "preco=?, anoLancamento=?, foto=?, modelo=? where id=?";
   try {
      pstmt = con.prepareStatement(fsql);
      pstmt.setString(1, gp.getNome());
      pstmt.setString(2, gp.getFab());
      pstmt.setInt(3, gp.getMem());
      pstmt.setDouble(4, gp.getPreco());
      pstmt.setString(5, gp.getAnoLancamento());
      pstmt.setBytes(6, gp.getFoto());
      pstmt.setString(7, gp.getModelo());
      pstmt.setLong(8, gp.getId());
      pstmt.execute();
      pstmt.close();
   JOptionPane.showMessageDialog(null,"Alterado com Sucesso!");
   } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e, drive, 0);
      return;
   }
}//////////

public void adiciona(GPU g)
{
   fsql = "INSERT INTO "+nometabela+" (nome,fab,memoria,preco,anoLancamento,foto,modelo) VALUES (?,?,?,?,?,?,?)";
   try {
      pstmt = con.prepareStatement(fsql);
      pstmt.setString(1, g.getNome());
      pstmt.setString(2, g.getFab());
      pstmt.setInt(3, g.getMem());
      pstmt.setDouble(4, g.getPreco());
      pstmt.setString(5, g.getAnoLancamento());
      pstmt.setBytes(6, g.getFoto());
      pstmt.setString(7, g.getModelo());
      pstmt.executeUpdate();
      JOptionPane.showMessageDialog(null,"Adicionado com sucesso!!!");
   } catch (Exception e) {
      JOptionPane.showMessageDialog(null,"erroaoadicionar"+e);
      return;
   }
}


public void exclui(GPU gp)
   {
   if(procura(gp)==false)
   {    JOptionPane.showMessageDialog(null,
      "gpu nao existe!!!");
      return;
   }
   fsql="delete from gpu where id=?";
   try {
   pstmt = con.prepareStatement(fsql);
   pstmt.setLong(1, gp.getId());
   pstmt.execute();
   pstmt.close();
   JOptionPane.showMessageDialog(null,"Excluido com Sucesso!");
   } catch (Exception e) {
   JOptionPane.showMessageDialog(null,"Erro ao excluir="+e);
   }
}

public ArrayList<GPU> getLista()
 {
   try {
   ArrayList<GPU> vetor = new ArrayList<GPU>();
   pstmt = con.prepareStatement("select * from gpu");
   rs = pstmt.executeQuery();
   while (rs.next())
   {
   // criando o objeto GPU
   GPU gpu = new GPU(); 
   gpu.setId(rs.getInt("id"));
   gpu.setNome(rs.getString("nome"));
   gpu.setFab(rs.getString("fab"));
   gpu.setMem(rs.getInt("memoria"));
   gpu.setPreco(rs.getDouble("preco"));
   gpu.setAnoLancamento(rs.getString("anoLancamento"));
   gpu.setFoto(rs.getBytes("foto"));
   gpu.setModelo(rs.getString("modelo"));
   // adicionando o objeto à lista
      vetor.add(gpu);
   }
   rs.close();
   pstmt.close();
   JOptionPane.showMessageDialog(null,"Feita leittura Banco");
   return vetor;
   } catch (Exception el) {
         JOptionPane.showMessageDialog(null,"Erro Arraylist"+el);
            return null;
   }
}
//função que converte o vetor de bytes em arquivo JPG

	public void ByteArrayToFileImage(byte[] bbb,String xid)
	 {
		  try
		  {
			  ByteArrayInputStream bis = new ByteArrayInputStream(bbb);
			  BufferedImage bImagex = ImageIO.read(bis);
			  ImageIO.write(bImagex, "jpg", new File("foto"+xid+".jpg") );
			  System.out.println("image created"+xid);
		  }
		  catch(Exception erroi)
		  { 
			  JOptionPane.showMessageDialog(null, "Erro imagem= " + erroi);
			return;
		  }
	}


	public void gravandotxt()
	{
		try
		{
			Formatter arquivo = new Formatter ("tabgpu.txt");
			fsql = "select * from gpu";
			pstmt = con.prepareStatement(fsql);
			pstmt = con.prepareStatement("select * from gpu");
			rs = pstmt.executeQuery();
			while (rs.next())
			 {
			 // criando o objeto GPU
				GPU cc = new GPU(); 
				sid = rs.getString("id");
				snome = rs.getString("nome");
				sfab= rs.getString("fab");
				smemoria = rs.getString("memoria");
				spreco = rs.getString("preco");
				fotogpu = rs.getBytes("foto");
            sanoLancamento = rs.getString("anoLancamento");
            smodelo = rs.getString("modelo");
            // adicionando o objeto à lista
				System.out.println("Lendo banco = " + sid+ " " + snome);
				arquivo.format("%s; %s; %s; %s; %s; %s; %s; \n" , sid, snome, sfab, smemoria, spreco, sanoLancamento ,"foto" + sid + ".jpg");
				ByteArrayToFileImage(fotogpu,sid); //gravando picture
			}
			rs.close();
			pstmt.close();
			arquivo.close();
		}
		
		catch (Exception egrava)
		{
			JOptionPane.showMessageDialog(null,
			"Erro gravando txt = " + egrava);
			return; 
		}
	}


	public void lendotxt()
	{
		try
		{
			File arquivo = new File ("tabgpu.txt");
			if(!arquivo.exists())
			{
				System.out.println ("Arquivo nao existe!!!!!");
					return;
			}
			
			Scanner sc = new Scanner (arquivo);
			sc.useDelimiter("\\s*;\\s*");
			String nomefoto = ""; 
			
			while (sc.hasNext())
			{
				sid= sc.next();
				snome = sc.next();
				smemoria = sc.next();
				smodelo = sc.next();
				spreco = sc.next();
				nomefoto = sc.next();
				System.out.println ("lendo  txt=" + sid + snome + smodelo + 
				smemoria);
				GPU ppp = new GPU(); 
				ppp.setId(Integer.parseInt(sid));
				ppp.setNome(snome);
				ppp.setModelo(smodelo);
				ppp.setMem(Integer.parseInt(smemoria));
				double vpreco = Double.parseDouble(spreco);
				ppp.setPreco (vpreco);
				fotogpu = imageToByte(nomefoto);
				
				if (procura(ppp))
					altera (ppp);
				else
					adiciona (ppp);
			} //while
			sc.close();
		}
		
		catch (Exception eler)
		{
			JOptionPane.showMessageDialog(null, "Erro leitura txt = " + eler);
			return;
		}
	}

   public static void main(String args[])
   {  
         BancoxSql b=new BancoxSql("gpu.db");
         b.conectar();
         GPU g=new GPU();       
         /* 
         g.setNome("GTX 1080"); 
         g.setFab("NVIDIA");
         g.setMem(8);
         g.setPreco(3000.0);
         g.setAnoLancamento("2016");         
         g.setModelo("GTX 1080");

         g.setFoto(imageToByte("gtx1080.jpeg"));
         if (g.getFoto() == null) {
            JOptionPane.showMessageDialog(null,"foto nula");
         }*/
         //b.adiciona(g); 
         //b.desconectar(); 
         //b.conectar();
         //
         //g.setFoto(fotogpu);
         /* 
         g.setFoto(imageToByte("gtx1080.jpeg"));
         if (g.getFoto() == null) {
            JOptionPane.showMessageDialog(null,"foto nula");
         }

         Lista=b.getLista();
         for (int i=0;i<Lista.size();i++)
         {
            System.out.println("-----------------");
            System.out.println("ID="+((GPU)Lista.get(i)).getId());
            System.out.println("Nome="+((GPU)Lista.get(i)).getNome());
            System.out.println("Fab="+((GPU)Lista.get(i)).getFab());
            System.out.println("Mem="+((GPU)Lista.get(i)).getMem());
            System.out.println("Preco="+((GPU)Lista.get(i)).getPreco());
            System.out.println("AnoLancamento="+((GPU)Lista.get(i)).getAnoLancamento());
            System.out.println("Modelo="+((GPU)Lista.get(i)).getModelo());
            //System.out.println("Foto="+((GPU)Lista.get(i)).getFoto());
         }
         */
         /* 
         g.setId(11);
         g.setNome("GTX 4080");
         g.setFab("NVIDIA");
         g.setMem(8);
         g.setPreco(3000.0);
         g.setAnoLancamento("2023");
         g.setModelo("GTX 1080");
         b.altera(g);

         b.exclui(g);*/
         //b.gravandotxt();
         b.lendotxt();
         b.desconectar();


   }//main

}//class
