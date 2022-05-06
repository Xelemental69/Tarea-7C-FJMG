/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author pikac
 */

public class Horario implements Comparable<Horario> {

    //Atributos de la clase
    private int numId;
    private String curso;
    private String inicialesProf;
    private String asignatura;
    private String aula; //Vacío si no tiene aula asignada en X hora.
    private int diaSemana; //1.Lunes, 2.Martes, 3.Miércoles, 4.Jueves, 5.Viernes
    private int hora; //1.1a, 2.2a, 3.3a, 5.4a, 6.5a, 7.6a.
    //4a no existe por ser recreo.

    //Constructor por defecto.
    public Horario() {
    }

    //Constructor parametrizado
    public Horario(int numId, String curso, String inicialesProf,
            String asignatura, String aula, int diaSemana, int hora) {
        
        this.numId = numId;
        this.curso = curso;
        this.inicialesProf = inicialesProf;
        this.asignatura = asignatura;
        this.aula = aula;
        this.diaSemana = diaSemana;
        this.hora = hora;
    
    }

    //Getters y setters.
    public int getNumId() {
        
        return numId;
        
    }

    public void setNumId(int numId) {
        
        this.numId = numId;
        
    }

    public String getCurso() {
        
        return curso;
        
    }

    public void setCurso(String curso) {
        
        this.curso = curso;
        
    }

    public String getInicialesProf() {
        
        return inicialesProf;
        
    }

    public void setInicialesProf(String inicialesProf) {
        
        this.inicialesProf = inicialesProf;
        
    }

    public String getAsignatura() {
        
        return asignatura;
        
    }

    public void setAsignatura(String asignatura) {
        
        this.asignatura = asignatura;
        
    }

    public String getAula() {
        
        return aula;
        
    }

    public void setAula(String aula) {
        
        this.aula = aula;
        
    }

    public int getDiaSemana() {
        
        return diaSemana;
        
    }

    public void setDiaSemana(int diaSemana) {
        
        this.diaSemana = diaSemana;
        
    }

    public int getHora() {
        
        return hora;
        
    }

    public void setHora(int hora) {
        
        this.hora = hora;
        
    }

    //toString()
    @Override
    public String toString() {
        
        return numId + ";" + curso + ";" + inicialesProf
                + ";" + asignatura + ";" + aula + ";"
                + diaSemana + ";" + hora;
        
    }

    //Se implementa el método abstracto de la interface Comparable.
    //Ordeno por día de semana.
    public int compareTo(Horario h) {

        return this.diaSemana - h.getDiaSemana();

    }
}
