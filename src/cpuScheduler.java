import java.util.ArrayList;


import java.util.ArrayList;


public class  cpuScheduler {
	
	static int timeslice=200;

	public static void roundRobin(ArrayList jobTable,int p[],int index){
				job job;
				job =(job) jobTable.get(index);
				int max=job.getTimeOnCpu();
				if (job.getAbsMax()-job.getTimeOnCpu() < timeslice){
					p[4]=job.getAbsMax()-job.getTimeOnCpu();
				}
					else
						p[4]=timeslice;
	}
	
	public static void latchedroundRobin(ArrayList jobTable,int p[],int index){
		job job;
		job =(job) jobTable.get(index);
		if (job.isNewJob()==true)
			p[4]=job.getAbsMax()-(p[5]-job.getEntered());
			else
				p[4]=job.getAbsMax()-((p[5]-job.getSecondTracker())+job.getTimeOnCpu());
		
	}
}
