package android.vn.lcd.ui.listcontact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.vn.lcd.data.ContactUpdateInfo
import android.vn.lcd.extensions.highLightNewPhoneNumber
import android.vn.lcd.extensions.highLightOldPhoneNumber
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adomino.ddsdb.R

class ListContactAdapter
    : ListAdapter<ContactUpdateInfo, ListContactAdapter.ContactItemViewHolder>(ContactDiffUtil()) {

    fun setDataList(contactList: List<ContactUpdateInfo>) {
        submitList(contactList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactItemViewHolder {
        return ContactItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ContactItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ContactItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val tvDisplayName: TextView by lazy {
            itemView.findViewById<TextView>(R.id.tvDisplayName)
        }

        private val tvPhoneNumber: TextView by lazy {
            itemView.findViewById<TextView>(R.id.tvPhoneNumber)
        }

        private val tvNewPhoneNumber: TextView by lazy {
            itemView.findViewById<TextView>(R.id.tvNewPhoneNumber)
        }

        companion object {

            fun from(parent: ViewGroup): ContactItemViewHolder {
                return ContactItemViewHolder(
                        LayoutInflater.from(parent.context).inflate(
                                R.layout.contact_item_view,
                                parent,
                                false)
                )
            }
        }

        fun bind(contactUpdateInfo: ContactUpdateInfo) {
            tvDisplayName.text = contactUpdateInfo.contactInfo.displayName
            tvPhoneNumber.text = contactUpdateInfo.contactInfo.phoneNumber.highLightOldPhoneNumber()
            tvNewPhoneNumber.text = contactUpdateInfo.newPhoneNumber.highLightNewPhoneNumber()
        }
    }

    class ContactDiffUtil: DiffUtil.ItemCallback<ContactUpdateInfo>() {

        override fun areItemsTheSame(oldItem: ContactUpdateInfo, newItem: ContactUpdateInfo): Boolean {
            return oldItem.contactInfo.id == newItem.contactInfo.id
        }

        override fun areContentsTheSame(oldItem: ContactUpdateInfo, newItem: ContactUpdateInfo): Boolean {
            return oldItem == newItem
        }
    }
}