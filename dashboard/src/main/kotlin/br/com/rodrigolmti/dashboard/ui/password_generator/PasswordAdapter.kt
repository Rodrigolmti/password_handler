package br.com.rodrigolmti.dashboard.ui.password_generator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.rodrigolmti.dashboard.databinding.GeneratedPasswordRowBinding
import br.com.rodrigolmti.dashboard.domain.model.PasswordModel

internal class PasswordAdapter(
    private val password: List<PasswordModel>,
    val onCopyClick: (item: PasswordModel) -> Unit,
    val onSaveClick: (item: PasswordModel) -> Unit
) :
    RecyclerView.Adapter<PasswordAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: GeneratedPasswordRowBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        fun bindData(item: PasswordModel) {
            binding.password.text = item.password
            binding.colorIndicator.selectedPosition = item.score
            binding.tvCopy.setOnClickListener {
                onCopyClick(item)
            }
            binding.tvSave.setOnClickListener {
                onSaveClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GeneratedPasswordRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(password[position])
    }

    override fun getItemCount() = password.size
}