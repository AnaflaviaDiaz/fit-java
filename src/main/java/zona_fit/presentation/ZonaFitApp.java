package zona_fit.presentation;

import zona_fit.data.ClientDAO;
import zona_fit.data.IClientDAO;
import zona_fit.domain.Client;

import java.util.Scanner;

public class ZonaFitApp {
    static void main() {
        zonaFitApp();
    }

    private static void zonaFitApp() {
        var shouldExit = false;
        var console = new Scanner(System.in);

        //objeto de la clase ClientDao
        IClientDAO clientDao = new ClientDAO();

        while (!shouldExit) {
            try {
                showMenu();
                var optionSelected = getNumberOperation(console);
                shouldExit = executeOptions(console, optionSelected, clientDao);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // salto de linea cada que muestra el menu
            System.out.println();
        }
    }

    private static boolean executeOptions(Scanner console, int option, IClientDAO clientDao)  {
        var shouldExit = false;
        switch (option) {
            case 1: {
                System.out.println("--Lista de clientes:");

                var clients=clientDao.getClients();
                clients.forEach(System.out::println);
                break;
            }
            case 2: {
                System.out.println("--Buscar cliente:");
                System.out.println("Ingresa el id del cliente: ");

                var id = Integer.parseInt(console.nextLine());
                var client = new Client(id);
                var exists = clientDao.getClientById(client);
                if (exists) {
                    System.out.println("Se encontró el cliente: " + client);
                } else {
                    System.out.println("No se encontró el cliente con el ID: " + id);
                }
                break;
            }
            case 3: {
                System.out.println("--Agregar un nuevo cliente:");

                var userFromConsole = getUserDataFromConsole(console);
                var client = new Client(userFromConsole.name, userFromConsole.lastname, userFromConsole.membership);
                var hasCreated = clientDao.addClient(client);
                if (hasCreated) {
                    System.out.println("Se creó el cliente: " + client);
                } else {
                    System.out.println("No se creó el cliente con el ID: " + client);
                }
                break;
            }
            case 4: {
                System.out.println("--Actualizar cliente:");
                System.out.println("Ingresa el id del cliente a actualizar: ");

                var id = Integer.parseInt(console.nextLine());
                var client = new Client(id);
                var exists = clientDao.getClientById(client);
                if (exists) {
                    var userFromConsole = getUserDataFromConsole(console);
                    var clientUpdated = new Client(id, userFromConsole.name, userFromConsole.lastname, userFromConsole.membership);
                    var hasUpdated = clientDao.updateClient(clientUpdated);
                    if (hasUpdated) {
                        System.out.println("Se actualizó el cliente: " + clientUpdated);
                    } else {
                        System.out.println("No se actualizó el cliente con el ID: " + clientUpdated);
                    }
                } else {
                    System.out.println("No se encontró el cliente con el ID: " + id);
                }
                break;
            }
            case 5: {
                System.out.println("--Eliminar cliente:");
                System.out.println("Ingresa el id del cliente: ");

                var id = Integer.parseInt(console.nextLine());
                var client = new Client(id);
                var exists = clientDao.getClientById(client);
                if (exists) {
                    var hasDeleted = clientDao.deleteClient(client);
                    if (hasDeleted) {
                        System.out.println("Se ha eliminado el cliente: " + client);
                    } else {
                        System.out.println("Hubo un problema al eliminar el usuario con ID: " + id);
                    }
                } else {
                    System.out.println("No se encontró el cliente con el ID: " + id);
                }
                break;
            }
            case 6: {
                shouldExit = true;
                System.out.println("Hasta pronto");
                break;
            }
            default: {
                System.out.println("Opción inválida");
                break;
            }
        }

        return shouldExit;
    }

    private record UserData(String name, String lastname, int membership) {}

    private static UserData getUserDataFromConsole(Scanner console) {
        System.out.println("Ingresa el nombre del cliente: ");
        var name = console.nextLine();
        System.out.println("Ingresa el apellido del cliente: ");
        var lastname = console.nextLine();
        System.out.println("Ingresa el numero de membresía del cliente: ");
        var membership = Integer.parseInt(console.nextLine());

        return new UserData(name, lastname, membership);
    }

    private static void showMenu() {
        System.out.println("""
                ***Zona Fit (GYM)***
                1. Listar clientes
                2. Buscar cliente
                3. Agregar un nuevo cliente
                4. Actualizar datos del cliente
                5. Eliminar cliente
                6. Salir
                Elige una opcion:\s""");
    }

    private static int getNumberOperation(Scanner console){
        return Integer.parseInt(console.nextLine());
    }
}
