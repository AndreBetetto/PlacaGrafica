import java.util.Scanner; //teclado

public class CrudGPU {
    public static String xopcao;
	public static Scanner teclado;
	public static String Scanf (String texto)
	{
		System.out.print(texto);
		xopcao = teclado.nextLine();
		return xopcao;
	}
	
	public static void MSG (String texto)
	{
		System.out.print(texto);
		teclado.nextLine();
	}
	
	public static void cls()
	{
		for(int linha = 0; linha < 20; linha++);
		System.out.println(""); 
	}
	
	public static void main (String tx[])
	{
		teclado = new Scanner(System.in);
		do
		{
			cls();	
			System.out.print("\n<<Crud - GPU >>");
			System.out.print("\n<1> - Incluir GPU......");
			System.out.print("\n<2> - Excluir GPU......");
			System.out.print("\n<3> - Listar GPU......");
			System.out.print("\n<4> - Alterar GPU......");
			System.out.print("\n<5> - Ler arquivo.txt......");
			System.out.print("\n<6> - Gravar arquivo.txt......");
			System.out.print("\n<7> - Fim programa......");
			xopcao=Scanf("\n Digite a sua opcao (1-7): ");
			
			try{
				int op = Integer.parseInt(xopcao);
				if(op < 1 || op > 7) MSG("Opção inválida!! <enter>");
				if(op==1) Incluir();
				if(op==2) Excluir();
				if(op==3) Listar();
				if(op==4) Alterar();
				if(op==5) Ler();
				if(op==6) Gravar();
				if(op == 7) System.exit(0);
				
			}catch(Exception e)
			{
				MSG("Valor inválido!! <enter>");
			}
			
		}while(true);
	}//main	
	
	public static void Incluir()
	{}
	
	public static void Excluir()
	{}
	
	public static void Alterar()
	{}
	
	public static void Listar()
	{}
	
	public static void Ler()
	{}
	
	public static void Gravar()
	{}
}
