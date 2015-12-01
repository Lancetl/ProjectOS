import java.util.ArrayList;
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
	 public static int[] mask = new int [50];
	 public static int index=0;//used tell where to add jobs in job table
	 public static int BAfreespace=0;//baseaddress of freespace
	 public static int trans=0;
	 public static boolean iopending=false;
	 public static memoryLink memoryLink;
	 /*
	  * array for memory management;
	  */ 
	 public static int memoryleft=100;
	 

	public static void startup(){
		//sos.ontrace();
		memoryLink = new memoryLink();
		System.out.print("Long live our idols");

	}
	
	//Interrupt handlers
	

	/*
	 * indicates the arrival of a new job on drum
	 * puts job onto JobTable 
	 * mask is used to check the index in arraylist 
	 * a job stored(
	 */
	public static void Crint (int []a, int[] p){
		memoryLink.printShit();
		job job = new job(p[1],p[2],p[3],p[4],p[5]);// create new job object
		jobTable.add(index,job);// adds job to JobTable
		index++;// increase index to where next job is going to coming in
						
		//Swap(p,0); job is swapped	
		
		MemoryManager(p);	
		//Swap(p,0); //job is swapped		
		a[0]=1;

	}

	public static void Dskint (int []a, int []p){
		iopending=false;
		
		trackTime(p,1);
		doIo(p,false);
		System.out.println(print(p));
		System.out.println("MAKUTA MATATA");
	}

	/*
	 * Drum interrup: transfer between Drum and memory is done
	 */
	public static void Drmint (int []a, int []p){
		
		if(trans == 0){//if it is in memory
			trackTime(p,1);
			cpuScheduler.roundRobin(jobTable,p,index-1);
			a[0]=2;
			p[2]=BAfreespace;	
			p[2]=memoryLink.addressFinder(p[3]);
			}
		else{
			a[0]=1;
		}
		System.out.println(print(p));
		System.out.println("MAKUTA 5454 MATATA");
		}

	public static void Tro (int []a, int []p){
		
		if(timeup(p) == false){
			trackTime(p,0);
			trackTime(p,1);
			if(timeup(p) == false){
			cpuScheduler.roundRobin(jobTable,p,index-1);
			a[0]=2;
			System.out.println(print(p));
			System.out.println("MAKUTA MATATA");
			}
			else{
				memoryLink.Terminate(p[1]);
				a[0]=1;
			}
		}
			else{
			a[0]=1;
			memoryLink.Terminate(p[1]);
			}
	}
	

	public static void Svc (int []a, int []p){
		if(a[0] == 5){
			//trackTime(p,0);
			a[0]=1;
			jobTable.remove(p);
			memoryLink.Terminate(p[1]);
			System.out.println(print(p));
			System.out.println("MAKUTA MATATA");
		}
		else 
			if(a[0] == 6){
				iopending=true;
				cpuScheduler.roundRobin(jobTable,p,index-1);
				doIo(p,true);
				cpuScheduler.roundRobin(jobTable,p,index-1);//gives time quantum
				sos.siodisk(p[1]);
				a[0]=2;
				System.out.println(print(p));
				System.out.println("MAKUTA MATATA");
			}
			else
				if(a[0] == 7){
					if(iopending == true)
					trackTime(p,0);
					if(doingIo(p) == true)
					a[0]=1;
					else{
						System.out.print("MAY THEY NEVER BE OUR RIVAL");
						a[0]=2; 
					}
				System.out.println(print(p));
				System.out.println("MAKUTA MATATA");
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
		
			memoryLink.freeSpaceTableBuilder();
			p[2] = memoryLink.addressFinder(p[3]);
			memoryLink.addTooMemory(p[2],p[3],p[1]);
			memoryLink.merge();
			
			job job = new job(p[1],p[2],p[3],p[4],p[5]);// create new job object
			jobTable.add(index,job);// adds job to JobTable
			index++;// increase index to where next job is going to coming in
			
			Swap(p,0);
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
		sos.siodrum(p[1], p[3], 0, k);
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
			if(temp.getJobID() == p[1])
				temp.setLatched(val);
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
	public static int print(int[] p){
		
		for(job temp : jobTable){
			if(temp.getJobID() == p[1]){
				return temp.getTimeOnCpu();
			}
		}
		return 0;
	}
}