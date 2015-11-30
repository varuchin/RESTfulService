package varuchin.Book;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;



public interface IBookDAO {


     String getByID(Integer id) throws SQLException;
     void remove(Book book) throws SQLException;
     void add(Book book) throws SQLException;
     Collection<String> findByAuthor(String author) throws SQLException;
     Collection<String> getAll() throws SQLException;

}
