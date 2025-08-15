/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agenda.de.contactos;

/**
 *
 * @author Elian
 */
public class persona {
    private String cedula;
    private String nombre;
    private String apellidos;
    private String fechaNacimiento;
    private String telefono;
    private String direccion;
    
    // Constructor vacío
    public persona() {
    }
    
    // Constructor con parámetros
    public persona(String cedula, String nombre, String apellidos, 
                   String fechaNacimiento, String telefono, String direccion) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.direccion = direccion;
    }
    
    // Getters y Setters
    public String getCedula() {
        return cedula;
    }
    
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellidos() {
        return apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    // Método toString para mostrar información de la persona
    @Override
    public String toString() {
        return String.format("Cédula: %s | %s %s | Tel: %s", 
                           cedula, nombre, apellidos, telefono);
    }
    
    // Método para obtener información completa
    public String getInformacionCompleta() {
        StringBuilder info = new StringBuilder();
        info.append("=== INFORMACIÓN DEL CONTACTO ===\n");
        info.append("Cédula: ").append(cedula != null ? cedula : "No especificada").append("\n");
        info.append("Nombre: ").append(nombre != null ? nombre : "No especificado").append("\n");
        info.append("Apellidos: ").append(apellidos != null ? apellidos : "No especificados").append("\n");
        info.append("Fecha de Nacimiento: ").append(fechaNacimiento != null ? fechaNacimiento : "No especificada").append("\n");
        info.append("Teléfono: ").append(telefono != null ? telefono : "No especificado").append("\n");
        info.append("Dirección: ").append(direccion != null ? direccion : "No especificada").append("\n");
        info.append("================================");
        return info.toString();
    }
    
    // Método para validar si los datos básicos están completos
    public boolean datosBasicosCompletos() {
        return cedula != null && !cedula.trim().isEmpty() &&
               nombre != null && !nombre.trim().isEmpty() &&
               apellidos != null && !apellidos.trim().isEmpty();
    }
}

