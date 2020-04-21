package br.com.rodrigolmti.dashboard.ui.passwords

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.rodrigolmti.dashboard.R
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel
import kotlinx.android.synthetic.main.saved_password_row.view.*

internal class SavedPasswordsAdapter(
    private val password: List<SavedPasswordModel>,
    val onItemClick: (item: SavedPasswordModel) -> Unit,
    val onCopyLoginClick: (item: SavedPasswordModel) -> Unit,
    val onCopyPasswordClick: (item: SavedPasswordModel) -> Unit
) :
    RecyclerView.Adapter<SavedPasswordsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(item: SavedPasswordModel) {
            itemView.tvPassword.text = item.obfuscatedPassword
            itemView.tvPasswordLabel.text = item.label
            itemView.tvPasswordLogin.text = item.login ?: "-"
            itemView.setOnClickListener {
                onItemClick(item)
            }
            itemView.tvCopyLogin.setOnClickListener {
                onCopyLoginClick(item)
            }
            itemView.tvCopyPassword.setOnClickListener {
                onCopyPasswordClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.saved_password_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(password[position])
    }

    override fun getItemCount() = password.size
}