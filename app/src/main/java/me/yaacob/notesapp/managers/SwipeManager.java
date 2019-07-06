package me.yaacob.notesapp.managers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import me.yaacob.notesapp.R;
import me.yaacob.notesapp.activities.MainActivity;

public class SwipeManager {

    //initSwipeBehaviorWithDelete
    public void initSwipe(final MainActivity activity) {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT |  ItemTouchHelper.RIGHT,ItemTouchHelper.LEFT |  ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                Dialog.OnClickListener clickListener=new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case DialogInterface.BUTTON_POSITIVE:
                                activity.getNoteManager().delete(activity.getNoteManager().notes.get(viewHolder.getAdapterPosition()));
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                activity.getAdapter().notifyItemChanged(viewHolder.getAdapterPosition());
                                break;
                        }
                    }
                };
                AlertDialog.Builder b =new AlertDialog.Builder(activity);
                b.setMessage(R.string.rusure).setPositiveButton(R.string.yes,clickListener).setNegativeButton(R.string.no,clickListener);
                b.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        activity.getAdapter().notifyItemChanged(viewHolder.getAdapterPosition());
                    }
                });
                b.show();

            }
        }){};
        itemTouchHelper.attachToRecyclerView(activity.getRecyclerView());

    }

}
