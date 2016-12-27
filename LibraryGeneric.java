package lab04;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.Comparator;

/**
 * Class representation of a library (a collection of library books).
 *
 */
public class LibraryGeneric<Type> {

    private ArrayList<LibraryBookGeneric<Type>> library;

    public LibraryGeneric() {
        library = new ArrayList<LibraryBookGeneric<Type>>();
    }

    /**
     * Add the specified book to the library, assume no duplicates.
     *
     * @param isbn --
     *          ISBN of the book to be added
     * @param author --
     *          author of the book to be added
     * @param title --
     *          title of the book to be added
     */
    public void add(long isbn, String author, String title) {
        library.add(new LibraryBook(isbn, author, title));
    }


    /**
     * Add the list of library books to the library, assume no duplicates.
     *
     * @param list --
     *          list of library books to be added
     */
    public void addAll(ArrayList<LibraryBook> list) {
        library.addAll(list);
    }

    /**
     * Add books specified by the input file. One book per line with ISBN, author,
     * and title separated by tabs.
     *
     * If file does not exist or format is violated, do nothing.
     *
     * @param filename
     */
    public void addAll(String filename) {
        ArrayList<LibraryBook> toBeAdded = new ArrayList<LibraryBook>();

        try {
            Scanner fileIn = new Scanner(new File(filename));
            int lineNum = 1;

            while (fileIn.hasNextLine()) {
                String line = fileIn.nextLine();

                Scanner lineIn = new Scanner(line);
                lineIn.useDelimiter("\\t");

                if (!lineIn.hasNextLong())
                    throw new ParseException("ISBN", lineNum);
                long isbn = lineIn.nextLong();

                if (!lineIn.hasNext())
                    throw new ParseException("Author", lineNum);
                String author = lineIn.next();

                if (!lineIn.hasNext())
                    throw new ParseException("Title", lineNum);
                String title = lineIn.next();

                toBeAdded.add(new LibraryBook(isbn, author, title));

                lineNum++;
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage() + " Nothing added to the library.");
            return;
        } catch (ParseException e) {
            System.err.println(e.getLocalizedMessage()
                    + " formatted incorrectly at line " + e.getErrorOffset()
                    + ". Nothing added to the library.");
            return;
        }

        library.addAll(toBeAdded);
    }

    /**
     * Returns the holder of the library book with the specified ISBN.
     * If no book with the specified ISBN is in the library, returns null.
     *
     * @param isbn --
     *          ISBN of the book to be looked up
     */
    public String lookup(long isbn) {
        // *FILL IN -- do not return null unless appropriate
        for (int i = 0; i < this.library.size(); i++){
            if (this.library.get(i).getIsbn() == isbn){
                return this.library.get(i).getHolder();
            }
        }
        return null;
    }




    /**
     * Sets the holder and due date of the library book with the specified ISBN.
     * If no book with the specified ISBN is in the library, returns false.
     * If the book with the specified ISBN is already checked out, returns false.
     * Otherwise, returns true.
     *
     * @param isbn --
     *          ISBN of the library book to be checked out
     * @param holder --
     *          new holder of the library book
     * @param month --
     *          month of the new due date of the library book
     * @param day --
     *          day of the new due date of the library book
     * @param year --
     *          year of the new due date of the library book
     */
    public boolean checkout(long isbn, String holder, int month, int day, int year) {
        // *FILL IN -- do not return false unless appropriate
        for (int i = 0; i < this.library.size(); i++)
            if (this.library.get(i).getIsbn() == isbn)
            {
                if (this.library.get(i).getHolder() != null)
                {
                    //returns false if there is already a holder associated with the book
                    return false;
                }
                else
                {
                    //this sets a holder and assigns a date for return in the library's database(array)
                    this.library.get(i).setHolder(holder);
                    this.library.get(i).setDueDate(new GregorianCalendar(year,month,day));

                }
                return true;

            }

        //returns false if fails the first condition
        return false;
    }

    // *** you will implement the rest of the methods for your assignment
    // *** don't touch them before finishing the lab portion

    /**
     * Returns the list of library books checked out to the specified holder.
     *
     * If the specified holder has no books checked out, returns an empty list.
     *
     * @param holder --
     *          holder whose checked out books are returned
     */
    public ArrayList<LibraryBook> lookup(String holder) {
        // FILL IN -- do not return null
        ArrayList<LibraryBook> holderList = new ArrayList<LibraryBook>();
        //adds the selected books to the specific holder in the library's database(array)
        for (int i = 0; i<this.library.size(); i++)
            if (holder.equals(this.library.get(i).getHolder())){
                holderList.add(this.library.get(i));
            }

        return holderList;
    }

    /**
     * Unsets the holder and due date of the library book.
     * If no book with the specified ISBN is in the library, returns false.
     * If the book with the specified ISBN is already checked in, returns false.
     * Otherwise, returns true.
     *
     * @param isbn --
     *          ISBN of the library book to be checked in
     */
    public boolean checkin(long isbn) {
        // FILL IN -- do not return false unless appropriate
        for (int i = 0; i <this.library.size(); i++)
            //if the book can be placed here, this unsets all info associated with the isbn number to null
            if (this.library.get(i).getIsbn() == isbn)
                if(this.library.get(i).getHolder() !=(null)){
                    this.library.get(i).setDueDate(null);
                    this.library.get(i).setHolder(null);
                    //this ensures that the boolean will be set to true, indicating the book is present
                    return true;

                }
                else {
                    //this indicates that the book does not fit in and will not be present, setting the bool to false
                    return false;
                }
        return false;
    }


    /**
     * Unsets the holder and due date for all library books checked out by the
     * specified holder.
     * If no books with the specified holder are in the library, returns false;
     * Otherwise, returns true.
     *
     * @param holder --
     *          holder of the library books to be checked in
     */
    public boolean checkin(Type holder) {
        // *FILL IN -- do not return false unless appropriate
        boolean bookIn = false;
        //this unsets the data that is added under the holder
        for (int i = 0; i < this.library.size(); i++)
            if (holder.equals(this.library.get(a).getHolder()))
            {
                this.library.get(i).setHolder(null);
                this.library.get(i).setDueDate(null);
                bookIn = true;
            }

        return bookIn;

        // FILL IN -- do not return false unless appropriate
        //this returns false only if the conditions are not met and there is no book to be returned
        return false;
    }

    /**
     * Returns the list of library books, sorted by ISBN (smallest ISBN first).
     */
    public ArrayList<LibraryBookGeneric<Type>> getInventoryList(){
        ArrayList<LibraryBookGeneric<Type>> libraryCopy = new ArrayList<LibraryBookGeneric<Type>>();
        libraryCopy.addAll(library);


        OrderByIsbn comparator = new OrderByIsbn();
        sort(libraryCopy, comparator);
        return libraryCopy;
    }

    /**
     * Returns the list of library books, sorted by author
     */
    public ArrayList<LibraryBookGeneric<Type>> getOrderedByAuthor(){
        ArrayList<LibraryBookGeneric<Type>> libraryCopy = new ArrayList<LibraryBookGeneric<Type>>();
        OrderByAuthor comparator = new OrderByAuthor();
        sort(libraryCopy, comparator);

        return libraryCopy;
    }
    /**
     * Returns the list of library books whose due date is older than the input
     * date. The list is sorted by date (oldest first).
     *
     * If no library books are overdue, returns an empty list.
     */
    public ArrayList<LibraryBookGeneric<Type>> getOverdueList (int month, int day, int year) {
        // FILL IN -- do not return null
        ArrayList<LibraryBookGeneric<Type>> libraryCopy = new ArrayList<LibraryBookGeneric<Type>>();

        GregorianCalendar currentDate = new GregorianCalendar (year, day, month);
        for (int i = 0; i<library.size(); i++){
            if(library.get(i).getDueDate() !=null
                    && library.get(i).getDueDate().before(currentDate)){
                libraryCopy.get(i);
            }
        }
        OrderByDueDate comparator = new OrderByDueDate();
        sort(libraryCopy, comparator);
        return libraryCopy;
    }
    /**
     * Performs a SELECTION SORT on the input ArrayList.
     *    1. Find the smallest item in the list.
     *    2. Swap the smallest item with the first item in the list.
     *    3. Now let the list be the remaining unsorted portion
     *       (second item to Nth item) and repeat steps 1, 2, and 3.
     */
    private static <ListType> void sort(ArrayList<ListType> list, Comparator<ListType> c){
        for (int i = 0; i < list.size() - 1; i++)
        {
            int j, minIndex;
            for (j = i + 1, minIndex = i; j < list.size(); j++)
                if (c.compare(list.get(j), list.get(minIndex)) < 0)
                    minIndex = j;
            ListType temp = list.get(i);
            list.set(i, list.get(minIndex));
            list.set(minIndex, temp);
        }
    }



    /**
     * Comparator that defines an ordering among library books using the ISBN.
     */
    protected class OrderByIsbn implements Comparator <LibraryBookGeneric<Type>> {
        //FILL IN - write the compare method
        @Override
        public int compare(LibraryBookGeneric<Type> compareOne, LibraryBookGeneric<Type> compareTwo) {
            return (int) (compareOne.getIsbn()-compareTwo.getIsbn());
        }
    }



    /**
     * Comparator that defines an ordering among library books using the author,  and book title as a tie-breaker.
     */
    protected class OrderByAuthor implements Comparator<LibraryBookGeneric<Type>> {
        // FILL IN - write the compare method
        @Override
        public int compare(LibraryBookGeneric<Type> compareOne, LibraryBookGeneric<Type> compareTwo) {
            if ((int) compareOne.getAuthor().compareTo(compareTwo.getAuthor()) == 0) {
                return (int) compareTwo.getTitle().compareTo(compareOne.getTitle());
            }

            return (int) compareOne.getAuthor().compareTo(compareTwo.getAuthor());
        }
    }



        /**
         * Comparator that defines an ordering among library books using the due date.
         */
        protected class OrderByDueDate implements Comparator<LibraryBookGeneric<Type>>{
            //TODO Auto-generated method stub
            @Override
            public int compare LibraryBookGeneric<Type> compareOne, LibraryBookGeneric<Type> compareTwo){
                int result = compareOne.getAuthor().compareTo(compareTwo.getAuthor());
                if (result == 0){
                    return (int) (compareOne.getDueDate().compareTo(compareTwo.getDueDate()));

                }
                return result;
            }
        }
