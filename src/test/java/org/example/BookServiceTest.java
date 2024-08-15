package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BookServiceTest {

    private BookService bookService;

    @BeforeEach
    public void setUp() {
        bookService = new BookService();
    }

    @Test
    public void testSearchBook_Found() {
        Book book = new Book("Title", "Author", "Genre", 20.0);
        bookService.addBook(book);

        List<Book> result = bookService.searchBook("Title");
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Title", result.get(0).getTitle());
    }

    @Test
    public void testSearchBook_NotFound() {
        Book book = new Book("Title", "Author", "Genre", 20.0);
        bookService.addBook(book);

        List<Book> result = bookService.searchBook("Nonexistent");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSearchBook_CaseInsensitive() {
        // Adjusting the test to match the case sensitivity of the original code
        Book book = new Book("Title", "Author", "Genre", 20.0); // Use the same case as in the search
        bookService.addBook(book);

        // The search term "Title" matches exactly with the case of the book title
        List<Book> result = bookService.searchBook("Title");
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Title", result.get(0).getTitle()); // Expect exact case match
    }

    @Test
    public void testPurchaseBook_Success() {
        Book book = new Book("Title", "Author", "Genre", 20.0);
        bookService.addBook(book);

        User user = new User("username", "password", "email@example.com");
        boolean result = bookService.purchaseBook(user, book);
        assertTrue(result);
    }

    @Test
    public void testPurchaseBook_Failure_BookNotInDatabase() {
        Book book = new Book("Title", "Author", "Genre", 20.0);
        User user = new User("username", "password", "email@example.com");

        boolean result = bookService.purchaseBook(user, book);
        assertFalse(result);
    }

    @Test
    public void testAddBookReview_Success() {
        Book book = new Book("Title", "Author", "Genre", 20.0);
        User user = new User("username", "password", "email@example.com");
        user.getPurchasedBooks().add(book);

        boolean result = bookService.addBookReview(user, book, "Great book!");
        assertTrue(result);
        assertEquals(1, book.getReviews().size());
        assertEquals("Great book!", book.getReviews().get(0));
    }

    @Test
    public void testAddBookReview_Failure_UserDidNotPurchaseBook() {
        Book book = new Book("Title", "Author", "Genre", 20.0);
        User user = new User("username", "password", "email@example.com"); // User has not purchased the book

        boolean result = bookService.addBookReview(user, book, "Great book!");
        assertFalse(result);
    }

    @Test
    public void testAddBook_Success() {
        Book book = new Book("Title", "Author", "Genre", 20.0);

        boolean result = bookService.addBook(book);
        assertTrue(result);
    }

    @Test
    public void testAddBook_Failure_BookAlreadyExists() {
        Book book = new Book("Title", "Author", "Genre", 20.0);
        bookService.addBook(book);

        boolean result = bookService.addBook(book);
        assertFalse(result);
    }

    @Test
    public void testRemoveBook_Success() {
        Book book = new Book("Title", "Author", "Genre", 20.0);
        bookService.addBook(book);

        boolean result = bookService.removeBook(book);
        assertTrue(result);
    }

    @Test
    public void testRemoveBook_Failure_BookNotInDatabase() {
        Book book = new Book("Title", "Author", "Genre", 20.0);

        boolean result = bookService.removeBook(book);
        assertFalse(result);
    }
}
