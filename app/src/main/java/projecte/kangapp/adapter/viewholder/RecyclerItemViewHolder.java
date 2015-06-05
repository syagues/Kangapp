package projecte.kangapp.adapter.viewholder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import projecte.kangapp.R;
import projecte.kangapp.adapter.RoundImage;

/**
 * Created by sergi on 23/5/15.
 */
public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {


    private final ImageView mItemImageView;
    private final TextView mItemNameTextView;
    private final TextView mItemTypeTextView;
    private final TextView mUserNameTextView;
    private final TextView mItemPriceTextView;
    private final TextView mItemBeginEndDateTextView;
    private final TextView mItemStateTextView;

    public RecyclerItemViewHolder(final View parent, ImageView mItemImageView, TextView mItemNameTextView, TextView mItemTypeTextView, TextView mUserNameTextView, TextView mItemPriceTextView, TextView mItemBeginEndDateTextView, TextView mItemStateTextView) {
        super(parent);
        this.mItemImageView = mItemImageView;
        this.mItemNameTextView = mItemNameTextView;
        this.mItemTypeTextView = mItemTypeTextView;
        this.mUserNameTextView = mUserNameTextView;
        this.mItemPriceTextView = mItemPriceTextView;
        this.mItemBeginEndDateTextView = mItemBeginEndDateTextView;
        this.mItemStateTextView = mItemStateTextView;
    }

    public static RecyclerItemViewHolder newInstance(View parent) {
        ImageView itemImageView = (ImageView) parent.findViewById(R.id.imageView);
        TextView itemNameTextView = (TextView) parent.findViewById(R.id.tv_nombre);
        TextView itemTypeTextView = (TextView) parent.findViewById(R.id.tv_tipo);
        TextView userNameTextView = (TextView) parent.findViewById(R.id.tv_usuario);
        TextView itemPriceTextView = (TextView) parent.findViewById(R.id.tv_precio);
        TextView itemBeginEndDateTextView = (TextView) parent.findViewById(R.id.tv_fechas);
        TextView itemStateTextView = (TextView) parent.findViewById(R.id.tv_encurso);

        return new RecyclerItemViewHolder(parent, itemImageView, itemNameTextView, itemTypeTextView, userNameTextView, itemPriceTextView, itemBeginEndDateTextView, itemStateTextView);
    }

    public void setItemParameters(String pathImage, String itemName, String itemType, String userName, String itemPrice, String itemBeginEndDate, String itemState) {
        // Imatge
        if(pathImage != null) {
            LoadImageFromURL loadImage = new LoadImageFromURL();
            loadImage.execute(pathImage);
        }

        mItemNameTextView.setText(itemName);
        mItemTypeTextView.setText(itemType);
        mUserNameTextView.setText(userName);
        mItemPriceTextView.setText(itemPrice);
        mItemBeginEndDateTextView.setText(itemBeginEndDate);
        mItemStateTextView.setText(itemState);
    }

    public class LoadImageFromURL extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                InputStream is = url.openConnection().getInputStream();
                Bitmap bitMap = BitmapFactory.decodeStream(is);
                return bitMap;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            RoundImage roundedImage = new RoundImage(result);
            mItemImageView.setImageDrawable(roundedImage);
        }

    }

}
