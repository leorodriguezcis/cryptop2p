package datos;

import java.math.BigDecimal;

public class DatosDummy {
    //Direcciones
    private static Direccion dir1 = new Direccion("calle 14", "1234", "1884","na", "na","Berazachuset");;
    private static Direccion dir2 = new Direccion("calle 14", "1235", "1884","na", "na","Berazachuset");
    private static Direccion dir3 = new Direccion("calle 11", "3443", "1884","na", "na","Berazachuset");

    //Aulas
    public static Aula aula1 = new Aula(null, 1, Double.valueOf(5),20,TipoPizzarron.PIZARRA_MARCADOR) ;
    public static Aula aula2 = new Aula(null, 2, Double.valueOf(5),20,TipoPizzarron.PIZARRA_TIZA) ;
    public static Aula aula3 = new Aula(null, 3, Double.valueOf(20),50,TipoPizzarron.PIZARRA_MARCADOR) ;

    //Carreras
    public static Carrera carrera1(){ return new Carrera(null,"Ingenieria en Sistemas",50,5); }

    public static Carrera carrera2(){
        return new Carrera(null,"Licensiatura en Sistemas",40,4);
    }

    public static Carrera carrera3(){
        return new Carrera(null,"Ingenieria Quimica",50,6);
    }

    //Carreras con id
    public static Carrera carrera4(){ return new Carrera(4,"Ingenieria en Sistemas",50,5); }

    public static Carrera carrera5(){
        return new Carrera(5,"Licensiatura en Sistemas",40,6);
    }

    public static Carrera carrera6(){
        return new Carrera(6,"Ingenieria Quimica",50,6);
    }

    //Pabellones
    public static Pabellon pabellonA(){return new Pabellon(null, Double.valueOf(150), "Pabellon A", dir1);}
    public static Pabellon pabellonB(){return new Pabellon(null, Double.valueOf(150), "Pabellon B", dir1);}
    public static Pabellon pabellonC(){return new Pabellon(null, Double.valueOf(200), "Pabellon C", dir1);}

    //Personas
    public static Persona empleado01(){ return  new Empleado(null, "Juan","Perez", "12345678", dir1, BigDecimal.valueOf(60000), TipoEmpleado.ADMINISTRATIVO); }

    public static Persona empleado02(){ return  new Empleado(null, "Antonio","Ramirez", "12345679", dir2, BigDecimal.valueOf(30000), TipoEmpleado.MANTENIMIENTO); }

    public static Persona profesor01(){return  new Profesor(null, "Fidel", "Nadal", "76011528",dir3,BigDecimal.valueOf(90000)); }

    public static Persona profesor02(){return  new Profesor(null, "Alfonso", "Castro", "76011769",dir2,BigDecimal.valueOf(90000)); }

    public static Persona alumno01(){return new Alumno(null, "Raul", "Perez", "90861937",dir1);}

    public static Persona alumno02(){return new Alumno(null, "julian", "Perez", "90861938",dir1);}

}
