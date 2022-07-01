
package db_test_operations;

import db_nucleo.ConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;


public class TestaInsercao {

	public static void main(String[] args) throws SQLException {
                                        String nome = "Pípato";
		String sobrenome = "Montenegro";
                                        String data_nascimento = "7 de junho de 1970";
                                        String nacionalidade = "Angolan – angolano";
                                        String telefone = "1278-9090";
                                        
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.recuperarConexao();

		PreparedStatement stm = connection.prepareStatement("INSERT INTO HOSPEDES (nome, sobrenome, data_nascimento, nacionalidade, telefone) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		
                                        stm.setString(1, nome);
                                        stm.setString(2, sobrenome);
                                        stm.setString(3, data_nascimento);
                                        stm.setString(4, nacionalidade);
                                        stm.setString(5, telefone);
                                        
                                        stm.execute();
                

		ResultSet rst = stm.getGeneratedKeys();
		while(rst.next()) {
			Integer id = rst.getInt(1);
			System.out.println("O id criado foi: " + id);
		}
		rst.close();
		stm.close();
		connection.close();
	}
}

