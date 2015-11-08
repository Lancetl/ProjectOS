
public class os {

	
	public static void startup()
	{
		
	}
	
    public void siodisk(int jobnum)
	{
		
	}
	public void siodrum(int jobnum, int jobsize, int coreaddress, int direction)
	{
		
	}
	/*public void ontrace()
	{
		
	}
	*/
	public void offtrace()
	{
		
	}
	//interrupt Handlers
	public static void Crint(int []a, int[] p)
	{
		/*indicates the arrival of a new job on the drum
		 *at call: p[0] = job number
		 *p[1] = priority
		 *p[2] = job,size
		 *p[3] = max CPU time allowed for job
		 *p[4] = current time 
		 */
	}
	public void Dskint(int []a, int[]p)
	{
		/*Disk interrupt
		 * at call: p[4] = current time
		 * 
		 */
		
	}
	public void Drmint(int []a, int []p)
	{
		/*
		 * Drum interrupt
		 * At call: p[4] = current time
		 */
	}
	public void Tro(int []a, int[]p)
	{
		/*
		 * Timer-run-out
		 * At Call :p[4] = current time
		 */
	}
	public void Svc(int  a, int[]p)
	{
		/*Supervisor call from user program
		 * At call p[4] = current time
		 * a = 5 => job has terminated
		 * a = 6 => job request disk i/o
		 * a = 7 => job wants to be blocked until all its pending
		 * i/o requests are completed
		 */
	}
	
}
