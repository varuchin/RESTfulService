package varuchin.Book;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;
import java.util.UUID;


@Path(value = "/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookService {

    private IBookDAO dao;

    public BookService() {
        dao = new BookDAO();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Book get(@PathParam("id") UUID id) throws SQLException {

        if (!dao.getByID(id).getId().equals(id)) {
            System.err.println("Not Found.");
            return null;
        }
        System.err.println("Found.");
        return dao.getByID(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Book book) throws SQLException {
        System.err.println("Working");
        dao.add(book);
        URI location = URI.create("/books" + book.getId());
        System.out.println(location.toString());
        return Response.created(location).build();
    }


    @PUT
    @Path(value = "/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") String id, Book book) throws SQLException {
        System.err.println("Started");
        Book originBook = new Book();
        boolean isNewBook;



        if (dao.getByID(UUID.fromString(id))==null)
            isNewBook = true;

        else
            isNewBook = false;


        if (isNewBook) {
            originBook.setId(UUID.randomUUID());
        }
        else {
            originBook.setId(UUID.fromString(id));
        }

        System.err.println("OK");
        System.out.println(book);
        originBook.setName(book.getName());
        originBook.setAuthor(book.getAuthor());
        originBook.setPrice(book.getPrice());
        originBook.setStock(book.getStock());

        System.out.println(originBook);

        dao.updateBook(originBook);

        System.err.println("Added");
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
    @Path(value = "/{id}")
    public Response remove(@PathParam("id") UUID id) throws SQLException {
        Book originBook = new Book();
        originBook.setId(id);
        //dao.getByID(id);
        if (originBook.equals(null))
            return Response.status(Response.Status.NOT_FOUND).build();
        dao.remove(originBook);
        return Response.noContent().build();
    }

    @GET
    @Path("/books")
    public Collection<Book> getByString(@QueryParam("string") String string) throws SQLException{
        return dao.getByString(string);
    }

    @GET
    @Path(value = "/names")
    public Collection<Book> getNames() throws SQLException {
        return dao.getAllNames();
    }

    @GET
    @Path(value = "/authors")
    public Collection<Book> getAuthors() throws SQLException {
        return dao.getAllAuthors();
    }

    @GET
    @Path(value = "/prices")
    public Collection<Book> getPrices() throws SQLException {
        return dao.getAllPrices();
    }

    @GET
    @Path(value = "/stocks")
    public Collection<Book> getStocks() throws SQLException {
        return dao.getAllStocks();
    }


    @GET
    @Path("/all")
    public Collection<Book> getAll() throws SQLException {
        return dao.getAll();
    }
}
