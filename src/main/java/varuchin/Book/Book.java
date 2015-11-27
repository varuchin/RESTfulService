package varuchin.Book;

import java.util.UUID;

public class Book {

    private UUID uuid;
    private int id;
    private String name;
    private String author;
    private String price;
    private String stock;

    public Book() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (getId() != book.getId()) return false;
        if (!getName().equals(book.getName())) return false;
        if (getAuthor() != null ? !getAuthor().equals(book.getAuthor()) : book.getAuthor() != null) return false;
        if (getPrice() != null ? !getPrice().equals(book.getPrice()) : book.getPrice() != null) return false;
        return !(getStock() != null ? !getStock().equals(book.getStock()) : book.getStock() != null);

    }


    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getName().hashCode();
        result = 31 * result + (getAuthor() != null ? getAuthor().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        result = 31 * result + (getStock() != null ? getStock().hashCode() : 0);
        return result;

    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", price='" + price + '\'' +
                ", stock='" + stock + '\'' +
                '}';
    }

}
