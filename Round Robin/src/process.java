
public class process {
	String name ;
	int AT ;	// arrival time 
	int BT ;	// burst time
	int beforeExecBT = 0 ;// burst time before execution
	int Turnarround = 0;// turnaround time
	boolean waiting = true;
	int waitingTime =0;
	public void setAT( int x) {
		AT = x ;
	}
	public void setBT( int x) {
		BT = x ;
		if(beforeExecBT == 0) {
			beforeExecBT = BT;
		}
	}
	public void setname(String n) {
		name = n;
	}
	public void setStatus(boolean x) {
		waiting = x ;
	}
	public void setwaitTime(int t) {
		waitingTime = t;
	}
	public void trdInc(int x ) {
		Turnarround = Turnarround + x ;
	}
	public void settrd() {
		Turnarround = Turnarround + beforeExecBT + waitingTime ;
	}
	public int gettrd() {
		return Turnarround ;
	}
	public int getwaitTime() {
		return waitingTime;
	}
	public boolean getStatus() {
		return waiting ;
	}
	public String getname() {
		return name;
	}
	public int getAT(){
		return AT ;
	}
	public int getBT() {
		return BT;
	}
}
