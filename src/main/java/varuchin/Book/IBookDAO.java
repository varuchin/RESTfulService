package varuchin.Book;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;



public interface IBookDAO {


     Book getByUUID(UUID uuid);
     void remove(Book book);
     void add(Book book);
     HashSet<String> findByAuthor(String author);//перепси
     Collection<Book> getAll();

}
