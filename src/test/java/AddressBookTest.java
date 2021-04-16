import java.sql.SQLException;
import java.time.LocalDate;
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
}

