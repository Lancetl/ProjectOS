import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.Hashtable;


public class os {
		
	/*
	 * ArrayList to hold out PCB
	 * mask is used to tell us what index jobs are stored in our arraylist
	 * index is used to push jobsID onto mask.
	 */
	// public static Vector<Vector<Integer>> JobTable = new Vector<Vector<Integer>>(50);
	 public static ArrayList<job> jobTable=new ArrayList<job>(50);
	 public static ArrayList<int[]> jobTable2=new ArrayList<int[]>(50);
	  public static memoryLink memoryLink;
	 
	 //variables to use as flags
	 public static int index=0;//used tell where to add jobs in job table
	 public static int trans =0;
	 public static int swapping=0;
	 public static int longTermtracker=0;
	 public static boolean Drumisbusy=false;
	 
	 /*
	  * array for memory management;
	  */ 
	 public static int memoryleft=100;
	 

	public static void startup(){
		sos.ontrace();
		memoryLink = new memoryLink();

	}
	
	//Interrupt handlers
	

	/*
	 * indicates the arrival of a new job on drum
	 * puts job onto JobTable 
	 * mask is used to check the index in arraylist 
	 * a job stored(
	 */
	public static void Crint (int []a, int[] p){
		MemoryManager(p);
		if (swapping==1){ //set swapping to 1 so job in memeory can run if any
			a[0]=2;
			p[2]=longTerm().getAddress();
			p[3]=longTerm().getSize();
		}
		else{
			a[0]=1;		//if no jobs are in memory
			swapping=1; //set cpu to not idle and increase swap to 1 (since a job will be swapped in soon)
		}
		
	}

	public static void Dskint (int []a, int []p){
		trackTime(p,1);
		updateSecond(p);
		doIo(p,false);
		cpuScheduler.roundRobin(jobTable,p,index-1);
		System.out.println(print(p));

	}

	/*
	 * Drum interrup: transfer between Drum and memory is done
	 */
	public static void Drmint (int []a, int []p){
		updateEntered(p);
		Drumisbusy=false;
		if(swapping == 1){//if it is in memory
			trackTime(p,1);
			cpuScheduler.roundRobin(jobTable,p,index-1);
			a[0]=2;
			p[2]=memoryLink.addressFinder(p[3]);
			}
		else{
			a[0]=1;
		}

		}

	public static void Tro (int []a, int []p){
		
		if(timeup(p) == false){
			trackTime(p,0);
			trackTime(p,1);
			if(timeup(p) == false){
				p=jobTable2.get(longTermtracker);
				cpuScheduler.roundRobin(jobTable,p,index-1);
				a[0]=2;
				if(jobTable.size() >1)
					longTermtracker++;
			}
			else{
				memoryLink.Terminate(p[1]);
				remove(p);
				index--;
				swapping=0;
				a[0]=1;
			}
		}
			else{
				memoryLink.Terminate(p[1]);
				remove(p);
				index--;
				swapping=0;
				a[0]=1;
			}
	}
	

	public static void Svc (int []a, int []p){
		if(a[0] == 5){
			a[0]=1;
			swapping=0;
			index--;
			memoryLink.Terminate(p[1]);
			remove(p);

		}
		else 
			if(a[0] == 6){
				
				doIo(p,true);
				cpuScheduler.latchedroundRobin(jobTable,p,index-1);//gives time quantum
				setnewJob(p);
				sos.siodisk(p[1]);
				a[0]=2;
				//updateTracker(p);
			}
			else
				if(a[0] == 7){
					trackTime(p,0);
					if(doingIo(p) == true)
					a[0]=1;
					else{
						a[0]=2; 
					}
				}
	}

	
	//methods called by OS only
	
	/*
	 * MemoryManager calls enoughMemory() to see
	 * if there is enough memory in SoS memory
	 * if there is, it swaps job into the memory
	 * and returns true
	 * if not it returns false
	 */
	public static void MemoryManager(int[] p){
			memoryLink.printShit();
			jobTable2.add(p);
			memoryLink.freeSpaceTableBuilder();
			p[2] = memoryLink.addressFinder(p[3]);
			memoryLink.addTooMemory(p[2],p[3],p[1]);
			memoryLink.merge();
			
			job job = new job(p[1],p[2],p[3],p[4],p[5]);// create new job object
			jobTable.add(index,job);// adds job to JobTable
			index++;// increase index to where next job is going to coming in
			
			if (Drumisbusy == false){
				Drumisbusy = true;
				Swap(p,0);	 
			}
			memoryLink.printShit();
				/*
			if(firstFit(p[3]) == true){
			Swap(p,0);
			return true;
			}
			else 
				return false;
				*/			
		}

	// swap job in or out of memory
	// 0 == from drum into memory
	// 1== fron disk to drum?
	public static void Swap(int[] p, int k){
		trans=k;				//set trans to 0 or 1 so we can decide what to set a when drumint is called
		sos.siodrum(p[1], p[3], p[2], k);
	}
	
	/*
	 * checks if the size of job is greater less than memory left
	 * if it is, decrease memory size by job size and return true
	 */
	public static boolean firstFit(int jobSize){
		 //subtract size from memory
		return true;
		
	}
	
	public static void doIo(int[] p, boolean val){
		for(job temp : jobTable){
			if(temp.getJobID() == p[1]){
				temp.setLatched(val);
				memoryLink.setLatch(val, temp.getJobID());
			}
		}
	}
	
	public static boolean doingIo(int[] p){
		for(job temp : jobTable){
			if(temp.getJobID() == p[1])
				return temp.getLatched();
		}
		return false;
	}
	
	public static void trackTime(int[] p, int flag){
		int time=0;
		for(job temp : jobTable){
			if(temp.getJobID() == p[1]){
				if(flag == 1){
					temp.setCputracker(p[5]);
				}
				else{
					time = temp.getTimeOnCpu();
					 temp.setTimeOnCpu(time+(p[5]-temp.getCputracker()) );
				}
				}
		}
	}
	//if mmaxtime exceeds xpu time
	public static boolean timeup(int[] p){
		for(job temp : jobTable){
			if(temp.getJobID() == p[1]){
				if(temp.getTimeOnCpu() >= temp.getAbsMax())
					return true;
			}
		}
		return false;
	}
	
	//remove job from arraylist
	public static void remove(int[] p){
		ArrayList<job> list = new ArrayList<job>();
		for (Iterator<job> iterator = jobTable.iterator(); iterator.hasNext(); ) {
		    job temp = iterator.next();
		    if (temp.getJobID() ==  p[1]) {
		        iterator.remove();
		    }
		}
		
	}
	
	public static job longTerm(){
		return jobTable.get(longTermtracker);
	}
	
	public static void updateEntered(int[] p){
		for(job temp : jobTable){
			if(temp.getJobID() == p[1])
				temp.setEntered(p[5]);
		}
		
	}
	
	public static void updateSecond(int[] p){
		for(job temp : jobTable){
			if(temp.getJobID() == p[1])
				temp.setSecondTracker(p[5]);
		}
	}
	public static void setnewJob(int[] p){
		for(job temp : jobTable){
			if(temp.getJobID() == p[1]){
				temp.setNewJob(false);
			}
		}
	}
	public static int print(int[] p){
		
		for(job temp : jobTable){
			if(temp.getJobID() == p[1]){
				return temp.getTimeOnCpu();
			}
		}
		return 0;
	}
}