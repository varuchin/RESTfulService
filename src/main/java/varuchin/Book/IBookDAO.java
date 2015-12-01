package varuchin.Book;

import java.sql.SQLException;
import java.util.Collection;
import java.util.UUID;


public interface IBookDAO {

//get с параметром (фильтр по имени (содержит строку like sql))


    Book getByID(UUID id) throws SQLException;

    void remove(Book book) throws SQLException;

    void add(Book book) throws SQLException;

    Book updateBook(Book book) throws SQLException;

    Collection<Book> getAll() throws SQLException;

    Collection<Book> getAllAuthors() throws SQLException;

    Collection<Book> getAllNames() throws SQLException;

    Collection<Book> getAllStocks() throws SQLException;

    Collection<Book> getAllPrices() throws SQLException;

}
