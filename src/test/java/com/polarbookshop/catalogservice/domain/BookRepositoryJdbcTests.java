package com.polarbookshop.catalogservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.polarbookshop.catalogservice.config.DataConfig;

@DataJdbcTest
@Import(DataConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
class BookRepositoryJdbcTests {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private JdbcAggregateTemplate jdbcAggregateTemplate;

	@Test
	void findBookByIsbnWhenExisting() {
		var bookIsbn = "1234561237";
		var book = Book.of(bookIsbn, "Title", "Author", 12.90, "2023");
		jdbcAggregateTemplate.insert(book);
		Optional<Book> actualBook = bookRepository.findByIsbn(bookIsbn);

		assertThat(actualBook).isPresent();
		assertThat(actualBook.get().isbn()).isEqualTo(book.isbn());
	}
}
