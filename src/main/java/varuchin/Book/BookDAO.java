package varuchin.Book;

import java.security.cert.CollectionCertStoreParameters;
import java.sql.*;
import java.util.*;


public class BookDAO implements IBookDAO {

    //private static Map<UUID, Book> books = new HashMap<>();
    private String user = "system";//Логин пользователя
    private String password = "oblivion";//Пароль пользователя
    private String url = "jdbc:oracle:thin:n103934.merann.ru:1521:XE";//URL адрес
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
    public String getByID(Integer id) throws SQLException {
        PreparedStatement st;
        StringBuilder book = new StringBuilder();
        String result;

        connect();
        try {
            st = c.prepareStatement("SELECT * FROM LIBRARY WHERE ID = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                book.append(rs.getString("ID") + " ");
                book.append(rs.getString("NAME") + " ");
                book.append(rs.getString("AUTHOR") + " ");
                book.append(rs.getString("PRICE") + " ");
                book.append(rs.getString("STOCK") + " ");
            }

        } catch (SQLException e) {
        } finally {
            c.close();
        }
        result = book.toString();
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
            String value = book.getId()
                    + ", " + book.getName()
                    + ", " + book.getAuthor()
                    + ", " + book.getPrice()
                    + ", " + book.getStock();

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
    public Collection<String> findByAuthor(String author) throws SQLException {
        int count;
        Collection<String> result = new ArrayList<>();

        connect();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM LIBRARY WHERE AUTHOR = ?");
            st.setString(1, author);
            ResultSet rs = st.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();
            count = metaData.getColumnCount();

            while (rs.next())
                for (int i = 0; i < count; i++)
                    result.add(rs.getString("NAME"));

        } catch (SQLException e) {
        } finally {
            c.close();
        }
        return result;
    }

    //переделать под дао с использованием бд
    @Override
    public Collection<String> getAll() throws SQLException {
        PreparedStatement st;
        Collection<String> result = new ArrayList<>();

        connect();
        try {
            st = c.prepareStatement("SELECT * FROM LIBRARY");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                StringBuilder builder = new StringBuilder();
                builder.append(rs.getString("ID").concat(" "));
                builder.append(rs.getString("NAME").concat(" "));
                builder.append(rs.getString("AUTHOR").concat(" "));
                builder.append(rs.getString("PRICE").concat(" "));
                builder.append(rs.getString("STOCK").concat(" "));
                String book = builder.toString();
                result.add(book);
            }

        } catch (SQLException e) {
        } finally {
            c.close();
        }

        return result;
    }
}
