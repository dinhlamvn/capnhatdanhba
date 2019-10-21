package android.vn.lcd.ui.listduplicate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.vn.lcd.data.ContactInfo
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adomino.ddsdb.R

class ListDuplicateContactAdapter(private val onContactChecked: OnContactChecked)
    : ListAdapter<ContactInfo, ListDuplicateContactAdapter.ContactItemViewHolder>(ContactDiffUtil()) {

    fun setDataList(contactList: List<ContactInfo>) {
        submitList(contactList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactItemViewHolder {
        return ContactItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ContactItemViewHolder, position: Int) {
        holder.bind(getItem(position), onContactChecked)
    }

    class ContactItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val tvDisplayName: TextView by lazy {
            itemView.findViewById<TextView>(R.id.tvDisplayName)
        }

        private val tvPhoneNumber: TextView by lazy {
            itemView.findViewById<TextView>(R.id.tvPhoneNumber)
        }

        private val cbChecked: CheckBox by lazy {
            itemView.findViewById<CheckBox>(R.id.cbKeep)
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

        fun bind(contactInfo: ContactInfo, onContactChecked: OnContactChecked) {
            tvDisplayName.text = contactInfo.displayName
            tvPhoneNumber.text = contactInfo.phoneNumber
            cbChecked.setOnCheckedChangeListener { _, isChecked ->
                onContactChecked.onChecked(contactInfo, isChecked)
            }
        }
    }

    class OnContactChecked(private val checked: (ContactInfo, Boolean) -> Unit) {
        fun onChecked(contactInfo: ContactInfo, isChecked: Boolean) = checked(contactInfo, isChecked)
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