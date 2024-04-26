package fp.Dataframe;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fp.Dataframe.DataFrame;
import fp.Dataframe.*;
import us.lsi.tools.Enumerate;
import us.lsi.tools.List2;
import us.lsi.tools.Stream2;
import us.lsi.tools.File2;
import us.lsi.tools.Preconditions;

public class ExamenEntrega2  {
	
	 private List<String> columNames; // Nombres de las columnas
	    private Map<String,Integer> columIndex; // Dado el nombre de una columna indica el índice
	    private List<List<String>> rows;
	

	public static DataFrameImpl emptyDataFrame() {
        List<String> columNames= new ArrayList<>();
        List<List<String>> rows= new ArrayList<>(new ArrayList<>());
        return DataFrameImpl.of(columNames,rows);
        
    }

	public ExamenEntrega2 addDataFrame(DataFrame df) {
	    List<String> newColumnNames = new ArrayList<>(this.columNames);
	    Map<String, Integer> newColumnIndex = new HashMap<>(this.columIndex);
	    List<List<String>> newRows = new ArrayList<>(this.rows);

	    newColumnNames.addAll(df.columNames());

	    int newIndex = this.columNames.size();

	    for (List<String> row : df.rows()) {
	        List<String> newRow = new ArrayList<>(Collections.nCopies(this.columNames.size(), ""));
	        newRow.addAll(row);
	        newRows.add(newRow);
	    }


	    for (int i = 0; i < df.columNames().size(); i++) {
	        newColumnIndex.put(df.columNames().get(i), newIndex + i);
	    }

	    return ExamenEntrega2.of(newColumnNames, newColumnIndex, newRows);
	}
	//ESTE ES EL QUE PIDO PERO ME DA ERROR AL IMPRIMIR
	public ExamenEntrega2 addDataFrame2(ExamenEntrega2 df1, ExamenEntrega2 df2) {
	    List<String> newColumnNames = new ArrayList<>(df1.columNames);
	    Map<String, Integer> newColumnIndex = new HashMap<>(df1.columIndex);
	    List<List<String>> newRows = new ArrayList<>(df1.rows);

	    newColumnNames.addAll(df2.columNames);

	    int newIndex = df1.columNames.size();

	    for (List<String> row : df2.rows) {
	        List<String> newRow = new ArrayList<>(Collections.nCopies(df1.columNames.size(), ""));
	        newRow.addAll(row);
	        newRows.add(newRow);
	    }

	    for (int i = 0; i < df2.columNames.size(); i++) {
	        newColumnIndex.put(df2.columNames.get(i), newIndex + i);
	    }

	    return ExamenEntrega2.of(newColumnNames, newColumnIndex, newRows);
	}

	
	private static ExamenEntrega2 of(List<String> newColumnNames, Map<String, Integer> newColumnIndex,
			List<List<String>> newRows) {
		// TODO Auto-generated method stub
		return null;
	}

	public DataFrame removeColumByIndex(int index) {

	    if (index < 0 || index >= columNames.size()) {
	        throw new IllegalArgumentException("Índice de columna fuera de rango");
	    }


	    List<String> newColumnNames = new ArrayList<>(columNames);

	    List<List<String>> newRows = new ArrayList<>();

	    for (List<String> row : rows) {
	        List<String> newRow = new ArrayList<>(row);
	        newRow.remove(index); // Eliminar el valor en el índice de la columna
	        newRows.add(newRow); // Agregar la fila actualizada a la lista de filas
	    }
	   
	    newColumnNames.remove(index);

	    Map<String, Integer> newColumnIndex = new HashMap<>();
	    IntStream.range(0, newColumnNames.size()).forEach(i -> newColumnIndex.put(newColumnNames.get(i), i));

	    return DataFrameImpl.of(newColumnNames, newColumnIndex, newRows);
	}
	
	public List<DataFrame> divideDataFrame(DataFrame df, int ci) {
	    Preconditions.checkArgument(ci > 0 && ci < df.columNumber(), "El índice de la columna debe ser mayor que 0 y menor que el número de columnas");

	    List<DataFrame> dividedFrames = new ArrayList<>();

	    List<String> columnNames = df.columNames();
	    List<String> firstColumnNames = new ArrayList<>(columnNames.subList(0, ci + 1));
	    List<String> secondColumnNames = new ArrayList<>(columnNames.subList(ci + 1, columnNames.size()));

	    Map<String, Integer> firstColumnIndex = new HashMap<>();
	    Map<String, Integer> secondColumnIndex = new HashMap<>();

	    for (int i = 0; i < firstColumnNames.size(); i++) {
	        firstColumnIndex.put(firstColumnNames.get(i), i);
	    }

	    for (int i = 0; i < secondColumnNames.size(); i++) {
	        secondColumnIndex.put(secondColumnNames.get(i), i);
	    }

	    List<List<String>> rows = df.rows();
	    List<List<String>> firstRows = new ArrayList<>();
	    List<List<String>> secondRows = new ArrayList<>();

	    for (List<String> row : rows) {
	        List<String> firstRow = new ArrayList<>(row.subList(0, ci + 1));
	        List<String> secondRow = new ArrayList<>(row.subList(ci + 1, row.size()));
	        firstRows.add(firstRow);
	        secondRows.add(secondRow);
	    }

	    DataFrame firstDataFrame = DataFrameImpl.of(firstColumnNames, firstColumnIndex, firstRows);
	    DataFrame secondDataFrame = DataFrameImpl.of(secondColumnNames, secondColumnIndex, secondRows);

	    dividedFrames.add(firstDataFrame);
	    dividedFrames.add(secondDataFrame);

	    return dividedFrames;
	}

	
	


public static void main(String[] args) {
	DataFrame df = DataFrame.parse("src/fp/Dataframe/personas.csv", 
			List.of("Id","Nombre","Apellidos","Altura","Fecha_Nacimiento"));
	
	
	DataFrame DatVacio = ExamenEntrega2.emptyDataFrame();
    System.out.println("---DATAFRAME VACIO---");
    System.out.println(DatVacio);
    
    //addDataFrame
    DataFrame d3= df.head(5);
    DataFrame dAñadido =  d3.addDataFrame(df.tail(5));
    System.out.println("---DATAFRAME AÑADIDO---:");
    System.out.println(dAñadido);
    
    //DivideDataframe
    int ci =2;
    List datDiv= df.divideDataFrame(df,ci);
    System.out.println("---DATAFRAME DIVIDIDO---:");
    System.out.println(datDiv);
    
   
    // removeColumnByIndex
    int indice= 1;
    System.out.println("---DATAFRAME SIN COLIUMNA INDICE: "+indice+ " ---");
    DataFrame DatsinIndice = df.removeColumByIndex(indice);
    System.out.println(DatsinIndice);
  

}
}
