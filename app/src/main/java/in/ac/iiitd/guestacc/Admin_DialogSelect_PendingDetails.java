package in.ac.iiitd.guestacc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

;

/**
 * Created by rakesh on 22/4/18.
 */

public class Admin_DialogSelect_PendingDetails extends DialogFragment implements View.OnClickListener
{

    private View cardview ;

    Admin_DialogSelect_PendingDetails currentObject ;
    private DialogClickListener mDialogClickListener ;


    protected void setCardview(View cardview,Admin_DialogSelect_PendingDetails obj)
    {
        this.cardview = cardview ;
        this.currentObject = obj ;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.admin_dialog_layout_pending_details,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        RadioButton one = (RadioButton)v.findViewById(R.id.one_room) ;
        RadioButton two = (RadioButton)v.findViewById(R.id.two_room) ;

        one.setChecked(true);



        builder.setView(v)
                .setMessage("Allocate Room")
               /* .setPositiveButton("Agree",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                })

                .setNegativeButton("Disagree",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })

                        */;

        Button agreeButton = (Button)v.findViewById(R.id.agree);
        Button disagreeButton = (Button)v.findViewById(R.id.disagree);
        //v.setOnClickListener(this);


// listen to the agree button
        agreeButton.setOnClickListener(this) ;



// listen to the disagree button
        disagreeButton.setOnClickListener(this);



        return builder.create() ;
    }
    void setClickListener(DialogClickListener dialogClickListener)
    {
        this.mDialogClickListener = dialogClickListener ;
    }

    @Override
    public void onClick(View v)
    {

        mDialogClickListener.onDialogClick(cardview,"You Clicked Dialog Disgaree button");
        Admin_DialogSelect_PendingDetails.this.dismiss();
    }



// interface to communicate with main activity


    public interface DialogClickListener
    {
        void onDialogClick(View view, String type) ;
    }
}
