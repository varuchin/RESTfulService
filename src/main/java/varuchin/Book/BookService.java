package varuchin.Book;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
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
    @Path("/{uuid}")
    public Book get(@PathParam("uuid") UUID uuid) {
        return dao.getByUUID(uuid);
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Book book) {
        dao.add(book);
        URI location = URI.create("/books" + book.getUuid().toString());
        return Response.created(location).build();
    }

    @PUT
    @Path("/{uuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("uuid") UUID uuid, Book book) {
        Book originBook = dao.getByUUID(uuid);
        boolean isNewBook = originBook == null;

        if (isNewBook) {
            originBook = new Book();
            originBook.setUuid(uuid);
        }

        originBook.setName(book.getName());
        originBook.setAuthor(book.getAuthor());
        originBook.setPrice(book.getPrice());
        originBook.setStock(book.getStock());

        dao.add(originBook);

        StringBuilder builder = new StringBuilder();
        String path = "/books/";
        String bookUUID = book.getUuid().toString();
        builder.append(path);
        builder.append(bookUUID);
        String loc = builder.toString();

        URI location = URI.create(loc);

        if (isNewBook)
            return Response.created(location).build();
        else
            return Response.noContent().location(location).build();

    }

    @DELETE
    @Path("/{uuid}")
    public Response remove(@PathParam("uuid") UUID uuid) {
        Book originBook = dao.getByUUID(uuid);
        if (originBook.equals(null))
            return Response.status(Response.Status.NOT_FOUND).build();
        dao.remove(originBook);
        return Response.noContent().build();
    }

   // @GET
    public HashSet<String> findByAuthor(@QueryParam("AUTHOR") String author) {
        if (!dao.findByAuthor(author).equals(author))
            return null;
        return dao.findByAuthor(author);
    }

   // @GET
    public Collection<Book> getAll() {
        return dao.getAll();
    }


}
