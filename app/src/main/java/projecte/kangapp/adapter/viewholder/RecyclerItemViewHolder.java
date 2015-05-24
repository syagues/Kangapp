package projecte.kangapp.adapter.viewholder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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

    private final ImageView mItemImageView;
    private final TextView mItemNameTextView;
    private final TextView mUserNameTextView;
    private final TextView mItemStateTextView;
    private final TextView mItemPriceTextView;
    private final TextView mItemBeginEndDateTextView;

    public RecyclerItemViewHolder(final View parent, ImageView mItemImageView, TextView mItemNameTextView, TextView mUserNameTextView, TextView mItemStateTextView, TextView mItemPriceTextView, TextView mItemBeginEndDateTextView) {
        super(parent);
        this.mItemImageView = mItemImageView;
        this.mItemNameTextView = mItemNameTextView;
        this.mUserNameTextView = mUserNameTextView;
        this.mItemStateTextView = mItemStateTextView;
        this.mItemPriceTextView = mItemPriceTextView;
        this.mItemBeginEndDateTextView = mItemBeginEndDateTextView;
    }

    public static RecyclerItemViewHolder newInstance(View parent) {
        ImageView itemImageView = (ImageView) parent.findViewById(R.id.imageView);
        TextView itemNameTextView = (TextView) parent.findViewById(R.id.tv_nombre);
        TextView userNameTextView = (TextView) parent.findViewById(R.id.tv_usuario);
        TextView itemStateTextView = (TextView) parent.findViewById(R.id.tv_encurso);
        TextView itemPriceTextView = (TextView) parent.findViewById(R.id.tv_precio);
        TextView itemBeginEndDateTextView = (TextView) parent.findViewById(R.id.tv_fechas);

        return new RecyclerItemViewHolder(parent, itemImageView, itemNameTextView, userNameTextView, itemStateTextView, itemPriceTextView, itemBeginEndDateTextView);
    }

    public void setItemParameters(View parent, int itemImageId, String itemName, String userName, String itemState, String itemPrice, String itemBeginEndDate) {
        // Imatge
        Bitmap bm = BitmapFactory.decodeResource(parent.getResources(), itemImageId);
        RoundImage roundedImage = new RoundImage(bm);
        mItemImageView.setImageDrawable(roundedImage);

        mItemNameTextView.setText(itemName);
        mUserNameTextView.setText(userName);
        mItemStateTextView.setText(itemState);
        mItemPriceTextView.setText(itemPrice);
        mItemBeginEndDateTextView.setText(itemBeginEndDate);
    }

}
