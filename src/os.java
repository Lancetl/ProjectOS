import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Hashtable;
import java.math.*;

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
	 public static LinkedList<memoryLink> memoryStorage = new LinkedList<memoryLink>();
	 public static ArrayList<freeSpace> freeSpaceTable = new ArrayList<freeSpace>();
	 
	 
	 public static Vector<Integer> memory = new Vector(50);
	 
	 

	public static void startup(){
		sos.ontrace();
		System.out.print("gittest");
		memoryLink first = new memoryLink(0,99,0,true, true);
		memoryStorage.addFirst(first);
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
		
		freeSpaceTableBuilder();
		p[2] = addressFinder(p[3]);
			
		
			Swap(p,0);
			
		}

	
	public static void Swap(int[] p, int k){
		sos.siodrum(p[1], p[3], 0, 0);
	}
	
	public static void freeSpaceTableBuilder(){
			freeSpaceTable.clear();
			
			for(memoryLink temp : memoryStorage){
				if(temp.freeSpace == true){
					freeSpace job = new freeSpace(temp.size, temp.address, temp.job);
					freeSpaceTable.add(job);
				}
			}
	}
	public static int addressFinder(int size){
		int closet =  99;
		int temp;
		int address = 0;
		boolean foundOne = false;
		for(freeSpace job: freeSpaceTable){
			temp = job.size - size;
			if(temp <= closet && temp>=0){
				closet = temp;
				address = job.address;
				foundOne = true;
			}
		}
		if(foundOne != true){
			closet = 99;
			for(memoryLink job: memoryStorage){
				if(job.busy != true){
					temp = job.size - size;  
					if(temp <= closet && temp >=0){
						closet = temp;
						address = job.address;
					}
				}
			}
		}
	return address;
	}
}








