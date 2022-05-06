/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parte_a;

import clases.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.awt.GridLayout;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import javax.swing.*;

/**
 *
 * @author pikac
 */
public class Main_A {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        //El fichero "horario.csv" debe de estar en la carpeta raíz del proyecto, así podremos abrirlo:
        String idFichero = "horario.csv";

        //Instancio nuevo ArrayList de Horario en la que se irán
        //almacenando todos los objetos generados:
        ArrayList<Horario> listaHorario = new ArrayList<>();

        //Variables que serán usadas en el try-with-resources:
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

        //Imprimimos la lista por pantalla.
        System.out.println("Imprimiendo lista original...");
        listaHorario.forEach(System.out::println);
        System.out.println("-----------------------------------------------------------------------");

        //Ordenamos la lista por día, y luego por hora.
        Collections.sort(listaHorario, Comparator.comparing(Horario::getDiaSemana).thenComparing(Horario::getHora));
        System.out.println("Imprimiendo lista ordenada (día y hora):");
        listaHorario.forEach(System.out::println);
        System.out.println("-----------------------------------------------------------------------");

        //Guardo los grupos en una estructura Set. Con TreeSet, serán ordenados alfabéticamente por orden natural.
        Set<String> gruposSet = new TreeSet<>();
        for (Horario hor : listaHorario) {
            gruposSet.add(hor.getCurso());
        }
        //Imprimimos por consola la estructura de almacenamiento Set.
        System.out.println("Imprimiendo grupos (en estructura Set):");
        gruposSet.forEach(System.out::println);
        System.out.println("-----------------------------------------------------------------------");

        //Hacemos lo mismo pero con las iniciales de los profesores:
        Set<String> inicialesSet = new TreeSet<>();
        for (Horario hor : listaHorario) {
            inicialesSet.add(hor.getInicialesProf());
        }

        System.out.println("Las iniciales de los profesores son las siguientes:");
        inicialesSet.forEach(System.out::println);
        System.out.println("-----------------------------------------------------------------------");

        //PROCEDEMOS CON LA PARTE A:
        //Array para utilizar en el JOptionPane: 
        String[] arrayOpciones = {"Consultar horarios por profesor/a",
            "Consultar horarios por grupo", "Salir"};
        //(Se usará esta vez JOptionPane porque la consola ya estará
        //cargada de texto de imprimir las listas)

        //Variable auxiliar para salir del bucle.
        boolean aux = true;

        //Se copian los Set en un Array de Strings, para proseguir con el ejercicio, utilizando JOptionPane.
        //Array de iniciales de profesor
        String[] arrayInicialesProfesor = Arrays.copyOf(inicialesSet.toArray(), inicialesSet.size(), String[].class);
        //Array de aulas
        String[] arrayGrupo = Arrays.copyOf(gruposSet.toArray(), gruposSet.size(), String[].class);
        //Variable que se usará dentro del bucle do-while.
        String opcion = "";
        do {
            int opcionesInt = JOptionPane.showOptionDialog(null, "Seleccione una opción:", "ELEGIR", 0, 1, null, arrayOpciones, null);

            switch (opcionesInt) {

                case 0:

                    //Conversión explícita de Object a String.
                    opcion = (String) JOptionPane.showInputDialog(null, "Elija a un profesor: ", "ELEGIR", JOptionPane.QUESTION_MESSAGE, null, arrayInicialesProfesor, null);
                    System.out.println("El profesor elegido es: " + opcion);

                    //Se declara la variable en la que almacenaré la ruta del fichero JSON, con las iniciales del profesor.
                    String idFicheroJson = opcion + ".json";

                    //Se inicializa un nuevo ArrayList en el que almacenaré todos los objetos
                    ArrayList<Horario> guardarEnJSON = new ArrayList<>();

                    //En un bucle for, se recorre la estructura ArrayList en la que se halla almacenados los
                    //profesores con sus atributos, comparando el atributo inicialesProf con el String 
                    //devuelto de haber hecho una elección en el JOptionPane:                    
                    for (Horario hor : listaHorario) {

                        if (hor.getInicialesProf().equals(opcion)) {

                            guardarEnJSON.add(hor);

                        }

                        ObjectMapper mapeador = new ObjectMapper();

                        // Permite al mapeador usar fechas según JavaTime:
                        mapeador.registerModule(new JavaTimeModule());

                        // Formato JSON bien formateado. Si se comenta, el fichero quedará compacto:
                        mapeador.configure(SerializationFeature.INDENT_OUTPUT, true);

                        // Escribe en un fichero JSON el objeto String:
                        mapeador.writeValue(new File(idFicheroJson), guardarEnJSON);
                    }   //Fin del bucle.

                    aux = false;
                    break;

                case 1:

                    opcion = (String) JOptionPane.showInputDialog(null, "Elija un grupo: ", "ELEGIR", JOptionPane.QUESTION_MESSAGE, null, arrayGrupo, null);
                    System.out.println("El grupo elegido es: " + opcion);

                    //Se declara la variable en que almacenará la ruta del fichero csv, con el dato del aula seleccionada.
                    String idFicheroCSV = opcion + ".csv";

                    //Se instancia un nuevo ArrayList en que almacenará todos los objetos
                    ArrayList<Horario> guardarEnCSV = new ArrayList<>();

                    //En un bucle for, se recorre la estructura ArrayList que contiene los
                    //objetos tipo Horario, comparando el String del atributo curso
                    //con el devuelto de haber hecho una elección en el JOptionPane:
                    for (Horario hor : listaHorario) {

                        if (hor.getCurso().equals(opcion)) {

                            guardarEnCSV.add(hor);

                        }

                    }   //Escribimos en un fichero CSV el objeto String seleccionado en el JOptionPane
                    try (BufferedWriter flujo = new BufferedWriter(new FileWriter(idFicheroCSV))) {

                        for (Horario h : guardarEnCSV) {

                            flujo.write(h.toString());
                            flujo.newLine();

                        }
                        // Metodo flush(): Fuerza el guardado cambios en disco.
                        flujo.flush();

                    } catch (IOException e) {

                        System.out.println(e.getMessage());
                    }
                    System.out.println("Fichero " + idFicheroCSV + " creado correctamente.");
                    //Fin del bucle.

                    aux = false;
                    break;

                default:

                    //Si elegimos salir en el JOptionPane, se acaba el bucle.
                    aux = false;
                    break;

            }
        } while (aux);

    }

}
