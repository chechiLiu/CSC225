//Che-Chi (Jack) Liu
//V00850558

import java.util.ArrayList;

public class PixelVertex {
	private boolean notVisited;
	private int x;
	private int y;
	
	ArrayList<PixelVertex> list;

	public PixelVertex(int x, int y) {
		this.x = x;
		this.y = y;
		notVisited = true;
		list = new ArrayList<PixelVertex>();
	}	
	
	public boolean visitValue() {
		return notVisited;
	}
	
	public void visited() {
		notVisited = false;
	}
	
	/* getX()
	   Return the x-coordinate of the pixel corresponding to this vertex.
	*/
	public int getX() {
		return x;
	}
	
	/* getY()
	   Return the y-coordinate of the pixel corresponding to this vertex.
	*/
	public int getY() {
		return y;
	}
	
	/* getNeighbours()
	   Return an array containing references to all neighbours of this vertex.
	*/
	public PixelVertex[] getNeighbours() {
		PixelVertex[] array = list.toArray(new PixelVertex[list.size()]);
		return array;
	}
	
	/* addNeighbour(newNeighbour)
	   Add the provided vertex as a neighbour of this vertex.
	*/
	public void addNeighbour(PixelVertex newNeighbour) {
		list.add(newNeighbour);
	}
	
	/* removeNeighbour(neighbour)
	   If the provided vertex object is a neighbour of this vertex,
	   remove it from the list of neighbours.
	*/
	public void removeNeighbour(PixelVertex neighbour) {
		if(list.contains(neighbour)) {
			list.remove(neighbour);
		}
	}
	
	/* getDegree()
	   Return the degree of this vertex.
	*/
	public int getDegree() {
		return list.size();
	}
	
	/* isNeighbour(otherVertex)
	   Return true if the provided PixelVertex object is a neighbour of this
	   vertex and false otherwise.
	*/
	public boolean isNeighbour(PixelVertex otherVertex) {
		if(list.contains(otherVertex)) {
			return true;
		}
		else {
			return false;
		}
	}
}
