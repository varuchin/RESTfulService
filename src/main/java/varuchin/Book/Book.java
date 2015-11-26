package varuchin.Book;

import java.util.UUID;


public class Book {

    private UUID uuid;
    private String name;
    private String author;
    private String price;
    private String stock;

    public Book()
    {}

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public void setStock(String stock)
    {
        this.stock = stock;
    }

    public String getStock()
    {
        return stock;
    }

    public String getPrice()
    {
        return price;
    }

    public String getName()
    {
        return name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!uuid.equals(book.uuid)) return false;
        if (!name.equals(book.name)) return false;
        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        if (price != null ? !price.equals(book.price) : book.price != null) return false;
        return !(stock != null ? !stock.equals(book.stock) : book.stock != null);

    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (stock != null ? stock.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "Library{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", price='" + price + '\'' +
                ", stock='" + stock + '\'' +
                '}';
    }

}
