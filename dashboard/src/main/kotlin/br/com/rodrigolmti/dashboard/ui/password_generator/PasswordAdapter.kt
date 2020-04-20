package br.com.rodrigolmti.dashboard.ui.password_generator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.rodrigolmti.dashboard.R
import br.com.rodrigolmti.dashboard.domain.model.PasswordModel
import kotlinx.android.synthetic.main.generated_password_row.view.*

internal class PasswordAdapter(
    private val password: List<PasswordModel>,
    val onCopyClick: (item: PasswordModel) -> Unit,
    val onSaveClick: (item: PasswordModel) -> Unit
) :
    RecyclerView.Adapter<PasswordAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(item: PasswordModel) {
            itemView.password.text = item.password
            itemView.colorIndicator.selectedPosition = item.score
            itemView.tvCopy.setOnClickListener {
                onCopyClick(item)
            }
            itemView.tvSave.setOnClickListener {
                onSaveClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.generated_password_row,
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