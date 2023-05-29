import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class main {
	 static process [] processes ;
	 static boolean allDone (process [] p) {		// checks for all processes execution completion
		 boolean check = true ;
		 for(int i =0 ; i<processes.length ;i++) {
			 if(processes[i].getBT() != 0) {
				 check = false ;	// still need to be executed
				 break;
			 }
		 }
		 return check;
	 }
	 static void RR(process [] all , int tq , int CTswitch) {
		int time = 0 ;
		process currentContext = null;
		Queue <process> request = new LinkedList <process> ();
		
		// --------------Processes Execution Order--------------
		System.out.println("-------------- Processes Execution Order --------------");
		System.out.println(" Process name	  |    From    |	To");
		System.out.println("-------------------------------------------");
		while( ! allDone(processes)) {	// CPU processes execution order
			for(int i = 0 ; i<processes.length ; i++) {		// check for waiting processes
				if(processes[i].getAT() <= time && processes[i].getStatus()) {
					processes[i].setwaitTime(time - processes[i].getAT()); 	// waiting time before execution
					request.add(processes[i]) ;
					processes[i].setStatus(false);
				}
			}
			if(currentContext != null) {
				for(int i =0 ; i<processes.length ; i++) {
					if( processes[i].getname().equals(currentContext.getname()) ){
						request.add(processes[i]);
						break;
					}
				}
				currentContext = null;
			}
			if(! request.isEmpty()) {
				System.out.print("\t" + request.peek().getname() + "\t|\t"+time+"\t|");
	
				
				
				if(request.peek().getBT() > tq) {
					time += tq;
					currentContext = request.peek();
					request.peek().setBT( request.peek().getBT()-tq );
					request.peek().trdInc(CTswitch); 		// adding context switching time to the process turnarround time
					request.poll();
					for(int i =0 ; i<processes.length ;i++) {
						if(request.contains(processes[i])) {
							processes[i].setwaitTime(  processes[i].getwaitTime()+tq+CTswitch); 	// increase waiting time
						}
					}
					time = time + CTswitch ;			// adding context switching time to the clock ( time )
					System.out.println("\t" + time);		// interruption time
					continue;
				}else {
					int execTime = request.peek().getBT();
					time += request.peek().getBT();
					request.peek().setBT(0);
					request.peek().trdInc(CTswitch);
					request.poll();
					for(int i =0 ; i<processes.length ;i++) {
						if(request.contains(processes[i])) {
							processes[i].setwaitTime(  processes[i].getwaitTime()+ execTime + CTswitch); 	// increase waiting time
						}
					}
					time = time + CTswitch ;			// adding context switching time to the clock ( time )
					System.out.println("\t" + time);
					continue;
				}
			}else {
				time = time +1 ;
			}
			 
		}
		// -------------- Processes Analysis --------------
		System.out.println("-------------- Processes Analysis --------------");
		System.out.println("Process name   |    waiting time     |    Turnaround time");
		System.out.println("----------------------------------------------------------");
		int totalwait = 0 ;
		int totaltrd = 0 ;
		for(int i =0 ; i<processes.length ;i++) {
			processes[i].settrd();
			totalwait = totalwait + processes[i].getwaitTime();
			totaltrd = totaltrd + processes[i].gettrd() ;
			System.out.print("\t"+processes[i].getname());
			System.out.print("\t|\t"+processes[i].getwaitTime());
			System.out.println("\t|\t\t" + processes[i].gettrd());
		}
		System.out.println();
		System.out.println("Average waiting time : " + (totalwait/processes.length));
		System.out.println("Average Turnaround time: " + (totaltrd/processes.length));
		
	}
	public static void main(String[] args) {
		int num;
		String name ;
		int at  = 0;
		int bt = 0;
		int tq = 0;
		int contextSwitch = 0;
		Scanner scan = new Scanner(System.in);
		do {
			System.out.println(" Enter the number of processes : ");
			num = scan.nextInt();
			if(num <1) {
				System.out.println(" Please enter a valid number !! ");
			}else {
				processes = new process[num];
			}
		}while(num <1);
		scan.nextLine();
		for(int i =0 ; i<num ; i++) {
			System.out.println(" Name of process " + (i+1) + " : ");
			name = scan.nextLine();
			processes[i] = new process ();
			processes[i].setname(name);
			System.out.println(" arrival time of process " + (i+1) + " : ");
			at = scan.nextInt();
			processes[i].setAT(at);
			System.out.println(" burst time of process " + (i+1) + " : ");
			bt = scan.nextInt();
			processes[i].setBT(bt);
			scan.nextLine();
		}
		System.out.println(" time quantum  (for RR)  : ");
		tq = scan.nextInt();
		System.out.println(" Context switch time (for RR and SJF ): ");
		contextSwitch = scan.nextInt();
		RR(processes, tq , contextSwitch);
		
	}

}
