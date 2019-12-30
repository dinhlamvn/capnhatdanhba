package ddsdb.vn.lcd.ui.main;

public interface MainViewListener {

  enum CurrentPage {
    CHANGE_HEAD_NUMBER_PAGE,
    REMOVE_DUPLICATE_PAGE
  }

  void executeUpdateContactList();

  void executeRemoveContactList();
}
