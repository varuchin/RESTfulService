package varuchin.Book;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;


@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookService {

    private IBookDAO dao;

    public BookService() {
        dao = new BookDAO();
    }

    @GET
    @Path("/{id}")
    public String get(@PathParam("id") Integer id) throws SQLException {
        return dao.getByID(id);
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Book book) throws SQLException {
        dao.add(book);
        URI location = URI.create("/books" + book.getId().toString());
        return Response.created(location).build();
    }

    @PUT
    @Path("/{id}")
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
    @Path("/{id}")
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
    public Collection<String> findByAuthor(@QueryParam("AUTHOR") String author) throws SQLException {
        if (!dao.findByAuthor(author).equals(author))
            return null;
        return dao.findByAuthor(author);
    }

    @GET
    public Collection<String> getAll() throws SQLException {
        return dao.getAll();
    }


}
