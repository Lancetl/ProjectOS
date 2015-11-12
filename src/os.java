import java.util.ArrayList;
import java.util.Vector;
import java.util.Hashtable;


public class os {
		
	/*
	 * ArrayList to hold out PCB
	 * AL was chosen jobs do not have to be in order
	 * thus we can stick everything in the end for O(1) amortized time
	 * subject to change
	 */
	// public static Vector<Vector<Integer>> JobTable = new Vector<Vector<Integer>>(50);
	 public static ArrayList<int[]> JobTable=new ArrayList<int[]>(50);
	 
	 
	 /*
	  * array for memory management;
	  */
	 
	 public static Vector<Integer> memory = new Vector(50);
	 
	 //freeSpaceTable implementation using hashtable
	 
	 public static Hashtable<Integer, Integer>FreeSpaceTable = new Hashtable<Integer, Integer>();
	 public static int memoryleft=100;
	 

	public static void startup(){
		//sos.ontrace();
		System.out.print("gittest");

	}
	
	//Interrupt handlers
	

	/*
	 * indicates the arrival of a new job on drum
	 * puts job onto JobTable (vector of vectors)
	 * numofJobs- global variable used to access job
	 * 
	 */
	public static void Crint (int []a, int[] p){
		
		JobTable.add(p);
		a[0]= 2;
		MemoryManager(p);
	}

	public static void Dskint (int []a, int []p){
		
	}

	public static void Drmint (int []a, int []p){
		
	}

	public static void Tro (int []a, int []p){
		
	}

	public static void Svc (int []a, int []p){
		
	}
	
	//methods called by OS only
	
	public static void MemoryManager( int []p){
	
		if(p[3]<memoryleft){
			int count =p[3];
			while(count !=0){
			memory.add(p[1]);
			count--;
			memoryleft--;
			Swap(p);
			}
		}
	

	}
	
	public static void Swap(int[] p){
		sos.siodrum(p[1], 0, p[3], 9);
	}
}