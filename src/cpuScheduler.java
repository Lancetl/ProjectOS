import java.util.ArrayList;


public class  cpuScheduler {
	
	static int timeslice=5;

	public static void roundRobin(ArrayList jobTable,int p[],int index){
				job job;
				job =(job) jobTable.get(index);
				int max=job.getMax();
				if (max>5){
					p[4]=timeslice;
					job.setMax(max-=timeslice);
				}
					else
						p[4]=max;
	}
}
			
