import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class AddressBookTest {
    AddressBook addressBook;
    List<AddressBookData> addressBookDataList;

    @Test
    public void givenThreeContactsInDB_WhenRetrieved_ShouldMatchEmployeeCount() throws SQLException {
        addressBook = new AddressBook();
        // addressBookDataList = addressBook.readAddressBookData(AddressBook.IOService.DB_IO);
        //Assertions.assertEquals(3, addressBookDataList.size());
        Assert.assertEquals(2, addressBook.readAddressBookData(AddressBook.IOService.DB_IO).size());
    }

    @Test
    public void givenContactDataInDB_whenUpdated_ShouldSyncWithDB() throws SQLException {
        addressBook = new AddressBook();
        addressBookDataList = addressBook.readAddressBookData(AddressBook.IOService.DB_IO);
        addressBook.updateContact("MM-ROAD", "sravani");
        boolean result = addressBook.checkAddressBookInSyncWithDB("sravani");
        Assert.assertTrue(result);
    }
    @Test
    public void givenDateRange_WhenContact_ShouldReturnEmpCount() throws SQLException{
        addressBook = new AddressBook();
        addressBookDataList = addressBook.readAddressBookData(AddressBook.IOService.DB_IO);
        LocalDate startDate = LocalDate.of(2018, 02, 01);
        LocalDate endDate = LocalDate.now();
        addressBookDataList = addressBook.readPersonDataForDateRange(startDate, endDate);
        Assert.assertEquals(2, addressBookDataList.size());
    }
    @Test
    public void givenContactDataInDB_whenCountByState_ShouldMatchWithExpectedValue() {
        addressBook = new AddressBook();
        List<AddressBookData>  addressBookDataList = addressBook.countByState("ap");
        Assert.assertEquals(2, addressBookDataList.size());
    }
    @Test
    public void givenContactDataInDB_whenCountByCity_ShouldMatchWithExpectedValue() {
        addressBook = new AddressBook();
        List<AddressBookData>  addressBookDataList = addressBook.countByCity("ongole");
        Assert.assertEquals(1, addressBookDataList.size());
    }
    @Test
    public void givenNewContactData_WhenAdded_ShouldSyncWithDB() throws SQLException {
        addressBook = new AddressBook();
        addressBookDataList = addressBook.readAddressBookData(AddressBook.IOService.DB_IO);
        addressBook.addContactToAddressBook(3,"aksha","sai","srnagar","Hyderabad","TN",5230,"958","saiaksh@in");
        boolean result = addressBook.checkAddressBookInSyncWithDB("aksha");
        System.out.println("new contact added in Database");
        Assert.assertTrue(result);
    }
    @Test
    public void given6Employees_WhenAdded_Should_ShouldMatchEmpEntries() throws SQLException {
        AddressBookData[] arrayOfContacts = {
                new AddressBookData(0, "vishnu", "vardhan", "Sai Nagar","Bangalore", "karnataka", 789654,
                        7534125, "vishnue@gm.com"),
                new AddressBookData(0, "Apurva", "sai", "Keshav Nagar","Pune", "MH", 969654,
                        972431556, "apue@gmail.com"),

        };
        AddressBook addressBook = new AddressBook();
        addressBook.readAddressBookData(AddressBook.IOService.DB_IO);
        Instant start = Instant.now();
        addressBook.addContactIntoDB(Arrays.asList(arrayOfContacts));
        Instant end = Instant.now();
        System.out.println("Duration without Thread: " + Duration.between(start, end));
        Instant threadStart = Instant.now();
        addressBook.addAddressBookDataWithThread(Arrays.asList(arrayOfContacts));
        Instant threadEnd = Instant.now();
        System.out.println("Duration with thread: " + Duration.between(threadStart, threadEnd));
        Assert.assertEquals(5, addressBook.countEntries(AddressBook.IOService.DB_IO));
    }
}

