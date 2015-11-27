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
		sos.ontrace();
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
		
		job job = new job(p[1],p[2],p[3],p[4],p[5]);// create new job object
		jobTable.add(index,job);// adds job to JobTable
		index++;// increase index to where next job is going to coming in
		MemoryManager(p);	
		//Swap(p,0); //job is swapped		
		a[0]=1;

	}

	public static void Dskint (int []a, int []p){
		iopending=false;
		
	}

	/*
	 * Drum interrup: transfer between Drum and memory is done
	 */
	public static void Drmint (int []a, int []p){
		if(trans == 0){//if it is in memory
			cpuScheduler.roundRobin(jobTable,p,index-1);
			a[0]=2;
			p[2]=BAfreespace;	
			}
		else
			a[0]=1;
	}

	public static void Tro (int []a, int []p){
			
			cpuScheduler.roundRobin(jobTable,p,index-1);
			a[0]=2;
	
		
	}

	public static void Svc (int []a, int []p){
		
		if(a[0] == 5){
			a[0]=1;
			jobTable.remove(p);
		}
		else 
			if(a[0] == 6){
				iopending=true;
				cpuScheduler.roundRobin(jobTable,p,index-1);
				sos.siodisk(p[1]);
				a[0]=2;
			}
			else
				if(a[0] == 7){
					if(iopending == true)
					a[0]=1;
					else{
						System.out.print("MAY THEY NEVER BE OUR RIVAL");
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
			memoryLink.freeSpaceTableBuilder();
			p[2] = memoryLink.addressFinder(p[3]);
			memoryLink.addTooMemory(p[2],p[3],p[1]);
			memoryLink.merge();
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
}