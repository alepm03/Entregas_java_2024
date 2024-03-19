package fp.tipos;

 import java.util.List;  
 import java.util.ArrayList;


public record Fecha(Integer año,
					Integer mes,
					Integer dia) {
	
	public static Fecha of(Integer año,Integer mes, Integer dia) {
		return new Fecha(año,mes,dia);
	}
	
	public static Fecha parse(String cadenaFecha) {
		String[] partes = cadenaFecha.split("-");
		Integer año = Integer.parseInt(partes[0].strip());
		Integer mes = Integer.parseInt(partes[1].strip());
		Integer dia = Integer.parseInt(partes[2].strip());
		return Fecha.of(año, mes, dia);
	}
	
	
	public String nombreMes() {
		List<String> meses = new ArrayList<>(List.of("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"));
		return meses.get(this.mes() - 1);
		
	}
	
	public String diaSemana() {
		List<String> dias = new ArrayList<>(List.of("Sabado","Domingo","Lunes","Martes","Miercoles","Jueves","Viernes"));
		Integer dia = Fecha.congruenciaZeller(this.año(), this.mes(), this.dia());
		return dias.get(dia);
	}
	
	public Fecha sumarDias(Integer n) {
		Integer diasEnEsteMes = Fecha.diasEnMes(this.año(), this.mes());
		Integer diasRestantes = n;
		
		Integer nuevoDia = this.dia();
		Integer nuevoMes = this.mes();
		Integer nuevoAño = this.año();
		
		while (diasRestantes > 0) {
			if(nuevoDia + diasRestantes > diasEnEsteMes) {
				Integer diasFaltantesParaFinMes = diasEnEsteMes - nuevoDia +1;
				diasRestantes -= diasFaltantesParaFinMes;
				nuevoDia = 1;
				nuevoMes++;
				if(nuevoMes > 12) {
					nuevoMes =1;
					nuevoAño++;
				}
				diasEnEsteMes = Fecha.diasEnMes(nuevoAño, nuevoMes);
			} else {
				nuevoDia += diasRestantes;
				diasRestantes = 0;
			}
				
			}
		return Fecha.of(nuevoAño, nuevoMes, nuevoDia);
	}
	
	
	public Fecha restarDias(Integer n) {
		Integer nuevoDia = this.dia() - n;
        Integer nuevoMes = this.mes();
        Integer nuevoAño = this.año();
        

        while (nuevoDia <= 0) {
            nuevoMes--;
            if (nuevoMes <= 0) {
                nuevoMes = 12;
                nuevoAño--;
            }
            nuevoDia += diasEnMes(nuevoAño, nuevoMes);
        }
		return Fecha.of(nuevoAño, nuevoMes, nuevoDia);
	}
	
	
	public Integer diferenciaEnDias(Fecha f) {
		Integer diferenciaEnAños = Math.abs(this.año() - f.año());
		Integer diferenciaEnMeses = Math.abs(this.mes() - f.mes());
		Integer diferenciaEnDias = Math.abs(this.dia() - f.dia());
		return diferenciaEnAños*365 + diferenciaEnMeses * (Fecha.diasEnMes(f.año(), f.mes())) + diferenciaEnDias; 
    }
	
	
	public static boolean esAñoBisiesto(Integer año) {
		return (año % 4 == 0 && año % 100 != 0) || (año % 400 == 0);
	}
	
	
	public static Integer congruenciaZeller(Integer año,Integer mes, Integer dia) {
		if (mes < 3) {
            mes += 12;
            año -= 1;
        }
        
        Integer K = año % 100;
        Integer J = año / 100;
        
        Integer q = dia;
        Integer m = mes;

        Integer h = (q + (13 * (m + 1) / 5) + K + (K / 4) + (J / 4) - (2 * J)) % 7;
        return h;
	}
	
	public static Integer diasEnMes(Integer año,Integer mes) {
		Integer dias = -1;
        switch (mes) {
            case 1: 
            case 3: 
            case 5: 
            case 7: 
            case 8: 
            case 10: 
            case 12: 
                dias = 31;
                break;
            case 4: 
            case 6: 
            case 9: 
            case 11: 
                dias = 30;
                break;
            case 2: 
                if ((año % 4 == 0 && año % 100 != 0) || (año % 400 == 0)) {
                    dias = 29; 
                } else {
                    dias = 28; 
                }
                break;
        }
        return dias;
	}
	
	@Override
	public String toString() {
		String nombreDias = this.diaSemana();
		String nombreMes = this.nombreMes();
		return nombreDias + ", " + this.dia() + " de " + nombreMes + " de " + this.año();
	}

}