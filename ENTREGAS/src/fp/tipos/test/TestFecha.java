package fp.tipos.test;
import fp.tipos.Fecha;

public class TestFecha {

	public static void main(String[] args) {
		Fecha fecha= Fecha.of(2023, 11, 25);
		//Test metodo of
		System.out.println(fecha);
		//Test metodo parse
		System.out.println(Fecha.parse("2023-11-25"));
		//Test metodo nombreMes
		String nombreMes= fecha.nombreMes();
		System.out.println(nombreMes);
		//Test metodo diaSemana
		String diaSemana = fecha.diaSemana();
		System.out.println(diaSemana);
		//Test sumarDias
		int dias_sumados=5;
		 Fecha fecha1 = fecha.sumarDias(dias_sumados);
	     System.out.format("Si a la fecha : " + fecha + " le sumamos %d dias," ,dias_sumados); System.out.println(" la fecha resultante es: "+fecha1);
	   //Test restarDias
		int dias_restados=5;
	    Fecha fecha2 = fecha.restarDias(dias_restados);
		System.out.format("Si a la fecha : " + fecha + " le restamos %d dias, " ,dias_restados); System.out.println("la fecha resultante es: "+fecha2);
		//Test diferenciaEnDias
		int diferencia= fecha.diferenciaEnDias(fecha1);
		System.out.println("La diferencia entre: " +fecha+ " y " +fecha1+ " son " +diferencia+ " dias");
		//Test esAñoBisisesto
		boolean esbisiesto= Fecha.esAñoBisiesto(2024);
		if(esbisiesto) {
			System.out.println("El año es bisiesto");
		}else {
			System.out.println("El año no es bisiesto");
		}
		//Test diaEnMes
		System.out.println("El mes indicado tiene "+ Fecha.diasEnMes(2023, 11)+ " dias");

		
		
				
		
		

	}

}
