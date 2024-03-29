/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package views;

import javax.swing.JOptionPane;

import data.Conexion;
import data.EmpleadoData;
import entities.Empleado;
import entities.Persona;

/**
 *
 * @author agusv
 */
public class EmpleadoDialogView extends javax.swing.JDialog {
        Empleado empleado = new Empleado();
        EmpleadoData empleadoData;

        public EmpleadoDialogView(java.awt.Frame parent, boolean modal) {
                super(parent, modal);
                initComponents();
        }

        public EmpleadoDialogView(java.awt.Frame parent, boolean modal, Conexion conexion) {
                super(parent, modal);
                initComponents();
                empleadoData = new EmpleadoData(conexion);
        }

        public EmpleadoDialogView(java.awt.Frame parent, boolean modal, Conexion conexion, Empleado empleado) {
                super(parent, modal);
                initComponents();
                empleadoData = new EmpleadoData(conexion);
                this.empleado = empleado;
                obtenerEmpleados();
        }

        public void obtenerEmpleados() {

                selectCargo.setEnabled(true);

                if (empleado.getId() != null) {
                        txfId.setText(String.valueOf(empleado.getId()));
                        selectCargo.setSelectedItem(empleado.getCargo());
                } else {
                        txfId.setText("");
                        selectCargo.setSelectedItem("Vendedor");
                }

                txfDni.setText(String.valueOf(empleado.getPersona().getDni()));
                txfNombre.setEnabled(true);
                txfNombre.setText(empleado.getPersona().getNombre());

                txfApellido.setEnabled(true);
                txfApellido.setText(empleado.getPersona().getApellido());

                txfCuit.setEnabled(true);
                txfCuit.setText(String.valueOf(empleado.getPersona().getCuit()));

                txfEmail.setEnabled(true);
                txfEmail.setText(empleado.getPersona().getEmail());

                txfTelefono.setEnabled(true);
                txfTelefono.setText(String.valueOf(empleado.getPersona().getTelefono()));

                selectCalificacionInquilino.setEnabled(true);
                selectCalificacionInquilino.setSelectedItem(empleado.getPersona().getCalificacionInquilino());

                selectCalificacionPropietario.setEnabled(true);
                selectCalificacionPropietario.setSelectedItem(empleado.getPersona().getCalificacionPropietario());

                selectCalificacionGarante.setEnabled(true);
                selectCalificacionGarante.setSelectedItem(empleado.getPersona().getCalificacionGarante());

                selectCalificacionEmpleado.setEnabled(true);
                selectCalificacionEmpleado.setSelectedItem(empleado.getPersona().getCalificacionEmpleado());

                btnBorrar.setEnabled(true);
                btnSalir.setEnabled(true);
                btnLimpiar.setEnabled(true);
                btnGuardar.setEnabled(true);
        }

        public void limpiar(Boolean state) {

                txfId.setText("");
                txfDni.setText("");

                selectCargo.setEnabled(state);
                selectCargo.setSelectedItem("Vendedor");

                txfNombre.setEnabled(state);
                txfNombre.setText("");

                txfApellido.setEnabled(state);
                txfApellido.setText("");

                txfCuit.setEnabled(state);
                txfCuit.setText("");

                txfEmail.setEnabled(state);
                txfEmail.setText("");

                txfTelefono.setEnabled(state);
                txfTelefono.setText("");

                selectCalificacionInquilino.setEnabled(state);
                selectCalificacionInquilino.setSelectedItem("Ninguna");
                selectCalificacionPropietario.setEnabled(state);
                selectCalificacionPropietario.setSelectedItem("Ninguna");
                selectCalificacionGarante.setEnabled(state);
                selectCalificacionGarante.setSelectedItem("Ninguna");
                selectCalificacionEmpleado.setEnabled(state);
                selectCalificacionEmpleado.setSelectedItem("Ninguna");

                btnBorrar.setEnabled(false);
                btnSalir.setEnabled(true);
                btnLimpiar.setEnabled(false);
                btnGuardar.setEnabled(true);
        }

        public Boolean validar() {
                String result = "";
                try {
                        Persona persona = new Persona();

                        // Si editamos obtenemos el id de la persona
                        if (empleado.getPersona() != null) {
                                persona.setId(empleado.getPersona().getId());
                        }

                        // validamos el nombre
                        if (txfNombre.getText().isBlank()) {
                                result += "Nombre: Vacio\n";
                        } else {
                                persona.setNombre(txfNombre.getText());
                        }

                        // validamos el apellido
                        if (txfApellido.getText().isBlank()) {
                                result += "Apellido: Vacio\n";
                        } else {
                                persona.setApellido(txfApellido.getText());
                        }

                        // validamos el dni
                        if (txfDni.getText().isBlank()) {
                                result += "Dni: Vacio\n";
                        } else {
                                try {
                                        persona.setDni(Long.parseLong(txfDni.getText()));
                                } catch (Exception e) {
                                        // TODO: handle exception
                                        result += "Dni: Debe ser un numero\n";
                                }
                        }

                        // validamos el cuit
                        if (!txfCuit.getText().isBlank()) {
                                try {
                                        persona.setCuit(Long.parseLong(txfCuit.getText()));
                                } catch (Exception e) {
                                        // TODO: handle exception
                                        result += "Cuit: Debe ser un numero\n";
                                }
                        }

                        // validamos el email
                        if (!txfEmail.getText().isBlank()) {
                                persona.setEmail(txfEmail.getText());
                        }

                        // Validamos telefono
                        if (!txfTelefono.getText().isBlank()) {
                                try {
                                        persona.setTelefono(Long.parseLong(txfTelefono.getText()));
                                } catch (Exception e) {
                                        // TODO: handle exception
                                        result += "Telefono: Debe ser un numero\n";
                                }
                        }

                        persona.setCalificacionInquilino(selectCalificacionInquilino.getSelectedItem().toString());
                        persona
                                        .setCalificacionPropietario(
                                                        selectCalificacionPropietario.getSelectedItem().toString());
                        persona.setCalificacionGarante(selectCalificacionGarante.getSelectedItem().toString());
                        persona.setCalificacionEmpleado(selectCalificacionEmpleado.getSelectedItem().toString());

                        empleado.setCargo(selectCargo.getSelectedItem().toString());

                        if (result.isEmpty() || result.isBlank()) {
                                empleado.setPersona(persona);                                
                                return true;
                        } else {
                                JOptionPane.showMessageDialog(null, "Valores ingresados invalidos\n" + result,
                                                "Valores invalidos",
                                                JOptionPane.WARNING_MESSAGE);
                                return false;
                        }
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(null,
                                        "Valores ingresados invalidos\n" + result + "\n" + e.getMessage(),
                                        "Valores invalidos",
                                        JOptionPane.ERROR_MESSAGE);
                        return false;
                }
        }

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jPanel5 = new javax.swing.JPanel();
                jPanel3 = new javax.swing.JPanel();
                jPanel4 = new javax.swing.JPanel();
                jLabel12 = new javax.swing.JLabel();
                jLabel11 = new javax.swing.JLabel();
                jLabel10 = new javax.swing.JLabel();
                jLabel13 = new javax.swing.JLabel();
                selectCalificacionInquilino = new javax.swing.JComboBox<>();
                selectCalificacionPropietario = new javax.swing.JComboBox<>();
                selectCalificacionGarante = new javax.swing.JComboBox<>();
                selectCalificacionEmpleado = new javax.swing.JComboBox<>();
                jLabel14 = new javax.swing.JLabel();
                jLabel15 = new javax.swing.JLabel();
                selectCargo = new javax.swing.JComboBox<>();
                jPanel2 = new javax.swing.JPanel();
                jLabel1 = new javax.swing.JLabel();
                jLabel2 = new javax.swing.JLabel();
                jLabel3 = new javax.swing.JLabel();
                jLabel4 = new javax.swing.JLabel();
                jLabel5 = new javax.swing.JLabel();
                jLabel6 = new javax.swing.JLabel();
                jLabel7 = new javax.swing.JLabel();
                txfId = new javax.swing.JTextField();
                txfDni = new javax.swing.JTextField();
                txfNombre = new javax.swing.JTextField();
                txfApellido = new javax.swing.JTextField();
                txfCuit = new javax.swing.JTextField();
                txfEmail = new javax.swing.JTextField();
                txfTelefono = new javax.swing.JTextField();
                jLabel9 = new javax.swing.JLabel();
                jPanel1 = new javax.swing.JPanel();
                btnGuardar = new javax.swing.JButton();
                btnSalir = new javax.swing.JButton();
                btnBorrar = new javax.swing.JButton();
                btnLimpiar = new javax.swing.JButton();

                setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

                jPanel3.setBorder(
                                javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(153, 153, 153)));
                jPanel3.setPreferredSize(new java.awt.Dimension(235, 287));

                jLabel12.setText("Garante:");

                jLabel11.setText("Propietario:");

                jLabel10.setText("Inquilino:");

                jLabel13.setText("Calificacion de la persona");

                selectCalificacionInquilino.setModel(new javax.swing.DefaultComboBoxModel<>(
                                new String[] { "Excelente", "Buena", "Mala", "Ninguna" }));
                selectCalificacionInquilino.setEnabled(false);

                selectCalificacionPropietario.setModel(new javax.swing.DefaultComboBoxModel<>(
                                new String[] { "Excelente", "Buena", "Mala", "Ninguna" }));
                selectCalificacionPropietario.setEnabled(false);

                selectCalificacionGarante.setModel(new javax.swing.DefaultComboBoxModel<>(
                                new String[] { "Excelente", "Buena", "Mala", "Ninguna" }));
                selectCalificacionGarante.setEnabled(false);

                selectCalificacionEmpleado.setModel(new javax.swing.DefaultComboBoxModel<>(
                                new String[] { "Excelente", "Buena", "Mala", "Ninguna" }));
                selectCalificacionEmpleado.setEnabled(false);

                jLabel14.setText("Empleado:");

                javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
                jPanel4.setLayout(jPanel4Layout);
                jPanel4Layout.setHorizontalGroup(
                                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                jPanel4Layout.createSequentialGroup()
                                                                                .addContainerGap(56, Short.MAX_VALUE)
                                                                                .addComponent(jLabel13)
                                                                                .addGap(36, 36, 36))
                                                .addGroup(jPanel4Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel4Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jLabel11)
                                                                                .addComponent(jLabel12)
                                                                                .addComponent(jLabel14)
                                                                                .addComponent(jLabel10))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(jPanel4Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                false)
                                                                                .addComponent(selectCalificacionEmpleado,
                                                                                                0, 130, Short.MAX_VALUE)
                                                                                .addComponent(selectCalificacionGarante,
                                                                                                0,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(selectCalificacionPropietario,
                                                                                                0,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(selectCalificacionInquilino,
                                                                                                0,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
                jPanel4Layout.setVerticalGroup(
                                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout
                                                                .createSequentialGroup()
                                                                .addComponent(jLabel13,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                24,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel4Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(selectCalificacionInquilino,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                30,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jLabel10))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel4Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(selectCalificacionPropietario,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                30,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jLabel11))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel4Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel12)
                                                                                .addComponent(selectCalificacionGarante,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                30,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel4Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel14)
                                                                                .addComponent(selectCalificacionEmpleado,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                30,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));

                jLabel15.setText("Cargo:");

                selectCargo.setModel(new javax.swing.DefaultComboBoxModel<>(
                                new String[] { "Dueño", "Gerente", "Vendedor", "Aprendiz" }));
                selectCargo.setSelectedIndex(2);
                selectCargo.setEnabled(false);
                selectCargo.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                selectCargoActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
                jPanel3.setLayout(jPanel3Layout);
                jPanel3Layout.setHorizontalGroup(
                                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(jPanel4,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(40, 40, 40))
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                                .addGap(26, 26, 26)
                                                                .addComponent(jLabel15)
                                                                .addGap(26, 26, 26)
                                                                .addComponent(selectCargo,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                131,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
                jPanel3Layout.setVerticalGroup(
                                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap(32, Short.MAX_VALUE)
                                                                .addGroup(jPanel3Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(selectCargo,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                30,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jLabel15))
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jPanel4,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(61, 61, 61)));

                jPanel2.setBorder(
                                javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(204, 204, 204)));

                jLabel1.setText("ID:");

                jLabel2.setText("Nombre:");

                jLabel3.setText("Apellido:");

                jLabel4.setText("Dni:");

                jLabel5.setText("Cuit:");

                jLabel6.setText("Email:");

                jLabel7.setText("Telefono");

                txfId.setEnabled(false);

                txfDni.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txfDniActionPerformed(evt);
                        }
                });

                txfNombre.setEnabled(false);

                txfApellido.setEnabled(false);

                txfCuit.setEnabled(false);

                txfEmail.setEnabled(false);

                txfTelefono.setEnabled(false);

                jLabel9.setText("Datos Personales");

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jLabel1,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                34,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jLabel4)
                                                                                .addComponent(jLabel2)
                                                                                .addComponent(jLabel3)
                                                                                .addComponent(jLabel5)
                                                                                .addComponent(jLabel6)
                                                                                .addComponent(jLabel7))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(txfEmail,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                150,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(txfCuit,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                150,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(txfApellido,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                150,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(txfNombre,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                150,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(txfDni,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                150,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(txfId,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                150,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(txfTelefono,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                150,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jLabel9,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                95,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap()));
                jPanel2Layout.setVerticalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addGap(20, 20, 20)
                                                                .addComponent(jLabel9)
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel1)
                                                                                .addComponent(txfId,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                30,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel4)
                                                                                .addComponent(txfDni,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                30,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel2)
                                                                                .addComponent(txfNombre,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                30,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(6, 6, 6)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel3)
                                                                                .addComponent(txfApellido,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                30,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(6, 6, 6)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel5)
                                                                                .addComponent(txfCuit,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                30,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel6)
                                                                                .addComponent(txfEmail,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                30,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(6, 6, 6)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel7)
                                                                                .addComponent(txfTelefono,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                30,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));

                btnGuardar.setText("Guardar");
                btnGuardar.setEnabled(false);
                btnGuardar.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnGuardarActionPerformed(evt);
                        }
                });

                btnSalir.setText("Salir");
                btnSalir.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnSalirActionPerformed(evt);
                        }
                });

                btnBorrar.setText("Borrar");
                btnBorrar.setEnabled(false);
                btnBorrar.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnBorrarActionPerformed(evt);
                        }
                });

                btnLimpiar.setText("Limpiar");
                btnLimpiar.setEnabled(false);
                btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnLimpiarActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(btnGuardar,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                73,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(btnLimpiar,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                73,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(btnBorrar,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                73,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(btnSalir,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                73,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
                jPanel1Layout.setVerticalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(btnGuardar)
                                                                                .addComponent(btnSalir)
                                                                                .addComponent(btnBorrar)
                                                                                .addComponent(btnLimpiar))
                                                                .addContainerGap()));

                javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
                jPanel5.setLayout(jPanel5Layout);
                jPanel5Layout.setHorizontalGroup(
                                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel5Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel5Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jPanel2,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(jPanel3,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                245,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addContainerGap())
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                jPanel5Layout.createSequentialGroup()
                                                                                                                .addGap(0, 79, Short.MAX_VALUE)
                                                                                                                .addComponent(jPanel1,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addGap(79, 79, 79)))));
                jPanel5Layout.setVerticalGroup(
                                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel5Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                false)
                                                                                .addComponent(jPanel2,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel3,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                336,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jPanel1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                                .createSequentialGroup()
                                                                .addGap(0, 10, Short.MAX_VALUE)
                                                                .addComponent(jPanel5,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE));

                pack();
        }// </editor-fold>//GEN-END:initComponents

        private void txfDniActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txfDniActionPerformed

                if (!txfDni.getText().isBlank()) {
                        try {
                                empleado = empleadoData.obtenerEmpleadosXDni(Long.parseLong(txfDni.getText()));

                                if (empleado != null) {
                                        obtenerEmpleados();
                                } else {
                                        empleado = new Empleado();
                                        String aux = txfDni.getText();
                                        limpiar(true);
                                        txfDni.setText(aux);
                                }

                        } catch (Exception e) {
                                // TODO: handle exception
                                JOptionPane.showMessageDialog(null, "El dni ingresado es invalido \n" + e.getMessage(),
                                                "Dni invalido",
                                                JOptionPane.WARNING_MESSAGE);
                        }
                }

        }// GEN-LAST:event_txfDniActionPerformed

        private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnGuardarActionPerformed
                if (validar() && empleado != null) {
                        if (empleado.getId() != null) {
                                // Actualizamos el empleado y la persona
                                int result = JOptionPane.showConfirmDialog(null,
                                                "Esta seguro que desea editar el empleado: ", "Editar empleado",
                                                JOptionPane.YES_NO_OPTION);
                                if (result == 0) {
                                        empleadoData.editarEmpleado(empleado);
                                }
                        } else {
                                int result = JOptionPane.showConfirmDialog(null,
                                                "Esta seguro que desea agregar el empleado: ", "Agregar empleado",
                                                JOptionPane.YES_NO_OPTION);
                                if (result == 0) {
                                        if (empleadoData.agregarEmpleado(empleado)) {
                                                dispose();
                                        }
                                }
                        }

                }
        }// GEN-LAST:event_btnGuardarActionPerformed

        private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSalirActionPerformed
                dispose();
        }// GEN-LAST:event_btnSalirActionPerformed

        private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnBorrarActionPerformed
                if (empleado.getId() != null) {
                        int result = JOptionPane.showConfirmDialog(null,
                                        "Esta seguro que desea eliminar el empleado: ", "Eliminar empleado",
                                        JOptionPane.YES_NO_OPTION);
                        if (result == 0) {
                                if (empleadoData.eliminarEmpleado(empleado.getId())) {
                                        empleado = new Empleado();
                                        limpiar(false);
                                }
                        }
                }
        }// GEN-LAST:event_btnBorrarActionPerformed

        private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnLimpiarActionPerformed
                limpiar(false);
        }// GEN-LAST:event_btnLimpiarActionPerformed

        private void selectCargoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_selectCargoActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_selectCargoActionPerformed

        /**
         * @param args the command line arguments
         */
        public static void main(String args[]) {
                /* Set the Nimbus look and feel */
                // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
                // (optional) ">
                /*
                 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
                 * look and feel.
                 * For details see
                 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
                 */
                try {
                        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                                        .getInstalledLookAndFeels()) {
                                if ("Nimbus".equals(info.getName())) {
                                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                                        break;
                                }
                        }
                } catch (ClassNotFoundException ex) {
                        java.util.logging.Logger.getLogger(EmpleadoDialogView.class.getName()).log(
                                        java.util.logging.Level.SEVERE,
                                        null, ex);
                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(EmpleadoDialogView.class.getName()).log(
                                        java.util.logging.Level.SEVERE,
                                        null, ex);
                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(EmpleadoDialogView.class.getName()).log(
                                        java.util.logging.Level.SEVERE,
                                        null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(EmpleadoDialogView.class.getName()).log(
                                        java.util.logging.Level.SEVERE,
                                        null, ex);
                }
                // </editor-fold>

                /* Create and display the dialog */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                EmpleadoDialogView dialog = new EmpleadoDialogView(new javax.swing.JFrame(), true);
                                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                                        @Override
                                        public void windowClosing(java.awt.event.WindowEvent e) {
                                                System.exit(0);
                                        }
                                });
                                dialog.setVisible(true);
                        }
                });
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton btnBorrar;
        private javax.swing.JButton btnGuardar;
        private javax.swing.JButton btnLimpiar;
        private javax.swing.JButton btnSalir;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel10;
        private javax.swing.JLabel jLabel11;
        private javax.swing.JLabel jLabel12;
        private javax.swing.JLabel jLabel13;
        private javax.swing.JLabel jLabel14;
        private javax.swing.JLabel jLabel15;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel6;
        private javax.swing.JLabel jLabel7;
        private javax.swing.JLabel jLabel9;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JPanel jPanel3;
        private javax.swing.JPanel jPanel4;
        private javax.swing.JPanel jPanel5;
        private javax.swing.JComboBox<String> selectCalificacionEmpleado;
        private javax.swing.JComboBox<String> selectCalificacionGarante;
        private javax.swing.JComboBox<String> selectCalificacionInquilino;
        private javax.swing.JComboBox<String> selectCalificacionPropietario;
        private javax.swing.JComboBox<String> selectCargo;
        private javax.swing.JTextField txfApellido;
        private javax.swing.JTextField txfCuit;
        private javax.swing.JTextField txfDni;
        private javax.swing.JTextField txfEmail;
        private javax.swing.JTextField txfId;
        private javax.swing.JTextField txfNombre;
        private javax.swing.JTextField txfTelefono;
        // End of variables declaration//GEN-END:variables
}
