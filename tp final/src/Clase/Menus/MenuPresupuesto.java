package Clase.Menus;

import enums.Posicion;
import exeptions.ElementoInexistenteEx;
import exeptions.FondoInsuficienteEx;
import exeptions.IngresoInvalido;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuPresupuesto {

    private MenuClub menuClub;
    private final Scanner scanner;

    public MenuPresupuesto(MenuClub menuClub) {
        this.menuClub = menuClub;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenuPresupuesto() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n----- MENU PRESUPUESTO -----");
            System.out.println("1. Agregar fondos");
            System.out.println("2. Quitar fondos");
            System.out.println("3. Ver saldo actual");
            System.out.println("4. Ver transacciones");
            System.out.println("5. Calcular pago de salios del CT");
            System.out.println("6. Aplicar gastos de los salarios del CT");
            System.out.println("7. Actualizar costo del mantenimiento del estadio");
            System.out.println("8. Pagar el mantenimiento del estadio");
            System.out.println("9. Pagar salarios de los jugadores");
            System.out.println("10 Aplicar la recaudacion de los socios");
            System.out.println("11.Mostrar la recaudacion total de los socios");
            System.out.println("12.Comprar jugador");
            System.out.println("13.Vender jugador");
            System.out.println("14 salir");
            System.out.print("Seleccione una opcion: ");

            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1" -> agregarFondos();
                case "2" -> quitarFondos();
                case "3" -> verSaldo();
                case "4" -> listarTransacciones();
                case "5" -> calcularGastoSalariosCT();
                case "6" -> aplicarGastoSalariosCT();
                case "7" -> actualizarCostoMantenimientoEstadio();
                case "8" -> pagarMantenimientoEstadio();
                case "9" -> pagarSalariosJugadores();
                case "10" -> aplicarRecaudacionSocios();
                case "11" -> mostrarRecaudacionTotalSocios();
                case "12" -> comprarJugador();
                case "13" -> venderJugador();
                case "14" -> salir = true;
                default -> System.out.println("Opcion invalida");
            }
        }
    }

    private void agregarFondos() {
        Double monto = null;
        String descripcion = null;
        LocalDate fecha = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (monto == null) {
            try {
                System.out.print("Monto a agregar: ");
                monto = Double.parseDouble(scanner.nextLine());
                if (monto <= 0) {
                    System.out.println("Monto debe ser mayor que 0");
                    monto = null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Monto invalido, ingrese un numero");
            }
        }

        while (descripcion == null) {
            System.out.print("Descripcion del ingreso: ");
            String input = scanner.nextLine();
            if (input == null || input.isBlank()) {
                System.out.println("Descripcion obligatoria");
            } else {
                descripcion = input;
            }
        }



        while (fecha == null) {
            try {
                System.out.print("Fecha (dd/MM/yyyy): ");
                String input = scanner.nextLine().trim();
                fecha = LocalDate.parse(input, formatter);
            } catch (Exception e) {
                System.out.println("Fecha invalida, use formato dd/MM/yyyy");
            }
        }

        try {
            menuClub.club.getGestionPresupuesto().agregarFondos(monto, descripcion, fecha);
            System.out.println("Fondos agregados correctamente");
        } catch (IngresoInvalido e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void quitarFondos() {
        Double monto = null;
        String descripcion = null;
        LocalDate fecha = null;

        while (monto == null) {
            try {
                System.out.print("Monto a retirar: ");
                monto = Double.parseDouble(scanner.nextLine());
                if (monto <= 0) {
                    System.out.println("Monto debe ser mayor que 0");
                    monto = null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Monto invalido, ingrese un numero");
            }
        }

        while (descripcion == null) {
            System.out.print("Descripcion del retiro: ");
            String input = scanner.nextLine();
            if (input == null || input.isBlank()) {
                System.out.println("Descripcion obligatoria");
            } else {
                descripcion = input;
            }
        }

        while (fecha == null) {
            try {
                System.out.print("Fecha (yyyy-mm-dd): ");
                fecha = LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Fecha invalida, intente de nuevo");
            }
        }

        try {
            menuClub.club.getGestionPresupuesto().quitarFondos(monto, descripcion, fecha);
            System.out.println("Fondos retirados correctamente");
        } catch (IngresoInvalido | FondoInsuficienteEx e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void verSaldo() {
        double saldo = menuClub.club.getGestionPresupuesto().verSaldoActual();
        System.out.println("Saldo actual: " + saldo);
    }

    private void listarTransacciones() {
        ArrayList<String> transacciones = menuClub.club.getGestionPresupuesto().listarTransaccionesInfo();
        if (transacciones.isEmpty()) {
            System.out.println("No hay transacciones registradas");
            return;
        }

        System.out.println("\nTransacciones:");
        transacciones.forEach(System.out::println);
    }

    private void calcularGastoSalariosCT() {
        try {
            double gasto = menuClub.club.getGestionCuerpoTecnico().calcularGastoSalarios();
            System.out.println("Gasto total en salarios del cuerpo técnico: " + gasto);
        } catch (Exception e) {
            System.out.println("Error al calcular salarios: " + e.getMessage());
        }
    }

    private void aplicarGastoSalariosCT() {

        LocalDate fecha = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        while (fecha == null) {
            System.out.print("Ingrese la fecha del pago (dd-MM-yyyy): ");
            try {
                String input = scanner.nextLine().trim();
                fecha = LocalDate.parse(input, formatter);

            } catch (Exception e) {
                System.out.println("Fecha invalida. Use formato dd-MM-yyyy");
            }
        }

        try {
            menuClub.club.getGestionCuerpoTecnico().aplicarGastoSalarios(fecha);
            System.out.println("Pago de salarios aplicado correctamente");

        } catch (Exception ex) {
            System.out.println("No se pudo aplicar el pago. Intente nuevamente");
        }
    }


    private void actualizarCostoMantenimientoEstadio() {
        try {
            System.out.print("Ingrese el nuevo costo de mantenimiento: ");
            double nuevoCosto = Double.parseDouble(scanner.nextLine());

            menuClub.club.getGestionEstadios().actualizarCostoMantenimiento(nuevoCosto);
        } catch (NumberFormatException e) {
            System.out.println("Error: monto invalido");
        } catch (Exception e) {
            System.out.println("Error al actualizar costo: " + e.getMessage());
        }
    }

    private void pagarMantenimientoEstadio() {
        try {
            System.out.print("Ingrese la fecha del pago (yyyy-mm-dd): ");
            LocalDate fecha = LocalDate.parse(scanner.nextLine());

            menuClub.club.getGestionEstadios().pagarMantenimiento(fecha);
        } catch (Exception e) {
            System.out.println("Error al pagar mantenimiento: " + e.getMessage());
        }
    }

    public void pagarSalariosJugadores() {
        try {
            System.out.print("Ingrese la fecha del pago (yyyy-mm-dd): ");
            LocalDate fecha = LocalDate.parse(scanner.nextLine());

            menuClub.club.getGestionJugadores().pagarSalarios(fecha);
        } catch (FondoInsuficienteEx | IngresoInvalido e) {
            System.out.println("Error al pagar salarios: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    public void aplicarRecaudacionSocios() {
        try {
            System.out.print("Ingrese la fecha de la recaudacion (yyyy-mm-dd) o deje vacio para hoy: ");
            String input = scanner.nextLine().trim();
            LocalDate fecha;
            if (input.isEmpty()) {
                fecha = LocalDate.now();
            } else {
                fecha = LocalDate.parse(input);
            }

            menuClub.club.getGestionSocios().aplicarRecaudacion(fecha);
            System.out.println("Recaudacion aplicada correctamente al presupuesto");

        } catch (IngresoInvalido e) {
            System.out.println("Error al aplicar recaudacion: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado");
        }
    }

    public void mostrarRecaudacionTotalSocios() {
        try {
            double total = menuClub.club.getGestionSocios().obtenerRecaudacionTotal();
            System.out.println("Recaudacion total de socios: $" + total);
        } catch (Exception e) {
            System.out.println("Error al obtener la recaudacion: " + e.getMessage());
        }
    }
    private void comprarJugador() {

        String dni = null;
        String nombre = null;
        String apellido = null;
        LocalDate fechaNacimiento = null;
        String nacionalidad = null;
        Integer numeroCamiseta = null;
        Double valorJugador = null;
        Double salario = null;
        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;
        Posicion posicion = null;

        LocalDate fechaCompra = null;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (fechaCompra == null) {
            try {
                System.out.print("Fecha de compra (dd/MM/yyyy): ");
                String input = scanner.nextLine().trim();
                LocalDate f = LocalDate.parse(input, formato);

                if (f.isAfter(LocalDate.now())) {
                    System.out.println("La fecha no puede ser futura.");
                } else {
                    fechaCompra = f;
                }
            } catch (Exception e) {
                System.out.println("Formato invalido. Use dd/MM/yyyy");
            }
        }

        while (dni == null) {
            System.out.print("Ingrese DNI solo numeros ");
            String input = scanner.nextLine().trim();
            if (!input.matches("\\d+")) {
                System.out.println("El DNI solo debe contener numeros");
            } else if (input.length() < 8) {
                System.out.println("El DNI debe tener minimo 8 digitos");
            } else if (menuClub.club.getGestionJugadores().existeJugador(input)) {
                System.out.println("Ya existe un jugador con ese DNI");
            } else {
                dni = input;
            }
        }


        while (nombre == null) {
            System.out.print("Ingrese nombre: ");
            String input = scanner.nextLine().trim();
            if (!input.matches("[a-zA-ZáéíóúÁÉÍÓÚ ]+")) {
                System.out.println("Nombre invalido. Solo letras");
            } else nombre = input;
        }

        while (apellido == null) {
            System.out.print("Ingrese apellido: ");
            String input = scanner.nextLine().trim();
            if (!input.matches("[a-zA-ZáéíóúÁÉÍÓÚ ]+")) {
                System.out.println("Apellido invalido. Solo letras");
            } else apellido = input;
        }

        while (fechaNacimiento == null) {
            try {
                System.out.print("Fecha nacimiento (dd/MM/yyyy): ");
                String input = scanner.nextLine().trim();
                LocalDate f = LocalDate.parse(input, formato);

                if (f.plusYears(14).isAfter(LocalDate.now())) {
                    System.out.println("El jugador debe tener al menos 14 años");
                } else {
                    fechaNacimiento = f;
                }

            } catch (Exception e) {
                System.out.println("Fecha invalida. Use dd/MM/yyyy");
            }
        }

        while (nacionalidad == null) {
            System.out.print("Nacionalidad: ");
            String input = scanner.nextLine().trim();

            if (input.isBlank()) {
                System.out.println("No puede estar vacia");
            } else if (!input.matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]+")) {
                System.out.println("Solo se permiten letras y espacios");
            } else {
                nacionalidad = input;
            }
        }



        while (numeroCamiseta == null) {
            System.out.print("Numero de camiseta (1-99): ");
            try {
                int num = Integer.parseInt(scanner.nextLine());
                if (num < 1 || num > 99) System.out.println("Numero invalido");
                else if (menuClub.club.getGestionJugadores().numeroCamisetaOcupado(num))
                    System.out.println("Numero ya ocupado");
                else numeroCamiseta = num;
            } catch (Exception e) {
                System.out.println("Debe ser un numero entero");
            }
        }

        while (valorJugador == null) {
            System.out.print("Valor del jugador: ");
            try {
                double val = Double.parseDouble(scanner.nextLine().trim());

                if (val <= 0) {
                    System.out.println("El valor debe ser mayor a 0.");
                }
                else if (menuClub.club.getGestionPresupuesto().verSaldoActual() < val) {
                    System.out.println("Fondos insuficientes para esta compra");
                    System.out.println("Ingrese un valor diferente.");
                }
                else {
                    valorJugador = val;
                }

            } catch (NumberFormatException e) {
                System.out.println("Valor invalido. Debe ser un numero");
            }
        }

        while (salario == null) {
            System.out.print("Salario mensual: ");
            try {
                double s = Double.parseDouble(scanner.nextLine().trim());

                if (s <= 0) {
                    System.out.println("El salario debe ser mayor a 0");
                } else {
                    salario = s;
                }

            } catch (NumberFormatException e) {
                System.out.println("Salario invalido. Debe ser un numero");
            }
        }

        while (fechaInicio == null) {
            try {
                System.out.print("Fecha inicio contrato (dd/MM/yyyy): ");
                String input = scanner.nextLine().trim();

                LocalDate inicio = LocalDate.parse(input, formato);

                if (inicio.isBefore(fechaCompra)) {
                    System.out.println("La fecha de inicio del contrato no puede ser antes de la fecha de compra");
                } else {
                    fechaInicio = inicio;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Formato invalido. Use dd/MM/yyyy");
            }
        }


        while (fechaFin == null) {
            try {
                System.out.print("Fecha fin contrato (dd/MM/yyyy): ");
                String input = scanner.nextLine().trim();
                LocalDate fin = LocalDate.parse(input, formato);

                if (fin.isBefore(fechaInicio)) {
                    System.out.println("La fecha de fin no puede ser anterior a la de inicio");
                } else {
                    fechaFin = fin;
                }

            } catch (Exception e) {
                System.out.println("Formato invalido. Use dd/MM/yyyy");
            }
        }

        while (posicion == null) {
            System.out.print("Posicion (PORTERO, DEFENSOR, MEDIOCAMPISTA, DELANTERO): ");
            try {
                posicion = Posicion.valueOf(scanner.nextLine().trim().toUpperCase());
            } catch (Exception e) {
                System.out.println("Posicion invalida");
            }
        }

        try {
            menuClub.club.getGestionPresupuesto().quitarFondos(valorJugador, "Compra de jugador " + nombre + " " + apellido, fechaCompra);

            menuClub.club.getGestionJugadores().agregarJugador(dni, nombre, apellido, fechaNacimiento, nacionalidad, numeroCamiseta, valorJugador, salario, fechaInicio, fechaFin, posicion);

            System.out.println("Jugador comprado y agregado correctamente");

        } catch (Exception e) {
            System.out.println("Error al comprar jugador ");
        }
    }

    private void venderJugador() {
        boolean vendido = false;

        while (!vendido) {

            String dni;
            while (true) {
                System.out.print("Ingrese DNI del jugador a vender o salir para volver ");
                dni = scanner.nextLine().trim();

                if (dni.equalsIgnoreCase("salir")) return;

                if (!dni.matches("\\d+")) {
                    System.out.println("El DNI solo debe contener numeros");
                } else if (dni.length() < 8) {
                    System.out.println("El DNI debe tener minimo 8 digitos");
                } else {
                    break;
                }
            }

            double monto;
            while (true) {
                System.out.print("Ingrese monto de la venta o salir para volver ");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("salir")) return;

                try {
                    monto = Double.parseDouble(input);
                    if (monto <= 0) {
                        System.out.println("El monto debe ser mayor a 0");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Monto invalido");
                }
            }

            try {
                menuClub.club.getGestionJugadores().venderJugador(dni, monto, menuClub.club.getGestionPresupuesto());
                System.out.println("Jugador vendido correctamente");
                vendido = true;
            } catch (IngresoInvalido | ElementoInexistenteEx e) {
                System.out.println("Error jugador no existe o ingreso invalido");
            }
        }
    }









}
