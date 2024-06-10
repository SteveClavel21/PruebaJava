/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.time.LocalDate;

/**
 *
 * @author MINEDUCYT
 */
public class Evaluacione {

    private String evaluacionId;
    private String titulo;
    private String descripcion;
    private LocalDate fecha;
    private double puntaje;
    private int estudianteId;

    private Estudiante estudiantes;

    public Evaluacione() {
    }

    public Evaluacione(String evaluacionId, String titulo, String descripcion, LocalDate fecha, double puntaje, int estudianteId, Estudiante estudiantes) {
        this.evaluacionId = evaluacionId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.puntaje = puntaje;
        this.estudianteId = estudianteId;
        this.estudiantes = estudiantes;
    }

    public String getEvaluacionId() {
        return evaluacionId;
    }

    public void setEvaluacionId(String evaluacionId) {
        this.evaluacionId = evaluacionId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(double puntaje) {
        this.puntaje = puntaje;
    }

    public int getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(int estudianteId) {
        this.estudianteId = estudianteId;
    }

    public Estudiante getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(Estudiante estudiantes) {
        this.estudiantes = estudiantes;
    }
    
    
}
