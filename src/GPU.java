class GPU {
	private int id_gpu;
	private String nome_gpu,fab_gpu;
	private int mem_gpu; 
	private double preco_gpu;
	private String anoLancamento_gpu;
	private String modelo_gpu;
	private byte[] foto_gpu; 
	
	GPU()
	{
		setId(0);
		setNome("");
		setFab ("");
		setMem(0);
		setPreco(0.0);
		setFoto(null);
	}

	public void setFoto(byte[] f)
	{
		this.foto_gpu = f;
	}

	public byte[] getFoto()
	{
		return this.foto_gpu;
	}

	public void setModelo(String m)
	{
		this.modelo_gpu = m;
	}

	public String getModelo()
	{
		return this.modelo_gpu;
	}

	public void setAnoLancamento(String a)
	{
		this.anoLancamento_gpu = a;
	}

	public String getAnoLancamento()
	{
		return this.anoLancamento_gpu;
	}
	
	public void setId(int i)
	{
		this.id_gpu = i;
	}
	
	public void setNome(String n)
	{
		this.nome_gpu = n;
	}
	
	public void setFab(String f)
	{
		this.fab_gpu = f;
	}
	
	public void setMem(int m)
	{
		this.mem_gpu = m;
	}
	
	public void setPreco(double p)
	{
		this.preco_gpu = p;
	}
	
	public int getId()
	{
		return this.id_gpu;
	}
	
	public String getNome()
	{
		return this.nome_gpu;
	}
	
	public String getFab()
	{
		return this.fab_gpu;
	}
	
	public int getMem()
	{
		return this.mem_gpu;
	}
	
	public double getPreco()
	{
		return this.preco_gpu;
	}

	public void emLinha()
	{
		System.out.print("\n"+getId());
		System.out.print("\t"+getNome());
		System.out.print("\t"+getFab());
		System.out.print("\t"+getMem());
		System.out.print("\t"+getPreco());
		System.out.print("\t"+getAnoLancamento());
		System.out.print("\t"+getModelo());
		System.out.print("\t"+getFoto());
	}

	public String emLinhaString()
	{
		return getId()+"\t"+getNome()+"\t"+getFab()+"\t"+getMem()+"\t"+getPreco()+"\t"+getAnoLancamento()+"\t"+getModelo()+"\t"+getFoto();
	}
	
}
	

