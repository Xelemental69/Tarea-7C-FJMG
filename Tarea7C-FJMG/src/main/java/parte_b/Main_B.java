/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parte_b;

import clases.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author pikac
 */
public class Main_B {

    public static void main(String[] args) {
        //Para leer el fichero, reutilizamos el mismo código del Main_A
        //El fichero "horario.csv" estará en la  carpeta raíz del proyecto.
        String idFichero = "horario.csv";

        //Se crea nuevo ArrayList de Horario que irá almacenando todos
        //los objetos generados:
        ArrayList<Horario> listaHorario = new ArrayList<>();

        //Variables que se usarán en el try-with-resources:
        String[] tokens;
        String linea;

        //Estructura try-with-resources para leer el fichero:
        try (Scanner datosFichero = new Scanner(new FileReader(idFichero))) {

            while (datosFichero.hasNextLine()) {

                linea = datosFichero.nextLine();
                tokens = linea.split(";");

                //Se crea una variable Horario auxiliar
                //en el que serán introducidos todos los
                //datos mediante los setters:
                Horario aux = new Horario();

                //Se introducen los valores en el objeto. Con el método "replaceAll", se pasa por parámetro
                //el String a reemplazar (1er valor), por el String que lo sustituirá (2º valor).
                //En este caso el String resultante es vacío en todos los casos:
                aux.setNumId(Integer.valueOf(tokens[0].replaceAll("\"", "")));
                aux.setCurso(tokens[1].replaceAll("\"", "").replaceAll(" ", ""));
                aux.setInicialesProf(tokens[2].replaceAll(" ", "").replaceAll("\"", ""));
                aux.setAsignatura(tokens[3].replaceAll(" ", "").replaceAll("\"", ""));
                //Si no hay aula, asignamos al objeto el valor de: ""
                if (tokens[4].replaceAll("\"", "").replaceAll(" ", "").equals("")) {
                    aux.setAula("\"\"");
                } else {
                    aux.setAula(tokens[4].replaceAll("\"", "").replaceAll(" ", ""));
                }
                aux.setDiaSemana(Integer.valueOf(tokens[5]));
                aux.setHora(Integer.valueOf(tokens[6].replaceAll("\"", "")));

                //Añadimos los objetos a la lista.
                listaHorario.add(aux);
            }
        } catch (FileNotFoundException e) {

            System.out.println(e.getMessage());

        }

        //a) Obtener todos los registros de 1ESOA que no son de la asignatura MUS:
        List<Horario> a = listaHorario.stream()
                .filter(hor -> hor.getCurso().equals("1ESOA"))//Nota: Se pueden poner ambos filtros en un .filter().
                .filter(hor -> !hor.getAsignatura().equals("MUS"))
                .collect(Collectors.toList());
        
        System.out.println("A: Obtener todos los registros de 1ESOA que no son de Música"); //¿No son de música? SACRILEGIO
        a.forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");

        //b) Contar las horas que se imparten de la asignatura PROGR
        long b = listaHorario.stream()
                .filter(hor -> hor.getAsignatura().equals("PROGR"))
                .count();//Para contar
        
        System.out.println("B: Contar las horas que se imparten de Programación");
        System.out.println(b);
        System.out.println("---------------------------------------------------------------------------");

        //c) Obtener una lista con las iniciales del profesorado que imparte la asignatura REL,
        //ordenadas en orden inverso al orden alfabético.
        List<String> c = listaHorario.stream()
                .filter(hor -> hor.getAsignatura().equals("REL"))
                .map(hor -> hor.getInicialesProf())//Se ordenan alfabéticamente...
                .sorted(Comparator.reverseOrder())//...para invertir el orden con esta línea.
                .collect(Collectors.toList());
        
        System.out.println("C: Obtener una lista con las iniciales del profesorado que imparte Religión,"
                + " ordenadas en orden inverso al alfabético");
        c.forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");

        //d) Obtener en una lista las aulas donde imparte clase el profesor "JFV"
        List<String> d = listaHorario.stream()
                .filter(hor -> hor.getInicialesProf().equals("JFV"))
                .map(hor -> hor.getAula())
                .collect(Collectors.toList());
        
        System.out.println("D: Obtener en una lista las aulas donde imparte clase Vico");
        d.forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");

        //e) Contar el número de asignaturas distintas que hay
        long e = listaHorario.stream()
                .map(hor -> hor.getAsignatura())
                .distinct()
                .count();
        
        System.out.println("E: Contar el número de asignaturas distintas existentes");
        System.out.println(e);
        System.out.println("---------------------------------------------------------------------------");

        //f) Contar el total de horas que se imparten a última hora de la mañana.
        long f = listaHorario.stream()
                .filter(hor -> hor.getHora() == 7)
                .count();
        
        System.out.println("F: Contar el total de horas impartidas a última hora de la mañana.");
        System.out.println(f);
        System.out.println("---------------------------------------------------------------------------");

        //g) Mostrar por consola los profesores que tienen clase a primera hora de la mañana.
        List<String> g = listaHorario.stream()
                .filter(hor -> hor.getHora() == 1)
                .map(hor -> hor.getInicialesProf())
                .distinct()
                .collect(Collectors.toList());
        
        System.out.println("G: Mostrar por consola los profesores que tienen clase a 1ª hora de la mañana.");
        g.forEach(System.out::println);

    }

}
