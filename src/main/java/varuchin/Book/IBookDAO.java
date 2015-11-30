package varuchin.Book;

import java.sql.SQLException;
import java.util.Collection;



public interface IBookDAO {


     Book getByID(Integer id) throws SQLException;
     void remove(Book book) throws SQLException;
     void add(Book book) throws SQLException;
     Collection<Book> findByAuthor(String author) throws SQLException;
     Collection<Book> getAll() throws SQLException;
     Collection<Book> getAllAuthors() throws SQLException;

}
