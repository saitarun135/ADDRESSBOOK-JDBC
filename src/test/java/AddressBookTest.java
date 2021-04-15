import java.util.List;
import org.junit.Assert;
import org.junit.Test;


public class AddressBookTest {
    AddressBook addressBook;
    List<AddressBookData> addressBookDataList;

    @Test
    public void givenThreeContactsInDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        addressBook = new AddressBook();
       // addressBookDataList = addressBook.readAddressBookData(AddressBook.IOService.DB_IO);
        //Assertions.assertEquals(3, addressBookDataList.size());
        Assert.assertEquals(3,  addressBook.readAddressBookData(AddressBook.IOService.DB_IO).size());
    }
}