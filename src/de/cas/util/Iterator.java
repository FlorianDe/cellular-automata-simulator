package de.cas.util;

public class Iterator <T> {
	
	//Dibo fragen, ob es hier einen besseren Trick gibt...sieht sehr ugly aus

	public interface IFunction_0_parameter{
		<T> T apply();
    }
    public void iterator(T[][] array2D, IFunction_0_parameter f) { 
        for(int y = 0; y < array2D.length; y++)
            for (int x = 0; x < array2D[y].length; x++)
            	array2D[y][x] = f.apply();
    }
    
    
	public interface IFunction_1_parameter{
		<T> void apply(T cell);
    }
    public void iterator(T[][] array2D, IFunction_1_parameter f) { 
        for(int y = 0; y < array2D.length; y++)
            for (int x = 0; x < array2D[y].length; x++)
               f.apply(array2D[y][x]);
    }
    
    
	public interface IFunction_3_parameter{
		<T> T apply(T cell, int y, int x);
    }
    public void iterator(T[][] array2D, IFunction_3_parameter f) { 
        for(int y = 0; y < array2D.length; y++)
            for (int x = 0; x < array2D[y].length; x++)
            	array2D[y][x] = f.apply(array2D[y][x], y, x);
    }
}
