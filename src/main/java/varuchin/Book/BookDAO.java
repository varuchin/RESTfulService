package varuchin.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;


public class BookDAO implements IBookDAO {

    //private static Map<UUID, Book> books = new HashMap<>();
    private String user = "system";//Логин пользователя
    private String password = "oblivion";//Пароль пользователя
    private String url = "jdbc:oracle:thin:@n103934.merann.ru:1521:XE";//URL адрес
    private Connection c = null;

    public BookDAO() {
    }

    ;

    public void connect() {
        try {
            c = DriverManager.getConnection(url, user, password);
            System.out.println("Connected.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //написать
    @Override
    public Book getByID(Integer id) throws SQLException {
        PreparedStatement st;
        //StringBuilder book = new StringBuilder();
        // String result;
        Book result = new Book();
        connect();
        try {
            st = c.prepareStatement("SELECT * FROM LIBRARY WHERE ID = ?");

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                result.setId(rs.getInt(1));
                result.setName(rs.getString(2));
                result.setAuthor(rs.getString(3));
                result.setPrice(rs.getString(4));
                result.setStock(rs.getString(5));
            }

            /**
             book.append(rs.getString("ID") + " ");
             book.append(rs.getString("NAME") + " ");
             book.append(rs.getString("AUTHOR") + " ");
             book.append(rs.getString("PRICE") + " ");
             book.append(rs.getString("STOCK") + " ");
             */


        } catch (SQLException e) {
        } finally {
            c.close();
        }

        return result;
    }


    @Override
    public void remove(Book book) throws SQLException {
        connect();
        PreparedStatement st;
        try {
            st = c.prepareStatement("DELETE FROM LIBRARY WHERE ID = ?");
            st.setInt(1, book.getId());
            st.executeUpdate();
            c.commit();

        } catch (SQLException e) {
        } finally {
            c.close();
        }
    }

    @Override
    public void add(Book book) throws SQLException {
        PreparedStatement st;
        connect();
        try {
            st = c.prepareStatement("INSERT INTO LIBRARY VALUES ?");

            StringBuilder builder = new StringBuilder();
            builder.append(book.getId());
            builder.append(", " + book.getName());
            builder.append(", " + book.getAuthor());
            builder.append(", " + book.getPrice());
            builder.append(", " + book.getStock());

            String value = builder.toString();

            st.setString(1, value);
            st.executeUpdate();
            c.commit();

        } catch (SQLException e) {
        } finally {
            c.close();
        }
    }

    //плохо
    //вернуть collection
    @Override
    public Collection<Book> findByAuthor(String author) throws SQLException {
        Collection<Book> result = new ArrayList<>();
        connect();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM LIBRARY WHERE AUTHOR = ?");
            st.setString(1, author);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt(1));
                book.setName(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setPrice(rs.getString(4));
                book.setStock(rs.getString(5));
                result.add(book);
            }

        } catch (SQLException e) {
        } finally {
            c.close();
        }
        return result;
    }

    //переделать под дао с использованием бд
    @Override
    public Collection<Book> getAll() throws SQLException {
        PreparedStatement st;
        Collection<Book> result = new ArrayList<>();

        connect();
        try {
            st = c.prepareStatement("SELECT * FROM LIBRARY");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt(1));
                book.setName(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setPrice(rs.getString(4));
                book.setStock(rs.getString(5));
                result.add(book);
            }

        } catch (SQLException e) {
        } finally {
            c.close();
        }
        return result;
    }

    @Override
    public Collection<Book> getAllAuthors() throws SQLException {
        PreparedStatement st;
        Collection<Book> result = new ArrayList<>();
        connect();

        try {
            st = c.prepareStatement("SELECT AUTHOR FROM LIBRARY");
            ResultSet rs = st.executeQuery();

            while (rs.next())
            {
                Book book = new Book();
                book.setAuthor(rs.getString(1));
                result.add(book);
            }
        }
        catch (SQLException e){}
        finally {
            c.close();
        }
        return result;
    }
}
