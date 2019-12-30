package ddsdb.vn.lcd.helper.contact

import android.vn.lcd.data.ContactInfo
import io.reactivex.Single

interface ContactTask {

  /**
   * Load the contact list from the device.
   */
  fun load(): Single<List<ContactInfo>>

  /**
   * Insert new contact to the device, if this task success it will be return the <b>ID</b> of the
   * contact, otherwise return negative integer.
   */
  fun insert(contactInfo: ContactInfo): Single<Long>

  /**
   * Update the contact already existed in the device and return the boolean value with <b>true</b>
   * if this task execute successful and false if this task fail.
   */
  fun update(contactInfo: ContactInfo): Single<Boolean>

  /**
   * Remove the contact already existed in the device and return the info of removed contact for
   * all cases.
   */
  fun remove(contactInfo: ContactInfo): Single<ContactInfo>
}