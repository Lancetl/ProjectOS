import java.util.ArrayList;


public class  cpuScheduler {
	
	static int timeslice=10;

	public static void roundRobin(ArrayList jobTable,int p[],int index){
				job job;
				job =(job) jobTable.get(index);
				int max=job.getMax();
				if (max>timeslice){
					p[4]=timeslice;
					job.setMax(max-=timeslice);
				}
					else
						p[4]=1;
	}
}
			
