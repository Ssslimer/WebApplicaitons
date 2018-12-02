public enum TestFunction
{
	ROSENBROCK("(1-par0)^2+100*(par1-par0^2)^2"),
	MATYAS("0.26*(par0^2+par1^2)-0.48*par0*par1"),
	BEALE("(1.5-par0+par0*par1)^2+(2.25-par0+par0*par1^2)^2+(2.625-par0+par0*par1^3)^2"),
	THREE_HUMP_CAMEL("2*par0^2-1.05*par0^4+par0^6/6+par0*par1+par1^2"),
	BOOTH("(par0+2*par1-7)^2+(2*par0+par1-5)^2"),
	GOLDSTEIN_PRICE("(1+(par0+par1+1)^2*(19-14*par0+3*par0^2-14*par1+6*par0*par1+3*par1^2))*(30+(2*par0-3*par1)^2*(18-32*par0+12*par0^2+48*par1-36*par0*par1+27*par1^2))"),
	ACKLEY("-20*exp(-0.2*(0.5*(par0^2+par1^2))^0.5)-exp(0.5*(cos(2*pi*par0)+cos(2*pi*par1)))+e+20");
	
	private String s;
	
	private TestFunction(String s)
	{
		this.s = s;
	}
	
	public String asString()
	{
		return s;
	}
}