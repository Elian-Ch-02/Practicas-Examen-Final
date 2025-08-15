/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package agenda.de.contactos;

import javax.swing.JOptionPane;
import java.util.ArrayList;

/**
 *
 * @author Elian
 */
public class AgendaContactos extends javax.swing.JFrame {

  private ArrayList<persona> listaContactos;
   private int indiceActual;
  
    /**
     * Creates new form AgendaContactos
     */
    public AgendaContactos() {
     
        // Inicializar la lista de contactos
        listaContactos = new ArrayList<>();
        indiceActual = -1;
        
        initComponents();
        configurarVentana();
        actualizarInterfaz();
    }
    
    private void configurarVentana() {
        setTitle("Agenda de Contactos");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        
        // Configurar campo de índice como solo lectura inicialmente
        txtIndice.setEditable(false);
    }
      private void limpiarCampos() {
        txtDNI.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtFnac.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
    }
     private void nuevoContacto() {
        limpiarCampos();
        indiceActual = -1;
        actualizarInterfaz();
        txtDNI.requestFocus();
    }
     private void actualizarInterfaz() {
        if (listaContactos.isEmpty()) {
            txtIndice.setText("Sin contactos");
        } else {
            if (indiceActual == -1) {
                txtIndice.setText("Nuevo contacto");
            } else {
                txtIndice.setText((indiceActual + 1) + " de " + listaContactos.size());
            }
        }
    }
      private void mostrarInformacionDetallada() {
        if (indiceActual == -1 || listaContactos.isEmpty()) {
            mostrarError("No hay contacto seleccionado.");
            return;
        }
         persona p = listaContactos.get(indiceActual);
        JOptionPane.showMessageDialog(
            this,
            p.getInformacionCompleta(),
            "Información Detallada",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
       private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
       private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void guardarContacto() {
        try {
            // Validar campos obligatorios
            if (!validarCamposObligatorios()) {
                return;
            }
            
            // Crear objeto Persona con los datos ingresados
            persona persona = new persona(
                txtDNI.getText().trim(),
                txtNombre.getText().trim(),
                txtApellido.getText().trim(),
                txtFnac.getText().trim(),
                txtTelefono.getText().trim(),
                txtDireccion.getText().trim()
            );
            
            // Verificar si es un nuevo contacto o modificación
            if (indiceActual == -1) {
                // Nuevo contacto - verificar que la cédula no exista
                if (buscarContactoPorCedula(persona.getCedula()) != -1) {
                    mostrarError("Ya existe un contacto con esa cédula.");
                    txtDNI.requestFocus();
                    return;
                }
                
                // Agregar nuevo contacto
                listaContactos.add(persona);
                indiceActual = listaContactos.size() - 1;
                
                mostrarMensaje("Contacto guardado exitosamente.\nTotal de contactos: " + listaContactos.size());
                
            } else {
                // Modificar contacto existente
                String cedulaOriginal = listaContactos.get(indiceActual).getCedula();
                
                // Si cambió la cédula, verificar que no exista en otro contacto
                if (!persona.getCedula().equals(cedulaOriginal)) {
                    int indiceExistente = buscarContactoPorCedula(persona.getCedula());
                    if (indiceExistente != -1 && indiceExistente != indiceActual) {
                        mostrarError("Ya existe otro contacto con esa cédula.");
                        txtDNI.requestFocus();
                        return;
                    }
                }
                
                // Actualizar contacto
                listaContactos.set(indiceActual, persona);
                mostrarMensaje("Contacto modificado exitosamente.");
            }
            
            // Limpiar campos después de guardar
            limpiarCampos();
            actualizarInterfaz();
            
        } catch (Exception e) {
            mostrarError("Error al guardar el contacto: " + e.getMessage());
        }
    }
    
     private boolean validarCamposObligatorios() {
        if (txtDNI.getText().trim().isEmpty()) {
            mostrarError("La cédula es obligatoria.");
            txtDNI.requestFocus();
            return false;
        }
        
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarError("El nombre es obligatorio.");
            txtNombre.requestFocus();
            return false;
        }
        
        if (txtApellido.getText().trim().isEmpty()) {
            mostrarError("Los apellidos son obligatorios.");
            txtApellido.requestFocus();
            return false;
        }
        
        return true;
    }
     
     private int buscarContactoPorCedula(String cedula) {
        for (int i = 0; i < listaContactos.size(); i++) {
            if (listaContactos.get(i).getCedula().equalsIgnoreCase(cedula.trim())) {
                return i;
            }
        }
        return -1;
    }
     
     private void buscarContacto() {
        if (listaContactos.isEmpty()) {
            mostrarError("No hay contactos en la agenda.");
            return;
        }
        
        String termino = JOptionPane.showInputDialog(
            this,
            "Ingrese cédula, nombre o apellido a buscar:",
            "Buscar Contacto",
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (termino != null && !termino.trim().isEmpty()) {
            ArrayList<Integer> resultados = new ArrayList<>();
            
            // Buscar por cédula, nombre o apellidos
            for (int i = 0; i < listaContactos.size(); i++) {
                persona p = listaContactos.get(i);
                if (p.getCedula().toLowerCase().contains(termino.toLowerCase()) ||
                    p.getNombre().toLowerCase().contains(termino.toLowerCase()) ||
                    p.getApellidos().toLowerCase().contains(termino.toLowerCase())) {
                    resultados.add(i);
                }
            }
            
            if (resultados.isEmpty()) {
                mostrarError("No se encontraron contactos que coincidan con: " + termino);
            } else if (resultados.size() == 1) {
                // Un solo resultado - mostrar directamente
                indiceActual = resultados.get(0);
                mostrarContactoActual();
                mostrarMensaje("Contacto encontrado.");
            } else {
                // Múltiples resultados - mostrar lista para seleccionar
                mostrarResultadosBusqueda(resultados, termino);
            }
        }
    }
     
      private void mostrarResultadosBusqueda(ArrayList<Integer> indices, String termino) {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Se encontraron ").append(indices.size()).append(" contactos:\n\n");
        
        for (int i = 0; i < indices.size(); i++) {
            persona p = listaContactos.get(indices.get(i));
            mensaje.append((i + 1)).append(". ").append(p.toString()).append("\n");
        }
        
        String seleccion = JOptionPane.showInputDialog(
            this,
            mensaje.toString() + "\nIngrese el número del contacto a ver (1-" + indices.size() + "):",
            "Seleccionar Contacto",
            JOptionPane.QUESTION_MESSAGE
        );
        
        try {
            int numeroSeleccionado = Integer.parseInt(seleccion);
            if (numeroSeleccionado >= 1 && numeroSeleccionado <= indices.size()) {
                indiceActual = indices.get(numeroSeleccionado - 1);
                mostrarContactoActual();
                mostrarMensaje("Contacto seleccionado para visualización/edición.");
            } else {
                mostrarError("Número de contacto inválido.");
            }
        } catch (NumberFormatException e) {
            // Usuario canceló o ingresó valor inválido
        }
    }
      
       private void mostrarContactoActual() {
        if (indiceActual >= 0 && indiceActual < listaContactos.size()) {
            persona p = listaContactos.get(indiceActual);
            
            txtDNI.setText(p.getCedula());
            txtNombre.setText(p.getNombre());
            txtApellido.setText(p.getApellidos());
            txtFnac.setText(p.getFechaNacimiento());
            txtTelefono.setText(p.getTelefono());
            txtDireccion.setText(p.getDireccion());
            
            actualizarInterfaz();
        }
    }
       
       private void siguienteContacto() {
        if (listaContactos.isEmpty()) {
            mostrarError("No hay contactos en la agenda.");
            return;
        }
        
        if (indiceActual < listaContactos.size() - 1) {
            indiceActual++;
        } else {
            indiceActual = 0; // Volver al primero
        }
        
        mostrarContactoActual();
    }
       
        private void anteriorContacto() {
        if (listaContactos.isEmpty()) {
            mostrarError("No hay contactos en la agenda.");
            return;
        }
        
        if (indiceActual > 0) {
            indiceActual--;
        } else {
            indiceActual = listaContactos.size() - 1; // Ir al último
        }
        
        mostrarContactoActual();
    }
        
        private void mostrarListaContactos() {
        if (listaContactos.isEmpty()) {
            mostrarError("No hay contactos en la agenda.");
            return;
        }
        
        StringBuilder lista = new StringBuilder();
        lista.append("=== LISTA COMPLETA DE CONTACTOS ===\n");
        lista.append("Total de contactos: ").append(listaContactos.size()).append("\n\n");
        
        for (int i = 0; i < listaContactos.size(); i++) {
            lista.append((i + 1)).append(". ").append(listaContactos.get(i).toString()).append("\n");
        }
        javax.swing.JTextArea areaTexto = new javax.swing.JTextArea(lista.toString());
        areaTexto.setEditable(false);
        areaTexto.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(areaTexto);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 400));
        
        JOptionPane.showMessageDialog(
            this,
            scrollPane,
            "Lista Completa de Contactos",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtDNI = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtFnac = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnAnteriorcontacto = new javax.swing.JButton();
        btnSiguenteContacto = new javax.swing.JButton();
        Indice = new javax.swing.JLabel();
        txtIndice = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Nombre");

        jLabel2.setText("Apellido");

        jLabel3.setText("DNI");

        txtDNI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDNIActionPerformed(evt);
            }
        });

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        jLabel4.setText("telefono");

        jLabel5.setText("Direccion");

        jLabel6.setText("F.Nac");

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnAnteriorcontacto.setText("<<");
        btnAnteriorcontacto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorcontactoActionPerformed(evt);
            }
        });

        btnSiguenteContacto.setText(">>");
        btnSiguenteContacto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguenteContactoActionPerformed(evt);
            }
        });

        Indice.setText("Indice");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(201, 201, 201)
                        .addComponent(Indice)
                        .addGap(35, 35, 35)
                        .addComponent(txtIndice, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel3)))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnAnteriorcontacto)
                                        .addGap(37, 37, 37)
                                        .addComponent(btnGuardar))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(52, 52, 52)
                                        .addComponent(jLabel6)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(33, 33, 33)
                                        .addComponent(btnSiguenteContacto))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(46, 46, 46)
                                        .addComponent(txtFnac, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(52, 52, 52)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5)))
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(87, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtFnac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnAnteriorcontacto)
                    .addComponent(btnSiguenteContacto))
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Indice)
                    .addComponent(txtIndice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDNIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDNIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDNIActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
         guardarContacto();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnAnteriorcontactoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorcontactoActionPerformed
        // TODO add your handling code here:
        anteriorContacto();
    }//GEN-LAST:event_btnAnteriorcontactoActionPerformed

    private void btnSiguenteContactoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguenteContactoActionPerformed
        // TODO add your handling code here:
        siguienteContacto();
    }//GEN-LAST:event_btnSiguenteContactoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AgendaContactos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AgendaContactos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AgendaContactos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AgendaContactos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AgendaContactos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Indice;
    private javax.swing.JButton btnAnteriorcontacto;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSiguenteContacto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtDNI;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtFnac;
    private javax.swing.JTextField txtIndice;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
