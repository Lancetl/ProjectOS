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
	 public static int[] mask = new int [50];
	 public static int index=0;
	 static int f =0;
	 /*
	  * array for memory management;
	  */
	 public static memoryLinkList memoryLink = new memoryLinkList();
	 public static Vector<Integer> memory = new Vector(50);
	 
	 //freeSpaceTable implementation using hashtable
	 
	// public static Hashtable<Integer, Integer>FreeSpaceTable = new Hashtable<Integer, Integer>();
	 public static int memoryleft=100;
	 

	public static void startup(){
		sos.ontrace();
		System.out.print("gittest");
		memoryLink.createFirstLink(0,0,0, false);
	}
	
	//Interrupt handlers
	

	/*
	 * indicates the arrival of a new job on drum
	 * puts job onto JobTable 
	 * mask is used to check the index  in arraylist 
	 * a job stored(
	 */
	public static void Crint (int []a, int[] p){
		
		JobTable.add(p);
		mask[index]=p[3];
		index++;
		a[0]= 1;
		MemoryManager(p);
	}

	public static void Dskint (int []a, int []p){
		
		a[0]=2;
		p[2]=0;
		f+=20;
	}

	public static void Drmint (int []a, int []p){
		
		a[0]=2;
		p[2]=0;
		
	}

	public static void Tro (int []a, int []p){
		
		a[0]=2;
		p[2]=0;
	}

	public static void Svc (int []a, int []p){
		if(a[0] == 5)
			;
		else 
			if(a[0] == 6)
				sos.siodisk(p[1]);
			
			else
				if(a[0] == 7)
					sos.siodisk(p[1]);
					a[0]=2;
		
	}
	
	//methods called by OS only
	
	public static void MemoryManager( int []p){

			Swap(p,0);
			
		}

	
	public static void Swap(int[] p, int k){
		sos.siodrum(p[1], p[3], 0, 0);
	}
}