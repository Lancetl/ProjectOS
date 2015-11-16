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
	 public static ArrayList<int[]> JobTable=new ArrayList<int[]>(50);
	 public static int[] mask = new int [50];
	 public static int index=0;
	 public static int BAfreespace=0;//baseaddress of freespace

	 /*
	  * array for memory management;
	  */ 
	 public static int memoryleft=100;
	 

	public static void startup(){
		sos.ontrace();
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
		
		if(MemoryManager(p) ==true){
			p[2]=0;
			BAfreespace+=p[3];// increase to point to new baseadress of free space
			JobTable.add(p); //puts job onto jobtable
			mask[index]=p[3];//puts job ID into mask 
			index++;		 // increase index, incase we need to access it we know where it is in JT
			a[0]= 1;		// set cpu to not run
		}
	}

	public static void Dskint (int []a, int []p){
		
	}

	/*
	 * Drum interrup: transfer between Drum and memory is done
	 */
	public static void Drmint (int []a, int []p){
		
		a[0]=2;
		p[4]=5;// time slice

	}

	public static void Tro (int []a, int []p){
		
		
		a[0]=1;
		p[2]=50;
		Swap(p,1);

		
	}

	public static void Svc (int []a, int []p){
		if(a[0] == 5)
			a[0]=1;
		else 
			if(a[0] == 6){
				sos.siodisk(p[1]);
			a[0]=2;
			}
			else
				if(a[0] == 7){
					a[0]=1;
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
	public  static boolean  MemoryManager(  int[] p){
			
			if(firstFit(p[3]) == true){
			Swap(p,0);
			return true;
			}
			else 
				return false;			
		}

	// swap job in or out of memory
	// 0 == from drum into memory
	// 1== fron disk to drum?
	public static void Swap(int[] p, int k){
		sos.siodrum(p[1], p[3], 0, k);
	}
	
	/*
	 * checks if the size of job is greater less than memory left
	 * if it is, decrease memory size by job size and return true
	 */
	public static boolean firstFit(int jobSize){
		 //subtract size from memory
		
		if(memoryleft >=jobSize ){
			memoryleft-=jobSize;
		return true;
		}
		else
			return false;
	}
}