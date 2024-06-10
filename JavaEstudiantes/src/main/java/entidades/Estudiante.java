/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author MINEDUCYT
 */
public class Estudiante {

    private int estudianteId;
    private String nombre;
    private String apellido;
    private String correo;
    private String carrera;

    public Estudiante() {
    }

    public Estudiante(int estudianteId, String nombre, String apellido, String correo, String carrera) {
        this.estudianteId = estudianteId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.carrera = carrera;
    }

    public int getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(int estudianteId) {
        this.estudianteId = estudianteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
//@Override
 //public String toString(){
 //return carrera;}
}
