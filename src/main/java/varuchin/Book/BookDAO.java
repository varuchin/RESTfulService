package varuchin.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


public class BookDAO implements IBookDAO {

    //private static Map<UUID, Book> books = new HashMap<>();
    private static final String user = "system";//Логин пользователя
    private static final String password = "oblivion";//Пароль пользователя
    private static final String url = "jdbc:oracle:thin:@n103934.merann.ru:1521:XE";//URL адрес
    private static Connection c = null;

    public BookDAO() {
    }


    private static void insertValues() throws SQLException {
        PreparedStatement st;
        String insertion = "INSERT INTO LIBRARY VALUES (? , ? , ? , ? , ?)";
        st = c.prepareStatement(insertion);

        st.setString(1, UUID.randomUUID().toString());
        st.setString(2, "Head First Java");
        st.setString(3, "Kathy Sierra");
        st.setInt(4, 1000);
        st.setString(5, "Moscow");

        st.executeUpdate();

        st = c.prepareStatement(insertion);
        st.setString(1, UUID.randomUUID().toString());
        st.setString(2, "Thinking in Java");
        st.setString(3, "Bruce Eckel");
        st.setInt(4, 1500);
        st.setString(5, "Moscow");

        st.executeUpdate();

        st = c.prepareStatement(insertion);
        st.setString(1, UUID.randomUUID().toString());
        st.setString(2, "A Programmer's guide to Java");
        st.setString(3, "Khalid Azi, Mughal");
        st.setInt(4, 800);
        st.setString(5, "Nizhny Novgorod");

        st.executeUpdate();


        st = c.prepareStatement(insertion);
        st.setString(1, UUID.randomUUID().toString());
        st.setString(2, "The pragmatic Programmer");
        st.setString(3, "Andrew Hunt");
        st.setInt(4, 1000);
        st.setString(5, "Saint Petersburg");

        st.executeUpdate();

        st = c.prepareStatement(insertion);
        st.setString(1, UUID.randomUUID().toString());
        st.setString(2, "The elements of Java style");
        st.setString(3, "Scott Amber");
        st.setInt(4, 1300);
        st.setString(5, "Moscow");

        st.executeUpdate();

        st = c.prepareStatement(insertion);
        st.setString(1, UUID.randomUUID().toString());
        st.setString(2, "Effective Java");
        st.setString(3, "Joshua Bloch");
        st.setInt(4, 1700);
        st.setString(5, "Nizhny Novgorod");

        st.executeUpdate();

        st = c.prepareStatement(insertion);
        st.setString(1, UUID.randomUUID().toString());
        st.setString(2, "Bitter Java");
        st.setString(3, "Bruce Tate");
        st.setInt(4, 700);
        st.setString(5, "Saint Petersburg");

        st.executeUpdate();

        st = c.prepareStatement(insertion);
        st.setString(1, UUID.randomUUID().toString());
        st.setString(2, "Head first Design Patterns");
        st.setString(3, "Eric Freeman");
        st.setInt(4, 1000);
        st.setString(5, "Moscow");

        st.executeUpdate();

        st = c.prepareStatement(insertion);
        st.setString(1, UUID.randomUUID().toString());
        st.setString(2, "The Java language specification");
        st.setString(3, "Sun");
        st.setInt(4, 500);
        st.setString(5, "Saint Petersburg");

        st.executeUpdate();

        st = c.prepareStatement(insertion);
        st.setString(1, UUID.randomUUID().toString());
        st.setString(2, "Clean code");
        st.setString(3, "Robert C. Martin");
        st.setInt(4, 1200);
        st.setString(5, "Moscow");

        st.executeUpdate();

        c.commit();

    }

    public static void createTable() throws SQLException {
        c = DriverManager.getConnection(url, user, password);
        PreparedStatement st;

        String tableQuery = "CREATE TABLE LIBRARY (ID VARCHAR(100) NOT NULL, " +
                "NAME VARCHAR(50) NOT NULL, " +
                "AUTHOR VARCHAR(30), " +
                "PRICE NUMBER(30,2), " +
                "STOCK VARCHAR(30))";

        st = c.prepareStatement(tableQuery);
        st.executeUpdate();
        insertValues();

        /**
         st = c.prepareStatement("CREATE TABLE LIBRARY (? VARCHAR(10) NOT NULL, " +
         "? VARCHAR(50) NOT NULL, " +
         "? VARCHAR(30)," +
         "? NUMBER(30,2)," +
         "? VARCHAR(30))");
         /**
         st.setString(1, "ID");
         st.setString(2, "NAME");
         st.setString(3, "AUTHOR");
         st.setString(4, "PRICE");
         st.setString(5, "STOCK");
         */
        //st.executeUpdate();

        c.commit();
        c.close();
    }


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
    public Book getByID(UUID id) throws SQLException {
        PreparedStatement st;
        //StringBuilder book = new StringBuilder();
        // String result;
        Book result = new Book();
        connect();
        try {
            st = c.prepareStatement("SELECT * FROM LIBRARY WHERE ID = ?");

            st.setString(1, id.toString());

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                result.setId(UUID.fromString(rs.getString(1)));
                result.setName(rs.getString(2));
                result.setAuthor(rs.getString(3));
                result.setPrice(rs.getString(4));
                result.setStock(rs.getString(5));
            }


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
            st.setString(1, book.getId().toString());
            st.executeUpdate();
            c.commit();

        } catch (SQLException e) {
        } finally {
            c.close();
        }
    }

    @Override
    public Book updateBook(Book book) throws SQLException {

        connect();

        PreparedStatement name =
                c.prepareStatement("UPDATE LIBRARY SET NAME = ? WHERE ID = ?");
        PreparedStatement author =
                c.prepareStatement("UPDATE LIBRARY SET AUTHOR = ? WHERE ID = ?");
        PreparedStatement price =
                c.prepareStatement("UPDATE LIBRARY SET PRICE = ? WHERE ID = ?");
        PreparedStatement stock =
                c.prepareStatement("UPDATE LIBRARY SET STOCK = ? WHERE ID = ?");


        if (book != null) {

            if (book.getName() != null) {
                name.setString(1, book.getName());
                name.setString(2, book.getId().toString());
            }
            if (book.getAuthor() != null) {
                author.setString(1, book.getAuthor());
                author.setString(2, book.toString());
            }
            if (book.getPrice() != null) {
                price.setString(1, book.getPrice());
                price.setString(2, book.getId().toString());
            }
            if (book.getStock() != null) {
                stock.setString(1, book.getStock());
                stock.setString(2, book.getId().toString());
            }
            c.commit();
            c.close();
            return book;
        } else {
            c.close();
            return null;
        }
    }


    @Override
    public void add(Book book) throws SQLException {
        PreparedStatement st;
        connect();
        book.setId(UUID.randomUUID());
        try {
            st = c.prepareStatement("INSERT INTO LIBRARY VALUES ? , ? , ? , ?");

            st.setString(1, book.getId().toString());
            st.setString(2, book.getName());
            st.setString(3, book.getAuthor());
            st.setString(4, book.getPrice());
            st.setString(5, book.getStock());

            st.executeUpdate();
            c.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            c.close();
        }
    }


    //переделать под дао с использованием бд
    @Override
    public Collection<Book> getAll() throws SQLException {
        PreparedStatement st;
        Collection<Book> result = new ArrayList<>();

        connect();
        try {
            st = c.prepareStatement("SELECT * FROM LIBRARY");
            st.setString(1, "saf");

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setId(UUID.fromString(rs.getString(1)));
                book.setName(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setPrice(rs.getString(4));
                book.setStock(rs.getString(5));
                result.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
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

            while (rs.next()) {
                Book book = new Book();
                book.setAuthor(rs.getString(1));
                result.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            c.close();
        }
        return result;
    }

    @Override
    public Collection<Book> getAllNames() throws SQLException {
        PreparedStatement st;
        Collection<Book> result = new ArrayList<>();
        connect();

        try {
            st = c.prepareStatement("SELECT NAME FROM LIBRARY");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setName(rs.getString(1));
                result.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            c.close();
        }
        return result;
    }

    @Override
    public Collection<Book> getAllPrices() throws SQLException {
        PreparedStatement st;
        Collection<Book> result = new ArrayList<>();
        connect();

        try {
            st = c.prepareStatement("SELECT PRICE FROM LIBRARY");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setPrice(rs.getString(1));
                result.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            c.close();
        }
        return result;
    }

    @Override
    public Collection<Book> getAllStocks() throws SQLException {
        PreparedStatement st;
        Collection<Book> result = new ArrayList<>();
        connect();

        try {
            st = c.prepareStatement("SELECT STOCK FROM LIBRARY");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setStock(rs.getString(1));
                result.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            c.close();
        }
        return result;
    }
}
