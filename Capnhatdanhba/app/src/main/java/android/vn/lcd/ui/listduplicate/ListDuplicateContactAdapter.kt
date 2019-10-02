package android.vn.lcd.ui.listduplicate

import android.lcd.vn.capnhatdanhba.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.vn.lcd.data.ContactInfo
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ListDuplicateContactAdapter
    : ListAdapter<ContactInfo, ListDuplicateContactAdapter.ContactItemViewHolder>(ContactDiffUtil()) {

    fun setDataList(contactList: List<ContactInfo>) {
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

        companion object {

            fun from(parent: ViewGroup): ContactItemViewHolder {
                return ContactItemViewHolder(
                        LayoutInflater.from(parent.context).inflate(
                                R.layout.contact_item_duplicate_view,
                                parent,
                                false)
                )
            }
        }

        fun bind(contactInfo: ContactInfo) {
            tvDisplayName.text = contactInfo.displayName
            tvPhoneNumber.text = contactInfo.phoneNumber
        }
    }

    class ContactDiffUtil: DiffUtil.ItemCallback<ContactInfo>() {

        override fun areItemsTheSame(oldItem: ContactInfo, newItem: ContactInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ContactInfo, newItem: ContactInfo): Boolean {
            return oldItem == newItem
        }
    }
}