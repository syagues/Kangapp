package projecte.kangapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import projecte.kangapp.R;
import projecte.kangapp.adapter.viewholder.RecyclerHeaderViewHolder;
import projecte.kangapp.adapter.viewholder.RecyclerItemViewHolder;

/**
 * Created by sergi on 23/5/15.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;
    private List<CardArticulo> mArticuloList;

    public RecyclerAdapter(List<CardArticulo> itemList) {
        mArticuloList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        if (viewType == TYPE_ITEM) {
            final View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
            return RecyclerItemViewHolder.newInstance(view);
        } else if (viewType == TYPE_HEADER) {
            final View view = LayoutInflater.from(context).inflate(R.layout.recycler_header, parent, false);
            return new RecyclerHeaderViewHolder(view);
        }
        throw new RuntimeException("There is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (!isPositionHeader(position)) {
            RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;
            //Bitmap itemImage = mArticuloList.get(position - 1).getArticuloImage();
            String pathImage = mArticuloList.get(position - 1).getPathArticuloImage();
            String itemName = mArticuloList.get(position - 1).getArticuloName();
            String itemType = mArticuloList.get(position - 1).getArticuloType();
            String userName = mArticuloList.get(position - 1).getUserName();
            String itemPrice = mArticuloList.get(position - 1).getArticuloPrice();
            String itemBeginEndDate = mArticuloList.get(position - 1).getArticuloBeginEndDate();
            String itemState = mArticuloList.get(position - 1).getArticuloState();
            holder.setItemParameters(pathImage, itemName, itemType, userName, itemPrice, itemBeginEndDate, itemState);
        }
    }

    public int getBasicItemCount() {
        return mArticuloList == null ? 0 : mArticuloList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }

        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return getBasicItemCount() + 1; // header
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }
}
