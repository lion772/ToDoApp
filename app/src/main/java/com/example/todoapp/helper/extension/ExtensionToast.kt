package williamlopes.project.rtcontrol.helper.extension

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.todoapp.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.dialog_progress.*

fun Context.toast(str:String){
    Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
}

fun Context.showMessage(text: String){
    val progressDialog = Dialog(this)
    progressDialog.setContentView(R.layout.dialog_progress)
    progressDialog.tv_progress_text.text = text
    progressDialog.show()
}

/*fun Context.showErrorSnackBar(message: String) {
    val snackBar =
        Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
    val snackBarView = snackBar.view
    snackBarView.setBackgroundColor(
        ContextCompat.getColor(
            this,
            R.color.snackbar_error_color
        )
    )
    snackBar.show()
}*/
