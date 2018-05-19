/* A5Algorithms.java
   CSC 225 - Summer 2017
   Programming Assignment 3 - Image Algorithms


   B. Bird - 07/03/2017
*/ 

//Che-Chi Jack Liu
//V00850558
   
import java.awt.Color;
import java.util.*;

public class A3Algorithms{
	/* FloodFillDFS(v, viewer, fillColour)
	   Traverse the component the vertex v using DFS and set the colour 
	   of the pixels corresponding to all vertices encountered during the 
	   traversal to fillColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			viewer.setPixel(x,y,c);
	*/
	public static void FloodFillDFS(PixelVertex v, ImageViewer225 viewer, Color fillColour){
		v.visited();
		viewer.setPixel(v.getX(), v.getY(), fillColour);

		for(PixelVertex p: v.getNeighbours()) {
			if(p.visitValue()) {
				FloodFillDFS(p, viewer, fillColour);
			}
		}
	}
	
	/* FloodFillBFS(v, viewer, fillColour)
	   Traverse the component the vertex v using BFS and set the colour 
	   of the pixels corresponding to all vertices encountered during the 
	   traversal to fillColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			viewer.setPixel(x,y,c);
	*/
	public static void FloodFillBFS(PixelVertex v, ImageViewer225 viewer, Color fillColour){
		Queue<PixelVertex> queue = new LinkedList<PixelVertex>();
		queue.add(v);
		v.visited();
			
		while(queue.peek() != null) {
			PixelVertex r = queue.remove();	
			viewer.setPixel(r.getX(), r.getY(), fillColour);
			for(PixelVertex p: r.getNeighbours()) {
				if(p.visitValue()) {
					p.visited();
					queue.add(p);
				}
			}
		}
	}
	
	/* OutlineRegionDFS(v, viewer, outlineColour)
	   Traverse the component the vertex v using DFS and set the colour 
	   of the pixels corresponding to all vertices with degree less than 4
	   encountered during the traversal to outlineColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			viewer.setPixel(x,y,c);
	*/
	public static void OutlineRegionDFS(PixelVertex v, ImageViewer225 viewer, Color outlineColour){
		v.visited();
		if (v.getDegree() < 4) {
			viewer.setPixel(v.getX(), v.getY(), outlineColour);
		}	

		for(PixelVertex p: v.getNeighbours()) {
			if(p.visitValue()) {
				OutlineRegionDFS(p, viewer, outlineColour);
			}
		}
	}
	
	/* OutlineRegionBFS(v, viewer, outlineColour)
	   Traverse the component the vertex v using BFS and set the colour 
	   of the pixels corresponding to all vertices with degree less than 4
	   encountered during the traversal to outlineColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			viewer.setPixel(x,y,c);
	*/
	public static void OutlineRegionBFS(PixelVertex v, ImageViewer225 viewer, Color outlineColour){
		Queue<PixelVertex> queue = new LinkedList<PixelVertex>();
		queue.add(v);
		v.visited();
			
		while(queue.peek() != null) {
			PixelVertex r = queue.remove();	
			if(r.getDegree() < 4) {
				viewer.setPixel(r.getX(), r.getY(), outlineColour);
			}	
			for(PixelVertex p: r.getNeighbours()) {
				if(p.visitValue()) {
					p.visited();
					queue.add(p);
				}
			}
		}
	}

	public static void RecursiveDFS (PixelGraph G, int[][] array, int componentNum, PixelVertex v) {
		array[v.getX()][v.getY()] = componentNum;
		for(PixelVertex p: v.getNeighbours()) {
			if(array[p.getX()][p.getY()] == -1) {
				RecursiveDFS(G, array, componentNum, p);
			}
		}
	}
	/* CountComponents(G)
	   Count the number of connected components in the provided PixelGraph 
	   object.
	*/
	public static int CountComponents(PixelGraph G){
		int[][] array = new int[G.getHeight()][G.getWidth()];
		for(int[] i: array) {
			Arrays.fill(i, -1);
		}
		int componentNum = 0;
		
		for(int column = 0; column < array[0].length; column++) {
			for(int row = 0; row < array.length; row++) {
				if(array[row][column] == -1) {
					RecursiveDFS(G, array, componentNum, G.getPixelVertex(row, column));
					componentNum = componentNum+1;
				}
			}
		}
		return componentNum;
	}
}