/* PixelGraph.java
   CSC 225 - Summer 2017
   Programming Assignment 3 - Pixel Graph Data Structure

   B. Bird - 07/03/2017
*/ 

//Che-Chi Jack Liu
//V00850558

import java.awt.Color;

public class PixelGraph{
	private PixelVertex[][] input;
	
	/* PixelGraph constructor
	   Given a 2d array of colour values (where element [x][y] is the colour 
	   of the pixel at position (x,y) in the image), initialize the data
	   structure to contain the pixel graph of the image. 
	*/
	public PixelGraph(Color[][] imagePixels){
		input = new PixelVertex[imagePixels.length][imagePixels[0].length];
		
		for(int column = 0; column < imagePixels[0].length; column++) {
			for(int row = 0; row < imagePixels.length; row++) {
				input[row][column] = new PixelVertex(row, column);
			}
		}
		
		//most of the graph, last row and column not included
		for(int column = 0; column < imagePixels[0].length - 1; column++) {
			for(int row = 0; row < imagePixels.length - 1; row++) {
				if(imagePixels[row][column].equals(imagePixels[row+1][column])) {
					input[row][column].addNeighbour(input[row+1][column]);
					input[row+1][column].addNeighbour(input[row][column]);
				}
				
				if(imagePixels[row][column].equals(imagePixels[row][column+1])) {
					input[row][column].addNeighbour(input[row][column+1]);
					input[row][column+1].addNeighbour(input[row][column]);
				}
			}
		}
		
		//last row
		for(int i = 0; i < imagePixels[0].length - 1; i++) {
			if(imagePixels[imagePixels.length-1][i].equals(imagePixels[imagePixels.length-1][i+1])) {
				input[imagePixels.length-1][i].addNeighbour(input[imagePixels.length-1][i+1]);
				
				input[imagePixels.length-1][i+1].addNeighbour(input[imagePixels.length-1][i]);
			}
		}
		
		//last column
		for(int i = 0; i < imagePixels.length - 1; i++) {
			if(imagePixels[i][imagePixels[0].length-1].equals(imagePixels[i+1][imagePixels[0].length-1])) {
				input[i][imagePixels[0].length-1].addNeighbour(input[i+1][imagePixels[0].length-1]);
				
				input[i+1][imagePixels[0].length-1].addNeighbour(input[i][imagePixels[0].length-1]);
			}
		
		}
	}
	
	/* getPixelVertex(x,y)
	   Given an (x,y) coordinate pair, return the PixelVertex object 
	   corresponding to the pixel at the provided coordinates.
	   This method is not required to perform any error checking (and you may
	   assume that the provided (x,y) pair is always a valid point in the 
	   image).
	*/
	public PixelVertex getPixelVertex(int x, int y){
		return input[x][y];
	}
	
	/* getWidth()
	   Return the width of the image corresponding to this PixelGraph 
	   object.
	*/
	public int getWidth(){
		return input[0].length;
	}
	
	/* getHeight()
	   Return the height of the image corresponding to this PixelGraph 
	   object.
	*/
	public int getHeight(){
		return input.length;
	}
	
}