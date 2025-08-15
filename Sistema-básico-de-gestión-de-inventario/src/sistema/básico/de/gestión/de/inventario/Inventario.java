/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sistema.básico.de.gestión.de.inventario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Elian
 */
public class Inventario extends javax.swing.JFrame {

     private DefaultTableModel modeloTabla;
    
    
    private final DecimalFormat formatoDecimal = new DecimalFormat("$#,##0.00");
    
    
    private Map<String, Double> catalogoProductos = null;
    
    /**
     * Creates new form Inventario
     */
    public Inventario() {
        catalogoProductos = new HashMap<>();
        catalogoProductos.put("Producto A", 10.50);
        catalogoProductos.put("Producto B", 25.75);
        catalogoProductos.put("Producto C", 8.99);
        
        initComponents();
        configurarVentana();
        configurarTabla();
    }
    
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(
            this,
            mensaje,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }

    
     private void configurarVentana() {
        setTitle("Sistema de Gestión de Inventario");
        setLocationRelativeTo(null); // Centrar ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Configurar texto inicial de cantidad
        txtCantidad.setText("1");
    }
     
     private void agregarProductos() {
        try {
            // Validar cantidad
            String textoCanitad = txtCantidad.getText().trim();
            if (textoCanitad.isEmpty()) {
                mostrarError("Por favor, ingrese una cantidad.");
                return;
            }
            
            int cantidad = Integer.parseInt(textoCanitad);
            if (cantidad <= 0) {
                mostrarError("La cantidad debe ser un número mayor a cero.");
                return;
            }
            
            // Verificar si hay productos seleccionados
            boolean haySeleccionados = false;
            
            // Verificar cada checkbox y agregar productos seleccionados
            if (cbxProductoA.isSelected()) {
                agregarProductoATabla("Producto A", cantidad);
                cbxProductoA.setSelected(false);
                haySeleccionados = true;
            }
            
            if (cbxProductoB.isSelected()) {
                agregarProductoATabla("Producto B", cantidad);
                cbxProductoB.setSelected(false);
                haySeleccionados = true;
            }
            
            if (cbxProductoC.isSelected()) {
                agregarProductoATabla("Producto C", cantidad);
                cbxProductoC.setSelected(false);
                haySeleccionados = true;
            }
            
            if (!haySeleccionados) {
                mostrarError("Por favor, seleccione al menos un producto.");
                return;
            }
            
            // Limpiar campo de cantidad después de agregar
            txtCantidad.setText("1");
            
            // Actualizar total general
            actualizarTotalGeneral();
            
            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(
                this,
                "Productos agregados exitosamente al inventario.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
            );
            
        } catch (NumberFormatException e) {
            mostrarError("La cantidad debe ser un número válido.");
            txtCantidad.setText("1");
            txtCantidad.requestFocus();
        }
    }
     
     private void configurarTabla() {
        // Definir columnas de la tabla
        String[] columnas = {"Producto", "Cantidad", "Precio Unitario", "Total"};
        
        // Crear modelo de tabla que no permita edición
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla de solo lectura
            }
        };
        
         Productos.setModel(modeloTabla);
         
        TableColumnModel columnModel = Productos.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150); // Producto
        columnModel.getColumn(1).setPreferredWidth(80);  // Cantidad
        columnModel.getColumn(2).setPreferredWidth(120); // Precio Unitario
        columnModel.getColumn(3).setPreferredWidth(120); // Total
        
        Productos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        agregarFilaTotalGeneral();
         
        
        private void agregarProductoATabla(String nombreProducto, int cantidad) {
        double precioUnitario = catalogoProductos.get(nombreProducto);
        double total = cantidad * precioUnitario;
        
        // Buscar si el producto ya existe en la tabla
        int filaExistente = buscarProductoEnTabla(nombreProducto);
        
        if (filaExistente != -1 && filaExistente < modeloTabla.getRowCount() - 1) {
            // Producto ya existe, actualizar cantidad y total
            int cantidadExistente = (Integer) modeloTabla.getValueAt(filaExistente, 1);
            int nuevaCantidad = cantidadExistente + cantidad;
            double nuevoTotal = nuevaCantidad * precioUnitario;
            
            modeloTabla.setValueAt(nuevaCantidad, filaExistente, 1);
            modeloTabla.setValueAt(formatoDecimal.format(nuevoTotal), filaExistente, 3);
        } else {
            // Producto nuevo, agregar antes de la fila de total
            int filaInsercion = modeloTabla.getRowCount() - 1;
            Object[] fila = {
                nombreProducto,
                cantidad,
                formatoDecimal.format(precioUnitario),
                formatoDecimal.format(total)
            };
            modeloTabla.insertRow(filaInsercion, fila);
        }
    }
         

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbxProductoA = new javax.swing.JCheckBox();
        cbxProductoB = new javax.swing.JCheckBox();
        cbxProductoC = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Productos = new javax.swing.JTable();
        btnFinalizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cbxProductoA.setText("Producto A");

        cbxProductoB.setText("Producto B");

        cbxProductoC.setText("Producto C");

        jLabel1.setText("Cantidad de productos");

        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        Productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Producto", "Cantidad", "Precio Unitario", "Total"
            }
        ));
        Productos.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                ProductosAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane1.setViewportView(Productos);

        btnFinalizar.setText("Finalizar");
        btnFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxProductoA, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxProductoB, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxProductoC, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAgregar)
                        .addGap(18, 18, 18)
                        .addComponent(btnFinalizar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addGap(0, 18, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregar)
                    .addComponent(btnFinalizar)
                    .addComponent(btnEliminar))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(cbxProductoA)
                        .addGap(62, 62, 62)
                        .addComponent(cbxProductoB)
                        .addGap(62, 62, 62)
                        .addComponent(cbxProductoC)
                        .addContainerGap(115, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ProductosAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_ProductosAncestorAdded
   
    }//GEN-LAST:event_ProductosAncestorAdded

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
           agregarProductos();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarActionPerformed
        // TODO add your handling code here:
        finalizarCompra();
    }//GEN-LAST:event_btnFinalizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        eliminarProductoSeleccionado();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
        agregarProductos();
    }//GEN-LAST:event_txtCantidadActionPerformed

     private int buscarProductoEnTabla(String nombreProducto) {
        for (int i = 0; i < modeloTabla.getRowCount() - 1; i++) { // Excluir fila de total
            if (nombreProducto.equals(modeloTabla.getValueAt(i, 0))) {
                return i;
            }
        }
        return -1;
    }
     
     private void agregarFilaTotalGeneral() {
        Object[] filaTotalGeneral = {
            "TOTAL GENERAL",
            "",
            "",
            "$0.00"
        };
        modeloTabla.addRow(filaTotalGeneral);
    }
     
     private void actualizarTotalGeneral() {
        double totalGeneral = 0.0;
        
        // Sumar todos los totales (excepto la última fila que es el total general)
        for (int i = 0; i < modeloTabla.getRowCount() - 1; i++) {
            String totalStr = modeloTabla.getValueAt(i, 3).toString();
            // Remover símbolo de moneda y comas para poder convertir a número
            double total = Double.parseDouble(totalStr.replace("$", "").replace(",", ""));
            totalGeneral += total;
        }
        
        // Actualizar la fila de total general
        int ultimaFila = modeloTabla.getRowCount() - 1;
        modeloTabla.setValueAt(formatoDecimal.format(totalGeneral), ultimaFila, 3);
    }
     
     private void eliminarProductoSeleccionado() {
        int filaSeleccionada = Productos.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            mostrarError("Por favor, seleccione un producto para eliminar.");
            return;
        }
        
        // No permitir eliminar la fila de total general
        if (filaSeleccionada == modeloTabla.getRowCount() - 1) {
            mostrarError("No se puede eliminar la fila de total general.");
            return;
        }
        
        // Confirmar eliminación
        String nombreProducto = modeloTabla.getValueAt(filaSeleccionada, 0).toString();
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro que desea eliminar \"" + nombreProducto + "\" del inventario?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            modeloTabla.removeRow(filaSeleccionada);
            actualizarTotalGeneral();
            
            JOptionPane.showMessageDialog(
                this,
                "Producto eliminado exitosamente.",
                "Eliminación Exitosa",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
     
      private void finalizarCompra() {
        if (modeloTabla.getRowCount() <= 1) { // Solo hay fila de total
            mostrarError("No hay productos en el inventario para finalizar la compra.");
            return;
        }
        
        StringBuilder resumen = new StringBuilder();
        resumen.append("=== RESUMEN FINAL DE COMPRA ===\n\n");  
        
        for (int i = 0; i < modeloTabla.getRowCount() - 1; i++) {
            String producto = modeloTabla.getValueAt(i, 0).toString();
            String cantidad = modeloTabla.getValueAt(i, 1).toString();
            String precioUnitario = modeloTabla.getValueAt(i, 2).toString();
            String total = modeloTabla.getValueAt(i, 3).toString();
            
            resumen.append(String.format("• %s\n", producto));
            resumen.append(String.format("  Cantidad: %s\n", cantidad));
            resumen.append(String.format("  Precio Unitario: %s\n", precioUnitario));
            resumen.append(String.format("  Subtotal: %s\n\n", total));
      }
        
        
        
        // Agregar detalles de cada producto
        for (int i = 0; i < modeloTabla.getRowCount() - 1; i++) {
            String producto = modeloTabla.getValueAt(i, 0).toString();
            String cantidad = modeloTabla.getValueAt(i, 1).toString();
            String precioUnitario = modeloTabla.getValueAt(i, 2).toString();
            String total = modeloTabla.getValueAt(i, 3).toString();
            
            resumen.append(String.format("• %s\n", producto));
            resumen.append(String.format("  Cantidad: %s\n", cantidad));
            resumen.append(String.format("  Precio Unitario: %s\n", precioUnitario));
            resumen.append(String.format("  Subtotal: %s\n\n", total));
        }
        
        // Agregar total general
        String totalGeneral = modeloTabla.getValueAt(modeloTabla.getRowCount() - 1, 3).toString();
        resumen.append("=====================================\n");
        resumen.append(String.format("TOTAL GENERAL: %s\n", totalGeneral));
        resumen.append("=====================================\n\n");
        resumen.append("¡Gracias por su compra!");
        
        // Mostrar resumen en un diálogo
        JTextArea areaTexto = new JTextArea(resumen.toString());
        areaTexto.setEditable(false);
        areaTexto.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setPreferredSize(new java.awt.Dimension(400, 300));
        
        JOptionPane.showMessageDialog(
            this,
            scrollPane,
            "Resumen Final de Compra",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
      
      private void limpiarInventario() {
        if (modeloTabla.getRowCount() <= 1) {
            mostrarError("El inventario ya está vacío.");
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro que desea limpiar todo el inventario?",
            "Confirmar Limpieza",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
         if (confirmacion == JOptionPane.YES_OPTION) {
            // Eliminar todas las filas excepto la de total general
            while (modeloTabla.getRowCount() > 1) {
                modeloTabla.removeRow(0);
            }
            
            // Reiniciar total general
            actualizarTotalGeneral();
            
            // Limpiar selecciones
            cbxProductoA.setSelected(false);
            cbxProductoB.setSelected(false);
            cbxProductoC.setSelected(false);
            txtCantidad.setText("1");
            
            JOptionPane.showMessageDialog(
                this,
                "Inventario limpiado exitosamente.",
                "Limpieza Exitosa",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
        
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Productos;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JCheckBox cbxProductoA;
    private javax.swing.JCheckBox cbxProductoB;
    private javax.swing.JCheckBox cbxProductoC;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtCantidad;
    // End of variables declaration//GEN-END:variables

 
   
}
