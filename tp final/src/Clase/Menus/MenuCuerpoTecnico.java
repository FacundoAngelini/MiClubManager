package Clase.Menus;

import Clase.Presupuesto.Contrato;
import Clase.Persona.CuerpoTecnico;
import enums.Puesto;
import exeptions.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuCuerpoTecnico {

    private MenuClub menuClub;
    private final Scanner scanner;

    public MenuCuerpoTecnico(MenuClub menuClub) {
        this.menuClub = menuClub;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenuCuerpoTecnico() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n----- MENU CUERPO TECNICO -----");
            System.out.println("1. Agregar miembro");
            System.out.println("2. Eliminar miembro");
            System.out.println("3. Listar miembros");
            System.out.println("4. Modificar miembro");
            System.out.println("5. Cambiar estado de contrato");
            System.out.println("6. Aplicar gasto de salarios");
            System.out.println("7. Volver al menú principal");
            System.out.print("Seleccione una opcion: ");

            int opcion;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un numero valido.");
                continue;
            }

            switch (opcion) {
                case 1 -> agregarMiembro();
                case 2 -> eliminarMiembro();
                case 3 -> listarMiembros();
                case 4 -> modificarMiembro();
                case 5 -> cambiarEstadoContrato();
                case 6 -> aplicarGastoSalarios();
                case 7 -> salir = true;
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    private void agregarMiembro() {
        try {
            System.out.print("DNI: ");
            String dni = scanner.nextLine();

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Apellido: ");
            String apellido = scanner.nextLine();

            System.out.print("Fecha de nacimiento (dd/mm/yyyy): ");
            String fechaNacimiento = scanner.nextLine();

            System.out.print("Nacionalidad: ");
            String nacionalidad = scanner.nextLine();

            System.out.print("Salario: ");
            double salario = Double.parseDouble(scanner.nextLine());

            System.out.print("Fecha inicio contrato (dd/mm/yyyy): ");
            String fechaInicio = scanner.nextLine();

            System.out.print("Fecha fin contrato (dd/mm/yyyy): ");
            String fechaFin = scanner.nextLine();

            System.out.print("Meses de duracion: ");
            int mesesDuracion = Integer.parseInt(scanner.nextLine());

            System.out.print("Puesto (DT, AYUDANTE, PREPARADOR, FISIOTERAPEUTA): ");
            Puesto puesto = Puesto.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("Anios de experiencia: ");
            int aniosExp = Integer.parseInt(scanner.nextLine());

            menuClub.club.getGestionCuerpoTecnico().agregarElemento(
                    dni, nombre, apellido, fechaNacimiento, nacionalidad,
                    salario, fechaInicio, fechaFin, mesesDuracion, puesto, aniosExp
            );
            menuClub.club.getGestionCuerpoTecnico().aplicarGastoSalarios(fechaInicio);
            menuClub.club.getGestionCuerpoTecnico().guardarJSON();
            System.out.println("Miembro agregado correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: valor invalido para Puesto o numerico.");
        }
    }

    private void eliminarMiembro() {
        try {
            System.out.print("DNI del miembro a eliminar: ");
            String dni = scanner.nextLine();

            menuClub.club.getGestionCuerpoTecnico().eliminarElemento(dni);

            menuClub.club.getGestionCuerpoTecnico().guardarJSON();
            System.out.println("Miembro eliminado correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listarMiembros() {
        ArrayList<CuerpoTecnico> lista = menuClub.club.getGestionCuerpoTecnico().listar();
        if (lista.isEmpty()) {
            System.out.println("No hay miembros del cuerpo técnico.");
            return;
        }
        for (CuerpoTecnico ct : lista) {
            System.out.println("DNI: " + ct.getDni() +
                    " | Nombre: " + ct.getNombre() +
                    " | Apellido: " + ct.getApellido() +
                    " | Puesto: " + ct.getPuesto() +
                    " | Experiencia: " + ct.getAniosExp() + " años" +
                    " | Salario: " + (ct.getContrato() != null ? ct.getContrato().getSalario() : "N/A") +
                    " | Contrato activo: " + (ct.getContrato() != null ? ct.getContrato().isContratoActivo() : "N/A"));
        }
    }

    private void modificarMiembro() {
        try {
            System.out.print("DNI del miembro a modificar: ");
            String dni = scanner.nextLine();

            CuerpoTecnico ct = menuClub.club.getGestionCuerpoTecnico().devuelveElemento(dni);

            System.out.print("Nuevo nombre (" + ct.getNombre() + "): ");
            String nombre = scanner.nextLine();
            if (nombre.isEmpty()) nombre = ct.getNombre();

            System.out.print("Nuevo apellido (" + ct.getApellido() + "): ");
            String apellido = scanner.nextLine();
            if (apellido.isEmpty()) apellido = ct.getApellido();

            System.out.print("Nueva fecha de nacimiento (" + ct.getFechaNacimiento() + "): ");
            String fechaNacimiento = scanner.nextLine();
            if (fechaNacimiento.isEmpty()) fechaNacimiento = ct.getFechaNacimiento();

            System.out.print("Nueva nacionalidad (" + ct.getNacionalidad() + "): ");
            String nacionalidad = scanner.nextLine();
            if (nacionalidad.isEmpty()) nacionalidad = ct.getNacionalidad();

            Contrato contrato = ct.getContrato();
            System.out.print("Nuevo salario (" + (contrato != null ? contrato.getSalario() : "0") + "): ");
            String salarioStr = scanner.nextLine();
            double salario = salarioStr.isEmpty() && contrato != null ? contrato.getSalario() : Double.parseDouble(salarioStr);

            System.out.print("Nueva fecha inicio (" + (contrato != null ? contrato.getFechaInicio() : "") + "): ");
            String fechaInicio = scanner.nextLine();
            if (fechaInicio.isEmpty() && contrato != null) fechaInicio = contrato.getFechaInicio();

            System.out.print("Nueva fecha fin (" + (contrato != null ? contrato.getFechaFin() : "") + "): ");
            String fechaFin = scanner.nextLine();
            if (fechaFin.isEmpty() && contrato != null) fechaFin = contrato.getFechaFin();

            System.out.print("Nueva duracion meses (" + (contrato != null ? contrato.getMesesDuracion() : "0") + "): ");
            String mesesStr = scanner.nextLine();
            int mesesDuracion = mesesStr.isEmpty() && contrato != null ? contrato.getMesesDuracion() : Integer.parseInt(mesesStr);

            System.out.print("Nuevo puesto (" + ct.getPuesto() + "): ");
            String puestoStr = scanner.nextLine();
            Puesto puesto = puestoStr.isEmpty() ? ct.getPuesto() : Puesto.valueOf(puestoStr.toUpperCase());

            System.out.print("Nueva experiencia anios (" + ct.getAniosExp() + "): ");
            String expStr = scanner.nextLine();
            int aniosExp = expStr.isEmpty() ? ct.getAniosExp() : Integer.parseInt(expStr);

            Contrato nuevoContrato = new Contrato(dni, salario, fechaFin, true, fechaInicio, mesesDuracion);

            menuClub.club.getGestionCuerpoTecnico().modificarCuerpoTecnico(
                    dni, nombre, apellido, fechaNacimiento, nacionalidad,
                    nuevoContrato, puesto, aniosExp
            );
            menuClub.club.getGestionCuerpoTecnico().guardarJSON();
            System.out.println("Miembro modificado correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: valor inválido para Puesto o numérico.");
        }
    }

    private void cambiarEstadoContrato() {
        try {
            System.out.print("DNI del miembro: ");
            String dni = scanner.nextLine();
            System.out.print("Nuevo estado de contrato (true/false): ");
            boolean estado = Boolean.parseBoolean(scanner.nextLine());

            menuClub.club.getGestionCuerpoTecnico().cambiarEstadoContrato(dni, estado);
            menuClub.club.getGestionCuerpoTecnico().guardarJSON();
            System.out.println("Estado de contrato actualizado correctamente.");
        } catch (ElementoInexistenteEx e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void aplicarGastoSalarios() {
        try {
            System.out.print("Fecha de aplicacion del gasto (dd/mm/yyyy): ");
            String fecha = scanner.nextLine();
            menuClub.club.getGestionCuerpoTecnico().aplicarGastoSalarios(fecha);

            menuClub.club.getGestionCuerpoTecnico().guardarJSON();
            System.out.println("Gasto de salarios aplicado correctamente.");
        } catch (IngresoInvalido e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
