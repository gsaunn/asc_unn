package campusevents.michael.android.com.campusevents;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class MyDialog extends DialogFragment {
    

		public static MyDialog newInstance() {
			return new MyDialog();
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return new AlertDialog.Builder(getActivity(), android.R.style.Theme_Holo_Dialog)
			
					.setTitle("About Us")
					.setMessage("Developed by Michael. A Computer geek of the University of Nigeria.\n \n ")
					.setCancelable(true)
					
							.create();
		}
	}


