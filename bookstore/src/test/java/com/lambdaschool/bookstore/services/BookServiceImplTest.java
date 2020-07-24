package com.lambdaschool.bookstore.services;

import com.lambdaschool.bookstore.BookstoreApplication;
import com.lambdaschool.bookstore.exceptions.ResourceNotFoundException;
import com.lambdaschool.bookstore.models.Book;
import com.lambdaschool.bookstore.models.Section;
import com.lambdaschool.bookstore.models.Wrote;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookstoreApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//**********
// Note security is handled at the controller, hence we do not need to worry about security here!
//**********
public class BookServiceImplTest
{

    @Autowired
    private BookService bookService;

    @Autowired
    private SectionService sectionService;

    @Before
    public void setUp() throws
            Exception
    {
        MockitoAnnotations.initMocks(this);

        List<Book> myList = bookService.findAll();

        for (Book b : myList)
        {
            System.out.println(b.getBookid() + " " + b.getTitle());
        }
    }

    @After
    public void tearDown() throws
            Exception
    {
    }

    @Test
    public void a_findAll()
    {
        assertEquals(5, bookService.findAll().size());
    }

    @Test
    public void b_findBookById()
    {
        assertEquals("The Da Vinci Code", bookService.findBookById(28).getTitle());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void c_notFindBookById()
    {
        assertEquals("The Da Vinci Code", bookService.findBookById(22).getTitle());
    }

    @Test
    public void d_delete()
    {
        bookService.delete(26);
        assertEquals(4, bookService.findAll().size());
    }

    @Test
    public void e_save()
    {
        Section s1 = new Section("Fantasy");
        s1 = sectionService.save(s1);

        Book b1 = new Book("Neverwhere", "97807206752", 1999, s1);
        Book newBook = bookService.save(b1);

        assertNotNull(newBook);
        assertEquals("Neverwhere", newBook.getTitle());

    }

    @Test
    public void f_update()
    {

        Section s1 = new Section("Horror");
        s1 = sectionService.save(s1);

        Book b1 = new Book("IT", "97807206752", 1999, s1);
        Book updateBook = bookService.update(b1, 30);

    }

    @Test
    public void g_deleteAll()
    {
        bookService.deleteAll();
        assertEquals(0, bookService.findAll().size());

    }
}