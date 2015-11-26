package varuchin.Book;

import java.sql.*;
import java.util.*;



public class BookDAO implements IBookDAO {

    private static Map<UUID, Book> books = new HashMap<>();
    private String user = "system";//Логин пользователя
    private String password = "oblivion";//Пароль пользователя
    private String url = "jdbc:oracle:thin:n103934.merann.ru:1521:XE";//URL адрес
    private String driver = "oracle.jdbc.driver.OracleDriver";//Имя драйвера
    private Connection c = null;


    public void connect()
    {
        try {
            Class.forName(driver);//Регистрируем драйвер
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            c = DriverManager.getConnection(url, user, password);
            System.out.println("Connected.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeUpd(String sql)
    {
        try
        {
            c = DriverManager.getConnection(url, user, password);
            System.out.println("Connected.");
            c.createStatement().executeUpdate(sql);
            c.commit();
            c.close();

        }

        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }

    }

    @Override
    public Book getByUUID(UUID uuid)
    {
        return books.get(uuid);
    }

    @Override
    public void remove(Book book)
    {
        String sql = "DELETE FROM LIBRARY WHERE NAME = "
                + book.getName();
        executeUpd(sql);

        books.remove(book.getUuid());
    }

    @Override
    public void add(Book book)
    {
        if(book.getUuid().equals(null))
            book.setUuid(UUID.randomUUID());

        String sql = "INSERT INTO LIBRARY VALUES " + book.getName()
                + ", " + book.getAuthor()
                + ", " + book.getPrice()
                + ", " + book.getStock();

        executeUpd(sql);

        books.put(book.getUuid(), book);
    }


    @Override
    public HashSet<String> findByAuthor(String author)
    {
       HashSet<String> result = new HashSet<>();

        connect();
        try
        {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM LIBRARY WHERE AUTHOR = " + author);

            while (rs.next())
                result.add(author);
            c.commit();
            c.close();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Collection<Book> getAll()
    {
        return books.values();
    }
}
