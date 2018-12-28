package exception;
//Source: 

public class AutoException extends Exception{
	private int errorno;
	private String errormsg;

	public AutoException() {
		super();
		printmyproblem();
	}

	public AutoException(String errormsg) {
		super();
		this.errormsg = errormsg;
		printmyproblem();
	}

	public AutoException(int errorno) {
		super();
		this.errorno = errorno;
		printmyproblem();
	}

	public AutoException(int errorno, String errormsg) {
		super();
		this.errorno = errorno;
		this.errormsg = errormsg;
		printmyproblem();
		fix(errorno);
	}

	public int getErrorno() {
		return errorno;
	}

	public void setErrorno(int errorno) {
		this.errorno = errorno;
	}

	public String getErrormsg() {
		return errormsg;
	}

	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}

	public void printmyproblem() {
		System.out.println("FixProblems [errorno = " + errorno + "], [errormsg = " + errormsg + "]"); 
	}
	public void fix(int errno)
	{
		Fix1to100 f1 = new Fix1to100();
		Fix101to200 f2 = new Fix101to200();
		switch(errno)
		{
			case 1: f1.fix1(errno);break;
			case 2: break;
			case 3: break;
			case 4: break;
			case 5: break;
			case 6: break; 
			case 7: break;
			case 8: break;
			case 9: break;
	
		}
	}
}
