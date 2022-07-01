
package db_test_operations;

import db_nucleo.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestaListagem {

	public static void main(String[] args) throws SQLException {

		ConnectionFactory connectionFactory = new ConnectionFactory();
		Connection connection = connectionFactory.recuperarConexao();

		PreparedStatement stm = connection.prepareStatement("SELECT ID, NOME, SOBRENOME, DATA_NASCIMENTO, NACIONALIDADE, TELEFONE FROM HOSPEDES");
		stm.execute();
		ResultSet rst = stm.getResultSet();
		while(rst.next()) {
			Integer id = rst.getInt("ID");
			String nome = rst.getString("NOME");
			String sobrenome= rst.getString("SOBRENOME");
                                                            String data_nascimento = rst.getString("DATA_NASCIMENTO");
                                                            String nacionalidade = rst.getString("NACIONALIDADE");
                                                            String telefone = rst.getString("TELEFONE");
			System.out.println(id);
			System.out.println(nome);
			System.out.println(sobrenome);
                                                            System.out.println(data_nascimento);
                                                            System.out.println(nacionalidade);
                                                            System.out.println(telefone);
		}
		rst.close();
		stm.close();
		connection.close();
	}
}


