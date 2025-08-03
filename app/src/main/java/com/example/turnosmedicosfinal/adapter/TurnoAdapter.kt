import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.turnosmedicosfinal.R
import com.example.turnosmedicosfinal.models.Turno

class TurnoAdapter(private val listaTurnos: List<Turno>) : RecyclerView.Adapter<TurnoAdapter.TurnoViewHolder>() {

    inner class TurnoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val numeroTurno: TextView = view.findViewById(R.id.textNumeroTurno)
        val especialidad: TextView = view.findViewById(R.id.textEspecialidad)
        val medico: TextView = view.findViewById(R.id.textMedico)
        val fecha: TextView = view.findViewById(R.id.textFecha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TurnoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_turno, parent, false)
        return TurnoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TurnoViewHolder, position: Int) {
        val turno = listaTurnos[position]
        holder.numeroTurno.text = "Número Turno: ${position + 1}"
        holder.especialidad.text = "Especialidad: ${turno.especialidad}"
        holder.medico.text = "Médico: ${turno.medico}"
        holder.fecha.text = "Fecha de atención: ${turno.fecha} (${turno.horario})"
    }

    override fun getItemCount(): Int = listaTurnos.size
}
