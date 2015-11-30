package varuchin.Book;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;


@Path(value="/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookService {

    private IBookDAO dao;

    public BookService() {
        dao = new BookDAO();
    }

    @GET
    @Path(value="/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Book get(@PathParam(value="id") Integer id) throws SQLException {

        if (!dao.getByID(id).getId().equals(id)){
            System.err.println("Not Found.");
            return null;
        }
        System.err.println("Found.");
        return dao.getByID(id);
    }

    @POST
    @Path(value="/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Book book) throws SQLException {
        dao.add(book);
        URI location = URI.create("/books" + book.getId());
        return Response.created(location).build();
    }

    @PUT
    @Path(value="/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Integer id, Book book) throws SQLException {
        Book originBook = new Book();
        boolean isNewBook = dao.getByID(id) == null;

        if (isNewBook) {
            originBook.setId(id);
        }

        originBook.setName(book.getName());
        originBook.setAuthor(book.getAuthor());
        originBook.setPrice(book.getPrice());
        originBook.setStock(book.getStock());

        dao.add(originBook);

        StringBuilder builder = new StringBuilder();
        String path = "/books/";
        String bookID = book.getId().toString();
        builder.append(path);
        builder.append(bookID);
        String loc = builder.toString();

        URI location = URI.create(loc);

        if (isNewBook)
            return Response.created(location).build();
        else
            return Response.noContent().location(location).build();

    }
//думать
    @DELETE
    @Path(value="/{id}")
    public Response remove(@PathParam("id") int id) throws SQLException {
        Book originBook = new Book();
        originBook.setId(id);
                //dao.getByID(id);
        if (originBook.equals(null))
            return Response.status(Response.Status.NOT_FOUND).build();
        dao.remove(originBook);
        return Response.noContent().build();
    }

    @GET
    @Path(value="/authors")
    public Collection<Book> getAuthors() throws  SQLException {
        return dao.getAllAuthors();
    }

    @GET
    @Path(value="/authors/{author}")
    public Collection<Book> findByAuthor (String author) throws SQLException {
        if (!dao.findByAuthor(author).equals(author)) {
            System.err.println("Not found.");
            return null; }
        System.err.println(author + " is in the base.");
        return dao.findByAuthor(author);
    }

    @GET
    public Collection<Book> getAll() throws SQLException {
        return dao.getAll();
    }


}
