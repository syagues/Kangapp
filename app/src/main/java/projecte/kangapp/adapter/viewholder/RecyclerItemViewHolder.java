package projecte.kangapp.adapter.viewholder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import projecte.kangapp.R;
import projecte.kangapp.adapter.RoundImage;

/**
 * Created by sergi on 23/5/15.
 */
public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView mItemTextView;

    public RecyclerItemViewHolder(final View parent, TextView itemTextView) {
        super(parent);
        mItemTextView = itemTextView;
    }

    public static RecyclerItemViewHolder newInstance(View parent) {
        TextView itemTextView = (TextView) parent.findViewById(R.id.tv_modelo);

        // Imatge
        ImageView imageView = (ImageView) parent.findViewById(R.id.imageView);
        Bitmap bm = BitmapFactory.decodeResource(parent.getResources(), R.drawable.item2);
        RoundImage roundedImage = new RoundImage(bm);
        if(imageView != null) {
            imageView.setImageDrawable(roundedImage);
            Log.i("Aplicacio", "imageview no null");
        } else {
            Log.i("Aplicacio", "imageview null");
        }

        return new RecyclerItemViewHolder(parent, itemTextView);
    }

    public void setItemText(CharSequence text) {
        mItemTextView.setText(text);
    }

}
