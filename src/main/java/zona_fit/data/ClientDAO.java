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
        return false;
    }

    @Override
    public boolean addClient(Client client) {
        return false;
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
        //listarclientes
        System.out.println("***Listar clientes***");
        IClientDAO clientDAO = new ClientDAO();

        var clients = clientDAO.getClients();
        clients.forEach(System.out::println);
    }
}
