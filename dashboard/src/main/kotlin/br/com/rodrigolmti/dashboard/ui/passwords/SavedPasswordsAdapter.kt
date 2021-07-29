package br.com.rodrigolmti.dashboard.ui.passwords

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.rodrigolmti.dashboard.databinding.SavedPasswordRowBinding
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel

internal class SavedPasswordsAdapter(
    private val password: List<SavedPasswordModel>,
    val onItemClick: (item: SavedPasswordModel) -> Unit,
    val onCopyLoginClick: (item: SavedPasswordModel) -> Unit,
    val onCopyPasswordClick: (item: SavedPasswordModel) -> Unit
) :
    RecyclerView.Adapter<SavedPasswordsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: SavedPasswordRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: SavedPasswordModel) {
            binding.tvPassword.text = item.obfuscatedPassword
            binding.tvPasswordLabel.text = item.label
            binding.tvPasswordLogin.text = item.login ?: "-"
            binding.root.setOnClickListener {
                onItemClick(item)
            }
            binding.tvCopyLogin.setOnClickListener {
                onCopyLoginClick(item)
            }
            binding.tvCopyPassword.setOnClickListener {
                onCopyPasswordClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SavedPasswordRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(password[position])
    }

    override fun getItemCount() = password.size
}