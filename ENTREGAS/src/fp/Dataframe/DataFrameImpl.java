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

import us.lsi.tools.Enumerate;
import us.lsi.tools.List2;
import us.lsi.tools.Stream2;
import us.lsi.tools.File2;
import us.lsi.tools.Preconditions;

public class DataFrameImpl implements DataFrame {
    // --------------------
    // Atributos
    private List<String> columNames; // Nombres de las columnas
    private Map<String,Integer> columIndex; // Dado el nombre de una columna indica el índice
    private List<List<String>> rows; // Lista de las filas
    // --------------------
    // Constructores
    private DataFrameImpl(List<String> columNames, Map<String, Integer> columIndex, List<List<String>> rows) {
        // Se inicializan los atributos, pero se asignan copias de los parámetros y no los parámetros en sí mismos
        // Inicialización de los atributos
        this.columNames = new ArrayList<>(columNames);
        this.columIndex = new HashMap<>(columIndex);
        this.rows = new ArrayList<>();
        // Clonar las filas para asegurar la inmutabilidad del DataFrame
        for (List<String> row : rows) {
            this.rows.add(new ArrayList<>(row));
        }
    }
    // --------------------
    // Métodos de factoría
    private static DataFrameImpl of(List<String> columNames, Map<String,Integer> columIndex, List<List<String>> rows) {
        // Se calcula a partir del constructor de manera directa
        return new DataFrameImpl(columNames, columIndex, rows);
    }
    
    public static DataFrameImpl of(Map<String,List<String>> data) {
        List<String> columNames = new ArrayList<>(data.keySet());
        List<List<String>> rows = new ArrayList<>();
        for (int i = 0; i < data.get(columNames.get(0)).size(); i++) {
            List<String> row = new ArrayList<>();
            for (String col : columNames) {
                row.add(data.get(col).get(i));
            }
            rows.add(row);
        }
        return DataFrameImpl.of(columNames, rows);
    }

   

    public static DataFrameImpl of(Map<String, List<String>> data, List<String> columnNames) {
        for (String columnName : columnNames) {
            Preconditions.checkArgument(data.containsKey(columnName), "Column not found in data: " + columnName);
        }
        Map<String, Integer> columIndex = new HashMap<>();
        int index = 0;
        for (String columnName : columnNames) {
            columIndex.put(columnName, index);
            index++;
        }

        List<List<String>> rows = new ArrayList<>();
        int numRows = data.values().stream().mapToInt(List::size).max().orElse(0);
        for (int i = 0; i < numRows; i++) {
            List<String> row = new ArrayList<>();
            for (String columnName : columnNames) {
                List<String> columnData = data.get(columnName);
                String value = (i < columnData.size()) ? columnData.get(i) : null;
                row.add(value);
            }
            rows.add(row);
        }

        return new DataFrameImpl(columnNames, columIndex, rows);
    }

    public static DataFrameImpl parse(String file) {
        List<String> columNames;
        List<List<String>> rows;
        Map<String, List<String>> data = File2.mapDeCsv(file);
        columNames = new ArrayList<>(data.keySet());
        rows = new ArrayList<>();
        for (int i = 0; i < data.get(columNames.get(0)).size(); i++) {
            List<String> row = new ArrayList<>();
            for (String col : columNames) {
                row.add(data.get(col).get(i));
            }
            rows.add(row);
        }
        return DataFrameImpl.of(columNames, rows);
    }

    public static DataFrameImpl parse(String file, List<String> columNames) {
        Map<String, List<String>> data = File2.mapDeCsv(file);
        return DataFrameImpl.of(data, columNames);
    }

    public static DataFrameImpl of(List<String> columNames, List<List<String>> rows) {
        Map<String,Integer> columIndex = new HashMap<>();
        IntStream.range(0,columNames.size()).forEach(i->columIndex.put(columNames.get(i),i));
        return DataFrameImpl.of(columNames,columIndex,rows);
    }

    public static Boolean allDifferent(List<String> values) {
		Integer n = values.size();
		Integer m = values.stream().collect(Collectors.toSet()).size();
        return n.equals(m);
    }
	// Método auxiliar para la propiedad groupBy
	public static String string(Object r) {
		String s = null;
		if(r instanceof LocalDate) {
			LocalDate r1 = (LocalDate) r;
			s = r1.format(DataFrame.dateFormat());
		} if(r instanceof LocalTime) {
			LocalTime r1 = (LocalTime) r;
			s = r1.format(DataFrame.timeFormat());
		} if(r instanceof LocalDateTime) {
			LocalDateTime r1 = (LocalDateTime) r;
			s = r1.format(DataFrame.dateTimeFormat());
		} else if(r instanceof Double) {
			s = String.format("%.2f",r);
		} else if(r instanceof Integer) {
			s = String.format("%d",r);
		} else {
			s = r.toString();
		}
		return s;
	}
	// Método de utilidad (no se llama en ningún otro método, se ofrece con el tipo)
	@SuppressWarnings("unchecked")
	public static <R> R parse(String text, Class<R> type) {
		Object r = null;
		if(type.equals(LocalDate.class)) {
			r = LocalDate.parse(text,DataFrame.dateFormat());
		} else if(type.equals(LocalTime.class)) {
			r = LocalTime.parse(text,DataFrame.timeFormat());
		} else if(type.equals(LocalDateTime.class)) {
			r = LocalDateTime.parse(text,DataFrame.dateTimeFormat());
		} else if(type.equals(Double.class)) {
			r = Double.parseDouble(text);
		} else if(type.equals(Integer.class)) {
			r = Integer.parseInt(text);
		} else {
			r = text;
		}
		return (R) r;
	}
	// --------------------
	// Métodos de las propiedades
	 @Override
	    public List<String> columNames() {
	        // Devuelve una copia del atributo correspondiente
	        return new ArrayList<>(columNames);
	    }

	    @Override
	    public Integer columNumber() {
	        // Se calcula a partir del atributo columNames
	        return columNames.size();
	    }

	    @Override
	    public List<String> colum(String name) {
	        // Se calcula a partir del atributo columIndex
	        List<String> column = new ArrayList<>();
	        Integer index = columIndex.get(name);
	        if (index != null) {
	            for (List<String> row : rows) {
	                column.add(row.get(index));
	            }
	        }
	        return column;
	    }

	    @Override
	    public List<String> colum(Integer index) {
	        // Se calcula a partir del atributo rows
	        List<String> column = new ArrayList<>();
	        for (List<String> row : rows) {
	            column.add(row.get(index));
	        }
	        return column;
	    }

	    @Override
	    public <R> List<R> colum(String name, Class<R> type) {
	        // Este método toma una columna por su nombre y la convierte a un tipo específico utilizando el método parse
	        return colum(name).stream().map(x -> DataFrame.parse(x, type)).toList();
	    }

	    @Override
	    public <R> List<R> colum(Integer index, Class<R> type) {
	        // Este método toma una columna por su índice y la convierte a un tipo específico utilizando el método parse
	        return colum(index).stream().map(x -> DataFrame.parse(x, type)).toList();
	    }
	
	    @Override
	    public Boolean columAllDifferent(String name) {
	        // Se calcula utilizando el método estático auxiliar allDifferent
	        List<String> column = colum(name);
	        return allDifferent(column);
	    }

	
	    @Override
	    public String propertie(List<String> row, String colum) {
	        Integer index = columIndex.get(colum);
	        if (index != null && index >= 0 && index < row.size()) {	          
	            return row.get(index);
	        } else {
	            return null;
	        }
	    }

	@Override
	public <R> R propertie(List<String> row, String colum, Class<R> type) {
		//
		String text = row.get(this.columIndex.get(colum));
		return DataFrame.parse(text, type);
	}
	@Override
	public String cell(Integer row, String colum) {
	    if (row >= 0 && row < rows.size()) {
	        Integer index = columIndex.get(colum);
	        if (index != null && index >= 0 && index < rows.get(row).size()) {

	            return rows.get(row).get(index);
	        }
	    }

	    return null;
	}

	@Override
	public String cell(Integer row, Integer colum) {
	    if (row >= 0 && row < rows.size()) {
	        if (colum >= 0 && colum < columNames.size()) {

	            return rows.get(row).get(colum);
	        }
	    }
	    return null;
	}
	
	public String cell(String columValue, String colum, String columCell) {
	    Integer indexColum = columIndex.get(colum);
	    Integer indexColumCell = columIndex.get(columCell);
	    if (indexColum != null && indexColumCell != null) {       
	        for (List<String> rowData : rows) {	            
	            if (rowData.get(indexColum).equals(columValue)) {
	               
	                return rowData.get(indexColumCell);
	            }
	        }
	    }
	    throw new IllegalArgumentException("Celda no encontrada" );
	}
	
	
	@Override
	public Integer rowNumber() {
	    // Se calcula a partir del atributo rows
	    return rows.size();
	}

	@Override
	public List<String> row(Integer i) {
	    if (i >= 0 && i < rows.size()) {
	        return new ArrayList<>(rows.get(i));
	    } else {
	        return null;
	    }
	}

	@Override
	public List<String> row(String rowValue, String colum) {
	    if (!columNames.contains(colum)) {
	        if (rows.stream().map(row -> row.get(columIndex.get(colum))).distinct().count() == rows.size()) {
	            for (List<String> rowData : rows) {
	                if (rowData.get(columIndex.get(colum)).equals(rowValue)) {
	                    return new ArrayList<>(rowData);
	                }
	            }
	        }
	    }
	    return null;
	}

	@Override
	public List<List<String>> rows() {
		//
		return this.rows.stream().<List<String>>map(r->r.stream().toList()).toList();
	}
	@Override
	public DataFrame head() {
		return this.head(5);
	}
	@Override
	public DataFrame head(Integer n) {
		// Muestra las n primeras filas del Dataframe: se calcula usando la propiedad subList
		//
		List<String> columNames = new ArrayList<>(this.columNames);
		Map<String,Integer> columIndex = new HashMap<>(this.columIndex);
		List<List<String>> rows = new ArrayList<>(this.rows);
		return DataFrameImpl.of(columNames,columIndex,rows.subList(0, n));
	}
	@Override
	public DataFrame tail() {
		// Análogo al método head
		return this.tail(5);
	}
	@Override
	public DataFrame tail(Integer n) {
	    List<List<String>> tailRows = rows.subList(Math.max(0, rows.size() - n), rows.size());
	    return DataFrameImpl.of(columNames, columIndex, tailRows);
	}
	@Override
	public DataFrame slice(Integer n, Integer m) {
	    Preconditions.checkNotNull(n, m);
	    Preconditions.checkArgument(n >= 0 && m >= n && m <= rows.size(), "Los índices de fila son inválidos.");
	    List<List<String>> slicedRows = rows.subList(n, m);
	    return DataFrameImpl.of(columNames, columIndex, slicedRows);
	}

	
	@Override
	public DataFrame filter(Predicate<List<String>> p) {
	    List<List<String>> filteredRows = rows.stream()
	                                          .filter(p)
	                                          .collect(Collectors.toList());

	    return DataFrameImpl.of(columNames, columIndex, filteredRows);
	}

	@Override
	public <E extends Comparable<? super E>> DataFrame sortBy(Function<List<String>, E> f, Boolean reverse) {
		//
		List<String> columNames = new ArrayList<>(this.columNames);
		Map<String,Integer> columIndex = new HashMap<>(this.columIndex);
		List<List<String>> rows = new ArrayList<>(this.rows);
		Comparator<List<String>> cmp = reverse?Comparator.comparing(f).reversed():Comparator.comparing(f);
		Collections.sort(rows,cmp);
		return DataFrameImpl.of(columNames,columIndex,rows);
	}
	private Set<Integer> indexes(List<String> columNames){
		//
		return columNames.stream().map(cn->this.columIndex.get(cn)).collect(Collectors.toSet());
	}
	private List<String> select(List<String> ls, Set<Integer> sl){
		//
		return IntStream.range(0, ls.size()).boxed()
				.filter(i->sl.contains(i))
				.map(i->ls.get(i))
				.toList();
	}
	@Override
	public <R> DataFrame groupBy(List<String> columNames, String newColumn, BinaryOperator<R> op,
			Function<List<String>, R> value) {
		//
		Function<List<String>,List<String>> key = ls->this.select(ls,this.indexes(columNames));
		Map<List<String>,R> g = Stream2.groupingReduce(this.rows.stream(),key,op,value);
		DataFrame r = DataFrame.of(columNames,g.keySet().stream().toList());		
		r = r.addColum(newColumn,g.values().stream().map(x->DataFrameImpl.string(x)).toList());
		return r;
	}
	@Override
	public DataFrame addColum(String newColum, List<String> datos) {
		//
		List<String> columNames = new ArrayList<>(this.columNames);
		columNames.add(newColum);
		Map<String,Integer> columIndex = new HashMap<>(this.columIndex);
		columIndex.put(newColum,this.columNumber()+1);
		List<List<String>> rows = new ArrayList<>(this.rows);
		Integer nr = this.rowNumber();
		List<List<String>> rn = IntStream.range(0, nr).boxed()
				.map(r->List2.concat(rows.get(r),List.of(datos.get(r))))
				.toList();
		return DataFrameImpl.of(columNames,columIndex,rn);
	}
	@Override
	public DataFrame addCalculatedColum(String newColum, Function<List<String>, String> f) {
	    List<String> newColumnValues = rows.stream()
	                                       .map(f)
	                                       .collect(Collectors.toList());
	  
	    List<String> newColumnNames = new ArrayList<>(columNames);
	    newColumnNames.add(newColum);

	    Map<String, Integer> newColumnIndex = new HashMap<>(columIndex);
	    newColumnIndex.put(newColum, columNames.size());
	    List<List<String>> newRows = new ArrayList<>();
	    IntStream.range(0, rows.size()).forEach(i -> {
	        List<String> newRow = new ArrayList<>(rows.get(i));
	        newRow.add(newColumnValues.get(i));
	        newRows.add(newRow);
	    });
	    return DataFrameImpl.of(newColumnNames, newColumnIndex, newRows);
	}

	@Override
	public DataFrame removeColum(String colum) {
		//
		 if (!columIndex.containsKey(colum)) {
		        throw new IllegalArgumentException("Columna no encontrada: " + colum);
		    }
		
		Integer c = this.columIndex.get(colum);
		List<String> columNames = new ArrayList<>(this.columNames);
		columNames.remove(colum);
		Map<String,Integer> columIndex = new HashMap<>();
		IntStream.range(0,columNames.size()).forEach(i->columIndex.put(columNames.get(i),i));		
		List<List<String>> rows = new ArrayList<>(this.rows);
		List<List<String>> rn = rows.stream()
				.map(r->IntStream.range(0, this.columNumber()).boxed().filter(i->i != c).map(i->r.get(i)).toList())
				.toList();
		return DataFrameImpl.of(columNames,columIndex,rn);
	}
	// --------------------
	// Métodos adicionales: redefinidos de Object
	@Override
	public String toString() {
		//
		Integer t = 16;
		String r = this.format(" ",this.columNames(),t);
		String line = this.line(this.columNames().size()+1, t);
		String r3 = Stream2.enumerate(this.rows.stream()).map(x->this.format(x,t))
				.collect(Collectors.joining("\n", r+"\n"+line+"\n", "\n"));
		return r3;
	}
	private String format(String propertie, List<String> ls, Integer n) {
		//
		List<String> nls = new ArrayList<String>();
		nls.add(propertie);
		nls.addAll(ls);
		String fmt = "%"+n+"s";
		return nls.stream().map(c->String.format(fmt,c)).collect(Collectors.joining("|","|","|"));
	}
	private String format(Enumerate<List<String>> e, Integer n) {
		//
		return this.format(e.counter().toString(),e.value(),n);
	}
	private String line(Integer m, Integer n) {
		//
		String s = IntStream.range(0, n).boxed().map(i->"_").collect(Collectors.joining(""));
		return IntStream.range(0, m).boxed().map(i->s).collect(Collectors.joining("|","|","|"));
	}
	//
	

}