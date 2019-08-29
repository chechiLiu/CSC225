//Che-Chi (Jack) Liu
//V00850558

//The NiceSimulator data structure.

import java.io.*;
import java.util.*;
import java.lang.*;

public class NiceSimulator{
	public static final int SIMULATE_IDLE = -2;
	public static final int SIMULATE_NONE_FINISHED = -1;
	
	int max;
	
	HashMap<Integer, Integer> time = new HashMap<Integer, Integer>();
	HashMap<Integer, Integer> priority = new HashMap<Integer, Integer>();
	
	/* Constructor(maxTasks)
	   Instantiate the data structure with the provided maximum 
	   number of tasks. No more than maxTasks different tasks will
	   be simultaneously added to the simulator, and additionally
	   you may assume that all task IDs will be in the range
	     0, 1, ..., maxTasks - 1
	*/
	public NiceSimulator(int maxTasks){
		max = maxTasks;
	}
	
	/* taskValid(taskID)
	   Given a task ID, return true if the ID is currently
	   in use by a valid task (i.e. a task with at least 1
	   unit of time remaining) and false otherwise.
	   
	   Note that you should include logic to check whether 
	   the ID is outside the valid range 0, 1, ..., maxTasks - 1
	   of task indices.
	
	*/
	public boolean taskValid(int taskID) {
		Integer value = time.get(taskID);
		
		if((taskID >= 0 && taskID <= max-1)) {
			if(value == null) {
				return false;
			} 
			else if(value >= 1) {
				return true;
			}
		}
	
		return false;
	}
	
	//Find the taskID with the lowest priority, if have the same priority, the lowest taskID is returned
	public int findID(HashMap<Integer, Integer> map) {
		int min = max - 1;
		
		int minValueInMap = Collections.min(map.values());

        	for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
        		if(entry.getValue() == minValueInMap && entry.getKey() < min) {
            			min = entry.getKey();
            		}
 		}
		
   		return min;
	}
	
	/* getPriority(taskID)
	   Return the current priority value for the provided
	   task ID. You may assume that the task ID provided
	   is valid.
	
	*/
	public int getPriority(int taskID) {
		return priority.get(taskID);
	}
	
	/* getRemaining(taskID)
	   Given a task ID, return the number of timesteps
	   remaining before the task completes. You may assume
	   that the task ID provided is valid.
	
	*/
	public int getRemaining(int taskID) {
		return time.get(taskID);
	}
	
	/* add(taskID, time_required)
	   Add a task with the provided task ID and time requirement
	   to the system. You may assume that the provided task ID is in
	   the correct range and is not a currently-active task.
	   The new task will be assigned nice level 0.
	*/
	public void add(int taskID, int time_required) {
		time.put(taskID, time_required);
		priority.put(taskID, 0);
	}
	
	/* kill(taskID)
	   Delete the task with the provided task ID from the system.
	   You may assume that the provided task ID is in the correct
	   range and is a currently-active task.
	*/
	public void kill(int taskID) {
		time.remove(taskID);
		priority.remove(taskID);
	}
	
	/* renice(taskID, new_priority)
	   Change the priority of the the provided task ID to the new priority
       value provided. The change must take effect at the next simulate() step.
	   You may assume that the provided task ID is in the correct
	   range and is a currently-active task.
	*/
	public void renice(int taskID, int new_priority) {
		priority.put(taskID, new_priority);
	}
	
	/* simulate()
	   Run one step of the simulation:
		 - If no tasks are left in the system, the CPU is idle, so return
		   the value SIMULATE_IDLE.
		 - Identify the next task to run based on the criteria given in the
		   specification (tasks with the lowest priority value are ranked first,
		   and if multiple tasks have the lowest priority value, choose the 
		   task with the lowest task ID).
		 - Subtract one from the chosen task's time requirement (since it is
		   being run for one step). If the task now requires 0 units of time,
		   it has finished, so remove it from the system and return its task ID.
		 - If the task did not finish, return SIMULATE_NONE_FINISHED.
	*/
	public int simulate() {
		if(time.isEmpty()) {
			return SIMULATE_IDLE;
		}

		Integer id = findID(priority);
		
		//subtract one from the chosen task's time requirement
		time.put(id, (time.get(id)-1));

		if(time.get(id).equals(0)) {
			time.remove(id);
			priority.remove(id);
			
			return id.intValue();
		}
		else {
			return SIMULATE_NONE_FINISHED;
		}
	}
}
