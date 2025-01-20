import java.util.*;

public class LibrarySystem {

    static class Book {
        String id;
        String title;
        String author;
        boolean available;

        Book(String id, String title, String author) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.available = true;
        }

        void borrowBook() throws Exception {
            if (!available) {
                throw new Exception("Book is already borrowed.");
            }
            available = false;
        }

        void returnBook() throws Exception {
            if (available) {
                throw new Exception("Book was not borrowed.");
            }
            available = true;
        }

        @Override
        public String toString() {
            return id + ": " + title + " by " + author + " | " + (available ? "Available" : "Not Available");
        }
    }

    static class User {
        String username;
        Set<Book> borrowedBooks;

        User(String username) {
            this.username = username;
            this.borrowedBooks = new HashSet<>();
        }

        void borrow(Book book) throws Exception {
            book.borrowBook();
            borrowedBooks.add(book);
        }

        void returnBook(Book book) throws Exception {
            if (!borrowedBooks.contains(book)) {
                throw new Exception("You haven't borrowed this book.");
            }
            book.returnBook();
            borrowedBooks.remove(book);
        }

        @Override
        public String toString() {
            return "User: " + username + " | Borrowed Books: " + borrowedBooks.size();
        }
    }

    static class Library {
        private Map<String, Book> bookCatalog;
        private Map<String, User> users;

        Library() {
            bookCatalog = new HashMap<>();
            users = new HashMap<>();
        }

        void addBook(String id, String title, String author) {
            Book newBook = new Book(id, title, author);
            bookCatalog.put(id, newBook);
            System.out.println("Book added: " + newBook);
        }

        void registerUser(String username) {
            if (users.containsKey(username)) {
                System.out.println("User already exists.");
                return;
            }
            User newUser = new User(username);
            users.put(username, newUser);
            System.out.println("User registered: " + newUser);
        }

        void borrowBook(String username, String bookId) {
            User user = users.get(username);
            Book book = bookCatalog.get(bookId);

            if (user == null) {
                System.out.println("User not found.");
                return;
            }
            if (book == null) {
                System.out.println("Book not found.");
                return;
            }

            try {
                user.borrow(book);
                System.out.println(username + " borrowed: " + book.title);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        void returnBook(String username, String bookId) {
            User user = users.get(username);
            Book book = bookCatalog.get(bookId);

            if (user == null) {
                System.out.println("User not found.");
                return;
            }
            if (book == null) {
                System.out.println("Book not found.");
                return;
            }

            try {
                user.returnBook(book);
                System.out.println(username + " returned: " + book.title);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        void displayBooks() {
            System.out.println("\nLibrary Books:");
            for (Book book : bookCatalog.values()) {
                System.out.println(book);
            }
        }

        void displayUsers() {
            System.out.println("\nRegistered Users:");
            for (User user : users.values()) {
                System.out.println(user);
            }
        }
    }

    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary System:");
            System.out.println("1. Add Book");
            System.out.println("2. Register User");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Display Books");
            System.out.println("6. Display Users");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Book ID: ");
                    String bookId = scanner.nextLine();
                    System.out.print("Enter Book Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Author Name: ");
                    String author = scanner.nextLine();
                    library.addBook(bookId, title, author);
                    break;
                case 2:
                    System.out.print("Enter Username: ");
                    String username = scanner.nextLine();
                    library.registerUser(username);
                    break;
                case 3:
                    System.out.print("Enter Username: ");
                    String borrowUser = scanner.nextLine();
                    System.out.print("Enter Book ID to Borrow: ");
                    String borrowBookId = scanner.nextLine();
                    library.borrowBook(borrowUser, borrowBookId);
                    break;
                case 4:
                    System.out.print("Enter Username: ");
                    String returnUser = scanner.nextLine();
                    System.out.print("Enter Book ID to Return: ");
                    String returnBookId = scanner.nextLine();
                    library.returnBook(returnUser, returnBookId);
                    break;
                case 5:
                    library.displayBooks();
                    break;
                case 6:
                    library.displayUsers();
                    break;
                case 7:
                    System.out.println("Exiting the system...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }
}
