package zona_fit.data;

import zona_fit.connection.Conexion;
import zona_fit.domain.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO implements IClientDAO{

    @Override
    public List<Client> getClients() {
        List<Client> clients = new ArrayList<>();
        PreparedStatement preparedStatement; // para preparar sentencia sql
        ResultSet resultSet; // para recibir la consulta sql
        Connection connection = Conexion.getConnection(); // conexion para la db

        // consulta de db de la tabla Client ordernando por ID
        final String sql = "SELECT * FROM client ORDER BY id";

        try {
            preparedStatement = connection.prepareStatement(sql);

            // ejecutar sentencia
            resultSet = preparedStatement.executeQuery();

            // itierar cada 1 de los resultados
            // .next() si devuelve false, ya no hay más elementos
            while (resultSet.next()) {
                var client = new Client();
                // .getInt para tener el valor de cada columna
                client.setId(resultSet.getInt("id"));
                client.setName(resultSet.getString("name"));
                client.setLastname(resultSet.getString("lastname"));
                client.setMembership(resultSet.getInt("membership"));

                // agregar este cliente a la lista de clientes
                clients.add(client);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return clients;
    }

    @Override
    public boolean getClientById(Client client) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        var connection =  Conexion.getConnection();
        var sql = "SELECT * FROM client WHERE id = ?";

        try  {
            preparedStatement=connection.prepareStatement(sql);
            // id=> numero, parametro se representa con ? de la sentencia sql
            preparedStatement.setInt(1, client.getId());
            resultSet = preparedStatement.executeQuery();

            // solo deberia devolver 1 o ninguno
            if (resultSet.next()) {
                client.setName(resultSet.getString("name"));
                client.setLastname(resultSet.getString("lastname"));
                client.setName(resultSet.getString("membership"));

                return  true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }  finally {
            try{
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    @Override
    public boolean addClient(Client client) {
        PreparedStatement preparedStatement;
        Connection connection = Conexion.getConnection();
        final String sql = "INSERT INTO client(name, lastname, membership) "
                + " VALUES(?, ?, ?)";

        try {
            preparedStatement=connection.prepareStatement(sql);
            // no se empieza de 0, estos elementos completan los "?"
            preparedStatement.setString(1,client.getName());
            preparedStatement.setString(2,client.getLastname());
            preparedStatement.setInt(3,client.getMembership());
            preparedStatement.execute();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean updateClient(Client client) {
        return false;
    }

    @Override
    public boolean deleteClient(Client client) {
        return false;
    }

    static void main() {
        IClientDAO clientDAO = new ClientDAO();

        // buscar por id
        // var client = new Client(3);
        // System.out.println("***Buscar por  id***");
        // var clientFound = clientDAO.getClientById(client);
        // if(clientFound) System.out.println("Cliente encontrado:"+client);
        // else System.out.println("No se encontró cliente");

        // agregar client
        var newClient = new Client("Carol", "Real", 400);
        var isAdded = clientDAO.addClient(newClient);
        if (isAdded) {
            System.out.println("Se agregó nuevo cliente: " + newClient);
        } else {
            System.out.println("No se puedo agregar el usuario: " + newClient);
        }

        // listar clientes
         System.out.println("***Listar clientes***");
         var clients = clientDAO.getClients();
         clients.forEach(System.out::println);
    }
}
